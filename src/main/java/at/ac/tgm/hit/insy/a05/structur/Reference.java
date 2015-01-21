package at.ac.tgm.hit.insy.a05.structur;

/**
 * Represents a Referenz or a foreign Key of a Table
 *
 * @author Martin Kritzl [mkritzl@student.tgm.ac.at]
 */
public class Reference {

    private Table refTable;

    private Attribute refAttribute;

    public Reference(Table refTable, Attribute refAttribute) {
        this.refTable = refTable;
        this.refAttribute = refAttribute;
    }

    public Table getRefTable() {
        return this.refTable;
    }

    public Attribute getRefAttribute() {
        return this.refAttribute;
    }

}
