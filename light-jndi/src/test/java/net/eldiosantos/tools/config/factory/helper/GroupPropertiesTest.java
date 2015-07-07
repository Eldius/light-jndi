package net.eldiosantos.tools.config.factory.helper;

import net.eldiosantos.tools.config.factory.loader.PropertiesFileLoader;
import net.eldiosantos.tools.context.RootConfigPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by esjunior on 06/07/2015.
 */
public class GroupPropertiesTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGroup() throws Exception {
        final Map<String, String>props = new HashMap<>();
        props.put("jdbc:/testDatasource.class", "org.h2.jdbcx.JdbcDataSource");
        props.put("jdbc:/testDatasource.url", "jdbc:h2:./test");
        props.put("jdbc:/testDatasource.user", "user");
        props.put("jdbc:/testDatasource.password", "pass");

        final Map<String, Map<String, String>> group = new GroupProperties().group(props.entrySet());
        assertEquals("Just one object in the group", 1, group.size());
        assertNotNull("Do we have an object here?", group.get("jdbc:/testDatasource"));
    }

    @Test
    public void testGroupMultipleObjects() throws Exception {
        final Map<String, String>props = new HashMap<>();
        props.put("jdbc:/testDatasource.class", "org.h2.jdbcx.JdbcDataSource");
        props.put("jdbc:/testDatasource.url", "jdbc:h2:./test");
        props.put("jdbc:/testDatasource.user", "user");
        props.put("jdbc:/testDatasource.password", "pass");

        props.put("jdbc:/testDatasource1.class", "org.h2.jdbcx.JdbcDataSource");
        props.put("jdbc:/testDatasource1.url", "jdbc:h2:./test");
        props.put("jdbc:/testDatasource1.user", "user");
        props.put("jdbc:/testDatasource1.password", "pass");

        final Map<String, Map<String, String>> group = new GroupProperties().group(props.entrySet());
        assertEquals("Just one object in the group", 2, group.size());
        assertNotNull("Do we have an object here?", group.get("jdbc:/testDatasource"));
        assertNotNull("Do we have an object here?", group.get("jdbc:/testDatasource1"));

        assertEquals("All properties are bound", 4, group.get("jdbc:/testDatasource").size());
        assertEquals("All properties are bound", 4, group.get("jdbc:/testDatasource1").size());
    }

    @Test
    public void testGroupMultipleObjects2() throws Exception {
            final Map<String, String> props = new HashMap<>();
        for (final File file:new RootConfigPath("src/test/resources/jndi").getFiles()) {
            props.putAll(new PropertiesFileLoader().loadFile(file));
        }
        final Map<String, Map<String, String>> group = new GroupProperties().group(props.entrySet());
        logger.debug("group:\n" + group);

        assertEquals("We have 3 objects here", 3, group.size());
        assertNotNull("Do we have an object here?", group.get("jdbc:/testDatasource"));
        assertNotNull("Do we have an object here?", group.get("jdbc:/testDatasource1"));

        assertEquals("All properties are bound", 5, group.get("jdbc:/testDatasource").size());
        assertEquals("All properties are bound", 5, group.get("jdbc:/testDatasource1").size());
        assertEquals("All properties are bound", 5, group.get("jdbc:/java/comp/env/myDatasource").size());

    }
}
