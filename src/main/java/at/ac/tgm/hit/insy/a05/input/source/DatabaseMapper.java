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
/**
 * Erstellt aus der angegebenen Connection eine objektorientierte Abbildung der Datenbank
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
     * Erstellt eine objektorientierte Abbildung der Datenbank
     *
     * @return Erstellte Datenbank
     * @throws SQLException
     */
    public Database executeMapping() throws SQLException {
        //Erstellen eines neuen Datenbankobjekts
        Database database = new Database(this.connection.getCatalog());
        //Erhalten aller Metadaten aus der Connection
        DatabaseMetaData result = this.connection.getMetaData();

        //Erhalten aller Tabellen
        ResultSet tables = result.getTables(null, null, "%", null);

        Table table = null;
        ResultSet columns = null;
        ResultSet pks = null;
        ResultSet foreign = null;
        Attribute attribute = null;
        Attribute pk = null;

        //Erstellen der Tabellen und Attribute ohne beruecksichtigung von foreignKeys

        while(tables.next()) {
            //Erstellen einer neuen Tabelle
            table = new Table(tables.getString("TABLE_NAME"));
            database.addTable(table);
            //Erhalten aller Attribute und PrimaryKeys der Tabelle
            columns = result.getColumns(null, null, table.getName(), null);
            pks = result.getPrimaryKeys(null, null, table.getName());
            //Hinzufuegen der PrimaryKeys
            while(pks.next()) {
                pk = new Attribute(pks.getString("COLUMN_NAME"));
                table.addPrimaryKey(pk);
            }
            //Hinzufuegen der Attribute
            while(columns.next()) {
                attribute = new Attribute(columns.getString("COLUMN_NAME"));
                //Wird nur hinzugefuegt wenn das Attribut nicht schon in den PrimaryKeys drinnen ist
                if (!table.getPrimaryKeys().contains(attribute))
                    table.addAttribute(attribute);
            }
        }

        tables = result.getTables(null, null, "%", null);
        Attribute foreignAttribute = null;
        Table foreignTable = null;
        Reference ref = null;

        //Hinzufuegen der foreign Keys zu den Attributen

        while(tables.next()) {
            //Vorhandene Tabelle laden
            table = database.getTable(tables.getString("TABLE_NAME"));
            //Foreign Keys aus den Metadaten holen
            foreign = result.getImportedKeys(null, null, table.getName());
            while (foreign.next()) {
                //Vorhandenes Attribut, welches von einer anderen Tabelle den Wert hat, laden
                attribute = table.getAttribute(foreign.getString("FKCOLUMN_NAME"));
                //Referenzierte Tabelle laden
                foreignTable = database.getTable(foreign.getString("PKTABLE_NAME"));
                //Referenziertes Attribut aus den PrimaryKeys laden
                foreignAttribute = foreignTable.getPrimaryKey(foreign.getString("PKCOLUMN_NAME"));
                //Wenn dies nicht in den PrimaryKeys enthalten ist, aus den Attributen holen
                if (foreignAttribute==null)
                    foreignAttribute = foreignTable.getAttribute(foreign.getString("PKCOLUMN_NAME"));
                //Erstellen der Referenz
                ref = new Reference(foreignTable, foreignAttribute);
                //Hinzufuegen der Referenz
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
