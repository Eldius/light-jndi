package net.eldiosantos.tools.context;

import net.eldiosantos.tools.custom.CustomMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import java.util.Map;

/**
 * The class who is responsible to find things into the context.
 */
public class ContextHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CustomMap context;

    public ContextHandler(CustomMap context) {
        this.context = context;
    }

    public Object lookup(final String name) throws NameNotFoundException {
        return lookup(name, context);
    }

    private Object lookup(final String name, final Map<?,?> map) throws NameNotFoundException {
        final String[] split = name.split("/");
        if(split.length > 1) {
            return lookup(name.substring(split[0].length() + 1), (Map<?, ?>) map.get(split[0]));
        } else {
            final Object result = map.get(name);
            if (result instanceof CustomMap) {
                return new CustomContext(new ContextHandler((CustomMap)result));
            }
            if(result == null) {
                throw new NameNotFoundException(String.format("Name '%s' not found", name));
            }
            return result;
        }
    }

    public Object bind(final String name, final Object value) throws NameAlreadyBoundException {
        return bind(name, context, value, false);
    }

    public Object rebind(final String name, final Object value) throws NameAlreadyBoundException {
        return bind(name, context, value, true);
    }

    private Object bind(final String name, final CustomMap map, final Object value, final Boolean replace) throws NameAlreadyBoundException {
        logger.debug(String.format("binding name '%s'", name));
        final String[] split = name.split("/");
        if(split.length > 1) {
            Object subContext = map.get(split[0]);
            if(subContext == null) {
                subContext = new CustomMap();
                map.put(split[0], subContext);
            }
            bind(name.substring(split[0].length() + 1), (CustomMap) subContext, value, replace);
        } else {
            if((map.get(name) != null) && (!replace)) {
                throw new NameAlreadyBoundException(String.format("Name %s already bound.", name));
            } else {
                return map.put(name, value);
            }
        }
        return null;
    }

    public Object unbind(final String name) throws NameNotFoundException {
        return unbind(name, context);
    }

    private Object unbind(final String name, final CustomMap map) throws NameNotFoundException {
        final String[] split = name.split("/");
        if(split.length > 1) {
            Object subContext = map.get(split[0]);
            if(subContext == null) {
                subContext = new CustomMap();
                map.put(split[0], subContext);
            }
            unbind(name.substring(split[0].length() + 1), (CustomMap) subContext);
        } else {
            return map.remove(name);
        }
        throw new NameNotFoundException(String.format("Name '%s' doesn't exists", name));
    }

    public void rename(final String oldName, final String newName) throws NamingException {
        bind(newName, unbind(oldName));
    }
}
