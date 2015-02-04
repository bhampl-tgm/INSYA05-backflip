package at.ac.tgm.hit.insy.a05.test;

import at.ac.tgm.hit.insy.a05.output.ExportEERDotfile;
import at.ac.tgm.hit.insy.a05.output.ExportFactory;
import at.ac.tgm.hit.insy.a05.output.ExportRMHTML;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the Methods of the ExportFactory Class.
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class TestExportFactory {

    @Test
    public void testRM() {
        assertTrue(ExportFactory.chooseExport("rm") instanceof ExportRMHTML);
    }

    @Test
     public void testEER() {
        assertTrue(ExportFactory.chooseExport("eer") instanceof ExportEERDotfile);
    }

    @Test
    public void testAllLetterRM() {
        assertTrue(ExportFactory.chooseExport("rM") instanceof ExportRMHTML);
    }

    @Test
    public void testAllLetterEER() {
        assertTrue(ExportFactory.chooseExport("EeR") instanceof ExportEERDotfile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonAvailableFormat() {
        ExportFactory.chooseExport("else");
    }
}
