package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structur.Database;

import java.io.File;
import java.io.PrintWriter;

public class ExportRMTXT implements Exportable {


    private PrintWriter output;

    @Override
    public void export(Database database, File file) {
    }

    @Override
    public void close() {

    }
}
