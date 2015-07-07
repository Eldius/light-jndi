package net.eldiosantos.tools.config.factory.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Eldius on 05/07/2015.
 */
public class GroupProperties {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Map<String, Map<String, String>>group(final Set<Map.Entry<String, String>> props) {

        final Map<String, Map<String, String>>map = new HashMap<>();

        // TODO Improve this code
        for(Map.Entry<String, String> entry: props) {
            final String[] tmpPath = entry.getKey().split("/");
            final String[] tmpObjectProps = tmpPath[tmpPath.length - 1].split("\\.");
            final String propName = tmpObjectProps[1];
            final String objName = tmpObjectProps[0];
            final String path = entry.getKey().substring(0, entry.getKey().length() - (tmpPath[tmpPath.length - 1].length() + 1));
            final String objectIdentification = path + "/" + objName;

            Map<String, String> objectProperties = map.get(objectIdentification);
            if(objectProperties == null) {
                objectProperties = new HashMap<>();
                map.put(objectIdentification, objectProperties);
            }
            objectProperties.put(propName, entry.getValue());
        }

        return map;
    }
}
