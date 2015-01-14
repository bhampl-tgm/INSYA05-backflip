package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structur.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ExportEERText implements Exportable {

    private PrintWriter output;

    @Override
    public void export(Database database, File file) throws FileNotFoundException {
        this.output = new PrintWriter(file);
    }

    @Override
    public void close() {
        this.output.close();
    }
}
