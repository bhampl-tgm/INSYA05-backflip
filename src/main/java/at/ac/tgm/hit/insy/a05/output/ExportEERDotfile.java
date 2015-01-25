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

    private void printAttributes() {
        this.output.println(ExportEERDotfile.startAttributes);
        for (Table table : this.database.getTables()) {
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
                    //TODO :( Fix that
                    if (reference.getRefTable().getPrimaryKeys().size() > 1) {
                        for (Attribute attribute1 : allAttributes) {
                            if ((!attribute.equals(attribute1)) && attribute.getTable().equals(attribute1.getTable())) {
                                  for (Attribute attribute2: reference.getRefTable().getPrimaryKeys()) {
                                      //this.references.contains();
                                  }
                            }
                        }
                    }
                    if (!this.references.contains(attribute) && !this.references.contains(attribute.getReference().getRefAttribute())) {
                        this.references.add(attribute);
                        this.output.println(sb.toString());
                    }
                }
            }
        }
    }

    private void printConnections() {
        for (Attribute attribute : this.references) {
            this.output.print(ExportEERDotfile.startConnection);
            this.output.print(attribute.getTable().getName());
            this.output.print(ExportEERDotfile.connectionSeperator);
            this.output.print(attribute.getTable().getName());
            this.output.print(attribute.getName());
            this.output.print(attribute.getReference().getRefTable().getName());
            this.output.print(attribute.getReference().getRefAttribute().getName());
            this.output.println(ExportEERDotfile.endAttribute);

            this.output.print(ExportEERDotfile.startConnection);
            this.output.print(attribute.getReference().getRefTable().getName());
            this.output.print(ExportEERDotfile.connectionSeperator);
            this.output.print(attribute.getTable().getName());
            this.output.print(attribute.getName());
            this.output.print(attribute.getReference().getRefTable().getName());
            this.output.print(attribute.getReference().getRefAttribute().getName());
            this.output.println(ExportEERDotfile.endAttribute);
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
