package Admin;

import Books.*;
import DataStructures.*;
import Users.*;

public class AdminHandling {

    public HashingOpenAddQuadratic adminList;

    public AdminHandling(UserHandling userHandling, BooksHandling booksHandling) {
        this.adminList = new HashingOpenAddQuadratic(1);
        this.adminList.insert(new Admin(98765, "AdminLibrary", 
                "admin@12345", userHandling, booksHandling));
    }

    public Admin logInAdmin(long ID, String password) {
        Admin admin = (Admin) this.adminList.search(ID + "");
        if (admin != null && admin.ID == ID && password.equals(password) == true) {
            return admin;
        }
        return null;
    }

}
