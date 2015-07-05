package net.eldiosantos.tools.config.factory;

import net.eldiosantos.tools.config.factory.helper.OrderProperties;
import net.eldiosantos.tools.constants.PropertyKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Eldius on 05/07/2015.
 */
public class ContextBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Map<String, Object>build() throws Exception {

        final Map environment = System.getenv();
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("jndi.properties"));

        System.getProperties().putAll(properties);
        final List<Map.Entry<Object, Object>> orderedEntries = new OrderProperties().order(properties.entrySet());

        logger.info(String.format("Context config files root: '%s'", System.getenv(PropertyKeys.ROOT_PATH)));
        logger.info(String.format("Context config files root: '%s'", System.getProperty(PropertyKeys.ROOT_PATH)));

        return new HashMap<>();
    }
}
