package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structur.Database;

import java.io.File;
import java.io.PrintWriter;

public class ExportRMDotfile implements Exportable {


    private PrintWriter output;

    private final static String startER = "graph ER {\n";

    private final static String endER = "graph ER {\n";

    @Override
    public void export(Database database, File file) {
    }

    @Override
    public void close() {

    }
}
