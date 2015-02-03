package at.ac.tgm.hit.insy.a05.test;

import at.ac.tgm.hit.insy.a05.output.ExportEERDotfile;
import at.ac.tgm.hit.insy.a05.structure.Attribute;
import at.ac.tgm.hit.insy.a05.structure.Database;
import at.ac.tgm.hit.insy.a05.structure.Reference;
import at.ac.tgm.hit.insy.a05.structure.Table;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;

/**
 * ExportEERDotfile Tester.
 *
 * @author Burkhard Hampl [burkhard.hampl@student.tgm.ac.at]
 * @version 0.1
 * @see at.ac.tgm.hit.insy.a05.output.ExportEERDotfile
 */
public class TestExportEERDotfile {

    private static final String startPK = "<<u>";
    private static final String endPK = "</u>>";
    private static final String notnull = "&lt;NOT NULL&gt;";
    private static final String seperator = " -- ";
    private static final String startCardinality = " [label=\"";
    private static final String endCardinality = "\"];";

    private Database database;

    @Before
    public void initialize() {
        this.database = new Database("testDatabase");
        Table planes = new Table("planes");
        planes.addPrimaryKey(new Attribute("id", planes));
        planes.addPrimaryKey(new Attribute("alternativeid", planes));
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
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        assertTrue(new File(TestMain.TESTPATH + "test.dot").toString() != null);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileNotExists() throws FileNotFoundException {
        new ExportEERDotfile().export(this.database, new File(""));
    }

    @Test
    public void testBeginAndEnd() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        assertTrue(line.contains("graph ER {"));
        String nextLine;
        while ((nextLine = file.readLine()) != null) {
            line = nextLine;
        }
        assertTrue(line.contains("}"));
    }

    @Test
    public void testTableNames() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        file.readLine();
        String line = file.readLine();
        assertTrue(line.contains("planes"));
        assertTrue(line.contains("airlines"));
        assertTrue(line.contains("flights"));
    }

    @Test
    public void testAttribute() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        while (!line.contains("ellipse")) {
            line = file.readLine();
        }
        file.readLine();
        file.readLine();
        line = file.readLine();
        assertTrue(line.contains("seats"));
    }

    @Test
    public void testPrimaryKey() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        while (!line.contains("ellipse")) {
            line = file.readLine();
        }
        line = file.readLine();
        assertTrue(line.contains("id" + TestExportEERDotfile.endPK));
        assertTrue(line.contains(TestExportEERDotfile.startPK));
    }

    @Test
    public void testNotNull() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        while (!line.contains("ellipse")) {
            line = file.readLine();
        }
        for (int i = 0; i < 4; i++) {
            file.readLine();
        }
        line = file.readLine();
        assertTrue(line.contains("country" + TestExportEERDotfile.notnull));
    }

    @Test
    public void testRelation() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        while (!line.contains("diamond")) {
            line = file.readLine();
        }
        line = file.readLine();
        assertTrue(line.contains("flights" + "airline" + "airlines" + "id"));
    }

    @Test
    public void testAttributeToTable() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        while (!line.contains("diamond")) {
            line = file.readLine();
        }
        file.readLine();
        file.readLine();
        line = file.readLine();
        assertTrue(line.contains("id" + TestExportEERDotfile.seperator + "planes"));
    }

    @Test
    public void testTableToRelation() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        while (!line.contains("diamond")) {
            line = file.readLine();
        }
        for (int i = 0; i < 8; i++) {
            file.readLine();
        }
        line = file.readLine();
        assertTrue(line.contains("flights" + TestExportEERDotfile.seperator + "flights" + "airline" + "airlines" + "id"));
    }

    @Test
    public void testCardinalityi_N() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        while (!line.contains("diamond")) {
            line = file.readLine();
        }
        for (int i = 0; i < 8; i++) {
            file.readLine();
        }
        line = file.readLine();
        assertTrue(line.contains("flights" + "airline" + "airlines" + "id" + TestExportEERDotfile.startCardinality + "n" + TestExportEERDotfile.endCardinality));
    }

    @Test
    public void testCardinality_1() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.dot"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.dot"));
        String line = file.readLine();
        while (!line.contains("diamond")) {
            line = file.readLine();
        }
        for (int i = 0; i < 9; i++) {
            file.readLine();
        }
        line = file.readLine();
        assertTrue(line.contains("flights" + "airline" + "airlines" + "id" + TestExportEERDotfile.startCardinality + "1" + TestExportEERDotfile.endCardinality));
    }
} 
