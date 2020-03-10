package game;


import javafx.scene.text.Text;
import units.Element;

public class Boat extends Text {

    @Nullable
    private Element element;

    public Boat(double x, double y) {
        super(x, y, "BOAT");
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
