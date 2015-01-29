package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structure.Attribute;
import at.ac.tgm.hit.insy.a05.structure.Database;
import at.ac.tgm.hit.insy.a05.structure.Reference;
import at.ac.tgm.hit.insy.a05.structure.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Exports a Database to a file in HTML format
 *
 * @author Burkhard Hampl [burkhard.hampl@student.tgm.ac.at]
 * @version 0.1
 * @see at.ac.tgm.hit.insy.a05.output.Exportable
 */
public class ExportRMHTML implements Exportable {


    private PrintWriter output;
    private Database database;

    private static final String beginHTML = "<!DOCTYPE html>"
            + System.lineSeparator()
            + "<html>"
            + System.lineSeparator()
            + "\t<head>"
            + System.lineSeparator()
            + "\t\t<meta charset='UTF-8'/>"
            + System.lineSeparator()
            + "\t\t<title>RM</title>"
            + System.lineSeparator()
            + "\t</head>"
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

    private static final String endLine = "<br />";

    private static final String refTableSeperator = ".";

    private static final String localNameSeperator = ":";

    private static final String beginFK = "<i>";

    private static final String endFK = "</i>";

    private static final String unique = "&lt;UNIQUE&gt;";

    private static final String notNull = "&lt;NOT NULL&gt;";

    @Override
    public void export(Database database, File file) throws FileNotFoundException {
        this.database = database;
        this.output = new PrintWriter(file);

        output.println(ExportRMHTML.beginHTML);
        this.printAll();

        output.println(ExportRMHTML.endHTML);
        this.close();
    }

    private void printAll() {
        StringBuilder sb;
        for (Table table : this.database.getTables()) {
            sb = new StringBuilder();

            // print beginning of line
            sb.append(ExportRMHTML.beginTable);
            sb.append(table.getName());
            sb.append(ExportRMHTML.beginAttributes);

            // print primary keys
            for (Attribute attribute : table.getPrimaryKeys()) {
                Reference reference = attribute.getReference();
                if (reference == null) {
                    sb.append(ExportRMHTML.beginPK);
                    sb.append(attribute.getName());
                    sb.append(ExportRMHTML.endPK);
                    sb.append(ExportRMHTML.endAttribute);
                } else {
                    sb.append(ExportRMHTML.beginPK);
                    this.printFK(sb, attribute, reference);
                    sb.append(ExportRMHTML.endPK);
                    sb.append(ExportRMHTML.endAttribute);
                }
            }

            // print other attribute
            for (Attribute attribute : table.getAttributes()) {
                if (attribute.getReference() == null) {
                    sb.append(attribute.getName());
                    if (attribute.isUnique()) sb.append(ExportRMHTML.unique);
                    if (attribute.isNotNull()) sb.append(ExportRMHTML.notNull);
                    sb.append(ExportRMHTML.endAttribute);
                }
            }

            // print foreign keys
            for (Attribute attribute : table.getAttributes()) {
                Reference reference = attribute.getReference();
                if (reference != null) {
                    this.printFK(sb, attribute, reference);
                    if (attribute.isUnique()) sb.append(ExportRMHTML.unique);
                    if (attribute.isNotNull()) sb.append(ExportRMHTML.notNull);
                    sb.append(ExportRMHTML.endAttribute);
                }
            }

            // print end of line
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.append(ExportRMHTML.endAttributes);
            sb.append(ExportRMHTML.endLine);
            this.output.println(sb.toString());

        }
    }

    private void printFK(StringBuilder sb, Attribute attribute, Reference reference) {
        sb.append(ExportRMHTML.beginFK);
        sb.append(attribute.getName());
        sb.append(ExportRMHTML.localNameSeperator);
        sb.append(reference.getRefTable().getName());
        sb.append(ExportRMHTML.refTableSeperator);
        sb.append(reference.getRefAttribute().getName());
        sb.append(ExportRMHTML.endFK);
    }

    @Override
    public void close() {
        this.output.close();
    }
}
