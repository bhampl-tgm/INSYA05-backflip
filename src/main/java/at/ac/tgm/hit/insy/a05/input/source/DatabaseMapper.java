package at.ac.tgm.hit.insy.a05.input.source;

import at.ac.tgm.hit.insy.a05.structur.Attribut;
import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.structur.Reference;
import at.ac.tgm.hit.insy.a05.structur.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMapper {

    private Connection connection;

    private Database database;

    public DatabaseMapper(Connection connection) {
        this.connection = connection;
    }

    public Database executeMapping() throws SQLException {
        this.database = new Database(this.connection.getCatalog());
        DatabaseMetaData result = this.connection.getMetaData();

        ResultSet tables = result.getTables(null, null, "%", null);
        while(tables.next()) {
            Table table = new Table(tables.getString("TABLE_NAME"));
            this.database.addTable(table);
            ResultSet columns = result.getColumns(null, null, table.getName(), null);
            ResultSet pks = result.getPrimaryKeys(null, null, table.getName());
            ResultSet foreign = result.getImportedKeys(null, null, table.getName());
            while(pks.next()) {
                Attribut pk = new Attribut(pks.getString("COLUMN_NAME"));
                table.addPrimaryKey(pk);
            }
            while(columns.next()) {
                Attribut attribut = new Attribut(columns.getString("COLUMN_NAME"));
                if (!table.getPrimaryKeys().contains(attribut))
                    table.addAttribute(attribut);
            }
        }
        tables = result.getTables(null, null, "%", null);
        while(tables.next()) {
            Table table = this.database.getTable(tables.getString("TABLE_NAME"));
            ResultSet foreign = result.getImportedKeys(null, null, table.getName());
            while (foreign.next()) {
                Table foreignTable = this.database.getTable(foreign.getString("PKTABLE_NAME"));
                Attribut attribut = table.getAttribute(foreign.getString("FKCOLUMN_NAME"));
                String temp = foreign.getString("PKCOLUMN_NAME");
                Reference ref = null;
                ref = new Reference(foreignTable, foreignTable.getPrimaryKey(foreign.getString("PKCOLUMN_NAME")));
                attribut.setReference(ref);
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
                for (Attribut pk : table.getPrimaryKeys()) {
                    System.out.println("\t\tPrimary Key: " + pk.getName());
                    if (pk.getReference()!=null)
                        System.out.println("\t\t\tReferenz: " + pk.getReference().getRefAttribute().getName());
                }
                for (Attribut attribut : table.getAttributes()) {
                    System.out.println("\t\tAttribut: " + attribut.getName());
                    if (attribut.getReference()!=null)
                        System.out.println("\t\t\tReferenz: " + attribut.getReference().getRefAttribute().getName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
