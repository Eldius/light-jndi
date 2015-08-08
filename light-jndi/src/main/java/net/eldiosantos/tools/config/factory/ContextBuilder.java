package net.eldiosantos.tools.config.factory;

import net.eldiosantos.tools.config.factory.helper.ObjectFactoryLoader;
import net.eldiosantos.tools.config.factory.impl.DatasourceObjectFactory;
import net.eldiosantos.tools.config.factory.impl.DefaultObjectFactory;
import net.eldiosantos.tools.constants.PropertyKeys;
import net.eldiosantos.tools.context.ContextHandler;
import net.eldiosantos.tools.context.RootConfigPath;
import net.eldiosantos.tools.custom.CustomMap;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The main class of this project. It's responsible to
 * start and configure the context. It manages the calls to others
 * classes to setup the context.
 */
public class ContextBuilder {

    private List<ObjectFactory>factoryList;

    public ContextHandler build() throws Exception {

        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("jndi.properties"));

        final ContextHandler contextHandler = new ContextHandler(new CustomMap());

        final Map<String, String>propertiesMap = Arrays.asList(
                new RootConfigPath(properties.getProperty(PropertyKeys.ROOT_PATH)).getFiles()
        ).parallelStream()
                .flatMap(f -> {
                    try {
                        return new BufferedReader(new FileReader(f)).lines();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(l-> !l.isEmpty())
                .map(l->Arrays.asList(l.split("=")))
                .collect(Collectors.toConcurrentMap((List<String> l) -> l.get(0).trim(), (List<String> l) -> l.get(1).trim()));

        final Map<String, Map<String, String>> groupedMaps = new HashMap<>();

        propertiesMap.entrySet().stream()
                .map(e->{
                    final List<String>result = new ArrayList<>();
                    final int lastDotIndex = e.getKey().lastIndexOf(".");
                    result.add(e.getKey().substring(0, lastDotIndex)); // path and object
                    result.add(e.getKey().substring(lastDotIndex)); // property name
                    result.add(e.getValue()); // property value

                    return result;
                }).forEachOrdered(l -> {
            final String objKey = l.get(0);
            Map<String, String> tmpMap = groupedMaps.get(objKey);
            if (tmpMap == null) {
                tmpMap = new HashMap<>();
            }
            tmpMap.put(l.get(1), l.get(2));
            groupedMaps.put(objKey, tmpMap);
        });

        factoryList = loadFactoryList(properties.getProperty(PropertyKeys.CUSTOM_FACTORIES));
        groupedMaps.entrySet()
                .parallelStream()
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
