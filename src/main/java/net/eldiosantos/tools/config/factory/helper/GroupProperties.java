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

        String lastPath = "";
        final Map<String, Map<String, String>>map = new HashMap<>();

        // TODO Improve this code
        for(Map.Entry<String, String> entry: props) {
            logger.debug(String.format("Parsing property key '%s' with value '%s'", entry.getKey(), entry.getValue()));
            final String key = entry.getKey().toString();
            final String[] propPathSplit = key.split("/");
            final String objectPropertyName = propPathSplit[propPathSplit.length - 1];

            logger.debug(String.format("objectPropertyName: %s", objectPropertyName));
            final String[] objectPropertyNameSplit = objectPropertyName.split("\\.");
            logger.debug(String.format("objectPropertyNameSplit length: %d", objectPropertyNameSplit.length));
            final String objectName = objectPropertyNameSplit[0];
            final String propertyName = objectPropertyNameSplit[1];

            final String path = key.replace("/" + propertyName, "");
            final String objectIdentification = path + objectName;
            Map<String, String> aux = map.get(objectIdentification);
            if(aux == null) {
                aux = new HashMap<>();
                map.put(objectIdentification, aux);
            }
            aux.put(propertyName, entry.getValue());
        }

        return map;
    }
}
