package at.ac.tgm.hit.insy.a05.input.source;

import com.mysql.fabric.jdbc.FabricMySQLDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLFactory implements ConnectionFactory {


    /**
     * @see at.ac.tgm.hit.insy.a05.input.source.ConnectionFactory#createConnection(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public Connection createConnection(String hostname, String database, String username, String password) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(hostname);
        dataSource.setDatabaseName(database);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource.getConnection();
    }

}
