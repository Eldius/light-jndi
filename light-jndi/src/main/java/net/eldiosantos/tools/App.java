package net.eldiosantos.tools;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println(new File(".").getAbsolutePath());
        System.out.println( "Hello World!" );
        Context ctx = new InitialContext();
        DataSource result = (DataSource) ctx.lookup("jdbc:/testDatasource");
        System.out.println(String.format("lookup result: %s", result.getConnection().getClientInfo()));
        System.out.println(String.format("lookup result: %s", result));
        ctx.close();
    }
}
