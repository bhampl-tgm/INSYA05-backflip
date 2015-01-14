package at.ac.tgm.hit.insy.a05.structur;

import java.util.List;

public class Table {

    private String name;

    private List<Attribut> attributes;

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<Attribut> getAttributes() {
        return this.attributes;
    }

    public void addAttribute(Attribut attribute) {
        this.attributes.add(attribute);
    }

}
