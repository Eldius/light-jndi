package net.eldiosantos.tools.naming;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Eldius on 05/07/2015.
 */
public class CustomNamingEnumeration implements NamingEnumeration {

    private final Iterator iterator;

    public CustomNamingEnumeration(final Map context) {
        iterator = context.keySet().iterator();
    }

    public CustomNamingEnumeration(Iterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public Object next() throws NamingException {
        return iterator.next();
    }

    @Override
    public boolean hasMore() throws NamingException {
        return iterator.hasNext();
    }

    @Override
    public void close() throws NamingException {

    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public Object nextElement() {
        return iterator.next();
    }
}
