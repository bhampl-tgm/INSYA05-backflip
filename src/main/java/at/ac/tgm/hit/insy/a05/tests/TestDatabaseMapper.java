package at.ac.tgm.hit.insy.a05.tests;

import static org.junit.Assert.*;

import at.ac.tgm.hit.insy.a05.input.source.ConnectionFactory;
import at.ac.tgm.hit.insy.a05.input.source.DatabaseConnection;
import at.ac.tgm.hit.insy.a05.input.source.DatabaseMapper;
import at.ac.tgm.hit.insy.a05.input.source.MySQLConnection;
import at.ac.tgm.hit.insy.a05.structur.Database;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDatabaseMapper {

    private Connection con;
    private DatabaseMetaData meta;
    private ResultSet tables;
    private ResultSet columns;
    private ResultSet pks;
    private ResultSet foreign;

    /**
     * Flugzeug: Nummer
     *           sitzplaetze
     * Flight: nr
     *         Primary foreign airline id
     *         Attribut Flugzeug id
     * Airline: id
     *          land
     */

    @Before
    public void initialize() throws SQLException {
        this.con = Mockito.mock(Connection.class);
        this.meta = Mockito.mock(DatabaseMetaData.class);

        Mockito.when(this.con.getMetaData()).thenReturn(this.meta);
        Mockito.when(this.con.getCatalog()).thenReturn("MyDatabase");
        Mockito.when(this.meta.getTables(null, null, "%", null)).thenReturn(this.tables);

        Mockito.when(this.tables.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.tables.getString("TABLE_NAME")).thenReturn("planes").thenReturn("flights").thenReturn("airline");

        Mockito.when(this.meta.getColumns(null, null, "planes", null)).thenReturn(this.columns);
        Mockito.when(this.meta.getPrimaryKeys(null, null, "planes")).thenReturn(this.pks);
        Mockito.when(this.pks.next()).thenReturn(true).thenReturn(false);
        Mockito.when(this.pks.getString("COLUMN_NAME")).thenReturn("id");
    }

    @Test
    public void test() throws SQLException {
        new DatabaseMapper(this.con).executeMapping();
        assertTrue(true);
    }
}
