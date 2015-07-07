package net.eldiosantos.tools.embeddedtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws Exception {
        logger.debug(new File(".").getAbsolutePath());
        logger.debug("Testing embedded config files.");
        Context ctx = new InitialContext();
        DataSource result = (DataSource) ctx.lookup("jdbc:/testDatasource");
        logger.debug(String.format("lookup result: %s", result.getConnection()));
        logger.debug(String.format("lookup result: %s", result));
        ctx.close();
    }
}
