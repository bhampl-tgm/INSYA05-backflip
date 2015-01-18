package at.ac.tgm.hit.insy.a05.input.source;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents serveral Connections for different Databases
 *
 * @author Martin Kritzl
 */
public abstract class AbstractDatabaseConnection {
    /**
     * Creates a Connection to a MySQL Database.
     *
     * @param hostname
     * @param database
     * @param username
     * @param password
     * @return the Connection to the MySQL database
     * @throws SQLException
     */
    public abstract Connection createMySQLConnection(String hostname, String database, String username, String password) throws SQLException;
}
