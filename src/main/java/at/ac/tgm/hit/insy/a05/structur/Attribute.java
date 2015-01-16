package at.ac.tgm.hit.insy.a05.structur;

public class Attribute {

    private String name;

    private Reference reference;

    public Attribute(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (!name.equals(attribute.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
