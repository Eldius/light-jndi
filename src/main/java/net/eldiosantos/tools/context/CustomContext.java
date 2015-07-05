package net.eldiosantos.tools.context;

import net.eldiosantos.tools.naming.CustomNamingEnumeration;

import javax.naming.*;
import java.util.Hashtable;

/**
 * Created by Eldius on 05/07/2015.
 */
public class CustomContext implements Context {

    private final CustomContextFactory factory;

    public CustomContext(CustomContextFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object lookup(Name name) throws NamingException {
        return factory.lookup(name.toString());
    }

    @Override
    public Object lookup(String name) throws NamingException {
        return factory.lookup(name);
    }

    @Override
    public void bind(Name name, Object obj) throws NamingException {
        factory.bind(name.toString(), obj);
    }

    @Override
    public void bind(String name, Object obj) throws NamingException {
        factory.bind(name, obj);
    }

    @Override
    public void rebind(Name name, Object obj) throws NamingException {
        factory.bind(name.toString(), obj);
    }

    @Override
    public void rebind(String name, Object obj) throws NamingException {
        factory.rebind(name, obj);
    }

    @Override
    public void unbind(Name name) throws NamingException {
        factory.unbind(name.toString());
    }

    @Override
    public void unbind(String name) throws NamingException {
        factory.unbind(name);
    }

    @Override
    public void rename(Name oldName, Name newName) throws NamingException {
        factory.rename(oldName.toString(), newName.toString());
    }

    @Override
    public void rename(String oldName, String newName) throws NamingException {
        factory.rename(oldName, newName);
    }

    @Override
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return new CustomNamingEnumeration(factory.getContext());
    }

    @Override
    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        return null;
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {

    }

    @Override
    public void destroySubcontext(String name) throws NamingException {

    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        return null;
    }

    @Override
    public Context createSubcontext(String name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(String name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(String name) throws NamingException {
        return null;
    }

    @Override
    public Name composeName(Name name, Name prefix) throws NamingException {
        return null;
    }

    @Override
    public String composeName(String name, String prefix) throws NamingException {
        return null;
    }

    @Override
    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        return null;
    }

    @Override
    public Object removeFromEnvironment(String propName) throws NamingException {
        return null;
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return null;
    }

    @Override
    public void close() throws NamingException {

    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return null;
    }
}
