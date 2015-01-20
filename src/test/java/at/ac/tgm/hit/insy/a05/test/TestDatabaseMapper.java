package at.ac.tgm.hit.insy.a05.test;

import static org.junit.Assert.*;

import at.ac.tgm.hit.insy.a05.input.source.ConnectionFactory;
import at.ac.tgm.hit.insy.a05.input.source.DatabaseConnection;
import at.ac.tgm.hit.insy.a05.input.source.DatabaseMapper;
import at.ac.tgm.hit.insy.a05.input.source.MySQLConnection;
import at.ac.tgm.hit.insy.a05.structur.Attribute;
import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.structur.Table;
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
    private Database database;
    private ResultSet tables;
    private ResultSet columnsPlane;
    private ResultSet pksPlane;
    private ResultSet foreignPlane;
    private ResultSet columnsFlight;
    private ResultSet pksFlight;
    private ResultSet foreignFlight;
    private ResultSet columnsAirline;
    private ResultSet pksAirline;
    private ResultSet foreignAirline;

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
        this.con = Mockito.mock(Connection.class);
        this.meta = Mockito.mock(DatabaseMetaData.class);
        this.tables = Mockito.mock(ResultSet.class);
        this.columnsPlane = Mockito.mock(ResultSet.class);
        this.pksPlane = Mockito.mock(ResultSet.class);
        this.foreignPlane = Mockito.mock(ResultSet.class);
        this.columnsFlight = Mockito.mock(ResultSet.class);
        this.pksFlight = Mockito.mock(ResultSet.class);
        this.foreignFlight = Mockito.mock(ResultSet.class);
        this.columnsAirline = Mockito.mock(ResultSet.class);
        this.pksAirline = Mockito.mock(ResultSet.class);
        this.foreignAirline = Mockito.mock(ResultSet.class);

        //Set Metadatas
        Mockito.when(this.con.getMetaData()).thenReturn(this.meta);
        Mockito.when(this.con.getCatalog()).thenReturn("MyDatabase");
        Mockito.when(this.meta.getTables(null, null, "%", null)).thenReturn(this.tables).thenReturn(this.tables);

        //Prepare tables
        Mockito.when(this.tables.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
                                        .thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.tables.getString("TABLE_NAME")).thenReturn("planes").thenReturn("flights").thenReturn("airlines")
                                                         .thenReturn("planes").thenReturn("flights").thenReturn("airlines");

        //Prepare primary keys, attributes and foreign keys
        Mockito.when(this.meta.getColumns(null, null, "planes", null)).thenReturn(this.columnsPlane);
        Mockito.when(this.meta.getPrimaryKeys(null, null, "planes")).thenReturn(this.pksPlane);
        Mockito.when(this.meta.getImportedKeys(null, null, "planes")).thenReturn(this.foreignPlane);
        Mockito.when(this.meta.getColumns(null, null, "flights", null)).thenReturn(this.columnsFlight);
        Mockito.when(this.meta.getImportedKeys(null, null, "flights")).thenReturn(this.foreignFlight);
        Mockito.when(this.meta.getPrimaryKeys(null, null, "flights")).thenReturn(this.pksFlight);
        Mockito.when(this.meta.getColumns(null, null, "airlines", null)).thenReturn(this.columnsAirline);
        Mockito.when(this.meta.getPrimaryKeys(null, null, "airlines")).thenReturn(this.pksAirline);
        Mockito.when(this.meta.getImportedKeys(null, null, "airlines")).thenReturn(this.foreignAirline);

        //Set primary keys
        Mockito.when(this.pksPlane.next()).thenReturn(true).thenReturn(false);
        Mockito.when(this.pksPlane.getString("COLUMN_NAME")).thenReturn("id");
        Mockito.when(this.pksFlight.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.pksFlight.getString("COLUMN_NAME")).thenReturn("nr").thenReturn("airline");
        Mockito.when(this.pksAirline.next()).thenReturn(true).thenReturn(false);
        Mockito.when(this.pksAirline.getString("COLUMN_NAME")).thenReturn("id");

        //Set attributes
        Mockito.when(this.columnsPlane.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.columnsPlane.getString("COLUMN_NAME")).thenReturn("id").thenReturn("sitzplaetze");
        Mockito.when(this.columnsFlight.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.columnsFlight.getString("COLUMN_NAME")).thenReturn("nr").thenReturn("airline").thenReturn("plane");
        Mockito.when(this.columnsAirline.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.columnsAirline.getString("COLUMN_NAME")).thenReturn("id").thenReturn("land");

        //Set foreign keys
        Mockito.when(this.foreignFlight.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.foreignFlight.getString("FKCOLUMN_NAME")).thenReturn("plane").thenReturn("airline");
        Mockito.when(this.foreignFlight.getString("PKTABLE_NAME")).thenReturn("planes").thenReturn("airlines");
        Mockito.when(this.foreignFlight.getString("PKCOLUMN_NAME")).thenReturn("id").thenReturn("id");

        this.database = new DatabaseMapper(this.con).executeMapping();
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
