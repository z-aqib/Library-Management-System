package Books;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class Book implements Comparable<Book> {

    private final long ID;
    private String name, author;
    private boolean Available;
    private long Quantity;
    private String genre;
    private final double rating;
    private int NumberOfTimesIssued;
    private final Image image;
    private final String[] description;

    // constructor
    public Book(long ID, String name, String author, boolean available, long quantity,
            String genre, double rating, int numberOfTimesIssued, Image image) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        Quantity = quantity;
        if (this.Quantity == 0) {
            setAvailable(false);
        } else {
            setAvailable(true);
        }

        this.genre = genre;
        this.rating = rating;
        NumberOfTimesIssued = numberOfTimesIssued;
        this.image = image;
        String[] buzzwords = {"captivating", "compelling", "riveting", "unforgettable",
            "intriguing", "inspiring", "masterful", "poignant"};
        Random random = new Random();
        description = ("'" + name.toLowerCase() + "' by '" + author.toLowerCase() + "' is a "
                + "\n" + buzzwords[random.nextInt(buzzwords.length)] + " "
                + genre.split(" ")[0].toLowerCase() + " novel. It offers a "
                + "\n" + buzzwords[random.nextInt(buzzwords.length)] + " storyline that will keep readers "
                + "\n" + getRandomChoice(new String[]{"on the edge of their seats", "hooked", "spellbound"}) + ". "
                + "With " + getRandomChoice(new String[]{"complex characters",
            "rich storytelling", "vivid imagery"}) + ", it \nis a " + getRandomChoice(new String[]{"must-read",
            "page-turner", "gem"}) + ".").split("\n");

    }

    private String getRandomChoice(String[] choices) {
        Random random = new Random();
        return choices[random.nextInt(choices.length)];
    }

    // get iD method
    public long getID() {
        return ID;
    }

    // get Name method
    public String getName() {
        return name;
    }

    //set name method
    public void setName(String name) {
        this.name = name;
    }

    // get Author method
    public String getAuthor() {
        return author;
    }

    // set author method
    public void setAuthor(String author) {
        this.author = author;
    }

    // get Genre method
    public String getGenre() {
        return genre;
    }

    // set genre method
    public void setGenre(String genre) {
        this.genre = genre;
    }

    // get Rating method
    public double getRating() {
        return rating;
    }

    // get Number of times issued method
    public int getTimesIssued() {
        return NumberOfTimesIssued;
    }

    // check whether the book is available or not
    public boolean isAvailable() {
        return Available;
    }

    // set available method
    private void setAvailable(boolean available) {
        Available = available;
    }

    // tostring method
    @Override
    public String toString() {
        return "ID = " + ID + ", Name = " + name + ", Author = " + author + ", "
                + "Available = " + Available + ", Quantity = " + Quantity + ", "
                + "Genre = " + genre + ", Rating = " + rating + ", "
                + "NumberOfTimesIssued = " + NumberOfTimesIssued + ", Image = " + image;
    }

    @Override
    public int compareTo(Book o) {
        return 0;
    }

    // to only display specific details to customer
    public String display() {
        return ", Name = " + name + ", Author = " + author + ", Genre = " + genre
                + ", Rating = " + rating + ", Image = " + image;

    }

    public long getQuantity() {
        return Quantity;
    }

    public void setQuantity(long Quantity) {
        this.Quantity = Quantity;
        if (this.Quantity == 0) {
            setAvailable(false);
        } else {
            setAvailable(true);
        }
    }

    public void changeQuantityBy(int change) {
        this.Quantity = this.Quantity + change;
        if (this.Quantity == 0) {
            setAvailable(false);
        } else {
            setAvailable(true);
        }
    }

    public void changeNumberOfTimesIssued(int change) {
        this.NumberOfTimesIssued = this.NumberOfTimesIssued + change;
    }

    public Image getImage() {
        return image;
    }

    public String[] getDescription() {
        return description;
    }

    public void paint(Graphics g, ImageObserver observer) {
        int[] panel = new int[]{10, 140, 1200 - 20, 650 - 175};
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        // display the picture of the user
        g.setColor(Color.black);
        g.fillRect(panel[0] + 20, panel[1] + 20, panel[2] / 5, (int) ((panel[2] / 5) * 1.5));
        g.setColor(Color.white);
        g.fillRect(panel[0] + 20 + 2, panel[1] + 22, (panel[2] / 5) - 4, (int) (((panel[2] / 5) * 1.5) - 4));
        g.drawImage(getImage(), 20 + 4 + panel[0],
                panel[1] + 20 + 4, (panel[2] / 5) - 8, (int) (((panel[2] / 5) * 1.5) - 8), observer);

        // display the headings
        String[] s = {"Book ID: ", "Title: ", "Author: ", "Stock: ", "Genre: ",
            "Rating: ", "Number of times issued: "};
        for (int i = 0; i < s.length; i++) {
            g.drawString(s[i], panel[0] + 40 + (panel[2] / 5) * 3, panel[1] + 30 + (i * 65));
            g.fillRect(panel[0] + 40 + (panel[2] / 5) * 3, panel[1] + 40 + (i * 65), 380, 25);
        }

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g.setColor(Color.black);

        // display the details of the user
        String[] p = {getID() + "", getName(), getAuthor(), getQuantity() + "",
            getGenre(), getRating() + "", getTimesIssued() + ""};
        for (int i = 0; i < p.length; i++) {
            g.drawString(p[i], panel[0] + 45 + (panel[2] / 5) * 3, panel[1] + 60 + (i * 65));
        }
    }

}
