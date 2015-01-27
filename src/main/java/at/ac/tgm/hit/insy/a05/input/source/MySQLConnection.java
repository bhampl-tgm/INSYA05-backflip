package at.ac.tgm.hit.insy.a05.input.source;

import com.mysql.fabric.jdbc.FabricMySQLDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Creates a connection to a MySQL-Database
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class MySQLConnection implements DatabaseConnection {

    /**
     *
     * @param hostname the hostname of the database
     * @param database the databasename
     * @param username the username
     * @param password the password of the user
     * @return Connection to the MySQL Database
     * @throws SQLException if the connection to the MySQL-Database fails with the given Parameters.
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
