package net.eldiosantos.tools.config.factory;

import net.eldiosantos.tools.config.factory.helper.GroupProperties;
import net.eldiosantos.tools.config.factory.helper.ObjectFactoryLoader;
import net.eldiosantos.tools.config.factory.impl.DatasourceObjectFactory;
import net.eldiosantos.tools.config.factory.impl.DefaultObjectFactory;
import net.eldiosantos.tools.config.factory.loader.PropertiesFileLoader;
import net.eldiosantos.tools.constants.PropertyKeys;
import net.eldiosantos.tools.context.ContextHandler;
import net.eldiosantos.tools.context.RootConfigPath;
import net.eldiosantos.tools.custom.CustomMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The main class of this project. It's responsible to
 * start and configure the context. It manages the calls to others
 * classes to setup the context.
 */
public class ContextBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<ObjectFactory>factoryList;

    public ContextHandler build() throws Exception {

        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("jndi.properties"));

        final ContextHandler contextHandler = new ContextHandler(new CustomMap());
        final Map<String, Map<String, String>> groupedMaps = new GroupProperties().group(
                loadPropertiesMapping(
                        properties
                        , new RootConfigPath(properties.getProperty(PropertyKeys.ROOT_PATH)).getFiles()
                ).entrySet()
        );

        loadFactoryList(properties.getProperty(PropertyKeys.CUSTOM_FACTORIES));
        for(Map.Entry<String, Map<String, String>>objProps: groupedMaps.entrySet()) {
            contextHandler.bind(objProps.getKey(), getFactory(objProps.getValue()).build(objProps.getValue()));
        }

        return contextHandler;
    }

    private Map<String, String> loadPropertiesMapping(Properties properties, File[] factoryFiles) throws IOException {
        logger.info(String.format("Loading %d files from %s", factoryFiles.length, properties.getProperty(PropertyKeys.ROOT_PATH)));
        final Map<String, String>configurationProperties = new HashMap<>();
        final PropertiesFileLoader propertiesFileLoader = new PropertiesFileLoader();
        for(File cfg: factoryFiles) {
            logger.debug(String.format("Loading file %s", cfg.getName()));
            configurationProperties.putAll(propertiesFileLoader.loadFile(cfg));
        }
        return configurationProperties;
    }

    private void loadFactoryList(final String classNames) throws Exception {
        this.factoryList = new ObjectFactoryLoader().loadFactories(classNames);
        this.factoryList.add(new DatasourceObjectFactory());
        this.factoryList.add(new DefaultObjectFactory());
    }

    private ObjectFactory getFactory(final Map<String, String> objectProperties) {
        for(ObjectFactory factory: this.factoryList) {
            if(factory.canBuild(objectProperties.get("class"))) {
                return factory;
            }
        }
        return null;
    }
}
