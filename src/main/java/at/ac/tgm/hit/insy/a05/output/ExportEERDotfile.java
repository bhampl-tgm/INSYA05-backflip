package at.ac.tgm.hit.insy.a05.output;

import at.ac.tgm.hit.insy.a05.structure.Attribute;
import at.ac.tgm.hit.insy.a05.structure.Database;
import at.ac.tgm.hit.insy.a05.structure.Reference;
import at.ac.tgm.hit.insy.a05.structure.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Exports a Database to a File in Graphviz Dot format
 *
 * @author Burkhard Hampl [burkhard.hampl@student.tgm.ac.at]
 * @version 0.1
 * @see at.ac.tgm.hit.insy.a05.output.Exportable
 */
public class ExportEERDotfile implements Exportable {

    private PrintWriter output;

    private Database database;

    private List<Attribute> references;

    private final static String startER = "graph ER {";

    private final static String endER = "}";

    private final static String startTables = "\tnode [shape=box]; ";

    private final static String startAttributes = "\tnode [shape=ellipse];";

    private final static String startNodeLablePK = "\t\t{node [label=<<u>";

    private final static String endLablePK = "</u>>] ";

    private final static String startNodeLableNormal = "\t\t{node [label=\"";

    private final static String endLableNormal = "\"] ";

    private final static String endNode = "}";

    private final static String endAttribute = "; ";

    private final static String connectionSeperator = " -- ";

    private final static String startAttributesToTable = "\t";

    private final static String startReference = "\tnode [shape=diamond];";

    private final static String startConnection = "\t";

    private final static String startCardinality = " [label=\"";

    private final static String endCardinality = "\"]";
    // ,len=1.00
    private static final String unique = "&ltUNIQUE&gt;";

    @Override
    public void export(Database database, File file) throws FileNotFoundException {
        this.database = database;
        this.output = new PrintWriter(file);
        this.references = new ArrayList<Attribute>();

        this.output.println(ExportEERDotfile.startER);
        this.printTables();
        this.printAttributes();
        this.printReferences();

        //TODO finish dot
        this.printAttributesToTables();
        this.printConnections();

        this.output.println(ExportEERDotfile.endER);
        this.close();
    }

    /**
     * Prints all attributes (primary keys and attributes but not references)
     */
    private void printAttributes() {
        this.output.println(ExportEERDotfile.startAttributes);
        for (Table table : this.database.getTables()) {

            // print all primary keys
            for (Attribute attribute : table.getPrimaryKeys()) {
                if (attribute.getReference() == null) {
                    this.output.print(ExportEERDotfile.startNodeLablePK);
                    this.output.print(attribute.getName());
                    this.output.print(ExportEERDotfile.endLablePK);
                    this.output.print(table.getName());
                    this.output.print(attribute.getName());
                    this.output.print(ExportEERDotfile.endAttribute);
                    this.output.println(ExportEERDotfile.endNode);
                }
            }

            // print all attributes
            for (Attribute attribute : table.getAttributes()) {
                if (attribute.getReference() == null) {
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
    }

    /**
     * Prints all connections between attributes and tables
     */
    private void printAttributesToTables() {
        for (Table table : this.database.getTables()) {
            List<Attribute> allAttributes = new ArrayList<Attribute>();
            allAttributes.addAll(table.getPrimaryKeys());
            allAttributes.addAll(table.getAttributes());
            for (Attribute attribute : allAttributes) {
                if (attribute.getReference() == null) {
                    this.output.print(ExportEERDotfile.startAttributesToTable);
                    this.output.print(table.getName());
                    this.output.print(attribute.getName());
                    this.output.print(ExportEERDotfile.connectionSeperator);
                    this.output.print(table.getName());
                    this.output.println(ExportEERDotfile.endAttribute);
                }
            }
        }
    }

    /**
     * Prints all references
     */
    private void printReferences() {
        this.output.println(ExportEERDotfile.startReference);
        for (Table table : this.database.getTables()) {
            List<Attribute> allAttributes = new ArrayList<Attribute>();
            allAttributes.addAll(table.getPrimaryKeys());
            allAttributes.addAll(table.getAttributes());
            for (Attribute attribute : allAttributes) {
                Reference reference = attribute.getReference();
                if (attribute.getReference() != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ExportEERDotfile.startNodeLableNormal);
                    sb.append(ExportEERDotfile.endLableNormal);
                    sb.append(table.getName());
                    sb.append(attribute.getName());
                    sb.append(attribute.getReference().getRefTable().getName());
                    sb.append(attribute.getReference().getRefAttribute().getName());
                    sb.append(ExportEERDotfile.endAttribute);
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(ExportEERDotfile.endNode);
                    boolean duplicate = false;

                    // checks if the table gets more then one key form a other table
                    duplicateBreak:
                    if (reference.getRefTable().getPrimaryKeys().size() > 1) {
                        for (Attribute attribute1 : reference.getRefTable().getPrimaryKeys()) {
                            for (Attribute attribute2 : this.references) {
                                if (!attribute2.equals(attribute) && attribute2.getReference().getRefAttribute().equals(attribute1)) {
                                    duplicate = true;
                                    break duplicateBreak;
                                }
                            }
                        }
                    }
                    if (!this.references.contains(attribute) && !this.references.contains(attribute.getReference().getRefAttribute()) && !duplicate) {
                        this.references.add(attribute);
                        this.output.println(sb.toString());
                    }
                }
            }
        }
    }

    /**
     * Prints all connections between references
     */
    private void printConnections() {
        //TODO add Cardinality
        for (Attribute attribute : this.references) {
            this.output.print(ExportEERDotfile.startConnection);
            this.output.print(attribute.getTable().getName());
            this.output.print(ExportEERDotfile.connectionSeperator);
            this.output.print(attribute.getTable().getName());
            this.output.print(attribute.getName());
            this.output.print(attribute.getReference().getRefTable().getName());
            this.output.print(attribute.getReference().getRefAttribute().getName());
            this.output.print(ExportEERDotfile.startCardinality);

            // prints Cardinality
            //TODO Cardinality: HERE!
            char refCardinality;
            if (false) {
                output.print("n");
                refCardinality = 'm';
            } else if (attribute.isUnique()) {
                output.print("1");
                refCardinality = '1';
            } else {
                output.print("n");
                refCardinality = '1';
            }

            this.output.print(ExportEERDotfile.endCardinality);
            this.output.println(ExportEERDotfile.endAttribute);

            this.output.print(ExportEERDotfile.startConnection);
            this.output.print(attribute.getReference().getRefTable().getName());
            this.output.print(ExportEERDotfile.connectionSeperator);
            this.output.print(attribute.getTable().getName());
            this.output.print(attribute.getName());
            this.output.print(attribute.getReference().getRefTable().getName());
            this.output.print(attribute.getReference().getRefAttribute().getName());
            this.output.print(ExportEERDotfile.startCardinality);
            this.output.print(refCardinality);

            this.output.print(ExportEERDotfile.endCardinality);
            this.output.println(ExportEERDotfile.endAttribute);
        }
    }

    /**
     * Prints all tables
     */
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
