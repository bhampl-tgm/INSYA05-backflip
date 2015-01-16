package at.ac.tgm.hit.insy.a05.structur;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private String name;

    private List<Table> tables;

    public Database(String name) {
        this.name = name;
        this.tables = new ArrayList<Table>();
    }

    public Table getTable(String tableName) {
        for (Table table : this.tables) {
            if (table.getName().equals(tableName)) return table;
        }
        return null;
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
