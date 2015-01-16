package at.ac.tgm.hit.insy.a05.input.source;

import at.ac.tgm.hit.insy.a05.structur.Attribute;
import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.structur.Reference;
import at.ac.tgm.hit.insy.a05.structur.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Erstellt aus der angegebenen Connection eine objektorientierte Abbildung der Datenbank
 *
 * @author Martin Kritzl
 *
 */
public class DatabaseMapper {

    private Connection connection;

    private Database database;

    public DatabaseMapper(Connection connection) {
        this.connection = connection;
    }

    /**
     * Erstellt eine objektorientierte Abbildung der Datenbank
     *
     * @return Erstellte Datenbank
     * @throws SQLException
     */
    public Database executeMapping() throws SQLException {
        this.database = new Database(this.connection.getCatalog());
        DatabaseMetaData result = this.connection.getMetaData();

        ResultSet tables = result.getTables(null, null, "%", null);

        Table table = null;
        ResultSet columns = null;
        ResultSet pks = null;
        ResultSet foreign = null;
        Attribute attribute = null;
        Attribute pk = null;

        while(tables.next()) {
            table = new Table(tables.getString("TABLE_NAME"));
            this.database.addTable(table);
            columns = result.getColumns(null, null, table.getName(), null);
            pks = result.getPrimaryKeys(null, null, table.getName());
            foreign = result.getImportedKeys(null, null, table.getName());
            while(pks.next()) {
                pk = new Attribute(pks.getString("COLUMN_NAME"));
                table.addPrimaryKey(pk);
            }
            while(columns.next()) {
                attribute = new Attribute(columns.getString("COLUMN_NAME"));
                if (!table.getPrimaryKeys().contains(attribute))
                    table.addAttribute(attribute);
            }
        }
        tables = result.getTables(null, null, "%", null);

        Attribute foreignAttribute = null;
        Table foreignTable = null;
        Reference ref = null;

        while(tables.next()) {
            table = this.database.getTable(tables.getString("TABLE_NAME"));
            foreign = result.getImportedKeys(null, null, table.getName());
            while (foreign.next()) {
                attribute = table.getAttribute(foreign.getString("FKCOLUMN_NAME"));
                foreignTable = this.database.getTable(foreign.getString("PKTABLE_NAME"));
                foreignAttribute = foreignTable.getPrimaryKey(foreign.getString("PKCOLUMN_NAME"));
                if (foreignAttribute==null)
                    foreignAttribute = foreignTable.getAttribute(foreign.getString("PKCOLUMN_NAME"));
                ref = new Reference(foreignTable, foreignAttribute);
                attribute.setReference(ref);
            }
        }
        return this.database;
    }

    public static void main(String[] args) {
        try {
            Database database = new DatabaseMapper(new MySQLFactory().createConnection("localhost", "premiere", "insy4", "blabla")).executeMapping();
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
