package at.ac.tgm.hit.insy.a05.structur;

import java.util.List;

public class Database {

    private String name;

    private List<Table> tables;

    private Table table;

    public Database(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<Table> getTables() {
        return this.tables;
    }

    public void addTable(Table table) {
        this.tables.add(table);
    }

}
