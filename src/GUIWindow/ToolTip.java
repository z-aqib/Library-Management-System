package GUIWindow;

import java.awt.*;

public class ToolTip {

    private ColoredButtons label;
    static ToolTip toolTip = new ToolTip();

    // make constructor private so the instance is not instantiated from outside
    private ToolTip() {

    }

    public static ToolTip getToolTip() {
        return toolTip;
    }

    public void declareLabel(String text, Point position) {
        this.label = new ColoredButtons(text, position, text.length() * 10,
                20, Color.white, Color.white,
                false, Color.black, Color.black);
    }

    public void paint(Graphics g) {
        if (this.label == null) {
            declareLabel("", new Point());
        }
        this.label.paint(g);
    }

    public void setPosition(Point newPosition) {
        if (this.label == null) {
            this.label = new ColoredButtons("", newPosition, 0,
                    20, Color.white, Color.white,
                    false, Color.black, Color.BLACK);
        }
        this.label.setTopLeft(newPosition);
    }

    @Override
    public String toString() {
        return "ToolTip{" + "label=" + label + '}';
    }

}
