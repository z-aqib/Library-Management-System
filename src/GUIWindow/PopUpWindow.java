package GUIWindow;

import java.awt.*;

public class PopUpWindow {

    /*
    this class prints a popup window in the middle of the screen. the values for the 
    popup window are fixed. 
     */
    private final ColoredButtons back;
    private final ColoredButtons topBorder;
    private final ColoredButtons crossButton;
    private final ColoredButtons okButton;
    public boolean printOrNot;
    public String[] text;

    public PopUpWindow(String text) {

        // the back of the window must be grey
        this.back = new ColoredButtons("Back", 390, 200, 350,
                200, Color.decode("#F5F5F5"), Color.decode("#F5F5F5"),
                true, Color.decode("#F5F5F5"), Color.decode("#F5F5F5"));

        // intialise necessary values
        int xStarting = this.back.getTopLeft().x;
        int yStarting = this.back.getTopLeft().y;
        int xEnding = this.back.getTopLeft().x + this.back.getWidth();
        int yEnding = this.back.getTopLeft().y + this.back.getHeight();
        int width = this.back.getWidth();

        // it will have a top border yellow
        this.topBorder = new ColoredButtons("Top Border", xStarting,
                yStarting, width, 30, Color.YELLOW,
                Color.yellow, true, Color.yellow, Color.yellow);
        // the top right has a cross button 'X' which will turn red when hovered
        this.crossButton = new ColoredButtons("X", xEnding - 63, yStarting + 1,
                60, 25, Color.WHITE, Color.RED,
                true, Color.black, Color.black);
        // the bottom left has a 'ok' button which will turn light pink when hovered
        this.okButton = new ColoredButtons("OK", xEnding - 65, yEnding - 30,
                60, 25, Color.WHITE, Color.PINK,
                true, Color.black, Color.black);
        // set printornot boolean as originally false
        this.printOrNot = true;

        String[] words = text.split(" ");
        int counter = 0;
        int index = 0;
        this.text = new String[words.length + 1];
        while (counter < words.length - 1) {
            int totalLength = 0;
            int start = counter;
            int end = counter;
            do {
                for (int i = start; i <= end; i++) {
                    totalLength += words[i].length();
                }
                if (totalLength * 3 < 350 == true) {
                    end++;
                } else {
                    break;
                }
            } while (end < words.length && totalLength * 3 < 350 == true);
            String totalString = words[start] + " ";
            for (int i = start + 1; i < end && i < words.length; i++) {
                totalString += words[i] + " ";
            }
            if (index != this.text.length) {
                this.text[index++] = totalString;
            }
            counter = end;
        }
        if (counter < words.length) {
            this.text[index] = words[counter];
        }
    }

    private void activate() {
        /*
        this method will be used to print the popup window whenever necessary.
         */
        this.printOrNot = true;
    }

    public void paint(Graphics g) {
        if (this.printOrNot == true) {
            g.setColor(Color.black);
            g.fillRect(this.back.getTopLeft().x - 2, this.back.getTopLeft().y - 2,
                    this.back.getWidth() + 4, this.back.getHeight() + 4);
            // paint the back first
            this.back.paint(g);
            // then paint the top border
            this.topBorder.paint(g);
            // then paint the back of the cross button
            this.crossButton.paint(g);
            // then paint the back of the ok button
            this.okButton.paint(g);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
            for (int i = 0; i < text.length; i++) {
                if (text[i] != null) {
                    g.drawString(text[i], 400, 250 + (i * 30));
                }
            }
        }
    }

    public boolean moved(int userX, int userY) {

        /*
        check the cross button, if cursor hovers on it, change color. 
         */
        boolean x = this.crossButton.ifMoved(userX, userY);
        boolean ok = this.okButton.ifMoved(userX, userY);
        return x == true || ok == true;

    }

    public boolean clicked(int userX, int userY) {

        /*
        if the window is clicked, and the cross button is clicked, then stop printing
        the window.
         */
        boolean x = this.crossButton.ifSelected(userX, userY);
        boolean ok = this.okButton.ifSelected(userX, userY);
        if (x == true || ok == true) {
            this.printOrNot = false;
            return true;
        }
        return false;

    }

}
