package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.Main;
import at.ac.tgm.hit.insy.a05.structur.Database;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Returns the correct exporting object depending on the chosen format.
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class ExportFactory {

    /**
     * Returns the correct exporting object depending on the chosen format.
     *
     * @param format the required name of the format
     * @return an exportable Object depending on the format
     */
    public static Exportable chooseExport(String format) {
        if (format.equalsIgnoreCase("EER"))
            return new ExportEERDotfile();
        else if(format.equalsIgnoreCase("RM"))
            return new ExportRMHTML();
        return null;
    }

}
