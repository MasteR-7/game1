package units;

import java.io.Serializable;

public class Unit implements Element, Serializable {

    private final Element.Name name;
    private Position position;

    public Unit(final Element.Name name) {
        this.name = name;
    }

    public Unit(final Element.Name name, final Position position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}