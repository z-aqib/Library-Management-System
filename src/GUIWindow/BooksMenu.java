package GUIWindow;

import Books.*;
import DataStructures.*;
import GUI.*;
import Users.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class BooksMenu implements WindowInterface {

    private final int[] totalArea;
    private final int[] sortingArea;
    private final int[] bookArea;
    private final ColoredButtons[] sortingButtons;

    private final ColoredButtons searchButton;
    private final ColoredButtons searchBox;
    private final BooksHandling booksHandling;
    private final Point messagePoint;
    private final Point errorMessagePoint;

    private boolean displayBookDetails;
    private int pageNumber;
    private int totalPages;
    private String message;
    private String error;
    private ColoredButtons[] pageNumbers;
    private MyArrayList<Book> displayingList;
    private BookButton[] displayingBooks;
    public BookButton displayBook;

    public BooksMenu(int[] panel) {
        this.displayBook = null;

        this.totalArea = panel;
        this.booksHandling = new BooksHandling();
        this.sortingArea = new int[]{1000, 235, 190, 380};
        this.bookArea = new int[]{totalArea[0], totalArea[1], 620, totalArea[3]};
        this.displayingList = booksHandling.getPopularBooks();
        this.searchButton = new ColoredButtons("Search", sortingArea[0] + 50, sortingArea[1] - 65,
                100, 30, Color.green, Color.BLUE,
                true, Color.white, Color.black);
        this.searchBox = new ColoredButtons("press search & enter in console",
                sortingArea[0], sortingArea[1] - 100, 190, 30, Color.decode("#F5F5F5"),
                Color.white, true, Color.black, Color.BLACK);
        this.messagePoint = new Point(bookArea[0], bookArea[1] + 20);
        this.errorMessagePoint = new Point(messagePoint.x, messagePoint.y + 20);
        this.message = "Showing Best Picks";
        this.error = "";

        // declare the menu
        String[] menuNames = {"Name: A to Z", "Name: Z to A", "Author: A to Z", "Author: Z to A",
            "Popularity: Low To High", "Popularity: High To Low", "Rating: Low To High", "Rating: High To Low",
            "Availability: High To Low", "ID: Low To High", "ID: High To Low"};
        sortingButtons = new ColoredButtons[menuNames.length];
        for (int i = 0; i < menuNames.length; i++) {
            sortingButtons[i] = new ColoredButtons(menuNames[i], sortingArea[0],
                    sortingArea[1] + (i * 25), sortingArea[2], 20, Color.decode("#ECECEC"),
                    Color.gray, true, Color.WHITE, Color.black);
        }
        // booka
        this.displayBookDetails = false;
        displayingBooks = new BookButton[15];
        newPage();
    }

    private void updatePageNumber(int update) {
        if (update <= totalPages) {
            this.pageNumber = update;
            updateBooks();
        }
    }

    private void updateBooks() {
        int width = bookArea[2] / 5;
        int height = (bookArea[3] - 50) / 3;
        displayingBooks = new BookButton[15];
        for (int i = 0, y = 0, x = 0, counter = ((pageNumber - 1) * 15); counter < displayingList.Length()
                && i < displayingBooks.length; counter++, x++, i++) {
            displayingBooks[i] = new BookButton(displayingList.get(counter),
                    bookArea[0] + (x * width), bookArea[1] + 50 + (y * height), width - 5, height - 5);
            if (i != 0 && (i + 1) % 5 == 0) {
                y++;
                x = -1;
            }
        }
    }

    private void newPage() {
        // declare the books
        this.pageNumber = 1;
        totalPages = (int) Math.ceil((double) displayingList.Length() / 15);
        this.pageNumbers = new ColoredButtons[(int) totalPages];
        for (int i = 0, y = 0, x = 0; i < pageNumbers.length; x++, i++) {
            pageNumbers[i] = new ColoredButtons((i + 1) + "", 1000 + (x * 30),
                    sortingButtons[sortingButtons.length - 1].getTopLeft().y
                    + sortingButtons[sortingButtons.length - 1].getHeight() + (y * 30) + 10,
                    25, 25, Color.lightGray, Color.gray,
                    true, Color.black, Color.white);
            if ((i + 1) % 12 == 0) {
                ++y;
                x = -1;
            }
        }
        if (totalPages != 0) {
            pageNumbers[0].make(true);
        }
        updateBooks();
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        // paint search box and button at all times
        this.searchButton.paint(g);
        this.searchBox.paint(g);

        // if we are not displaying a book and displaying lists of books, 
        if (displayBookDetails == false) {
            // paint the message: what list are we showing? is it a genre or a search result?
            g.setColor(Color.black);
            g.setFont(new Font(Font.SERIF, Font.BOLD, 23));
            g.drawString(message, messagePoint.x, messagePoint.y);

            // paint the error: what if search result was 0? if not, how many books are we displaying?
            g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
            g.drawString(error, errorMessagePoint.x, errorMessagePoint.y);

            // paint the menu buttons of sorting
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
            g.drawString("Sort Books By", sortingArea[0] + 30, sortingArea[1] - 10);
            for (int i = 0; i < sortingButtons.length; i++) {
                sortingButtons[i].paint(g);
            }

            // now paint each book being displayed
            for (int i = 0; i < displayingBooks.length; i++) {
                if (displayingBooks[i] != null) {
                    displayingBooks[i].paint(g, observer);
                }
            }

            // paint each page number button
            for (int i = 0; i < pageNumbers.length; i++) {
                pageNumbers[i].paint(g);
            }

        } else { // if else, we are displaying the details of a specific book, 
            displayBook.paint(g, observer);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (displayBookDetails == false) {
            if (x > sortingArea[0] && x < sortingButtons[0].getWidth() + sortingArea[0]
                    && y > sortingArea[1] && y < (sortingButtons.length * 25) + sortingArea[1]) {
                for (int i = 0; i < sortingButtons.length; i++) {
                    if (sortingButtons[i].ifSelected(x, y) == true) {
                        displayingList = booksHandling.bubbleSort(displayingList, sortingButtons[i].getName());
                        newPage();
                    }
                }
            }
            for (int i = 0; i < displayingBooks.length; i++) {
                if (displayingBooks[i] != null && displayingBooks[i].ifClicked(x, y) == true) {
                    displayBookDetails = true;
                    displayBook = displayingBooks[i];
                }
            }
            for (int i = 0; i < pageNumbers.length; i++) {
                if (pageNumbers[i].ifSelected(x, y) == true) {
                    updatePageNumber(i + 1);
                }
            }
        } else {
            displayBook.ifClicked(x, y);
            if (displayBook.backButton.ifSelected(x, y) == true) {
                displayBook = null;
                displayBookDetails = false;
            }
        }
        if (this.searchButton.ifSelected(x, y) == true) {
            searchBox.setName("Please write search in console: ");
            Scanner input = new Scanner(System.in);
            System.out.print("Please enter what to search: ");
            String userInput = input.nextLine();
            searchBox.setName(userInput);
            displayingList = booksHandling.searchByUser(userInput);
            this.message = "Showing search results for '" + userInput + "' ";
            if (this.displayingList.Length() == 0) {
                this.error = "ERROR: No results found. ";
            } else {
                this.error = displayingList.Length() + " search results found. ";
            }
            newPage();
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
        this.searchButton.ifMoved(x, y);
        if (displayBookDetails == false) {
            for (int i = 0; i < sortingButtons.length; i++) {
                sortingButtons[i].ifMoved(x, y);
            }
            for (int i = 0; i < displayingBooks.length; i++) {
                if (displayingBooks[i] != null) {
                    displayingBooks[i].ifMoved(x, y);
                }
            }
            for (int i = 0; i < pageNumbers.length; i++) {
                pageNumbers[i].ifMoved(x, y);
            }
        } else {
            displayBook.ifMoved(x, y);
        }
    }

    @Override
    public void setGenre(ColoredButtons button) {
        if (button.getName().equalsIgnoreCase("Best Picks") == true) {
            this.displayingList = booksHandling.getPopularBooks();
            this.message = "Showing Best Picks";
        } else if (button.getName().equalsIgnoreCase("All Books") == true) {
            this.displayingList = booksHandling.books;
            this.message = "Showing All Books";
        } else {
            this.displayingList = booksHandling.sortByGenre(button.getName());
            this.message = "Showing " + button.getName() + "";
        }
        this.error = "Contains " + this.displayingList.Length() + " books";
        newPage();
        displayBookDetails = false;
        displayBook = null;
    }

    @Override
    public UserHandling getUserHandling() {
        return null;
    }

    @Override
    public BooksHandling getBookHandling() {
        return booksHandling;
    }

}
