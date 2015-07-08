package net.eldiosantos.tools.config.factory.helper;

import net.eldiosantos.tools.config.factory.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The classe who will criate the factory objects.
 */
public class ObjectFactoryLoader {
    public List<ObjectFactory>loadFactories(final String classNames) throws Exception {
        List<ObjectFactory>result = new ArrayList<>();
        if(classNames != null) {
            for(final String className: classNames.split(",")) {
                result.add((ObjectFactory) Class.forName(className.trim()).newInstance());
            }
        }

        return result;
    }
}
