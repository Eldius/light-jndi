package net.eldiosantos.tools;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NamingException {
        System.out.println( "Hello World!" );
        Context ctx = new InitialContext();
        Object result = ctx.lookup("jndiName");
        System.out.println(String.format("lookup result: %s", result));
        ctx.close();
    }
}
