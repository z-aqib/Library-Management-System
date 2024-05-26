package Admin;

import Books.*;
import Users.*;
import java.awt.*;

public class Admin implements Comparable {

    public long ID; // get
    public String name; //get
    public String password; //get
    public UserHandling userHandling;
    public BooksHandling booksHandling;

    public Admin(long ID, String name, String password, UserHandling u, BooksHandling a) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.userHandling = u;
        this.booksHandling = a;
    }

    public void updateUserName(User user, String name) {
        user.setName(name);
    }

    public void updateUserAddress(User user, String address) {
        user.setAddress(address);
    }

    public void updateUserEmail(User user, String email) {
        user.setEmail(email);
    }

    public void updateUserPassword(User user, String password) {
        user.setPassword(password);
    }

    public User addUser(String name, String email, String password, String address, Image img) {
        long ID_new = userHandling.hashing.getElements() + 12345 + 1;
        User u = new User(ID_new, name, email, password, address, img);
        userHandling.hashing.insert(u); // average = constant, worst = n
        return u;
    }

    public boolean deleteBook(Book book) {
        //deletion of book will only work IF no other user has issued any of its copies currently
        //booksHandling.BKList.delete(book);
        User[] userTable = (User[]) userHandling.hashing.getTable();
        for (int i = 0; i < userHandling.hashing.getTable().length; i++) {
            if (userTable[i].getCurrentlyissuedbooks().find(book) == true) {
                return false;
            }
        }
        booksHandling.BKList.delete(book);
        return true;
    }

    //this method will generate ID of book and create book itself, then insert into HashMap
    public Book addBook(String name, String author, long quantity, String genre,
            double rating, Image img) {
        Book b =new Book(booksHandling.BKList.getElements() + 10000 + 1,
                name, author, true, quantity, genre, rating, 0, img);
        booksHandling.BKList.insert(b);
        return b;
    }

    public void updateBookName(Book book, String name) {
        book.setName(name);
    }

    public void updateBookAuthor(Book book, String author) {
        book.setAuthor(author);
    }

    public void changeStock(Book book, int change) {
        book.setQuantity(change);
    }

    //For books: No.of timesissued, users issued with(LL) cannot be changed
    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        return ID + "";
    }

}
