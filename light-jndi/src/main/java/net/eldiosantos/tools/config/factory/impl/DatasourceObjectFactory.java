package net.eldiosantos.tools.config.factory.impl;

import net.eldiosantos.tools.config.factory.ObjectFactory;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by esjunior on 06/07/2015.
 */
public class DatasourceObjectFactory implements ObjectFactory {

    @Override
    public Boolean canBuild(String className) {
        return DataSource.class.getCanonicalName().equals(className);
    }

    public DataSource build(final Map<String, String>props) throws Exception {

        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(props.get("driver"));
        dataSource.setUrl(props.get("url"));
        dataSource.setUsername(props.get("user"));
        dataSource.setPassword(props.get("password"));

        return dataSource;
    }
}
