package at.ac.tgm.hit.insy.a05;

import at.ac.tgm.hit.insy.a05.input.CLI;
import at.ac.tgm.hit.insy.a05.input.source.ConnectionFactory;
import at.ac.tgm.hit.insy.a05.input.source.DatabaseMapper;
import at.ac.tgm.hit.insy.a05.output.ExportFactory;
import at.ac.tgm.hit.insy.a05.structure.Database;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Is the starting point of the program and handles the thrown Exceptions
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class Main {
    
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Main main = new Main();

        CLI cli = main.parse(args);
        Connection connection = main.getConnection(cli.getHostname(), cli.getDatabaseName(), cli.getUser(), cli.getPassword());
        Database database = main.mapDatabase(connection);
        main.export(database, cli.getFile(), cli.getFormat());
    }

    public CLI parse(String[] args) {
        CLI cli = new CLI();
        try {
            cli.parseArgs(args);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
        return cli;
    }

    /**
     * Creates a Connection to a MySQL Database
     *
     * @param hostname the hostname of the database
     * @param database the databasename
     * @param username the username
     * @param password the password of the user
     * @return Created Connection to a MySQL Database
     */
    public Connection getConnection(String hostname, String database, String username, String password) {
        Connection connection = null;
        try {
            connection = ConnectionFactory.createMySQLConnection(hostname, database, username, password);
        } catch (SQLException e) {
            logger.error("Connection to the database refused");
            System.exit(1);
        }
        return connection;
    }

    /**
     * Map a Database into a object-oriented database
     *
     * @param connection Connection to a Database
     * @return The mapped database
     */
    public Database mapDatabase(Connection connection) {
        Database database=null;
        try {
            database = new DatabaseMapper(connection).executeMapping();
        } catch (SQLException e) {
            logger.error("Database can not be mapped");
            System.exit(1);
        }
        return database;
    }

    /**
     * Writes the result of the mapping into a File
     *
     * @param database Already mapped database
     * @param output The destination of a file
     * @param format The wanted format of the output
     */
    public void export(Database database, File output, String format) {
        try {
            ExportFactory.chooseExport(format).export(database, output);
            logger.info("The file was successfully created under " + output.getPath());
        } catch (FileNotFoundException e) {
            logger.error("The file can not created under " + output.getPath());
            System.exit(1);
        }
    }
}
