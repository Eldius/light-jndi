package net.eldiosantos.tools.config.factory.impl;

import net.eldiosantos.tools.config.factory.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * The default factory for the objects who will be inserted in
 * the context.
 */
public class DefaultObjectFactory implements ObjectFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Boolean canBuild(String className) {
        return Boolean.TRUE;
    }

    public Object build(final Map<String, String>properties) throws Exception {
        final Class<?> clazz = Class.forName(properties.get("class"));
        logger.info(String.format("Classe to create: '%s'", clazz.getCanonicalName()));
        if(clazz.getCanonicalName().equals("javax.sql.DataSource")) {
            return new DatasourceObjectFactory().build(properties);
        }

        final Object result = clazz.newInstance();
        final Map<String, Method>methodTable = new HashMap<>();
        for(Method m: clazz.getDeclaredMethods()) {
            if(m.getName().startsWith("set")) {
                methodTable.put(m.getName().toLowerCase(), m);
            }
        }

        for(final String key:properties.keySet()) {
                if (!key.equals("class")) {
                    final Method method = methodTable.get("set" + key);
                    final Class<?>[] parameterTypes = method.getParameterTypes();

                    try {
                        if (parameterTypes[0].equals(Integer.class)) {
                            method.invoke(result, Integer.valueOf(properties.get(key)));
                        } else if (parameterTypes[0].equals(String.class)) {
                            method.invoke(result, key);
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(String.format("Error trying to set property '%s' whith value '%s' using the method %s", key, properties.get(key), method.toGenericString()), e);
                    }
                }
        }
        return result;
    }
}
