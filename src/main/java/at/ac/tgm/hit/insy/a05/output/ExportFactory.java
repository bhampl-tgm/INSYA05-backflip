package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.Main;
import at.ac.tgm.hit.insy.a05.structur.Database;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Martin Kritzl on 18.01.2015.
 */
public class ExportFactory {

    public Exportable chooseExport(String format) {
        if (format.equalsIgnoreCase("EER"))
            return new ExportEERDotfile();
        else if(format.equalsIgnoreCase("RM"))
            return new ExportRMHTML();
        return null;
    }

}
