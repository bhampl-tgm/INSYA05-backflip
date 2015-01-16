package at.ac.tgm.hit.insy.a05.input.source;

import at.ac.tgm.hit.insy.a05.structur.Attribut;
import at.ac.tgm.hit.insy.a05.structur.Database;
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
            Table table = new Table(tables.getString(3));
            this.database.addTable(table);
            ResultSet columns = result.getColumns(null, null, "%", null);
            ResultSet pks = result.getPrimaryKeys(null, null, table.getName());
            while(pks.next()) {
                Attribut pk = new Attribut(pks.getString(4), true);
//                System.out.println("pk: " + pk.getName());
            }
            while(columns.next()) {
                Attribut attribut = new Attribut(columns.getString(4), false);
//                System.out.println(attribut.getName());
//                columns.is
//                attribut.setReference();
                table.addAttribute(attribut);
            }
            for (Attribut a : table.getAttributes()) {
                System.out.println(a.getName() + "\t" + a.isPrimaryKey());
            }
        }
        return this.database;
    }

    public static void main(String[] args) {
        try {
            new DatabaseMapper(new MySQLFactory().createConnection("localhost", "premiere", "insy4", "blabla")).executeMapping();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
