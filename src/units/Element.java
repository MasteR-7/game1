package units;

public interface Element {
    Element.Name getName();

    Position getPosition();
    void setPosition(final Position position);

    public enum Name {
        FARMER,
        WOLF,
        GOAT,
        CABBAGE
    }
}