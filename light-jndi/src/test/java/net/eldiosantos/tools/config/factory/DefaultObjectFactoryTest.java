package net.eldiosantos.tools.config.factory;

import junit.framework.TestCase;
import net.eldiosantos.tools.config.factory.impl.DefaultObjectFactory;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by esjunior on 06/07/2015.
 */
public class DefaultObjectFactoryTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testBuild() throws Exception {
        Map<String, String>props = new HashMap<>();
        props.put("class", "org.h2.jdbcx.JdbcDataSource");
        props.put("url", "jdbc:h2:./test");
        props.put("user", "user");
        props.put("password", "password");

        final Object buildedObject = new DefaultObjectFactory().build(props);

        assertTrue("Created a datasource, right?", buildedObject instanceof DataSource);
        assertTrue("Created a JdbcDataSource, right?", buildedObject instanceof JdbcDataSource);
    }
}