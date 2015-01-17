package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structur.Attribute;
import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.structur.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Exports a Database to a file in HTML format
 *
 * @see at.ac.tgm.hit.insy.a05.output.Exportable
 */
public class ExportRMHTML implements Exportable {


    private PrintWriter output;
    private Database database;

    private static final String beginHTML = "<!DOCTYPE html>"
            + System.lineSeparator()
            + "<html>"
            + System.lineSeparator()
            + "\t<body>";

    private static final String endHTML = "\t</body>"
            + System.lineSeparator()
            + "</html>";

    private static final String beginAttributes = "(";

    private static final String endAttributes = ")";

    private static final String beginTable = "\t\t";

    private static final String beginPK = "<u>";

    private static final String endPK = "</u>";

    private static final String endAttribute = ", ";

    @Override
    public void export(Database database, File file) throws FileNotFoundException {
        this.database = database;
        this.output = new PrintWriter(file);
        output.println(ExportRMHTML.beginHTML);

        //TODO implement HTML
        this.printAll();

        output.println(ExportRMHTML.endHTML);
        this.close();
    }

    private void printAll() {
        //TODO finish HTML
        for (Table table : this.database.getTables()) {
            this.output.print(ExportRMHTML.beginTable);
            this.output.print(table.getName());
            this.output.print(ExportRMHTML.beginAttributes);
            for (Attribute attribute : table.getPrimaryKeys()) {
                this.output.print(ExportRMHTML.beginPK);
                this.output.print(attribute.getName());
                this.output.print(ExportRMHTML.endPK);
                this.output.print(ExportRMHTML.endAttribute);
            }
            for (Attribute attribute : table.getAttributes()) {
                this.output.print(attribute.getName());
                this.output.print(ExportRMHTML.endAttribute);
            }
            this.output.println(ExportRMHTML.endAttributes);
        }
    }

    @Override
    public void close() {
        this.output.close();
    }
}
