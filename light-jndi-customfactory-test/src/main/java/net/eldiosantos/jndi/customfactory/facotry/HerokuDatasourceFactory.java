package net.eldiosantos.jndi.customfactory.facotry;

import net.eldiosantos.tools.config.factory.ObjectFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

/**
 * Created by esjunior on 08/07/2015.
 */
public class HerokuDatasourceFactory implements ObjectFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Boolean canBuild(String className) {
        return getClass().getCanonicalName().equals(className);
    }

    @Override
    public Object build(Map<String, String> objectProperties) throws Exception {
        final String databaseDataEnvName = objectProperties.get("databasePropertyEnvName");

        String databaseData = System.getenv(databaseDataEnvName);
        logger.debug(String.format("Database info: %s=> %s", databaseDataEnvName, databaseData));

        final URI uri = new URI(databaseData);

        final String database_url = String.format("jdbc:postgresql://%s:%d%s", uri.getHost(), uri.getPort(), uri.getPath());

        final String database_username = uri.getUserInfo().split(":")[0];

        final String database_password = uri.getUserInfo().split(":")[1];;

        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(objectProperties.get("driver"));
        dataSource.setUrl(database_url);
        dataSource.setUsername(database_username);
        dataSource.setPassword(database_password);

        return dataSource;
    }
}
