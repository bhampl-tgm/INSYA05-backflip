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

    private Database database;

    @Before
    public void initialize() {
        this.database = new Database("testDatabase");
        Table planes = new Table("planes");
        planes.addPrimaryKey(new Attribute("id", planes));
        planes.addAttribute(new Attribute("seats", planes));

        Table airlines = new Table("airlines");
        airlines.addPrimaryKey(new Attribute("id", airlines));
        airlines.addAttribute(new Attribute("country", airlines));

        Table flights = new Table("flights");
        flights.addPrimaryKey(new Attribute("nr", flights));
        Attribute airlinePrimary = new Attribute("airline", flights);
        airlinePrimary.setReference(new Reference(airlines, airlines.getPrimaryKey("id")));
        Attribute planeAttribute = new Attribute("plane", flights);
        planeAttribute.setReference(new Reference(planes, planes.getPrimaryKey("id")));
        flights.addPrimaryKey(airlinePrimary);
        flights.addAttribute(planeAttribute);

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
    public void testAttributes() throws IOException {
        new ExportEERDotfile().export(this.database, new File(TestMain.TESTPATH + "test.html"));
        BufferedReader file = new BufferedReader(new FileReader(TestMain.TESTPATH + "test.html"));
        String line = file.readLine();
        while (!line.contains("ellipse")) {
            line = file.readLine();
        }
        line = file.readLine();
        assertTrue(line.contains("id"));
    }


} 
