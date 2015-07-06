package net.eldiosantos.tools.config.factory.loader;

import java.util.*;

/**
 * Created by Eldius on 05/07/2015.
 */
public class PorpertiesOrderer {

    public List<Map.Entry<String, String>>order(final Map<String, String> map) {
        final List<Map.Entry<String, String>>result = new ArrayList<>(map.entrySet());

        Collections.sort(result, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        return result;
    }
}
