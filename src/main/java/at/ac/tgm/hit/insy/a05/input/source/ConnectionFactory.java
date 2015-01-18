package at.ac.tgm.hit.insy.a05.input.source;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Creates several Connections to different Databases.
 *
 * @author Martin Kritzl
 */
public class ConnectionFactory extends AbstractDatabaseConnection {
    /**
     * @see at.ac.tgm.hit.insy.a05.input.source.AbstractDatabaseConnection#createMySQLConnection(String, String, String, String)
     */
    public Connection createMySQLConnection(String hostname, String database, String username, String password) throws SQLException {
        return new MySQLFactory().createConnection(hostname,database,username,password);
    }

}
