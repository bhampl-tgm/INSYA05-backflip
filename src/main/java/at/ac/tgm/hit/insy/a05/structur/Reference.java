package at.ac.tgm.hit.insy.a05.structur;

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
