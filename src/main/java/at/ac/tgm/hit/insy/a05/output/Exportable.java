package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structure.Database;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Interface for Export a database
 *
 * @version 0.1
 * @author Burkhard Hampl [burkhard.hampl@student.tgm.ac.at]
 */
public interface Exportable extends Closeable {

    /**
     * Export database to file
     *
     * @param database database
     * @param file file
     * @throws java.io.FileNotFoundException if the given file is not found
     */
    public void export(Database database, File file) throws FileNotFoundException;

}
