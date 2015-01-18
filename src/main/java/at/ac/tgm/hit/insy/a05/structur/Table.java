package at.ac.tgm.hit.insy.a05.structur;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Table of a database
 *
 * @author Martin Kritzl
 */
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

    /**
     * Returns the Attributes that are not a primary key
     *
     * @return normal Attributes
     */
    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    /**
     * Returns the Attributes that are primary keys
     *
     * @return Primray Keys
     */
    public Set<Attribute> getPrimaryKeys() {
        return this.primaryKeys;
    }

    public void addPrimaryKey(Attribute primaryKey) {
        this.primaryKeys.add(primaryKey);
    }

    /**
     * Returns the Attribute with the given Name
     *
     * @param attributeName the name of the wanted Attribute
     * @return the wanted Attribute
     */
    public Attribute getAttribute(String attributeName) {
        for (Attribute attribute : this.attributes) {
            if (attribute.getName().equals(attributeName)) return attribute;
        }
        return null;
    }

    /**
     * Returns the Primary Key with the given Name
     *
     * @param primaryKeyName the name of the wanted Primary Key
     * @return the wanted Primary Key
     */
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
