package at.ac.tgm.hit.insy.a05.test;

import at.ac.tgm.hit.insy.a05.input.source.DatabaseMapper;
import at.ac.tgm.hit.insy.a05.structur.Attribute;
import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.structur.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TestDatabaseMapper {

    private Database database;

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
        //Initialize Mock Objects
        Connection con = Mockito.mock(Connection.class);
        DatabaseMetaData meta = Mockito.mock(DatabaseMetaData.class);
        ResultSet tables = Mockito.mock(ResultSet.class);
        ResultSet columnsPlane = Mockito.mock(ResultSet.class);
        ResultSet pksPlane = Mockito.mock(ResultSet.class);
        ResultSet foreignPlane = Mockito.mock(ResultSet.class);
        ResultSet columnsFlight = Mockito.mock(ResultSet.class);
        ResultSet pksFlight = Mockito.mock(ResultSet.class);
        ResultSet foreignFlight = Mockito.mock(ResultSet.class);
        ResultSet columnsAirline = Mockito.mock(ResultSet.class);
        ResultSet pksAirline = Mockito.mock(ResultSet.class);
        ResultSet foreignAirline = Mockito.mock(ResultSet.class);

        //Set Metadatas
        Mockito.when(con.getMetaData()).thenReturn(meta);
        Mockito.when(con.getCatalog()).thenReturn("MyDatabase");
        Mockito.when(meta.getTables(null, null, "%", null)).thenReturn(tables).thenReturn(tables);

        //Prepare tables
        Mockito.when(tables.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
                                        .thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(tables.getString("TABLE_NAME")).thenReturn("planes").thenReturn("flights").thenReturn("airlines")
                                                         .thenReturn("planes").thenReturn("flights").thenReturn("airlines");

        //Prepare primary keys, attributes and foreign keys
        Mockito.when(meta.getColumns(null, null, "planes", null)).thenReturn(columnsPlane);
        Mockito.when(meta.getPrimaryKeys(null, null, "planes")).thenReturn(pksPlane);
        Mockito.when(meta.getImportedKeys(null, null, "planes")).thenReturn(foreignPlane);
        Mockito.when(meta.getColumns(null, null, "flights", null)).thenReturn(columnsFlight);
        Mockito.when(meta.getImportedKeys(null, null, "flights")).thenReturn(foreignFlight);
        Mockito.when(meta.getPrimaryKeys(null, null, "flights")).thenReturn(pksFlight);
        Mockito.when(meta.getColumns(null, null, "airlines", null)).thenReturn(columnsAirline);
        Mockito.when(meta.getPrimaryKeys(null, null, "airlines")).thenReturn(pksAirline);
        Mockito.when(meta.getImportedKeys(null, null, "airlines")).thenReturn(foreignAirline);

        //Set primary keys
        Mockito.when(pksPlane.next()).thenReturn(true).thenReturn(false);
        Mockito.when(pksPlane.getString("COLUMN_NAME")).thenReturn("id");
        Mockito.when(pksFlight.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(pksFlight.getString("COLUMN_NAME")).thenReturn("nr").thenReturn("airline");
        Mockito.when(pksAirline.next()).thenReturn(true).thenReturn(false);
        Mockito.when(pksAirline.getString("COLUMN_NAME")).thenReturn("id");

        //Set attributes
        Mockito.when(columnsPlane.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(columnsPlane.getString("COLUMN_NAME")).thenReturn("id").thenReturn("sitzplaetze");
        Mockito.when(columnsFlight.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(columnsFlight.getString("COLUMN_NAME")).thenReturn("nr").thenReturn("airline").thenReturn("plane");
        Mockito.when(columnsAirline.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(columnsAirline.getString("COLUMN_NAME")).thenReturn("id").thenReturn("land");

        //Set foreign keys
        Mockito.when(foreignFlight.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(foreignFlight.getString("FKCOLUMN_NAME")).thenReturn("plane").thenReturn("airline");
        Mockito.when(foreignFlight.getString("PKTABLE_NAME")).thenReturn("planes").thenReturn("airlines");
        Mockito.when(foreignFlight.getString("PKCOLUMN_NAME")).thenReturn("id").thenReturn("id");

        this.database = new DatabaseMapper(con).executeMapping();
    }

    @Test
    public void testDatabaseName() throws SQLException {
        assertEquals("MyDatabase", this.database.getName());
    }

    @Test
    public void testTableNames() throws SQLException {
        assertEquals("planes", this.database.getTables().get(0).getName());
        assertEquals("flights", this.database.getTables().get(1).getName());
        assertEquals("airlines", this.database.getTables().get(2).getName());
    }

    @Test
    public void testGetTable() throws SQLException {
        assertEquals("planes", this.database.getTable("planes").getName());
        assertEquals("flights", this.database.getTable("flights").getName());
        assertEquals("airlines", this.database.getTable("airlines").getName());
    }

    @Test
    public void testGetAttributes() throws SQLException {
        assertEquals("sitzplaetze", this.database.getTable("planes").getAttribute("sitzplaetze").getName());
    }

    @Test
    public void testGetPrimaryKeys() throws SQLException {
        assertEquals("id", this.database.getTable("planes").getPrimaryKey("id").getName());
    }

    @Test
    public void testGetTableWithNotEnteredName() {
        assertEquals(null, this.database.getTable("NotEnteredName"));
    }

    @Test
    public void getReferenzTable() {
        assertEquals("airlines", this.database.getTable("flights").getPrimaryKey("airline").getReference().getRefTable().getName());
    }

    @Test
    public void getReferenzAttribut() {
        assertEquals("id", this.database.getTable("flights").getPrimaryKey("airline").getReference().getRefAttribute().getName());
    }

    @Test
    public void getAttribute() {
        assertEquals("sitzplaetze", this.database.getTable("planes").getAttribute("sitzplaetze").getName());
    }

    @Test
    public void getNotEnteredNameAttribute() {
        assertEquals(null, this.database.getTable("planes").getAttribute("laenge"));
    }

    @Test
    public void getPrimaryKey() {
        assertEquals("id", this.database.getTable("planes").getPrimaryKey("id").getName());
    }

    @Test
    public void getNotEnteredNamePrimaryKey() {
        assertEquals(null, this.database.getTable("planes").getPrimaryKey("nr"));
    }

    @Test
    public void test1() {
        Attribute temp = new Attribute("newAttribute");
        assertTrue(temp.equals(temp));
    }

    @Test
    public void test2() {
        Attribute temp = new Attribute("newAttribute");
        assertFalse(temp.equals(this.database.getTable("flights").getAttribute("plane")));
    }

    @Test
    public void test3() {
        Attribute temp = new Attribute("newAttribute");
        assertFalse(temp.equals(null));
    }

    @Test
    public void test4() {
        Attribute temp = new Attribute("newAttribute");
        assertFalse(temp.equals(new Table("newTable")));
    }

    @Test
    public void getAttributes() {
        assertEquals(1, this.database.getTable("planes").getAttributes().size());
    }
}
