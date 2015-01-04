package at.ac.tgm.hit.insy.a05.input.output;

import java.io.File;
import at.ac.tgm.hit.insy.a05.structur.Database;

public interface Exportable {

    public abstract boolean export(Database database, File file);

}
