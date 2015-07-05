package net.eldiosantos.tools.context;

import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Eldius on 05/07/2015.
 */
public class CustomContextFactory implements InitialContextFactory {

    private static Map<String, Object> context;

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        if(context == null) {
            context = new HashMap<>();
        }
        return new CustomContext(this);
    }

    Object lookup(final String name) {
        return context.get(name);
    }

    public static Map<String, Object> getContext() {
        return context;
    }

    void bind(final String name, final Object object) throws NameAlreadyBoundException {
        if(context.containsKey(name)) {
            throw new NameAlreadyBoundException(String.format("Name %s already bound", name));
        }
        context.put(name, object);
    }
    void rebind(final String name, final Object object) throws NameAlreadyBoundException {
        context.put(name, object);
    }

    void unbind(final String name) {
        context.remove(name);
    }

    void rename(final String oldName, final String newName) {
        context.put(newName, context.remove(oldName));
    }
}
