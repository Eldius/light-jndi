package net.eldiosantos.tools.config.exception;

import javax.naming.NamingException;

/**
 * Created by Eldius on 05/07/2015.
 */
public class BuilderNamingException extends NamingException {

    public BuilderNamingException(String explanation) {
        super(explanation);
    }

    public BuilderNamingException(String explanation, Throwable t) {
        super(explanation);
        super.setRootCause(t);
    }
}
