package at.ac.tgm.hit.insy.a05.input.source;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Creates a Connection to a Database.
 *
 * @author Martin Kritzl
 */
public interface DatabaseConnection {
    /**
     *
     * @param hostname
     * @param database
     * @param username
     * @param password
     * @return The connection to the database
     * @throws SQLException
     */
    public Connection createConnection(String hostname, String database, String username, String password) throws SQLException;
}
