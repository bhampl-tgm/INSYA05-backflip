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


    /**
     * The Attribute is specified by its name and table
     *
     * @param o Object that should be compared
     * @return If the Object equals the name and table
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (!name.equals(attribute.name)) return false;
        if (!table.equals(attribute.table)) return false;

        return true;
    }

    /**
     * The hashcode is only made out of the name and table
     *
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + table.hashCode();
        return result;
    }

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

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public Table getTable() {
        return table;
    }
}
