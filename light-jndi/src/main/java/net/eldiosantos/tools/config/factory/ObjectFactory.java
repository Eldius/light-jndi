package net.eldiosantos.tools.config.factory;

import java.util.Map;

/**
 * Created by esjunior on 08/07/2015.
 */
public interface ObjectFactory {
    public Boolean canBuild(String className);
    public Object build(Map<String, String>objectProperties) throws Exception;
}
