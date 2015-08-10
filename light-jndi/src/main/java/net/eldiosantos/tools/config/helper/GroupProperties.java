package net.eldiosantos.tools.config.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by esjunior on 10/08/2015.
 */
public class GroupProperties {

    // Grouping objects properties
    public Map<String, Map<String, String>> group(final Map<String, String> propertiesMap) {
        final Map<String, Map<String, String>> groupedMaps = new HashMap<>();
        propertiesMap.entrySet().stream()
                .map(e->{
                    final List<String> result = new ArrayList<>();
                    final int lastDotIndex = e.getKey().lastIndexOf(".");
                    result.add(e.getKey().substring(0, lastDotIndex)); // path and object
                    result.add(e.getKey().substring(lastDotIndex + 1)); // property name
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

        return groupedMaps;
    }
}
