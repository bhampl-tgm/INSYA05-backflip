package at.ac.tgm.hit.insy.a05.input.output;

import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.input.File;

public interface Exportable {

	public abstract boolean export(Database database, File file);

}
