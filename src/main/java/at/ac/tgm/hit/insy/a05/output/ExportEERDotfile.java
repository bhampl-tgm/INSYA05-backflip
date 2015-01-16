package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structur.Attribute;
import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.structur.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ExportEERDotfile implements Exportable {

    private PrintWriter output;
    private Database database;

    private final static String startER = "graph ER {";

    private final static String endER = "}";

    private final static String tables = "\tnode [shape=box]; ";

    private final static String attributes = "\tnode [shape=ellipse]; ";

    private final static String startNodeLable = "\t\t{node [label=\"";

    private final static String endLable = "\"] ";

    private final static String endNode = "}";

    private final static String endAttribute = "; ";

    @Override
    public void export(Database database, File file) throws FileNotFoundException {
        this.database = database;
        this.output = new PrintWriter(file);
        this.output.println(ExportEERDotfile.startER);
        this.printTables();
        this.printAttributes();

        this.output.println(ExportEERDotfile.endER);
    }

    private void printAttributes() {
        this.output.println(ExportEERDotfile.attributes);
        for (Table table : database.getTables()) {
            for (Attribute attribute : table.getPrimaryKeys()) {
                //TODO PK underline
                this.output.print(ExportEERDotfile.startNodeLable);
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.endLable);
                this.output.print(table.getName());
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.endAttribute);
                this.output.println(ExportEERDotfile.endNode);
            }

            for (Attribute attribute : table.getAttributes()) {
                this.output.print(ExportEERDotfile.startNodeLable);
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.endLable);
                this.output.print(table.getName());
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.endAttribute);
                this.output.println(ExportEERDotfile.endNode);
            }
        }

    }

    private void printTables() {
        this.output.print(ExportEERDotfile.tables);
        for (Table table : database.getTables()) {
            this.output.print(table.getName());
            this.output.print(ExportEERDotfile.endAttribute);
        }
        this.output.println();
    }


    @Override
    public void close() {
        this.output.close();
    }
}
