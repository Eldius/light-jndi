package net.eldiosantos.tools.config.factory;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * Created by Eldius on 05/07/2015.
 */
public class ObjectFactory {

    public Object build(final Map<String, String>properties) throws Exception {
        final Class<?> clazz = Class.forName(properties.get("class"));
        final Object result = clazz.newInstance();

        for(final String key:properties.keySet()) {
            if(!key.equals("class")) {
                final char firstChar = key.charAt(1);
                final Method method = clazz.getMethod("set" + key.replace(firstChar, Character.toUpperCase(firstChar)));
                final TypeVariable<Method>[] typeParameters = method.getTypeParameters();

                if(typeParameters[0].getClass().equals(Integer.class)) {
                    method.invoke(result, Integer.valueOf(properties.get(key)));
                } else if(typeParameters[0].getClass().equals(String.class)) {
                    method.invoke(result, key);
                }
            }
        }

        return result;
    }
}
