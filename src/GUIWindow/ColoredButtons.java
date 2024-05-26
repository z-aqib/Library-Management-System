package GUIWindow;

import java.awt.*;

public class ColoredButtons {

    private String name; // the name of the color button
    private Point topLeft; // its topleft point
    private int width; // width of the rectangle
    private int height; // height of the rectangle
    private boolean movedStatus;
    private boolean clickedStatus;
    private ToolTip toolTip;
    private Color pressedFillColor;
    private Color pressedTextColor;
    private Color unpressedFillColor;
    private Color unpressedTextColor;

    public ColoredButtons(String name, int x, int y, int width, int height,
            Color unpressedfillColor, Color pressedfillColor, boolean tooltip, Color pressedTextColor,
            Color unpressedTextColor) {
        this(name, new Point(x, y), width, height, unpressedfillColor, pressedfillColor, tooltip,
                pressedTextColor, unpressedTextColor);
    }

    public ColoredButtons(String name, Point p, int width, int height,
            Color unpressedfillColor, Color pressedfillColor, boolean tooltip, Color pressedTextColor,
            Color unpressedTextColor) {
        this.name = name;
        this.topLeft = p;
        this.width = width;
        this.height = height;
        this.movedStatus = false;
        this.clickedStatus = false;
        if (tooltip == true) {
            this.toolTip = ToolTip.getToolTip();
            this.toolTip.declareLabel(name, p);
        }
        this.pressedFillColor = pressedfillColor;
        this.unpressedFillColor = unpressedfillColor;
        this.pressedTextColor = pressedTextColor;
        this.unpressedTextColor = unpressedTextColor;
    }

    public boolean ifMoved(int x, int y) {
        if (x >= topLeft.x && x <= (topLeft.x) + width && y >= topLeft.y && y <= (topLeft.y) + height) {
            this.movedStatus = true;
            if (this.toolTip != null) {
                this.toolTip.declareLabel(this.name, new Point(x, y));
            }
        } else {
            this.movedStatus = false;
        }
        return this.movedStatus;
    }

    public boolean ifSelected(int x, int y) {
        this.clickedStatus = ifMoved(x, y);
        return this.clickedStatus;
    }

    public void make(boolean clicked) {
        if (clicked == true) {
            ifSelected(generateValue(topLeft.x, topLeft.x + width, true),
                    generateValue(topLeft.y, topLeft.y + height, true));
        } else {
            ifSelected(generateValue(topLeft.x, topLeft.x + width, false),
                    generateValue(topLeft.y, topLeft.y + height, false));
        }
    }

    public int generateValue(int start, int end, boolean within) {
        if (within == true) {
            int x = (int) (Math.random() * end);
            while (x < start || x > end) {
                x = (int) (Math.random() * end);
            }
            return x;
        } else {
            int x = (int) (Math.random() * (2 * end));
            while (x >= start || x <= end) {
                x = (int) (Math.random() * (2 * end));
            }
            return x;
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(topLeft.x, topLeft.y, width, height);
        if (this.movedStatus == true || this.clickedStatus == true) {
            g.setColor(pressedFillColor);
        } else {
            g.setColor(unpressedFillColor);
        }
        g.fillRect(topLeft.x + 1, topLeft.y + 1, width - 2, height - 2);
        if (this.movedStatus == true || this.clickedStatus == true) {
            g.setColor(pressedTextColor);
        } else {
            g.setColor(unpressedTextColor);
        }
        if (this.toolTip != null && (name.length() * 6) < width - 5) {
            g.setFont(new Font("SansSherif", Font.BOLD, 13));
            g.drawString(name, topLeft.x + (width / 2) - ((name.length() / 2) * 7) - 3, topLeft.y + (height / 2) + 5);
        } else if (this.toolTip != null) {
            g.setFont(new Font("SansSherif", Font.BOLD, 11));
            g.drawString(name, topLeft.x + 3, topLeft.y + (height / 2) + 5);
        }
        if (this.toolTip == null) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSherif", Font.PLAIN, 12));
            g.drawString(name, topLeft.x + 15, topLeft.y + (height / 2) + 5);
        }
    }

    public void paintToolTip(Graphics g) {
        if (this.movedStatus == true) {
            this.toolTip.paint(g);
        }
    }

    @Override
    public String toString() {
        return "";
    }

    public boolean isMovedStatus() {
        return movedStatus;
    }

    public String getName() {
        return name;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClickedStatus() {
        return clickedStatus;
    }

}
