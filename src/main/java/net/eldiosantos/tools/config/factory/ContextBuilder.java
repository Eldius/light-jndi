package net.eldiosantos.tools.config.factory;

import net.eldiosantos.tools.config.factory.helper.GroupProperties;
import net.eldiosantos.tools.config.factory.helper.OrderProperties;
import net.eldiosantos.tools.config.factory.loader.PropertiesFileLoader;
import net.eldiosantos.tools.constants.PropertyKeys;
import net.eldiosantos.tools.context.ContextHandler;
import net.eldiosantos.tools.context.RootConfigPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * Created by Eldius on 05/07/2015.
 */
public class ContextBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ContextHandler build() throws Exception {

        final Map environment = System.getenv();
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("jndi.properties"));

        System.getProperties().putAll(properties);
        final List<Map.Entry<Object, Object>> orderedEntries = new OrderProperties().order(properties.entrySet());

        final File[] factoryFiles = new RootConfigPath(properties.getProperty(PropertyKeys.ROOT_PATH)).getFiles();

        logger.info(String.format("Loading %d files from %s", factoryFiles.length, properties.getProperty(PropertyKeys.ROOT_PATH)));
        final Map<String, String>configurationProperties = new HashMap<>();
        final PropertiesFileLoader propertiesFileLoader = new PropertiesFileLoader();
        for(File cfg: factoryFiles) {
            logger.debug(String.format("Loading file %s", cfg.getName()));
            configurationProperties.putAll(propertiesFileLoader.loadFile(cfg));
        }


        logger.debug("Properties:");
        for (Map.Entry<String, String>entry:configurationProperties.entrySet()) {
            logger.debug(String.format("Property: '%s' => '%s'", entry.getKey(), entry.getValue()));
        }

        final ContextHandler contextHandler = new ContextHandler();
        final Map<String, Map<String, String>> groupedMaps = new GroupProperties().group(configurationProperties.entrySet());
        final ObjectFactory objectFactory = new ObjectFactory();
        logger.debug("grouped props: \n" + groupedMaps.toString());
        for(Map.Entry<String, Map<String, String>>objProps: groupedMaps.entrySet()) {
            //contextHandler.bind(objProps.getKey(), objectFactory.build(objProps.getValue()));
        }

        return contextHandler;
    }
}
