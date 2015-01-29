package at.ac.tgm.hit.insy.a05.input.source;

import at.ac.tgm.hit.insy.a05.structure.Attribute;
import at.ac.tgm.hit.insy.a05.structure.Database;
import at.ac.tgm.hit.insy.a05.structure.Reference;
import at.ac.tgm.hit.insy.a05.structure.Table;

import java.sql.*;

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
     * @throws SQLException if the connection fails with.
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
        Attribute attribute;
        ResultSetMetaData tableMetaData;

        boolean unique;

        int i = 1;

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

            tableMetaData = connection.createStatement().executeQuery("SELECT * FROM " + table.getName()).getMetaData();

            //Adding primary keys
            while(pks.next()) {
                attribute = new Attribute(pks.getString("COLUMN_NAME"), table);
                attribute.setUnique(true);
                table.addPrimaryKey(attribute);
//                if (!pks.getBoolean("NULLABLE")) System.out.println("NULL");
            }

            //Adding attributes
            i=1;
            while(columns.next()) {
                //System.out.println("Field" + i + ": " + columns.getMetaData().isNullable(i));
                attribute = new Attribute(columns.getString("COLUMN_NAME"), table);
                //It will only be added, when the attribute is not a primary key
                if (!table.getPrimaryKeys().contains(attribute)) {
                    table.addAttribute(attribute);
                    if (tableMetaData.isNullable(i) == ResultSetMetaData.columnNoNulls)
                        attribute.setNotNull(true);
                }
                i++;
            }
        }



        tables = result.getTables(null, null, "%", null);
        Attribute foreignAttribute;
        Table foreignTable;
        Reference ref;
        ResultSet indexInfo;

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
            //check if the attribute is unique
            indexInfo = result.getIndexInfo(null, null, table.getName(), true, true);
            while (indexInfo.next()) {
                for (Attribute atr : table.getAttributes()) {
                    if (atr.getName().equals(indexInfo.getString("COLUMN_NAME")))
                        atr.setUnique(true);
                }
            }
        }
        return database;
    }
}
