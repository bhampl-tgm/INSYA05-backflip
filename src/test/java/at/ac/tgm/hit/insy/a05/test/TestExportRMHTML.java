package at.ac.tgm.hit.insy.a05.test;

import at.ac.tgm.hit.insy.a05.output.ExportRMHTML;
import at.ac.tgm.hit.insy.a05.structure.Attribute;
import at.ac.tgm.hit.insy.a05.structure.Database;
import at.ac.tgm.hit.insy.a05.structure.Reference;
import at.ac.tgm.hit.insy.a05.structure.Table;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;


/**
 * Created by Martin Kritzl on 24.01.2015.
 */
public class TestExportRMHTML {

    private static final String beginPK = "<u>";

    private static final String endPK = "</u>";

    private static final String beginFK = "<i>";

    private static final String endFK = "</i>";

    private static final String endAttribute = ", ";

    private static final String unique = "&lt;UNIQUE&gt;";

    private static final String notnull = "&lt;NOT NULL&gt;";

    private Database database;

    /**
     * Flugzeug: Nummer
     * sitzplaetze
     * Flight: nr
     * Primary foreign airline id
     * Attribut Flugzeug id
     * Airline: id
     * land
     */

    @Before
    public void initialize() {
        this.database = new Database("testDatabase");
        Table planes = new Table("planes");
        planes.addPrimaryKey(new Attribute("id", planes));
        Attribute seatsAttribute = new Attribute("seats", planes);
        seatsAttribute.setUnique(true);
        planes.addAttribute(seatsAttribute);

        Table airlines = new Table("airlines");
        airlines.addPrimaryKey(new Attribute("id", airlines));
        Attribute countryAttribute = new Attribute("country", airlines);
        countryAttribute.setNotNull(true);
        airlines.addAttribute(countryAttribute);

        Table flights = new Table("flights");
        flights.addPrimaryKey(new Attribute("nr", flights));
        Attribute airlinePrimary = new Attribute("airline", flights);
        airlinePrimary.setReference(new Reference(airlines, airlines.getPrimaryKey("id")));
        Attribute planeAttribute = new Attribute("plane", flights);
        planeAttribute.setUnique(true);
        planeAttribute.setReference(new Reference(planes, planes.getPrimaryKey("id")));
        Attribute planeAttribute1 = new Attribute("plane1", flights);
        planeAttribute1.setNotNull(true);
        planeAttribute1.setReference(new Reference(planes, planes.getPrimaryKey("id")));
        flights.addPrimaryKey(airlinePrimary);
        flights.addAttribute(planeAttribute);
        flights.addAttribute(planeAttribute1);

        this.database.addTable(planes);
        this.database.addTable(airlines);
        this.database.addTable(flights);
    }


    @Test
    public void testFileExists() throws FileNotFoundException {
        new ExportRMHTML().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        assertTrue(new FileReader(TestMain.TESTPATH + "test.html").toString() != null);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileNotExists() throws FileNotFoundException {
        new ExportRMHTML().export(this.database, new File(""));
    }

    @Test
    public void testTableNames() throws IOException {
        new ExportRMHTML().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.html"));
        String line = file.readLine();
        while (!line.contains("<br />")) {
            line = file.readLine();
        }
        assertTrue(line.contains("planes"));
        line = file.readLine();
        assertTrue(line.contains("airlines"));
        line = file.readLine();
        assertTrue(line.contains("flights"));
    }

    @Test
    public void testAttributes() throws IOException {
        new ExportRMHTML().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.html"));
        String line = file.readLine();
        while (!line.contains("<br />")) {
            line = file.readLine();
        }
        assertTrue(line.contains(endAttribute + "seats"));
    }

    @Test
    public void testPrimaryKeys() throws IOException {
        new ExportRMHTML().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.html"));
        String line = file.readLine();
        while (!line.contains("<br />")) {
            line = file.readLine();
        }
        assertTrue(line.contains(beginPK + "id" + endPK));
    }

    @Test
    public void testForeignKey() throws IOException {
        new ExportRMHTML().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.html"));
        String line = file.readLine();
        while (!line.contains("<br />")) {
            line = file.readLine();
        }
        file.readLine();
        line = file.readLine();
        assertTrue(line.contains(endAttribute + beginFK + "plane:planes.id" + endFK));
    }

    @Test
    public void testForeignPrimaryKey() throws IOException {
        new ExportRMHTML().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.html"));
        String line = file.readLine();
        while (!line.contains("<br />")) {
            line = file.readLine();
        }
        file.readLine();
        line = file.readLine();
        assertTrue(line.contains(beginPK + beginFK + "airline:airlines.id" + endFK + endPK));
    }

    @Test
    public void testUnique() throws IOException {
        new ExportRMHTML().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.html"));
        String line = file.readLine();
        while (!line.contains("<br />")) {
            line = file.readLine();
        }
        file.readLine();
        line = file.readLine();
        assertTrue(line.contains(endAttribute + beginFK + "plane:planes.id" + endFK + unique));
    }

    @Test
    public void testNotNull() throws IOException {
        new ExportRMHTML().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.html"));
        String line = file.readLine();
        while (!line.contains("<br />")) {
            line = file.readLine();
        }
        line = file.readLine();
        assertTrue(line.contains(endAttribute + "country" + notnull));
    }

}
