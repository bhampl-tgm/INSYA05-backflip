package at.ac.tgm.hit.insy.a05.input.source;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Creates several Connections to different Databases.
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class ConnectionFactory {
    /**
     *
     * @param hostname the hostname of the database
     * @param database the databasename
     * @param username the username
     * @param password the password of the user
     * @return the connection to the database
     * @throws SQLException if the connection to the MySQL-Database fails with the given Parameters.
     *
     */
    public static Connection createMySQLConnection(String hostname, String database, String username, String password) throws SQLException {
        return new MySQLConnection().createConnection(hostname,database,username,password);
    }

}
