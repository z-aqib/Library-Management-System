package GUIWindow;

import Books.*;
import DataStructures.*;
import GUI.*;
import Users.*;
import java.awt.*;
import java.awt.image.*;

public class PortalUser implements WindowInterface {

    private final int[] panel;
    private final Point errorPoint;
    private final UserHandling userHandling;
    private final ColoredButtons[] loginButtons;
    private final String[] welcomeLines;
    private final ExceptionHandling exceptionHandling;

    private int welcomeLinesCounter = 0;
    private long startTime;
    private BookButton displayingBook;
    public User userLoggedIn;
    private String userEnteredPW;
    private String errorMessage;

    public PortalUser(int[] panel) {
        this.userEnteredPW = "";
        this.startTime = System.currentTimeMillis() / 1000;
        this.exceptionHandling = new ExceptionHandling();
        this.userHandling = new UserHandling();
        this.panel = panel;
        this.userLoggedIn = null;
        this.welcomeLines = new String[]{"Your books journey awaits you...", "Explore Stories That Take You Away...",
            "Open a Book, Begin Your Journey...", "Dream Big with Every Page Turn...",
            "Find Magic in Every Book You Open...", "Discover New Worlds with Each Read...",
            "Where Words Spark Your Dreams...", "Every Book is a Door to Dreams...",
            "Read and Imagine, Your World Awaits...", "Turn Pages, Unveil Your Story...",
            "Read. Dream. Repeat...",};
        this.welcomeLinesCounter = 0;
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
        this.displayingBook = null;
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        if (displayingBook == null) {
            g.setColor(Color.black);
            g.fillRect(panel[0], panel[1], panel[2], panel[3]);
            // paint the gradient background each time
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FF0000"),
                    panel[2] - 4, 0, Color.decode("#1E90FF"));
            // Set the paint to the Graphics2D context
            g2d.setPaint(gradient);
            // Draw a rectangle with the gradient paint
            g.fillRect(panel[0] + 2, panel[1] + 2, panel[2] - 4, panel[3] - 4);

            // if the user has not been logged in yet, log them in
            if (userLoggedIn == null) {
                paintLoginPage(g, g2d);
            } else {
                userLoggedIn.paintFirstHalfUserDetails(g, observer, false);
                userLoggedIn.paintSecondHalfUserDetails(g, observer);
            }
        } else {
            displayingBook.paint(g, observer);
        }
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
        g.drawString("Welcome Back", panel[0] + (panel[2] / 2) - 140, panel[1] + 80);

        // paint the welcome background
        g.setColor(Color.decode("#45b6fe"));
        g.fillRect(panel[0] + (panel[2] / 2) - 247, panel[1] + 90, 500 - 6, 30);

        // paint the welcome message
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g.drawString(welcomeLines[welcomeLinesCounter],
                panel[0] + (panel[2] / 2) - ((welcomeLines[welcomeLinesCounter].length() / 2) * 7), panel[1] + 110);

        // paint the error message
        g.setColor(Color.red);
        g.drawString(errorMessage, errorPoint.x, errorPoint.y);

        // update the welcome line: if it has been displayed for more than 1 second, change it. 
        if ((startTime - System.currentTimeMillis() / 1000) <= -2) {
            if (++welcomeLinesCounter == welcomeLines.length) {
                welcomeLinesCounter = 0;
            }
            startTime = System.currentTimeMillis() / 1000; // update the start time of displaying
        }

        // paint the login buttons of user ID and password
        loginButtons[0].paint(g);
        loginButtons[1].paint(g);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        g.drawString("User ID: ", loginButtons[0].getTopLeft().x, loginButtons[0].getTopLeft().y - 10);
        g.drawString("Password: ", loginButtons[1].getTopLeft().x, loginButtons[1].getTopLeft().y - 10);

        // paint the login pressing button
        g.setColor(Color.black);
        g.fillRoundRect(loginButtons[2].getTopLeft().x - 2, loginButtons[2].getTopLeft().y - 2,
                loginButtons[2].getWidth() + 4, loginButtons[2].getHeight() + 4, 20, 20);
        if (loginButtons[2].isMovedStatus() == false && loginButtons[2].isClickedStatus() == false) {
            g2d.setPaint(new GradientPaint(loginButtons[2].getTopLeft().x, loginButtons[2].getHeight() / 2,
                    Color.decode("#ff8a8a"), loginButtons[2].getWidth() + loginButtons[2].getTopLeft().x,
                    loginButtons[2].getHeight() / 2, Color.decode("#66C3FF")));
        } else {
            g2d.setPaint(new GradientPaint(loginButtons[2].getTopLeft().x, loginButtons[2].getHeight() / 2,
                    Color.decode("#f78aff"), loginButtons[2].getWidth() + loginButtons[2].getTopLeft().x,
                    loginButtons[2].getHeight() / 2, Color.decode("#616eff")));
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
        // if the user id or password button is clicked,
        if (userLoggedIn == null) {
            if (loginButtons[0].ifSelected(x, y) == true) {
                long ID = exceptionHandling.longValueInput("Please enter your User ID: ");
                loginButtons[0].setName(ID + "");
                System.out.println("ID entered successfully! ");
            } else if (loginButtons[1].ifSelected(x, y) == true) {
                System.out.print("Please enter your password: ");
                userEnteredPW = exceptionHandling.input.next();
                String pw = "";
                for (int i = 0; i < userEnteredPW.length(); i++) {
                    pw += "*";
                }
                loginButtons[1].setName(pw);
                System.out.println("Password entered successfully! It will not be shown on the screen. ");
            } else if (loginButtons[2].ifSelected(x, y) == true) {
                if (userEnteredPW.equals("") == true && loginButtons[0].getName().equalsIgnoreCase(
                        "(to enter ID, press this box and enter in console)") == true) {
                    errorMessage = "ERROR: Insufficient Details.  ";
                } else {
                    User user = userHandling.loginUser(
                            Long.parseLong(loginButtons[0].getName()), userEnteredPW);
                    if (user == null) {
                        errorMessage = "ERROR: Incorrect User ID or Password. ";
                    } else {
                        userLoggedIn = user;
                        userLoggedIn.createButtonsHistory();
                        userLoggedIn.createButtonsCurrent();
                    }
                    loginButtons[0].setName("(to enter ID, press this box and enter in console)");
                    loginButtons[1].setName("(to enter password, press this box and enter in console)");
                    userEnteredPW = "";
                }
            }
        } else if (displayingBook == null) {
            displayingBook = userLoggedIn.ifClicked(x, y);
            if (userLoggedIn.logOutButton.ifSelected(x, y) == true) {
                userLoggedIn = null;
            }
        } else {
            displayingBook.ifClicked(x, y);
            if (displayingBook.backButton.ifSelected(x, y) == true) {
                displayingBook = null;
            }
        }
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
        if (userLoggedIn == null) {
            for (int i = 0; i < loginButtons.length; i++) {
                loginButtons[i].ifMoved(x, y);
            }
        } else {
            userLoggedIn.ifMoved(x, y);
        }
    }

    @Override
    public void setGenre(ColoredButtons button) {
    }

    @Override
    public UserHandling getUserHandling() {
        return userHandling;
    }

    @Override
    public BooksHandling getBookHandling() {
        return null;
    }

}
