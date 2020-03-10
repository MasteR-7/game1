package units;

import javafx.scene.text.Text;

public class TextUnit extends Text {
    private Element element;

    public TextUnit(Element element, double x, double y) {
        super(x, y, element.getName().toString());
        this.element = element;
    }

    public TextUnit(double x, double y) {
        super(x, y, "ПРАВЫЙ БЕРЕГ");
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
