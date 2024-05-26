package Users;

import Books.*;
import DataStructures.*;
import GUIWindow.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class User implements Comparable {

    Image image;
    String email;
    String address;  // get set
    String password; // nothing
    long ID; //getter
    String name;  //get set
    SinglyLLH currentlyissuedbooks; //get
    private BookButton[] currentButtons;
    private BookButton[] historyButtons;
    public final ColoredButtons logOutButton;
    private final int[] panel;
    SinglyLLH history;

    public User(long id, String name, String email, String password, String address, Image image) {
        this.name = name;
        this.ID = id;
        this.email = email;
        this.address = address;
        this.password = password;
        this.image = image;
        currentlyissuedbooks = new SinglyLLH(); //get
        history = new SinglyLLH();
        this.panel = new int[]{10, 140, 1200 - 20, 650 - 175};
        this.logOutButton = new ColoredButtons("Log out", 80 + 4 + panel[0],
                panel[1] + 54 + (panel[2] / 5) - 8, 100, 50, Color.white,
                Color.LIGHT_GRAY, true, Color.black, Color.black);
        createButtonsCurrent();
        createButtonsHistory();
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public long getID() {
        return ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean issueBook(Book book) { //adds book to currently issued LL
        if (checkIssued() && book.getQuantity() != 0) {
            currentlyissuedbooks.insertStart((Comparable) book);
            createButtonsCurrent();
            book.changeQuantityBy(-1);
            book.changeNumberOfTimesIssued(1);
            return true;
        } else {
            return false;
        }
    }

    public boolean returnBook(Book book) { //will remove from currently issued LL and add to history LL
        SinglyNode s = currentlyissuedbooks.deleteFirstOccurence((Comparable) book);
        if (s != null) {
            history.insertStart((Comparable) book);
            createButtonsHistory();
            createButtonsCurrent();
            book.changeQuantityBy(1);
            return true;
        } else {
            return false;
        }
    }

    boolean checkIssued() {  //It will run each time before issuing book
        return currentlyissuedbooks.size() <= 3;
    }

    public final void createButtonsCurrent() {
        int x = panel[0] - 40 + (panel[2] / 5) * 3;
        int y = panel[1] + 60;
        int width = 105;
        int height = (panel[3] - 100) / 3;
        currentButtons = new BookButton[currentlyissuedbooks.size()];
        for (int i = 0, xi = 0, yi = 0; i < currentButtons.length; xi++, i++) {
            currentButtons[i] = new BookButton((Book) currentlyissuedbooks.getNode(i).data,
                    x + (xi * (width + 10)), y + (yi * (height + 10)), width, height);
            if (xi == 1) {
                xi = -1;
                yi++;
            }
        }
    }

    public final void createButtonsHistory() {
        int x = panel[0] + 220 + (panel[2] / 5) * 3;
        int y = panel[1] + 60;
        int width = 105;
        int height = (panel[3] - 100) / 3;
        historyButtons = new BookButton[this.history.size()];
        for (int i = 0, xi = 0, yi = 0; i < this.historyButtons.length; xi++, i++) {
            historyButtons[i] = new BookButton((Book) this.history.getNode(i).data,
                    x + (xi * (width + 10)), y + (yi * (height + 10)), width, height);
            if (xi == 1) {
                xi = -1;
                yi++;
            }
        }
    }

    @Override
    public String toString() {
        return ID + "";
    }

    @Override
    public int compareTo(Object o) {
        String user = (String) o;
        if (Long.parseLong(user) > this.ID) {
            return 1;
        } else if (Long.parseLong(user) == this.ID) {
            return 0;
        }
        return -1;
    }

    public Image getImage() {
        return image;
    }

    public SinglyLLH getCurrentlyissuedbooks() {
        return currentlyissuedbooks;
    }

    public SinglyLLH getHistory() {
        return history;
    }

    public void paintFirstHalfUserDetails(Graphics g, ImageObserver observer, boolean adminOrNot) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        // display the picture of the user
        g.setColor(Color.black);
        g.fillRect(panel[0] + 20, panel[1] + 20, panel[2] / 5, panel[2] / 5);
        g.setColor(Color.white);
        g.fillRect(panel[0] + 20 + 2, panel[1] + 22, (panel[2] / 5) - 4, (panel[2] / 5) - 4);
        g.drawImage(getImage(), 20 + 4 + panel[0],
                panel[1] + 20 + 4, (panel[2] / 5) - 8, (panel[2] / 5) - 8, observer);

        // display the headings
        String[] s = {"User ID: ", "Name: ", "Email Address: ", "Password: ", "Home Address: "};
        if (adminOrNot == false) {
            for (int i = 0; i < s.length; i++) {
                g.drawString(s[i], panel[0] + 40 + panel[2] / 5, panel[1] + 40 + (i * 80));
                g.fillRect(panel[0] + 40 + panel[2] / 5, panel[1] + 50 + (i * 80), 380, 35);
            }
        } else {
            for (int i = 0; i < s.length; i++) {
                g.drawString(s[i], panel[0] + 40 + (panel[2] / 5) * 3, panel[1] + 40 + (i * 80));
                g.fillRect(panel[0] + 40 + (panel[2] / 5) * 3, panel[1] + 50 + (i * 80), 380, 35);
            }
        }

        g.setColor(Color.black);

        // display the details of the user
        String[] p = {getID() + "", getName(), getEmail(), getPassword(), getAddress()};
        if (adminOrNot == false) {
            for (int i = 0; i < p.length; i++) {
                g.drawString(p[i], panel[0] + 50 + (panel[2] / 5), panel[1] + 75 + (i * 80));
            }
        } else {
            for (int i = 0; i < p.length; i++) {
                g.drawString(p[i], panel[0] + 50 + (panel[2] / 5)*3, panel[1] + 75 + (i * 80));
            }
        }

    }

    public void paintSecondHalfUserDetails(Graphics g, ImageObserver observer) {

        g.setColor(Color.white);

        g.drawString("To change details, ", 20, 510);
        g.drawString("please contact admin. ", 20, 535);

        // display the book's in hand headings
        g.drawString("Currently Issued Books: ", panel[0] - 35 + (panel[2] / 5) * 3, panel[1] + 40);
        g.drawRect(panel[0] - 40 + (panel[2] / 5) * 3, panel[1] + 10, 240, panel[3] - 20);
        g.drawRect(panel[0] - 40 + (panel[2] / 5) * 3, panel[1] + 10, 240, 40);
        g.drawString("History of Issued Books: ", panel[0] + 225 + (panel[2] / 5) * 3, panel[1] + 40);
        g.drawRect(panel[0] + 220 + (panel[2] / 5) * 3, panel[1] + 10, 240, panel[3] - 20);
        g.drawRect(panel[0] + 220 + (panel[2] / 5) * 3, panel[1] + 10, 240, 40);

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        // display books issued
        if (getCurrentlyissuedbooks().size() == 0) {
            g.drawString("No books issued currently.", panel[0] - 20 + (panel[2] / 5) * 3, panel[1] + 150);
        } else {
            for (int i = 0; i < currentButtons.length; i++) {
                currentButtons[i].paint(g, observer);
            }
        }

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g.setColor(Color.white);

        // display history books
        if (getHistory().size() == 0) {
            g.drawString("No history of issued books.", panel[0] + 240 + (panel[2] / 5) * 3, panel[1] + 150);
        } else {
            for (int i = 0; i < historyButtons.length; i++) {
                historyButtons[i].paint(g, observer);
            }
        }

        logOutButton.paint(g);
    }

    public BookButton ifClicked(int x, int y) {
        for (int i = 0; i < currentButtons.length; i++) {
            if (currentButtons[i].ifClicked(x, y) == true) {
                return currentButtons[i];
            }
        }
        for (int i = 0; i < historyButtons.length; i++) {
            if (historyButtons[i].ifClicked(x, y) == true) {
                return historyButtons[i];
            }
        }
        return null;
    }

    public boolean ifMoved(int x, int y) {
        for (int i = 0; i < currentButtons.length; i++) {
            currentButtons[i].ifMoved(x, y);
        }
        for (int i = 0; i < historyButtons.length; i++) {
            historyButtons[i].ifMoved(x, y);
        }
        logOutButton.ifMoved(x, y);
        return false;
    }

}
