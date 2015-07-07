package net.eldiosantos.tools.config.factory.loader;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by esjunior on 06/07/2015.
 */
public class DatasourceObjectFactory {

    public DataSource build(final Map<String, String>props) {

        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(props.get("driver"));
        dataSource.setUrl(props.get("url"));
        dataSource.setUsername(props.get("user"));
        dataSource.setPassword(props.get("password"));

        return dataSource;
    }
}
