package at.ac.tgm.hit.insy.a05.structur;

import java.util.HashSet;
import java.util.Set;

public class Table {

    private String name;

    private Set<Attribute> attributes;

    private Set<Attribute> primaryKeys;

    public Table(String name) {
        this.name = name;
        this.attributes = new HashSet<Attribute>();
        this.primaryKeys = new HashSet<Attribute>();
    }

    public String getName() {
        return this.name;
    }

    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    public Set<Attribute> getPrimaryKeys() {
        return this.primaryKeys;
    }

    public void addPrimaryKey(Attribute primaryKey) {
        this.primaryKeys.add(primaryKey);
    }

    public Attribute getAttribute(String attributeName) {
        for (Attribute attribute : this.attributes) {
            if (attribute.getName().equals(attributeName)) return attribute;
        }
        return null;
    }

    public Attribute getPrimaryKey(String primaryKeyName) {
        for (Attribute primaryKey : this.primaryKeys) {
            if (primaryKey.getName().equals(primaryKeyName)) return primaryKey;
        }
        return null;
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

}
