package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structur.Attribute;
import at.ac.tgm.hit.insy.a05.structur.Database;
import at.ac.tgm.hit.insy.a05.structur.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Exports a Database to a File in Graphviz Dot format
 *
 * @see at.ac.tgm.hit.insy.a05.output.Exportable
 */
public class ExportEERDotfile implements Exportable {

    private PrintWriter output;
    private Database database;

    private final static String startER = "graph ER {";

    private final static String endER = "}";

    private final static String startTables = "\tnode [shape=box]; ";

    private final static String startAttributes = "\tnode [shape=ellipse]; ";

    private final static String startNodeLablePK = "\t\t{node [label=<<u>";

    private final static String endLablePK = "</u>>] ";

    private final static String startNodeLableNormal = "\t\t{node [label=\"";

    private final static String endLableNormal = "\"] ";

    private final static String endNode = "}";

    private final static String endAttribute = "; ";

    private final static String startAttributesToTable = " -- ";

    @Override
    public void export(Database database, File file) throws FileNotFoundException {
        this.database = database;
        this.output = new PrintWriter(file);
        this.output.println(ExportEERDotfile.startER);
        this.printTables();
        this.printAttributes();

        //TODO finish dot
        this.printAttributesToTables();
        this.output.println(ExportEERDotfile.endER);

        this.close();
    }

    private void printAttributes() {
        this.output.println(ExportEERDotfile.startAttributes);
        for (Table table : this.database.getTables()) {
            for (Attribute attribute : table.getPrimaryKeys()) {
                this.output.print(ExportEERDotfile.startNodeLablePK);
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.endLablePK);
                this.output.print(table.getName());
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.endAttribute);
                this.output.println(ExportEERDotfile.endNode);
            }

            for (Attribute attribute : table.getAttributes()) {
                this.output.print(ExportEERDotfile.startNodeLableNormal);
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.endLableNormal);
                this.output.print(table.getName());
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.endAttribute);
                this.output.println(ExportEERDotfile.endNode);
            }
        }


    }

    private void printAttributesToTables() {
        for (Table table : this.database.getTables()) {
            List<Attribute> allAttributes = new ArrayList<Attribute>();
            allAttributes.addAll(table.getPrimaryKeys());
            allAttributes.addAll(table.getAttributes());
            for (Attribute attribute : allAttributes) {
                this.output.print(table.getName());
                this.output.print(attribute.getName());
                this.output.print(ExportEERDotfile.startAttributesToTable);
                this.output.print(table.getName());
                this.output.println(ExportEERDotfile.endAttribute);
            }
        }
    }

    private void printTables() {
        this.output.print(ExportEERDotfile.startTables);
        for (Table table : this.database.getTables()) {
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
