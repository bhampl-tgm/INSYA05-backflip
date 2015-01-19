package at.ac.tgm.hit.insy.a05.input.source;

import at.ac.tgm.hit.insy.a05.structur.Attribute;
import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.structur.Reference;
import at.ac.tgm.hit.insy.a05.structur.Table;
import com.mysql.jdbc.*;
import com.mysql.jdbc.MySQLConnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates a object-oriented structure of the Database from the given Connection
 *
 * @author Martin Kritzl
 *
 */
public class DatabaseMapper {

    private Connection connection;

    public DatabaseMapper(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a object-oriented structure of the Database
     *
     * @return Created Database
     * @throws SQLException
     */
    public Database executeMapping() throws SQLException {
        //Creates a new database object
        Database database = new Database(this.connection.getCatalog());
        //Receive all MetaData from the connection
        DatabaseMetaData result = this.connection.getMetaData();

        //Receive all Tables
        ResultSet tables = result.getTables(null, null, "%", null);

        Table table = null;
        ResultSet columns = null;
        ResultSet pks = null;
        ResultSet foreign = null;
        Attribute attribute = null;
        Attribute pk = null;

        /**
         * Creating all Tables and Attributes without any foreign keys
         */
        while(tables.next()) {
            //Creating a new table
            table = new Table(tables.getString("TABLE_NAME"));
            database.addTable(table);
            //Receive all attributes and primary keys from the table
            columns = result.getColumns(null, null, table.getName(), null);
            pks = result.getPrimaryKeys(null, null, table.getName());
            //Adding primary keys
            while(pks.next()) {
                pk = new Attribute(pks.getString("COLUMN_NAME"));
                table.addPrimaryKey(pk);
            }
            //Adding attributes
            while(columns.next()) {
                attribute = new Attribute(columns.getString("COLUMN_NAME"));
                //It will only be added, when the attribute is not a primary key
                if (!table.getPrimaryKeys().contains(attribute))
                    table.addAttribute(attribute);
            }
        }

        tables = result.getTables(null, null, "%", null);
        Attribute foreignAttribute = null;
        Table foreignTable = null;
        Reference ref = null;

        //Adding foreign keys to the attributes

        while(tables.next()) {
            //load existing table
            table = database.getTable(tables.getString("TABLE_NAME"));
            //receiving foreign keys
            foreign = result.getImportedKeys(null, null, table.getName());
            while (foreign.next()) {
                //Loading existing Attribute, that uses a value from another table
                attribute = table.getPrimaryKey(foreign.getString("FKCOLUMN_NAME"));
                //When the foreign key is not a primary key, the attribute will be loaded
                if (attribute==null) attribute = table.getAttribute(foreign.getString("FKCOLUMN_NAME"));
                //Load referenced table
                foreignTable = database.getTable(foreign.getString("PKTABLE_NAME"));
                //Load referenced attribute from the primary keys
                foreignAttribute = foreignTable.getPrimaryKey(foreign.getString("PKCOLUMN_NAME"));
                //If the referenced attribute is not a primary key, the Attribute will be loaded
                if (foreignAttribute==null)
                    foreignAttribute = foreignTable.getAttribute(foreign.getString("PKCOLUMN_NAME"));
                //Creating the reference
                ref = new Reference(foreignTable, foreignAttribute);
                //Adding the reference to the attribute
                attribute.setReference(ref);
            }
        }
        return database;
    }

    /**
     * Bitte löschen vor der Abgabe!!!!!!!!!!!!!!
     * Ist zum Visualisieren der erfassten Daten, solange die anderen Outputs noch nicht funktionsfähig sind
     *
     * @param args
     */

    public static void main(String[] args) {
        try {
            Database database = new DatabaseMapper(new ConnectionFactory().createMySQLConnection("localhost", "premiere", "insy4", "blabla")).executeMapping();
            System.out.println("Datenbank: " + database.getName());
            for (Table table : database.getTables()) {
                System.out.println("\tTabelle: " + table.getName());
                for (Attribute pk : table.getPrimaryKeys()) {
                    System.out.println("\t\tPrimary Key: " + pk.getName());
                    if (pk.getReference()!=null)
                        System.out.println("\t\t\tReferenz: " + pk.getReference().getRefAttribute().getName());
                }
                for (Attribute attribute : table.getAttributes()) {
                    System.out.println("\t\tAttribut: " + attribute.getName());
                    if (attribute.getReference()!=null)
                        System.out.println("\t\t\tReferenz: " + attribute.getReference().getRefAttribute().getName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
