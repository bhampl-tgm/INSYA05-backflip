package at.ac.tgm.hit.insy.a05.structure;

/**
 * Represents an Attribute of a Table and might be a foreign key of another table
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class Attribute {

    private String name;

    private Reference reference;

    private boolean unique;

    private Table table;

    public Attribute(String name, Table table) {
        this.name = name;
        this.table = table;
        this.unique = false;
    }

    public String getName() {
        return name;
    }

    public Reference getReference() {
        return this.reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    /**
     * A name of an Attribute in one table could only exists once.
     * So only the name specifies the identification.
     *
     * @param o Object that should be compared
     * @return If the Object equals the name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        return name.equals(attribute.name);

    }

    /**
     * The hashcode is only made out of the name
     *
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
