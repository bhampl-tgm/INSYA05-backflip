package at.ac.tgm.hit.insy.a05.structur;

import java.util.HashSet;
import java.util.Set;

public class Table {

    private String name;

    private Set<Attribut> attributes;

    private Set<Attribut> primaryKeys;

    public Table(String name) {
        this.name = name;
        this.attributes = new HashSet<Attribut>();
        this.primaryKeys = new HashSet<Attribut>();
    }

    public String getName() {
        return this.name;
    }

    public Set<Attribut> getAttributes() {
        return this.attributes;
    }

    public Set<Attribut> getPrimaryKeys() {
        return this.primaryKeys;
    }

    public void addPrimaryKey(Attribut primaryKey) {
        this.primaryKeys.add(primaryKey);
    }

    public void addAttribute(Attribut attribute) {
        this.attributes.add(attribute);
    }

}
