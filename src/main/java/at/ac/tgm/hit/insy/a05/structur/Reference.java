package at.ac.tgm.hit.insy.a05.structur;

public class Reference {

    private Table refTable;

    private Attribut refAttribute;

    public Reference(Table refTable, Attribut refAttribute) {
        this.refTable = refTable;
        this.refAttribute = refAttribute;
    }

    public Table getRefTable() {
        return this.refTable;
    }

    public Attribut getRefAttribute() {
        return this.refAttribute;
    }

}
