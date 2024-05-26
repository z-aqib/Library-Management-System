package GUIWindow;

import Admin.*;
import Books.*;
import DataStructures.*;
import GUI.*;
import Users.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.ImageIcon;

public class PortalAdmin implements WindowInterface {

    private final int[] panel;
    private final Point errorPoint;
    private final ColoredButtons[] loginButtons;
    private final ColoredButtons[] bookOptions;
    private final ColoredButtons[] userOptions;
    private final ColoredButtons logOutButton;
    private final AdminHandling adminHandling;
    private final UserHandling userHandling;
    private final BooksHandling bookHandling;
    private final ExceptionHandling exceptionHandling;

    PopUpWindow popUp;

    private String adminEnteredPW;
    private String errorMessage;
    private Admin adminLoggedIn;
    private ColoredButtons pressedOption;
    private User editingUser = null;
    private Book editingBook = null;
    private int editing = -1; // -1 for nothing, 0 for user, 1for book

    public PortalAdmin(int[] panel, UserHandling u, BooksHandling b) {
        this.adminEnteredPW = "";
        this.exceptionHandling = new ExceptionHandling();
        this.adminLoggedIn = null;
        this.adminHandling = new AdminHandling(u, b);
        this.userHandling = u;
        this.bookHandling = b;
        this.panel = panel;
        this.errorPoint = new Point(panel[0] + (panel[2] / 2) - 235, panel[1] + 150);
        this.errorMessage = "";
        this.loginButtons = new ColoredButtons[3];
        this.loginButtons[0] = new ColoredButtons("(to enter ID, press this box and enter in console)",
                panel[0] + (panel[2] / 2) - 235, panel[1] + 200, 470, 30,
                Color.decode("#f2f2f2"), Color.decode("#d8d8d8"),
                true, Color.black, Color.BLACK);
        this.loginButtons[1] = new ColoredButtons("(to enter password, press this box and enter in console)",
                panel[0] + (panel[2] / 2) - 235, panel[1] + 270, 470, 30,
                Color.decode("#f2f2f2"), Color.decode("#d8d8d8"),
                true, Color.black, Color.BLACK);
        this.loginButtons[2] = new ColoredButtons("Login",
                panel[0] + (panel[2] / 2) - 100, panel[1] + (panel[3] / 4) * 3, 200, 40,
                Color.black, Color.white, true,
                Color.white, Color.white);
        String[] bookOptionss = {"Add Book", "Delete Book", "Update Book Stock",
            "Update Book Title", "Update Book Author"};
        this.bookOptions = new ColoredButtons[bookOptionss.length];
        for (int i = 0; i < bookOptions.length; i++) {
            bookOptions[i] = new ColoredButtons(bookOptionss[i], panel[0] - 100 + (panel[2] / 5) * 4,
                    panel[1] + 70 + (i * 80), 300, 70, Color.white,
                    Color.LIGHT_GRAY, true, Color.BLACK, Color.BLACK);
        }
        String[] userOptionss = {"Add User Account", "Update User Name", "Update User Email",
            "Update User Password", "Update User Address"};
        this.userOptions = new ColoredButtons[userOptionss.length];
        for (int i = 0; i < userOptions.length; i++) {
            userOptions[i] = new ColoredButtons(userOptionss[i], panel[0] - 200 + (panel[2] / 4) * 2,
                    panel[1] + 70 + (i * 80), 300, 70, Color.white,
                    Color.LIGHT_GRAY, true, Color.BLACK, Color.BLACK);
        }
        this.logOutButton = new ColoredButtons("Log Out", panel[0] + 40,
                panel[1] + 50 + (4 * 80), 100, 35, Color.white,
                Color.lightGray, true, Color.BLACK, Color.black);
        this.adminLoggedIn = new Admin(1, "2", "a", u, b);
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        g.setColor(Color.black);
        g.fillRect(panel[0], panel[1], panel[2], panel[3]);
        // paint the gradient background each time
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#ffd52e"),
                panel[2] - 4, 0, Color.decode("#2EFF6D"));
        // Set the paint to the Graphics2D context
        g2d.setPaint(gradient);
        // Draw a rectangle with the gradient paint
        g.fillRect(panel[0] + 2, panel[1] + 2, panel[2] - 4, panel[3] - 4);

