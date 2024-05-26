package GUIWindow;

import Books.*;
import GUI.*;
import Users.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class StartingPage implements WindowInterface {

    private final int[] panelSize; // the size of the window i will be working with
    private final ColoredButtons[] headerButtons; //the header buttons below the logo
    private final ColoredButtons[] genreButtons; //the genres buttons
    private final String[] welcometxt;// text to be displayed on the welcome page
    private final ImageIcon[] bestPicksImages;
    public final BooksMenu bookMenu;
    public final PortalUser portalUser;
    private final PortalAdmin portalAdmin;

    private int currentWindow = -1; //what window is being printed at the moment
    private int currentBestPickImage = 0; // what best pick image is being printed at the moment
    private long startTime = System.currentTimeMillis() / 1000; //start time of how long has it been since best pick image was printed
    private BookButton issueBook = null;
    private BookButton returnBook = null;
    private PopUpWindow popUp = null;

    public StartingPage(int[] panelSize) {
        /*
        constructor: 
         */
        this.bestPicksImages = new ImageIcon[]{new ImageIcon("src/ImagesBestPicks/img1.jpg"),
            new ImageIcon("src/ImagesBestPicks/img2.jpg"),
            new ImageIcon("src/ImagesBestPicks/img3.jpg")};
        this.panelSize = panelSize;

        // declare the windows we will have and what sizes they will work om
        this.bookMenu = new BooksMenu(new int[]{370, 140, panelSize[2] - 370, panelSize[3] - 170});
        this.portalUser = new PortalUser(new int[]{10, 140, panelSize[2] - 20, panelSize[3] - 175});
        this.portalAdmin = new PortalAdmin(new int[]{10, 140, panelSize[2] - 20, panelSize[3] - 175},
                portalUser.getUserHandling(), bookMenu.getBookHandling());

        // declare the header buttons and their sizes
        String[] buttonNames = {"Welcome ", "All Books", "Best Picks", "Romance ", "Mystery",
            "Fantasy", "Thriller", "Young Adult", "Non Fiction", "Crime", "Suspense",
            "Chicklit", "User Login", "Admin "};
        headerButtons = new ColoredButtons[buttonNames.length];
        int width = (panelSize[2] + 10) / buttonNames.length;
        for (int i = 0; i < headerButtons.length; i++) {
            String[] hexColor = {"#EF5D58", "#4ABCB0"};
            if (i >= headerButtons.length - 2 || i == 0) {
                hexColor[0] = "#00A2E4";
                hexColor[1] = "#005c82";
            }
            headerButtons[i] = new ColoredButtons(buttonNames[i], panelSize[0] + (i * width),
                    100, width, 30, Color.decode(hexColor[0]),
                    Color.decode(hexColor[1]), true,
                    Color.white, Color.white);
        }
        headerButtons[0].make(true);

        // declare the genres buttons and their sizes
        String[] genresNames = {"Science Fiction", "Inspirational", "Classic", "Adult Fiction",
            "Drama", "Fiction", "Magical Realism", "Historical", "Horror",
            "Memoir", "Psychological Thriller", "Self Help", "Romantic Suspense",
            "Children", "Contemporary", "Erotic", "History", "Indian Literature",
            "Politics", "Adventure", "Detective", "Family Drama", "Humour",
            "Middle Grade", "Paranormal", "Relationships", "Spirituality",
            "Turkish Literature", "Asia", "Business", "Classic Literature",
            "Comedy", "Faith", "Fitness", "Health", "Islam", "Literary",
            "Military", "Philosophy", "Poetry", "Short Stories", "Technology"};
        genreButtons = new ColoredButtons[genresNames.length];
        width *= 1.35;
        for (int i = 0, x = 0, y = 0; i < genresNames.length; x++, i++) {
            genreButtons[i] = new ColoredButtons(genresNames[i], panelSize[0] + 10 + (x * width),
                    180 + (30 * y), width - 5, 25, Color.lightGray, Color.gray,
                    true, Color.white, Color.black);
            if (i != 0 && (i + 1) % 3 == 0) {
                y++;
                x = -1;
            }
        }

        // declare the welcome text to be painted
        String txt = """
                   Welcome to Preloved Book Store Library, where the joy of reading 
                   knows no bounds! Our mission is simple: to bring your favorite 
                   books and novels to your fingertips. With the mantra, "You 
                   name it, we have it," we aim to be your go-to digital 
                   destination for all things literary.
                   \nPreloved Book Store Library is your personal portal to a 
                   world of stories and knowledge. Dive into a diverse collection 
                   that caters to every taste and mood, from classics to 
                   contemporary gems. We're here to make your reading experience 
                   convenient and accessible - anytime, anywhere.
                   \nJoin our community of book lovers and explore the wonders 
                   of literature together. Prelove Book Store Library is more 
                   than just a digital shelf; it's a shared space for discovery 
                   and connection. Whether you're a seasoned reader or just starting 
                   your literary journey, Preloved Book Store Library is your 
                   companion for all things books. Let's embark on this adventure 
                   together - your next favorite read is just a click away. 
                   """;
        welcometxt = txt.split("\n");
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        // so depending on the window i am on, 
        switch (currentWindow) {
            case -1 -> {
                // if im at the starting page, ill paint the genreButtons and the welcome
                paintWelcome(g, observer);
                paintGenres(g);
            }
            case 0 -> {
                // but if im at the books window, ill paint genreButtons and the books window
                bookMenu.paint(g, observer);
                paintGenres(g);
            }
            case 1 ->
                portalUser.paint(g, observer);
            case 2 ->
                // other than that, i could be at user or admin window. paint them
                // but for user admin window, i dont need genreButtons so i wont print that
                portalAdmin.paint(g, observer);
        }
        // the foreground (logo, header headerButtons) will always be painted, regardless of window. 
        paintForeGround(g, observer);
        if (popUp != null && popUp.printOrNot == true) {
            popUp.paint(g);
        }
    }

    private void paintForeGround(Graphics g, ImageObserver observer) {
        // first paint our logo picture right at the top
        g.drawImage(new ImageIcon("src/ImagesOthers/logo2.png").getImage(),
                panelSize[0], panelSize[1], panelSize[0] + panelSize[2], 100, observer);

        // then paint the header headerButtons
        int toolTip_index = -1;
        for (int i = 0; i < headerButtons.length; i++) {
            headerButtons[i].paint(g);
            if (headerButtons[i].isMovedStatus() == true) {
                toolTip_index = i; // if cursor is on the button, save its index
            }
        }

        // then paint the tooltip of the required header button
        if (toolTip_index != -1) { // if there is a saved index, print its tooltip
            headerButtons[toolTip_index].paintToolTip(g);
        }
    }

    private void paintGenres(Graphics g) {
        // paint the title to show that we are painting genreButtons
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 25));
        g.drawString("More Genres To Explore From", 18, 165);

        // now paint each genre button
        int toolTip_index = -1;
        for (int i = 0; i < genreButtons.length; i++) {
            genreButtons[i].paint(g);
            if (genreButtons[i].isMovedStatus() == true) {
                toolTip_index = i;
            }
        }

        // if a tooltip is on, paint the tooltip of the required button
        if (toolTip_index != -1) {
            genreButtons[toolTip_index].paintToolTip(g);
        }
    }

    private void paintWelcome(Graphics g, ImageObserver observer) {
        // paint the title of the welcome page i.e. the library name
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("The Preloved BookStore Library", ((panelSize[2] / 5) * 4) - (13 * 15) + 5, 170);

        // now paint the agenda and motivation of the library
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        for (int i = 0; i < welcometxt.length; i++) {
            g.drawString(welcometxt[i], (panelSize[2] / 5) * 3 + 50, 200 + (i * 20));
        }

        // now paint the best picks bestPicksImages of the library
        g.drawImage(bestPicksImages[currentBestPickImage].getImage(), (panelSize[0] + (panelSize[2] / 3)) - 30,
                145, (panelSize[2] / 3) - 10, panelSize[3] - 180, observer);

        // update the best picks bestPicksImages: if it has been displayed for more than 1 second, change it. 
        if ((startTime - System.currentTimeMillis() / 1000) <= -1) {
            if (++currentBestPickImage == bestPicksImages.length) {
                currentBestPickImage = 0;
            }
            startTime = System.currentTimeMillis() / 1000; // update the start time of displaying
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (popUp == null) {
            // if im clicking on one of the header headerButtons below the logo, then i will change my window
            if (y >= 100 && y <= 130 || (x >= 0 && x <= 355 && y >= 150 && y <= 595)) {
                for (int i = 0; i < headerButtons.length; i++) {
                    //if i click the first button, it means i want the welcome page. we will come to this window
                    if (i == 0 && headerButtons[i].ifSelected(x, y) == true) {
                        currentWindow = -1;
                    } //but if i click on the second last button, it means im going to the user window
                    else if (i == headerButtons.length - 2 && headerButtons[i].ifSelected(x, y) == true) {
                        currentWindow = 1;
                    } // but if i click on the last button, it means im going to the admin window
                    else if (i == headerButtons.length - 1 && headerButtons[i].ifSelected(x, y) == true) {
                        currentWindow = 2;
                    } //but if i click any of the headerButtons in between the first, secondlast and last button, i go to books window
                    else if (headerButtons[i].ifSelected(x, y) == true) {
                        currentWindow = 0;
                        bookMenu.setGenre(headerButtons[i]);
                    }
                }
                if (currentWindow == 0 || currentWindow == -1) {
                    //if i press any of the genreButtons, i go straight to the books window
                    for (int i = 0; i < genreButtons.length; i++) {
                        if (genreButtons[i].ifSelected(x, y) == true) {
                            currentWindow = 0;
                            bookMenu.setGenre(genreButtons[i]); // and i update which books i want
                        }
                    }
                }
            }
            // if im not on this window and on some other window, then make sure to call its clicked
            switch (currentWindow) {
                case 0 ->
                    bookMenu.mouseClicked(x, y);
                case 1 ->
                    portalUser.mouseClicked(x, y);
                case 2 ->
                    portalAdmin.mouseClicked(x, y);
            }
            if (issueBook == null) {
                if (bookMenu.displayBook != null && bookMenu.displayBook.issueButton.ifSelected(x, y) == true) {
                    issueBook = bookMenu.displayBook;
                    if (portalUser.userLoggedIn != null) {
                        if (portalUser.userLoggedIn.issueBook(issueBook.book) == true) {
                            popUp = new PopUpWindow("SUCCESS: '" + issueBook.book.getName().toLowerCase()
                                    + "' has been issued successfully. ");
                        } else {
                            popUp = new PopUpWindow("ERROR: '" + issueBook.book.getName().toLowerCase()
                                    + "' cannot be issued as you can only issue 4 books at once OR the "
                                    + "book is out of stock. ");
                        }
                        issueBook = null;
                    } else {
                        currentWindow = 1;
                        popUp = new PopUpWindow("To issue book '" + issueBook.book.getName().toLowerCase()
                                + "' you will have to login first. ");
                    }
                }
            } else {
                if (portalUser.userLoggedIn != null) {
                    if (portalUser.userLoggedIn.issueBook(issueBook.book) == true) {
                        popUp = new PopUpWindow("SUCCESS: '" + issueBook.book.getName().toLowerCase()
                                + "' has been issued successfully. ");
                    } else {
                        popUp = new PopUpWindow("ERROR: '" + issueBook.book.getName().toLowerCase()
                                + "' cannot be issued as you can only issue 4 books at once OR the "
                                + "book is out of stock. ");
                    }
                    issueBook = null;
                } else {
                    if (currentWindow != 1) {
                        currentWindow = 1;
                        popUp = new PopUpWindow("To issue book '" + issueBook.book.getName().toLowerCase()
                                + "' you will have to login first. ");
                    }
                }
            }
            if (returnBook == null) {
                if (bookMenu.displayBook != null && bookMenu.displayBook.returnButton.ifSelected(x, y) == true) {
                    returnBook = bookMenu.displayBook;
                    if (portalUser.userLoggedIn != null) {
                        if (portalUser.userLoggedIn.returnBook(returnBook.book) == true) {
                            popUp = new PopUpWindow("SUCCESS: '" + returnBook.book.getName().toLowerCase()
                                    + "' has been returned successfully. ");
                        } else {
                            popUp = new PopUpWindow("ERROR: '" + returnBook.book.getName().toLowerCase()
                                    + "' cannot be returned as you have not issued it. ");
                        }
                        returnBook = null;
                    } else {
                        currentWindow = 1;
                        popUp = new PopUpWindow("To return book '" + returnBook.book.getName().toLowerCase()
                                + "' you will have to login first. ");
                    }
                }
            } else {
                if (portalUser.userLoggedIn != null) {
                    if (portalUser.userLoggedIn.returnBook(returnBook.book) == true) {
                        popUp = new PopUpWindow("SUCCESS: '" + returnBook.book.getName().toLowerCase()
                                + "' has been issued successfully. ");
                    } else {
                            popUp = new PopUpWindow("ERROR: '" + returnBook.book.getName().toLowerCase()
                                    + "' cannot be returned as you have not issued it. ");
                        }
                        returnBook = null;
                } else {
                    if (currentWindow != 1) {
                        currentWindow = 1;
                        popUp = new PopUpWindow("To return book '" + returnBook.book.getName().toLowerCase()
                                + "' you will have to login first. ");
                    }
                }
            }
        } else {
            if (popUp.clicked(x, y) == true) {
                popUp = null;
            }
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        switch (currentWindow) {
            case 0 ->
                bookMenu.mousePressed(x, y);
            case 1 ->
                portalUser.mousePressed(x, y);
            case 2 ->
                portalAdmin.mousePressed(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        switch (currentWindow) {
            case 0 ->
                bookMenu.mouseDragged(x, y);
            case 1 ->
                portalUser.mouseDragged(x, y);
            case 2 ->
                portalAdmin.mouseDragged(x, y);
        }
    }

    @Override
    public void mouseEntered(int x, int y) {
        switch (currentWindow) {
            case 0 ->
                bookMenu.mouseEntered(x, y);
            case 1 ->
                portalUser.mouseEntered(x, y);
            case 2 ->
                portalAdmin.mouseEntered(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        switch (currentWindow) {
            case 0 ->
                bookMenu.mouseReleased(x, y);
            case 1 ->
                portalUser.mouseReleased(x, y);
            case 2 ->
                portalAdmin.mouseReleased(x, y);
        }
    }

    @Override
    public void mouseExited(int x, int y) {
        switch (currentWindow) {
            case 0 ->
                bookMenu.mouseExited(x, y);
            case 1 ->
                portalUser.mouseExited(x, y);
            case 2 ->
                portalAdmin.mouseExited(x, y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (popUp == null) {
            for (int i = 0; i < headerButtons.length; i++) {
                headerButtons[i].ifMoved(x, y);
            }
            if (currentWindow == -1 || currentWindow == 0) {
                for (int i = 0; i < genreButtons.length; i++) {
                    genreButtons[i].ifMoved(x, y);
                }
            }
            switch (currentWindow) {
                case 0 ->
                    bookMenu.mouseMoved(x, y);
                case 1 ->
                    portalUser.mouseMoved(x, y);
                case 2 ->
                    portalAdmin.mouseMoved(x, y);
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
