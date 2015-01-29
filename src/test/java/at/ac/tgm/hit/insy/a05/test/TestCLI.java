package at.ac.tgm.hit.insy.a05.test;

import at.ac.tgm.hit.insy.a05.input.CLI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin Kritzl on 26.01.2015.
 */
public class TestCLI {

    private CLI cli;

    @Before
    public void initialize() {
        this.cli = new CLI();
    }
    
    @Test
    public void testHostname() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-o", "test.html", "-f", "rm", "-p", "blabla"});
        assertEquals("127.0.0.1", this.cli.getHostname());
    }

    @Test
    public void testUser() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-o", "test.html", "-f", "rm", "-p", "blabla"});
        assertEquals("insy4", this.cli.getUser());
    }

    @Test
    public void testDatabase() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-o", "test.html", "-f", "rm", "-p", "blabla"});
        assertEquals("backflip", this.cli.getDatabaseName());
    }

    @Test
    public void testOutputFile() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-o", "test.html", "-f", "rm", "-p", "blabla"});
        assertEquals("test.html", this.cli.getFile().getPath());
    }

    @Test
    public void testFormatRM() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-o", "test.html", "-f", "rm", "-p", "blabla"});
        assertEquals("rm", this.cli.getFormat());
    }

    @Test
    public void testFormatEER() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-o", "test.dot", "-f", "eer", "-p", "blabla"});
        assertEquals("eer", this.cli.getFormat());
    }

    @Test
    public void testPassword() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-o", "test.html", "-f", "rm", "-p", "blabla"});
        assertEquals("blabla", this.cli.getPassword());
    }

    @Test
    public void testEmptyFileFormatRM() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-f", "rm", "-p", "blabla"});
        assertEquals("rm.html", this.cli.getFile().getPath());
    }

    @Test
    public void testEmptyFileFormatEER() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-f", "eer", "-p", "blabla"});
        assertEquals("eer.dot", this.cli.getFile().getPath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArguments() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-f", "eer", "-p", "blabla"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongFormat() {
        this.cli.parseArgs(new String[]{"-h", "127.0.0.1", "-u", "insy4",  "-d", "backflip", "-f", "falseFormat", "-p", "blabla"});
    }
}
