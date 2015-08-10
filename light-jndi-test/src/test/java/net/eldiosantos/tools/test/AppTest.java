package net.eldiosantos.tools.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by esjunior on 10/08/2015.
 */
public class AppTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp() throws Exception {
        logger.info(String.format("rootpath: %s", new File(".").getAbsolutePath()));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testMain() throws Exception {
        logger.info("Testing config.");
        Context ctx = new InitialContext();
        DataSource result = (DataSource) ctx.lookup("jdbc:/testDatasource");
        assertNotNull("We have a datasource", result);
        logger.info(String.format("lookup result: %s", result.getConnection()));
        logger.info(String.format("lookup result: %s", result));
        ctx.close();
    }
}
