package net.eldiosantos.tools.config.factory.helper;

import net.eldiosantos.tools.config.factory.ObjectFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The classe who will criate the factory objects.
 */
public class ObjectFactoryLoader {
    public List<ObjectFactory>loadFactories(final String classNames) throws Exception {
        return Arrays.asList(classNames.split(",")).parallelStream()
                .map(String::trim)
                .filter((String c)-> c.length() > 0)
                .map((String className) -> {
                    try {
                        return (ObjectFactory) Class.forName(className.trim()).newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
