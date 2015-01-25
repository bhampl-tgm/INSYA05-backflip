package at.ac.tgm.hit.insy.a05.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestDatabaseMapper.class, TestDatabaseMapper.class, TestExportFactory.class, TestMain.class})
/**
 * Tests all test-classes.
 */
public class TestAll {
    public static void main(String[] args) {
        JUnitCore.main("at.ac.tgm.hit.insy.a05.test.AllTests");
    }
}
