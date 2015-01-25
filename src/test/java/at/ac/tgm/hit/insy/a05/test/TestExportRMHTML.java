package at.ac.tgm.hit.insy.a05.test;

import at.ac.tgm.hit.insy.a05.output.ExportRMHTML;
import at.ac.tgm.hit.insy.a05.structure.Attribute;
import at.ac.tgm.hit.insy.a05.structure.Database;
import at.ac.tgm.hit.insy.a05.structure.Reference;
import at.ac.tgm.hit.insy.a05.structure.Table;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;


/**
 * Created by Martin Kritzl on 24.01.2015.
 */
public class TestExportRMHTML {

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
    public void initialize() {
        this.database = new Database("testDatabase");
        Table planes =  new Table("planes");
        planes.addPrimaryKey(new Attribute("id"));
        planes.addAttribute(new Attribute("seats"));

        Table airlines = new Table("airlines");
        airlines.addPrimaryKey(new Attribute("id"));
        airlines.addAttribute(new Attribute("country"));

        Table flights = new Table("flights");
        flights.addPrimaryKey(new Attribute("nr"));
        Attribute airlinePrimary = new Attribute("airline");
        airlinePrimary.setReference(new Reference(airlines, airlines.getPrimaryKey("id")));
        Attribute planeAttribute = new Attribute("plane");
        planeAttribute.setReference(new Reference(planes, planes.getPrimaryKey("id")));
        flights.addPrimaryKey(airlinePrimary);
        flights.addAttribute(planeAttribute);

        this.database.addTable(planes);
        this.database.addTable(airlines);
        this.database.addTable(flights);
    }

    @Test
    public void testFileExists() throws FileNotFoundException {
        new ExportRMHTML().export(this.database, new File("./test.html"));
        assertTrue(new FileReader("./test.html").toString()!=null);
    }


}
