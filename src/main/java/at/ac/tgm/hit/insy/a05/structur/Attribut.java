package at.ac.tgm.hit.insy.a05.structur;

public class Attribut {

    private String name;

    private boolean primaryKey;

    private Reference reference;

    public Attribut(String name, boolean pk) {
        this.name = name;
        this.primaryKey = pk;
    }

    public String getName() {
        return name;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public Reference getReference() {
        return this.reference;
    }

}
