package GUIWindow;

import Books.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class BookButton {

    public Book book;
    private String[] title;
    private ColoredButtons button;
    public boolean clicked;
    public String first = "";
    public String second = "";
    public String third = "";
    private int[] sortingArea;
    public final ColoredButtons backButton;
    public final ColoredButtons issueButton;
    public final ColoredButtons returnButton;

    public BookButton(Book book, int x, int y, int width, int height) {
        this.book = book;
        this.clicked = false;
        this.sortingArea = new int[]{1000, 235, 190, 380};
        this.backButton = new ColoredButtons("Back", sortingArea[0] - 630, sortingArea[1] + sortingArea[3] - 30,
                100, 30, Color.GREEN, Color.blue, true,
                Color.white, Color.white);
        this.issueButton = new ColoredButtons("Issue Book", sortingArea[0] - 520, sortingArea[1] + sortingArea[3] - 30,
                100, 30, Color.red, Color.CYAN, true,
                Color.BLACK, Color.white);
        this.returnButton = new ColoredButtons("Return Book", sortingArea[0] - 410, sortingArea[1] + sortingArea[3] - 30,
                100, 30, Color.red, Color.CYAN, true,
                Color.BLACK, Color.white);
        this.button = new ColoredButtons("", x, y, width, height,
                Color.white, Color.decode("#F5F5F5"),
                false, Color.black, Color.black);
        String[] words = this.book.getName().split(" ");
        int counter = 0;
        int index = 0;
        this.title = new String[words.length + 1];
        while (counter < words.length - 1) {
            int totalLength = 0;
            int start = counter;
            int end = counter;
            do {
                for (int i = start; i <= end; i++) {
                    totalLength += words[i].length();
                }
                if (totalLength * 9 < this.button.getWidth() == true) {
                    end++;
                } else {
                    break;
                }
            } while (end < words.length && totalLength * 9 < this.button.getWidth() == true);
            String totalString = words[start] + " ";
            for (int i = start + 1; i < end && i < words.length; i++) {
                totalString += words[i] + " ";
            }
            if (index != title.length) {
                title[index++] = totalString;
            }
            counter = end;
        }
        if (counter < words.length) {
            title[index] = words[counter];
        }
        for (int i = 0; i < words.length / 3; i++) {
            first += words[i] + " ";
        }
        for (int i = words.length / 3; i < (words.length / 3) * 2; i++) {
            second += words[i] + " ";
        }
        for (int i = (words.length / 3) * 2; i < words.length; i++) {
            third += words[i] + " ";
        }
    }

    public void paint(Graphics g, ImageObserver observer) {
        if (clicked == false) {
            this.button.paint(g);
            g.setColor(Color.black);
            g.drawRect(this.button.getTopLeft().x + 4, this.button.getTopLeft().y + 4,
                    button.getWidth() - 8, 2 + button.getHeight() / 2);
            g.drawImage(book.getImage(), this.button.getTopLeft().x + 5, this.button.getTopLeft().y + 5,
                    button.getWidth() - 10, button.getHeight() / 2, observer);
            g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
            g.drawString(book.getAuthor(),
                    this.button.getTopLeft().x + button.getWidth() / 2 - (book.getAuthor().length() / 2 * 6),
                    this.button.getTopLeft().y + button.getHeight() - 7);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
            for (int i = title.length - 1, y = 1; i >= 0; i--) {
                if (title[i] != null) {
                    g.drawString(title[i], this.button.getTopLeft().x + button.getWidth() / 2 - (title[i].length() / 2 * 8),
                            this.button.getTopLeft().y + this.button.getHeight() - 5 - (y++ * 13));
                }
            }
        } else {
            g.setColor(Color.white);
            g.fillRect(sortingArea[0], sortingArea[1], sortingArea[2], sortingArea[3]);

            // paint the back button
            this.backButton.paint(g);

            // paint the issue button
            this.issueButton.paint(g);
            
            this.returnButton.paint(g);

            // paint the image of the book
            g.setColor(Color.black);
            g.drawRect(-100 + sortingArea[0] - sortingArea[2], sortingArea[1] - sortingArea[3] / 4,
                    (int) (sortingArea[2] * 1.5), 480);
            g.drawImage(book.getImage(), -100 + sortingArea[0] - sortingArea[2] + 2, (sortingArea[1] - sortingArea[3] / 4) + 2,
                    (int) ((sortingArea[2] * 1.5) - 4), 480-4, observer);

            // paint the name of the book
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
            g.drawString(first, 360, sortingArea[1]);
            g.drawString(second, 360, sortingArea[1] + 30);
            g.drawString(third, 360, sortingArea[1] + 60);

            // paint the author of the book
            g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 25));
            g.drawString(book.getAuthor(),
                    (int) (sortingArea[0] - (sortingArea[2] * 2) - (book.getAuthor().length() / 2) * 22),
                    sortingArea[1] + 90);

            // paint the rating of the book in stars
            int numOfStars = (int) Math.floor(book.getRating());
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            g.drawString("Rating = ", 365, sortingArea[1] + 130);
            for (int i = 0; i < numOfStars; i++) {
                g.drawImage(new ImageIcon("src/ImagesOthers/star.png").getImage(),
                        450 + (i * 35), sortingArea[1] + 110, 25, 25, observer);
            }

            // paint the numeric rating of the book
            g.drawString("(" + book.getRating() + ")", 450 + (numOfStars * 35), sortingArea[1] + 130);

            // paint the id of the book
            g.drawString("Book ID = " + book.getID(), 360, sortingArea[1] - 30);

            // paint the genres of the book
            g.drawString("Genres = " + book.getGenre(), 360, sortingArea[1] + 175);

            // paint alerting messages: low stock or no stock at all
            g.setColor(Color.red);
            if (book.getQuantity() == 0) {
                g.drawString("SOLD OUT! Currently unavailable. ", 400, sortingArea[1] + 155);
            } else if (book.getQuantity() <= 5) {
                g.drawString("Hurry! Only " + book.getQuantity() + " left in stock. ", 400, sortingArea[1] + 155);
            }

            g.setColor(Color.blue);

            for (int i = 0; i < book.getDescription().length; i++) {
                g.drawString(book.getDescription()[i], 360, sortingArea[1] + 200 + (i * 30));
            }

        }
    }

    public boolean ifClicked(int x, int y) {
        if (clicked == true) {
            clicked = !this.backButton.ifSelected(x, y);
            return clicked;
        } else {
            clicked = button.ifSelected(x, y);
            return clicked;
        }
    }

    public boolean ifMoved(int x, int y) {
        this.backButton.ifMoved(x, y);
        this.issueButton.ifMoved(x, y);
        return button.ifMoved(x, y);
    }

}
