package net.eldiosantos.tools.config.helper;

import net.eldiosantos.tools.constants.PropertyKeys;
import net.eldiosantos.tools.context.RootConfigPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by esjunior on 10/08/2015.
 */
public class PropertiesLoader {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // Load JNDI objects properties
    public Map<String, String> load(final Properties properties) throws Exception {

        return new RootConfigPath(properties.getProperty(PropertyKeys.ROOT_PATH))
                .getFiles()
                .parallelStream()
                .flatMap(f -> {
                    try {
                        return new BufferedReader(new FileReader(f)).lines();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(l -> !l.isEmpty())
                .filter(l-> !l.trim().startsWith("#"))
                .peek(l -> logger.debug(String.format("Line: %s", l)))
                .map(l -> Arrays.asList(l.split("=")))
                .peek(l -> logger.debug(String.format("list: %s", l)))
                .collect(Collectors.toConcurrentMap((List<String> l) -> l.get(0).trim(), (List<String> l) -> l.get(1).trim()));
    }
}
