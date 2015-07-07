package net.eldiosantos.tools.config.factory.helper;

import java.util.*;

/**
 * Created by Eldius on 05/07/2015.
 */
public class OrderProperties {
    public List<Map.Entry<Object, Object>>order(final Set<Map.Entry<Object, Object>>props) {
        final ArrayList<Map.Entry<Object, Object>> entries = new ArrayList<>(props);
        Collections.sort(entries, new Comparator<Map.Entry<Object, Object>>() {
            @Override
            public int compare(Map.Entry<Object, Object> o1, Map.Entry<Object, Object> o2) {
                return o1.getKey().toString().compareTo(o2.getKey().toString());
            }
        });

        return entries;
    }
}
