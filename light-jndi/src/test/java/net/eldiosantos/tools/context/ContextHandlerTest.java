package net.eldiosantos.tools.context;

import net.eldiosantos.tools.config.factory.ObjectFactory;
import net.eldiosantos.tools.custom.CustomMap;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by esjunior on 06/07/2015.
 */
public class ContextHandlerTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBindAndLookup() throws Exception {
        Map<String, String> props = new HashMap<>();
        props.put("class", "org.h2.jdbcx.JdbcDataSource");
        props.put("url", "jdbc:h2:./test");
        props.put("user", "user");
        props.put("password", "password");

        final Object buildedObject = new ObjectFactory().build(props);

        ContextHandler contextHandler = new ContextHandler(new CustomMap());
        String name = "jdbc:/java/comp/env/myDatasource";
        contextHandler.bind(name, buildedObject);

        assertEquals("We get the same object?", buildedObject, contextHandler.lookup(name));
    }

    @Test(expected = NameAlreadyBoundException.class)
    public void testBindAlreadyAddedName() throws Exception {
        Map<String, String> props = new HashMap<>();
        props.put("class", "org.h2.jdbcx.JdbcDataSource");
        props.put("url", "jdbc:h2:./test");
        props.put("user", "user");
        props.put("password", "password");

        final Object buildedObject = new ObjectFactory().build(props);

        ContextHandler contextHandler = new ContextHandler(new CustomMap());

        String name = "jdbc:/java/comp/env/myDatasource";
        contextHandler.bind(name, buildedObject);

        contextHandler.bind(name, new JdbcDataSource());
    }

    @Test
    public void testRebind() throws Exception {
        Map<String, String> props = new HashMap<>();
        props.put("class", "org.h2.jdbcx.JdbcDataSource");
        props.put("url", "jdbc:h2:./test");
        props.put("user", "user");
        props.put("password", "password");

        final Object buildedObject = new ObjectFactory().build(props);

        ContextHandler contextHandler = new ContextHandler(new CustomMap());

        String name = "jdbc:/java/comp/env/myDatasource";
        contextHandler.bind(name, buildedObject);

        JdbcDataSource newValue = new JdbcDataSource();
        contextHandler.rebind(name, newValue);

        assertEquals("The value has changed", newValue, contextHandler.lookup(name));
    }

    @Test(expected = NameNotFoundException.class)
    public void testUnbind() throws Exception {
        Map<String, String> props = new HashMap<>();
        props.put("class", "org.h2.jdbcx.JdbcDataSource");
        props.put("url", "jdbc:h2:./test");
        props.put("user", "user");
        props.put("password", "password");

        final Object buildedObject = new ObjectFactory().build(props);

        ContextHandler contextHandler = new ContextHandler(new CustomMap());
        String name = "jdbc:/java/comp/env/myDatasource";
        contextHandler.bind(name, buildedObject);

        assertEquals("We get the same object?", buildedObject, contextHandler.lookup(name));

        contextHandler.unbind(name);

        assertNull("We don't have anythong here", contextHandler.lookup(name));
    }

    @Test
    public void testRename() throws Exception {

    }
}