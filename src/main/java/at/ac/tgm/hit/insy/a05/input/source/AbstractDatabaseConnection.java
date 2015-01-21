package at.ac.tgm.hit.insy.a05.input.source;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents serveral Connections for different Databases
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public abstract class AbstractDatabaseConnection {
    /**
     * Creates a Connection to a MySQL Database.
     *
     * @param hostname the hostname of the database
     * @param database the databasename
     * @param username the username
     * @param password the password of the user
     * @return the Connection to the MySQL database
     * @throws SQLException
     */
    public abstract Connection createMySQLConnection(String hostname, String database, String username, String password) throws SQLException;
}
