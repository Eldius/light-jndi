package net.eldiosantos.tools.config.factory.helper;

import net.eldiosantos.tools.config.factory.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esjunior on 08/07/2015.
 */
public class ObjectFactoryLoader {
    public List<ObjectFactory>loadFactories(final String classNames) throws Exception {
        List<ObjectFactory>result = new ArrayList<>();
        if(classNames != null) {
            for(final String className: classNames.split(",")) {
                result.add((ObjectFactory) Class.forName(className).newInstance());
            }
        }

        return result;
    }
}
