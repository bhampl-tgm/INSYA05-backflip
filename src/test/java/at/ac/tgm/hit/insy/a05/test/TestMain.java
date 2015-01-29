package at.ac.tgm.hit.insy.a05.test;

import at.ac.tgm.hit.insy.a05.Main;
import at.ac.tgm.hit.insy.a05.structure.Database;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.mockito.Mockito;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/**
 * Tests the Methods of the Main Class.
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class TestMain {

    protected static Appender testAppender;
    private Main main;
    private Connection emptyDatabaseConnection;
    public static final String TESTPATH = "." + File.separator + "build" + File.separator;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void initialize() throws SQLException {
        this.main = new Main();
        testAppender = new Appender();
        Logger.getRootLogger().addAppender(testAppender);

        this.emptyDatabaseConnection = Mockito.mock(Connection.class);
        DatabaseMetaData meta = Mockito.mock(DatabaseMetaData.class);
        ResultSet tables = Mockito.mock(ResultSet.class);
        Mockito.when(this.emptyDatabaseConnection.getMetaData()).thenReturn(meta);
        Mockito.when(meta.getTables(null, null, "%", null)).thenReturn(tables).thenReturn(tables);
    }

    @Test
    public void testConnection() {
        this.main.getConnection("localhost","backflip", "insy4","blabla");
        assertTrue(testAppender.getLog().size()==0);
    }

    @Test
    public void testParsingFails() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertTrue(testAppender.getLog().get(0).getMessage().toString().contains("java -jar "));
            }
        });
        this.main.parse(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-f", "falseFormat", "-p", "blabla"});
    }

    @Test
    public void testConnectionFails() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertTrue(testAppender.getLog().get(0).getMessage().toString().contains("refused"));
            }
        });
        this.main.getConnection("localhost","", "insy4","password");
    }


    @Test
    public void testMapping() throws SQLException {
        this.main.mapDatabase(this.emptyDatabaseConnection);
        assertTrue(testAppender.getLog().size()==0);
    }

    @Test
    public void testExpoting() {
        this.main.export(new Database("test"), new File(TESTPATH + "test.html"), "rm");
        assertTrue(testAppender.getLog().get(0).getMessage().toString().contains("success"));
    }

    @Test
    public void testCLI() {
        this.main.parse(new String[]{"-h", "127.0.0.1", "-u", "insy4", "-d", "backflip", "-o", "test.html", "-f", "rm", "-p", "blabla"});
        assertTrue(testAppender.getLog().size()==0);
    }

    @Test
    public void testfalseConnection() throws SQLException {
        Mockito.when(this.emptyDatabaseConnection.getMetaData()).thenThrow(SQLException.class);
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertTrue(TestMain.testAppender.getLog().get(0).getMessage().toString().contains("be mapped"));
            }
        });
        this.main.mapDatabase(this.emptyDatabaseConnection);
    }

    @Test
    public void testfalseFile() {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertTrue(TestMain.testAppender.getLog().get(0).getMessage().toString().contains("not created"));
            }
        });
        this.main.export(new Database("test"), new File(""), "rm");
    }
}
