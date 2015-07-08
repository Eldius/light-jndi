package net.eldiosantos.tools.context;

import net.eldiosantos.tools.config.exception.BuilderNamingException;
import net.eldiosantos.tools.config.factory.ContextBuilder;

import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;
import java.util.Map;

/**
 * The context factory implementation. It needs to be set as the
 * default context factory to work.
 */
public class CustomContextFactory implements InitialContextFactory {

    private static ContextHandler context;

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        if(context == null) {
            try {
                context = new ContextBuilder().build();
            } catch (Exception e) {
                throw new BuilderNamingException("Error building context.", e);
            }
        }
        return new CustomContext(context);
    }
}
