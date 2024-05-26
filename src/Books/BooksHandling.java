package Books;

import DataStructures.*;
import java.io.*;
import javax.swing.ImageIcon;

public class BooksHandling {

    public HashingOpenAddQuadratic<Book> BKList;
    public MyArrayList<Book> books;

    public BooksHandling() {
        filing();
        books = new MyArrayList<>(5000);
        for (int i = 0; i < BKList.getTable().length; i++) {
            if (BKList.getTable()[i] != null) {
                books.add((Book) BKList.getTable()[i]);
            }
        }
    }

    private void filing() {
        BKList = new HashingOpenAddQuadratic<>(5000);
        try {
            FileInputStream fstream = new FileInputStream("src/Books/Books.csv");
            try ( DataInputStream in = new DataInputStream(fstream)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    String[] word = strLine.split(",");
                    long ID = Long.parseLong(word[0]);
                    String name = word[1];
                    String author = word[2];
                    String s = word[3];
                    boolean available;
                    available = s.equalsIgnoreCase("yes") == true;
                    long quantity = Long.parseLong(word[4]);
                    String genre = word[5];
                    double rating = Double.parseDouble(word[6]);
                    int numberOfTimesIssued = Integer.parseInt(word[7]);
                    String imagePath = word[8];
                    ImageIcon temp = new ImageIcon(imagePath);
                    BKList.insert(new Book(ID, name, author, available, quantity, genre, rating, numberOfTimesIssued, temp.getImage()));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // engine search method user enters a word to be search the method check for the word in name as well as author and displays
    // which contain the word either in name or author
    public MyArrayList<Book> searchByUser(String SearchName) {
        MyArrayList<Book> Templist = new MyArrayList<>();
        System.out.println("size = " + BKList.getElements());
        for (Comparable<Book> book : BKList.getTable()) {
            Book temp = (Book) book;
            if (temp != null) {
                String t = temp.getAuthor().toLowerCase();
                String s = temp.getName().toLowerCase();
                if (s.contains(SearchName.toLowerCase()) || t.contains(SearchName.toLowerCase())) {
                    Templist.add(temp);
                }
            }
        }
        return Templist;
    }

    //search method to be used in issue books to help check books availability
    public Book searchForIssue(long targetID) {
        for (Comparable<Book> value : BKList.getTable()) {
            if (value != null) {
                Book book = (Book) value;
                if (book.getID() == targetID) {
                    return book;
                }
            }
        }
        return null; // Book not found
    }

    //  issue book methodd
    // method to sort books by genre (assumes hashtable is an array)
    public MyArrayList<Book> sortByGenre(String G) {
        MyArrayList<Book> bookss = new MyArrayList<>();
        for (int i = 0; i < BKList.getTable().length; i++) {
            Book temp = (Book) BKList.getTable()[i];
            if (temp != null) {
                String s = temp.getGenre();
                if (s.contains(G.trim()) == true) {
                    bookss.add(temp);
                }
            }
        }
        return bookss;
    }

    public MyArrayList bubbleSort(MyArrayList<Book> array, String s) {
        int n = array.Length();
        String[] sp = s.split(":");
        while (n > 0) {
            int lastModifiedIndex = 0;
            for (int currentIndex = 1; currentIndex < n; currentIndex++) {
                if (array.get(currentIndex) != null && array.get(currentIndex - 1) != null) {
                    // if the item at the previous index is greater than the item at the `currentIndex`, swap them
                    if (sp[0].equalsIgnoreCase("name")) { // sorting by name
                        if (sp[1].contains("A to Z") == true) {
                            if (array.get(currentIndex - 1).getName().compareTo(array.get(currentIndex).getName()) > 0) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        } else if (sp[1].contains("Z to A") == true) {
                            if (array.get(currentIndex - 1).getName().compareTo(array.get(currentIndex).getName()) < 0) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        }
                    } else if (sp[0].equalsIgnoreCase("author")) { //sorting by author
                        if (sp[1].contains("A to Z") == true) {
                            if (array.get(currentIndex - 1).getAuthor().compareTo(array.get(currentIndex).getAuthor()) > 0) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        } else if (sp[1].contains("Z to A") == true) {
                            if (array.get(currentIndex - 1).getAuthor().compareTo(array.get(currentIndex).getAuthor()) < 0) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        }
                    } else if (sp[0].equalsIgnoreCase("popularity")) { //sorting by popularity
                        if (sp[1].contains("High To Low") == true) {
                            if (array.get(currentIndex - 1).getTimesIssued() < (array.get(currentIndex).getTimesIssued())) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        } else if (sp[1].contains("Low To High") == true) {
                            if (array.get(currentIndex - 1).getTimesIssued() > (array.get(currentIndex).getTimesIssued())) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        }
                    } else if (sp[0].equalsIgnoreCase("rating")) { //sorting by rating
                        if (sp[1].contains("Low To High") == true) {
                            if (array.get(currentIndex - 1).getRating() > (array.get(currentIndex).getRating())) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        } else if (sp[1].contains("High To Low") == true) {
                            if (array.get(currentIndex - 1).getRating() < (array.get(currentIndex).getRating())) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        }
                    } else if (sp[0].equalsIgnoreCase("availability")) { //sorting by availability
                        if (array.get(currentIndex - 1).getQuantity() < array.get(currentIndex).getQuantity()) {
                            // swap
                            array = swap(array, currentIndex);
                            // save the index that was modified
                            lastModifiedIndex = currentIndex;
                        }
                    } else if (sp[0].equalsIgnoreCase("id")) { //sorting by ID
                        if (sp[1].contains("Low To High") == true) {
                            if (array.get(currentIndex - 1).getID() > array.get(currentIndex).getID()) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        } else if (sp[1].contains("High To Low") == true) {
                            if (array.get(currentIndex - 1).getID() < array.get(currentIndex).getID()) {
                                // swap
                                array = swap(array, currentIndex);
                                // save the index that was modified
                                lastModifiedIndex = currentIndex;
                            }
                        }
                    }
                }
            }
            // save the last modified index so we know not to iterate past it since all proceeding values are sorted
            n = lastModifiedIndex;
        }
        return array;
    }

    private MyArrayList<Book> swap(MyArrayList<Book> array, int currentIndex) {
        Book temp = array.get(currentIndex - 1);
        array.set(currentIndex - 1, array.get(currentIndex));
        array.set(currentIndex, temp);
        return array;
    }

    public MyArrayList<Book> getPopularBooks() {
        MyArrayList<Book> bookss = new MyArrayList<>();
        for (int i = 0; i < BKList.getTable().length; i++) {
            Book temp = (Book) BKList.getTable()[i];
            if (temp != null && (temp.getRating() >= 4.70 || temp.getTimesIssued() >= 95)) {
                bookss.add(temp);
            }
        }
        return bookss;
    }

    public Book getMostIssuedBook() {
        return (Book) bubbleSort(getPopularBooks(), "Popularity: High To Low").get(0);
    }

    public Book getLeastIssuedBook() {
        return (Book) bubbleSort(books, "Popularity: Low To High").get(0);
    }

    public Book getMostRatedBook() {
        return (Book) bubbleSort(books, "Rating: High To Low").get(0);
    }

    public Book getLeastRatedBook() {
        return (Book) bubbleSort(books, "Rating: Low To High").get(0);
    }

    public double getAverageIssuedCount() {
        double total = 0;
        int count = 0;
        for (int i = 0; i < BKList.getTable().length; i++) {
            Book temp = (Book) BKList.getTable()[i];
            if (temp != null) {
                total += temp.getTimesIssued();
                count++;
            }
        }
        return total / count;
    }

    public double getAverageRating() {
        double total = 0;
        int count = 0;
        for (int i = 0; i < BKList.getTable().length; i++) {
            Book temp = (Book) BKList.getTable()[i];
            if (temp != null) {
                total += temp.getRating();
                count++;
            }
        }
        return total / count;
    }

    public int getTotalBooksQuantityInStore() {
        int total = 0;
        for (int i = 0; i < BKList.getTable().length; i++) {
            Book temp = (Book) BKList.getTable()[i];
            if (temp != null) {
                total += temp.getQuantity();
            }
        }
        return total;
    }

}
