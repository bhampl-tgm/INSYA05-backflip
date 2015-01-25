package at.ac.tgm.hit.insy.a05.input.source;

import at.ac.tgm.hit.insy.a05.structure.Attribute;
import at.ac.tgm.hit.insy.a05.structure.Database;
import at.ac.tgm.hit.insy.a05.structure.Reference;
import at.ac.tgm.hit.insy.a05.structure.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates a object-oriented structure of the Database from the given Connection
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
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

        Table table;
        ResultSet columns;
        ResultSet pks;
        ResultSet foreign;
        Attribute attribute = null;
        ResultSet indexInfo;

        boolean unique;

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
            indexInfo = result.getIndexInfo(null, null, table.getName(), true, true);
            //Adding primary keys
            while(pks.next()) {
                attribute = new Attribute(pks.getString("COLUMN_NAME"));
                table.addPrimaryKey(attribute);
            }
            //Adding attributes
            while(columns.next()) {
                attribute = new Attribute(columns.getString("COLUMN_NAME"));
                //It will only be added, when the attribute is not a primary key
                if (!table.getPrimaryKeys().contains(attribute))
                    table.addAttribute(attribute);
            }
            while (indexInfo.next()) {
                unique = indexInfo.getBoolean("NON_UNIQUE");
                if (indexInfo.getBoolean("NON_UNIQUE") && indexInfo.getString("COLUMN_NAME").equals(attribute.getName())) {
                    attribute.setUnique(true);
                }
            }
        }

        tables = result.getTables(null, null, "%", null);
        Attribute foreignAttribute;
        Table foreignTable;
        Reference ref;

        //Adding foreign keys to the attributes

        while(tables.next()) {
            //load existing table
            table = database.getTable(tables.getString("TABLE_NAME"));
            //receiving foreign keys
            foreign = result.getImportedKeys(null, null, table.getName());
            while (foreign.next()) {
                //Loading existing Attribute, that uses a value from another table
                String localName = foreign.getString("FKCOLUMN_NAME");
                attribute = table.getPrimaryKey(localName);
                //When the foreign key is not a primary key, the attribute will be loaded
                if (attribute==null) attribute = table.getAttribute(localName);
                //Load referenced table
                foreignTable = database.getTable(foreign.getString("PKTABLE_NAME"));
                //Load referenced attribute from the primary keys
                foreignAttribute = foreignTable.getPrimaryKey(foreign.getString("PKCOLUMN_NAME"));
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
     */

    public void show(Database database) {
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
    }

    public static void main(String[] args) throws SQLException {
        Connection con = ConnectionFactory.createMySQLConnection("localhost", "backflip", "insy4", "blabla");
        DatabaseMapper map = new DatabaseMapper(con);
        map.show(map.executeMapping());
    }

}
