package at.ac.tgm.hit.insy.a05;

import at.ac.tgm.hit.insy.a05.input.CLI;
import at.ac.tgm.hit.insy.a05.input.source.MySQLConnection;
import at.ac.tgm.hit.insy.a05.output.ExportEERDotfile;
import at.ac.tgm.hit.insy.a05.output.ExportFactory;
import at.ac.tgm.hit.insy.a05.output.ExportRMHTML;
import at.ac.tgm.hit.insy.a05.output.Exportable;
import at.ac.tgm.hit.insy.a05.input.source.ConnectionFactory;
import at.ac.tgm.hit.insy.a05.input.source.DatabaseMapper;
import at.ac.tgm.hit.insy.a05.structur.Database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static final CLI CLI = new CLI();

    private DatabaseMapper databaseMapper;

    private Exportable exportable;

    public static void main(String[] args) {
        Main.CLI.parseArgs(args);
        Connection connection = null;
        try {
            connection = new ConnectionFactory().createMySQLConnection(Main.CLI.getHostname(), Main.CLI.getDatabaseName(), Main.CLI.getUser(), Main.CLI.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Database database = null;
        try {
            database = new DatabaseMapper(connection).executeMapping();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            new ExportFactory().chooseExport(Main.CLI.getFormat()).export(database, Main.CLI.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
