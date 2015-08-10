package net.eldiosantos.jndi.customfactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import static org.junit.Assert.*;

/**
 * Created by esjunior on 10/08/2015.
 */
public class CustomFactoryAppTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp() throws Exception {

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
        ctx.close();
    }
}