        // if the admin has not been logged in yet, log them in
        if (adminLoggedIn == null) {
            paintLoginPage(g, g2d);
        } else if (editingUser != null) {
            editingUser.paintFirstHalfUserDetails(g, observer, true);
        } else if (editingBook != null) {
            editingBook.paint(g, observer);
        } else {
            paintOptions(g);
        }
        if (popUp != null && popUp.printOrNot == true) {
            popUp.paint(g);
        }

    }

    private void paintOptions(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.setColor(Color.white);

        String[] s = {"Admin ID: ", "Name: ", "Password: ",};
        for (int i = 0; i < s.length; i++) {
            g.drawString(s[i], panel[0] + 40, panel[1] + 40 + (i * 80));
            g.fillRect(panel[0] + 40, panel[1] + 50 + (i * 80), 250, 35);
        }

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        g.drawString("Users Options", panel[0] - 30 + (panel[2] / 5) * 2, panel[1] + 50);
        g.drawString("Books Options", panel[0] - 50 + (panel[2] / 5) * 4, panel[1] + 50);

        g.setColor(Color.black);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        // display the details of the admin
        String[] p = {adminLoggedIn.ID + "", adminLoggedIn.name, adminLoggedIn.password};
        for (int i = 0; i < p.length; i++) {
            g.drawString(p[i], panel[0] + 50, panel[1] + 75 + (i * 80));
        }

        // display message to check console
        g.drawString("When you click an option,", panel[0] + 40,
                panel[1] + 50 + (s.length * 80));
        g.drawString("please check console. ", panel[0] + 40,
                panel[1] + 80 + (s.length * 80));

        for (int i = 0; i < userOptions.length; i++) {
            userOptions[i].paint(g);
        }

        for (int i = 0; i < bookOptions.length; i++) {
            bookOptions[i].paint(g);
        }

        logOutButton.paint(g);

    }

    private void paintLoginPage(Graphics g, Graphics2D g2d) {
        // paint the login box
        g.setColor(Color.black);
        g.fillRect(panel[0] + (panel[2] / 2) - 250, panel[1] + 25, 500, panel[3] - 50);
        g.setColor(Color.white);
        g.fillRect(panel[0] + (panel[2] / 2) - 250 + 2, panel[1] + 25 + 2, 500 - 4, panel[3] - 50 - 4);

        // paint the login heading
        g.setColor(Color.black);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        g.drawString("Hello, Boss.", panel[0] + (panel[2] / 2) - 100, panel[1] + 80);

        // paint the welcome background
        g.setColor(Color.decode("#35f374"));
        g.fillRect(panel[0] + (panel[2] / 2) - 247, panel[1] + 90, 500 - 6, 30);

        // paint the welcome message
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g.drawString("WE MISSED YOU",
                panel[0] + (panel[2] / 2) - ((15 / 2) * 7), panel[1] + 110);

        // paint the error message
        g.setColor(Color.red);
        g.drawString(errorMessage, errorPoint.x, errorPoint.y);

        // paint the login buttons of user ID and password
        loginButtons[0].paint(g);
        loginButtons[1].paint(g);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        g.drawString("Admin ID: ", loginButtons[0].getTopLeft().x, loginButtons[0].getTopLeft().y - 10);
        g.drawString("Password: ", loginButtons[1].getTopLeft().x, loginButtons[1].getTopLeft().y - 10);

        // paint the login pressing button
        g.setColor(Color.black);
        g.fillRoundRect(loginButtons[2].getTopLeft().x - 2, loginButtons[2].getTopLeft().y - 2,
                loginButtons[2].getWidth() + 4, loginButtons[2].getHeight() + 4, 20, 20);
        if (loginButtons[2].isMovedStatus() == false && loginButtons[2].isClickedStatus() == false) {
            g2d.setPaint(new GradientPaint(loginButtons[2].getTopLeft().x, loginButtons[2].getHeight() / 2,
                    Color.decode("#DED30D"), loginButtons[2].getWidth() + loginButtons[2].getTopLeft().x,
                    loginButtons[2].getHeight() / 2, Color.decode("#43F44C")));
        } else {
            g2d.setPaint(new GradientPaint(loginButtons[2].getTopLeft().x, loginButtons[2].getHeight() / 2,
                    Color.decode("#ffd52e"), loginButtons[2].getWidth() + loginButtons[2].getTopLeft().x,
                    loginButtons[2].getHeight() / 2, Color.decode("#2EFF6D")));
        }
        g.fillRoundRect(loginButtons[2].getTopLeft().x, loginButtons[2].getTopLeft().y,
                loginButtons[2].getWidth(), loginButtons[2].getHeight(), 20, 20);
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        g.drawString("Login", loginButtons[2].getTopLeft().x + (loginButtons[2].getWidth() / 6) * 2,
                loginButtons[2].getTopLeft().y + (loginButtons[2].getHeight() / 5) * 3 + 5);
    }

    @Override
    public void mouseClicked(int x, int y) {
        // if the admin id or password button is clicked,
        if (adminLoggedIn == null) {
            if (loginButtons[0].ifSelected(x, y) == true) {
                long ID = exceptionHandling.longValueInput("Please enter your Admin ID: ");
                loginButtons[0].setName(ID + "");
                System.out.println("ID entered successfully! ");
            } else if (loginButtons[1].ifSelected(x, y) == true) {
                System.out.print("Please enter your password: ");
                adminEnteredPW = exceptionHandling.input.next();
                String pw = "";
                for (int i = 0; i < adminEnteredPW.length(); i++) {
                    pw += "*";
                }
                loginButtons[1].setName(pw);
                System.out.println("Password entered successfully! It will not be shown on the screen. ");
            } else if (loginButtons[2].ifSelected(x, y) == true) {
                if (adminEnteredPW.equals("") == true && loginButtons[0].getName().equalsIgnoreCase(
                        "(to enter ID, press this box and enter in console)") == true) {
                    errorMessage = "ERROR: Insufficient Details.  ";
                } else {
                    Admin admin = adminHandling.logInAdmin(Long.parseLong(loginButtons[0].getName()),
                            adminEnteredPW);
                    if (admin == null) {
                        errorMessage = "ERROR: Incorrect Admin ID or Password. ";
                    } else {
                        adminLoggedIn = admin;
                    }
                    loginButtons[0].setName("(to enter ID, press this box and enter in console)");
                    loginButtons[1].setName("(to enter password, press this box and enter in console)");
                    adminEnteredPW = "";
                }
            }
        } else if (popUp == null && editingUser == null && editingBook == null) {
            for (int i = 0; i < userOptions.length; i++) {
                if ((i == 0 || i == 1) && userOptions[i].ifSelected(x, y) == true) {
                    pressedOption = userOptions[i];
                    editing = 0;
                    this.popUp = new PopUpWindow("Please check the console. ");
                } else if ((i != 0 && i != 1) && userOptions[i].ifSelected(x, y) == true) {
                    pressedOption = userOptions[i];
                    editing = 0;
                    this.popUp = new PopUpWindow("Please enter the ID of the User at the console. ");
                }
            }
            for (int i = 0; i < bookOptions.length; i++) {
                if (i == 0 && bookOptions[i].ifSelected(x, y) == true) {
                    pressedOption = bookOptions[i];
                    editing = 1;
                    this.popUp = new PopUpWindow("Please check the console. ");
                } else if (i != 0 && bookOptions[i].ifSelected(x, y) == true) {
                    pressedOption = bookOptions[i];
                    editing = 1;
                    this.popUp = new PopUpWindow("Please enter the ID of the Book at the console. ");
                }
            }
            if (logOutButton.ifSelected(x, y) == true) {
                adminLoggedIn = null;
            }
        } else if (editing == 0 && editingUser == null) {
            if (popUp.clicked(x, y) == true) {
                String txt = popUp.text[0];
                popUp = null;
                if (txt.contains("enter the ID") == true) {
                    long ID = exceptionHandling.longValueInput("Please enter the ID of the user to be updated: ");
                    System.out.println("User ID entered successfully! ");
                    User user = userHandling.getUser(ID);
                    if (user != null) {
                        editingUser = user;
                        popUp = new PopUpWindow("Please enter the new "
                                + pressedOption.getName().substring(12,
                                        pressedOption.getName().length()).toLowerCase() + " of this user "
                                + "in the console. ");
                    } else {
                        popUp = new PopUpWindow("ERROR: Please enter the ID of an existing "
                                + "User at the console. The ID entered previously does not exist. ");
                    }
                } else if (txt.contains("check the console") == true) {
                    System.out.print("Please enter the name of the new user: ");
                    String name = exceptionHandling.input.next();
                    System.out.print("Please enter the password of '" + name + "' user: ");
                    String password = exceptionHandling.input.next();
                    System.out.print("Please enter the email ID of '" + name + "' user: ");
                    String email = exceptionHandling.input.next();
                    System.out.print("Please enter the home address of '" + name + "' user: ");
                    String address = exceptionHandling.input.next();
                    System.out.print("Please enter the path of the image of '" + name + "' user: ");
                    String img = exceptionHandling.input.next();
                    User u = adminLoggedIn.addUser(name, email, password, address, new ImageIcon(img).getImage());
                    popUp = new PopUpWindow("SUCESS: User account with '" + u.getName() + "' and ID "
                            + u.getID() + " has been created successfully. ");
                    editingUser = u;
                }
            }
        } else if (editing == 0) {
            if (popUp.clicked(x, y) == true) {
                String txt = popUp.text[0];
                popUp = null;
                if (txt.contains("Please enter")) {
                    changeUserDetails();
                } else {
                    pressedOption = null;
                    editingUser = null;
                    editing = -1;
                }
            }
        } else if (editing == 1 && editingBook == null) {
            if (popUp.clicked(x, y) == true) {
                String txt = popUp.text[0];
                popUp = null;
                if (txt.contains("enter the ID") == true) {
                    long ID = exceptionHandling.longValueInput("Please enter the "
                            + "ID of the book to be updated/deleted: ");
                    System.out.println("Book ID entered successfully! ");
                    Book book = bookHandling.searchForIssue(ID);
                    if (book != null) {
                        System.out.println("book found");
                        editingBook = book;
                        if (pressedOption.getName().contains("Delete") == true) {
                            bookHandling.BKList.delete(editingBook);
                            bookHandling.books.Remove(editingBook);
                            popUp = new PopUpWindow("SUCCESS: Book " + editingBook.getID() + " has been deleted successfully. ");
                        } else {
                            popUp = new PopUpWindow("Please enter the new "
                                    + pressedOption.getName().substring(11,
                                            pressedOption.getName().length()).toLowerCase() + " of this book "
                                    + "in the console. ");
                        }
                    } else {
                        System.out.println("book not found");
                        popUp = new PopUpWindow("ERROR: Please enter the ID of an existing "
                                + "Book at the console. The ID entered previously does not exist. ");
                    }
                } else if (txt.contains("check the console") == true) {
                    System.out.print("Please enter the name of the new book: ");
                    String name = exceptionHandling.input.next();
                    System.out.print("Please enter the author of '" + name + "' book: ");
                    String author = exceptionHandling.input.next();
                    long quantity = exceptionHandling.longValueInput("Please enter the quantity of the book: ");
                    System.out.print("Please enter the genre(s) of '" + name + "' book: ");
                    String genre = exceptionHandling.input.next();
                    double rating = exceptionHandling.doubleValueInput("Please enter the rating of the book: ");
                    rating = exceptionHandling.doubleValueRange(rating,
                            0, 5, -1, "Please enter the rating of the book: ");
                    System.out.print("Please enter the image path of the image of book: ");
                    String img = exceptionHandling.input.next();
                    Book b = adminLoggedIn.addBook(name, author, quantity, genre, rating, new ImageIcon(img).getImage());
                    popUp = new PopUpWindow("SUCESS: User account with '" + b.getName() + "' and ID "
                            + b.getID() + " has been created successfully. ");
                    editingBook = b;
                }
            }
        } else if (editing == 1) {
            if (popUp.clicked(x, y) == true) {
                String txt = popUp.text[0];
                popUp = null;
                if (txt.contains("Please enter")) {
                    changeBookDetails();
                } else {
                    pressedOption = null;
                    editingBook = null;
                    editing = -1;
                }
            }
        }

    }

    private void changeUserDetails() {
//            String[] userOptionss = {"Add User Account", "Update User Name", "Update User Email",
//                "Update User Password", "Update User Address"};
        System.out.print("Please enter the new " + pressedOption.getName().substring(12,
                pressedOption.getName().length()).toLowerCase() + " of the user: ");
        String updatingElement = exceptionHandling.input.nextLine();
        if (pressedOption.getName().contains("Name") == true) {
            editingUser.setName(updatingElement);
        } else if (pressedOption.getName().contains("Email") == true) {
            editingUser.setEmail(updatingElement);
        } else if (pressedOption.getName().contains("Password") == true) {
            editingUser.setPassword(updatingElement);
        } else if (pressedOption.getName().contains("Address") == true) {
            editingUser.setAddress(updatingElement);
        }
        popUp = new PopUpWindow("SUCCESS: User " + editingUser.getID() + " has been updated successfully. ");
    }

    private void changeBookDetails() {
//            String[] bookOptionss = {"Add Book", "Delete Book", "Update Book Stock",
//            "Update Book Title", "Update Book Author"};
        if (pressedOption.getName().contains("Stock") == false) {
            System.out.print("Please enter the new " + pressedOption.getName().substring(11,
                    pressedOption.getName().length()).toLowerCase() + " of the book: ");
            String updatingElement = exceptionHandling.input.nextLine();
            if (pressedOption.getName().contains("Title") == true) {
                editingBook.setName(updatingElement);
            } else if (pressedOption.getName().contains("Author") == true) {
                editingBook.setAuthor(updatingElement);
            }
        } else {
            int quantity = exceptionHandling.integerValueInput("Please enter the quantity of the book: ");
            editingBook.setQuantity(quantity);
        }
        popUp = new PopUpWindow("SUCCESS: Book " + editingBook.getID() + " has been updated successfully. ");
    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    @Override
    public void mouseEntered(int x, int y) {

    }

    @Override
    public void mouseReleased(int x, int y) {

    }

    @Override
    public void mouseExited(int x, int y) {

    }

    @Override
    public void mouseMoved(int x, int y) {
        if (adminLoggedIn == null) {
            for (int i = 0; i < loginButtons.length; i++) {
                loginButtons[i].ifMoved(x, y);
            }
        } else if (popUp == null) {
            for (int i = 0; i < userOptions.length; i++) {
                userOptions[i].ifMoved(x, y);
            }
            this.logOutButton.ifMoved(x, y);
            for (int i = 0; i < bookOptions.length; i++) {
                bookOptions[i].ifMoved(x, y);
            }
        } else {
            popUp.moved(x, y);
        }
    }

    @Override
    public void setGenre(ColoredButtons button) {

    }

    @Override
    public UserHandling getUserHandling() {
        return null;
    }

    @Override
    public BooksHandling getBookHandling() {
        return null;
    }

}
