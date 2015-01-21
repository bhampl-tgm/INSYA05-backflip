package at.ac.tgm.hit.insy.a05;

import at.ac.tgm.hit.insy.a05.input.CLI;
import at.ac.tgm.hit.insy.a05.input.source.ConnectionFactory;
import at.ac.tgm.hit.insy.a05.input.source.DatabaseMapper;
import at.ac.tgm.hit.insy.a05.output.ExportFactory;
import at.ac.tgm.hit.insy.a05.structur.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final CLI CLI = new CLI();
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Main.CLI.parseArgs(args);
        Connection connection = null;
        try {
            connection = ConnectionFactory.createMySQLConnection(Main.CLI.getHostname(), Main.CLI.getDatabaseName(), Main.CLI.getUser(), Main.CLI.getPassword());
        } catch (SQLException e) {
            logger.info("Connection to the database refused");
            System.exit(1);

        }
        Database database = null;
        try {
            database = new DatabaseMapper(connection).executeMapping();
        } catch (SQLException e) {
            logger.info("Database can not be mapped");
            System.exit(1);
        }
        try {
            new ExportFactory().chooseExport(Main.CLI.getFormat()).export(database, Main.CLI.getFile());
            logger.info("The file was successfully created under " + Main.CLI.getFile().getPath()+ "\\" + Main.CLI.getFile().getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
