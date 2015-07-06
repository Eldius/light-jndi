package net.eldiosantos.tools.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NameAlreadyBoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eldius on 05/07/2015.
 */
public class ContextHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Map<Object, Object> context = new HashMap<>();

    public Object lookup(final String name) {
        return lookup(name, context);
    }

    private Object lookup(final String name, final Map<?,?> map) {
        final String[] split = name.split("/");
        if(split.length > 1) {
            return lookup(name.substring(split[0].length()), (Map<?, ?>) map.get(split[0]));
        } else {
            return map.get(name);
        }
    }

    public Object bind(final String name, final Object value) throws NameAlreadyBoundException {
        return bind(name, context, value, false);
    }

    public Object rebind(final String name, final Object value) throws NameAlreadyBoundException {
        return bind(name, context, value, true);
    }

    private Object bind(final String name, final Map<Object,Object> map, final Object value, final Boolean replace) throws NameAlreadyBoundException {
        logger.debug(String.format("binding name '%s'", name));
        final String[] split = name.split("/");
        if(split.length > 1) {
            Object subContext = map.get(split[0]);
            if(subContext == null) {
                subContext = new HashMap<String, Object>();
                map.put(split[0], subContext);
            }
            bind(name.substring(split[0].length()), (Map<Object,Object>) subContext, value, replace);
        } else {
            if((map.get(name) != null) && (!replace)) {
                throw new NameAlreadyBoundException(String.format("Name %s already bound.", name));
            } else {
                return map.put(name, value);
            }
        }
        return null;
    }

    public Object unbind(final String name) {
        return unbind(name, context);
    }

    private Object unbind(final String name, final Map<Object,Object> map) {
        final String[] split = name.split("/");
        if(split.length > 1) {
            Object subContext = map.get(split[0]);
            if(subContext == null) {
                subContext = new HashMap<String, Object>();
                map.put(split[0], subContext);
            }
            unbind(name.substring(split[0].length()), (Map<Object, Object>) subContext);
        } else {
            return map.remove(name);
        }
        return null;
    }

    public void rename(final String oldName, final String newName) throws NameAlreadyBoundException {
        bind(newName, unbind(oldName));
    }
}
