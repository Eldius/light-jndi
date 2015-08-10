package net.eldiosantos.tools.config.factory;

import net.eldiosantos.tools.config.factory.helper.ObjectFactoryLoader;
import net.eldiosantos.tools.config.factory.impl.DatasourceObjectFactory;
import net.eldiosantos.tools.config.factory.impl.DefaultObjectFactory;
import net.eldiosantos.tools.config.helper.GroupProperties;
import net.eldiosantos.tools.config.helper.PropertiesLoader;
import net.eldiosantos.tools.constants.PropertyKeys;
import net.eldiosantos.tools.context.ContextHandler;
import net.eldiosantos.tools.context.RootConfigPath;
import net.eldiosantos.tools.custom.CustomMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The main class of this project. It's responsible to
 * start and configure the context. It manages the calls to others
 * classes to setup the context.
 */
public class ContextBuilder {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<ObjectFactory>factoryList;

    public ContextHandler build() throws Exception {

        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("jndi.properties"));

        final ContextHandler contextHandler = new ContextHandler(new CustomMap());

        // Creating objects
        factoryList = loadFactoryList(properties.getProperty(PropertyKeys.CUSTOM_FACTORIES, ""));
        new GroupProperties().group(new PropertiesLoader().load(properties)).entrySet()
                .stream()
                .peek(m->logger.debug(String.format("object: %s", m)))
                .forEach(objProps -> {
                    try {
                        contextHandler.bind(objProps.getKey(), getFactory(objProps.getValue()).build(objProps.getValue()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        return contextHandler;
    }

    private List<ObjectFactory> loadFactoryList(final String classNames) throws Exception {
        List<ObjectFactory> factoryList = new ObjectFactoryLoader().loadFactories(classNames);
        factoryList.add(new DatasourceObjectFactory());
        factoryList.add(new DefaultObjectFactory());
        return factoryList;
    }

    private ObjectFactory getFactory(final Map<String, String> objectProperties) {
        return this.factoryList.stream()
                .findFirst()
                .filter(factory->factory.canBuild(objectProperties.get("class")))
                .get();
    }
}
