package net.eldiosantos.tools.config.factory;

import java.util.Map;

/**
 * Interface used by the factory classes, classes who will be responsible
 * to instantiate and configure the objects.
 */
public interface ObjectFactory {
    /**
     * It can handle the criation of this class?
     * @param className
     * @return
     */
    public Boolean canBuild(String className);

    /**
     * Creates the object from it's properties map.
     * @param objectProperties
     * @return
     * @throws Exception
     */
    public Object build(Map<String, String>objectProperties) throws Exception;
}
