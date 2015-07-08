package net.eldiosantos.jndi.customfactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Testing Heroku config." );
        Context ctx = new InitialContext();
        DataSource result = (DataSource) ctx.lookup("jdbc:/testDatasource");
        System.out.println(String.format("lookup result: %s", result));
        ctx.close();

    }
}
