package net.eldiosantos.tools.test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Testing config." );
        Context ctx = new InitialContext();
        DataSource result = (DataSource) ctx.lookup("jdbc:/testDatasource");
        System.out.println(String.format("lookup result: %s", result.getConnection()));
        System.out.println(String.format("lookup result: %s", result));
        ctx.close();
    }
}
