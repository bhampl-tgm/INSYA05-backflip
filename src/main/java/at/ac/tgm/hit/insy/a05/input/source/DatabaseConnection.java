package at.ac.tgm.hit.insy.a05.input.source;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Creates a Connection to a Database.
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public interface DatabaseConnection {
    /**
     * Creates a Connection to a Database.
     *
     * @param hostname the hostname of the database
     * @param database the databasename
     * @param username the username
     * @param password the password of the user
     * @return The connection to the database
     * @throws SQLException if the connection fails with the given Parameters.
     */
    public Connection createConnection(String hostname, String database, String username, String password) throws SQLException;
}
