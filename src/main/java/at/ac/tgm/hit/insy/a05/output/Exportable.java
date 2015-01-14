package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structur.Database;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Interface for Export a database
 */
public interface Exportable extends Closeable {

    /**
     * Export database to file
     *
     * @param database database
     * @param file file
     */
    public void export(Database database, File file) throws FileNotFoundException;

}
