package Users;

import DataStructures.*;
import java.io.*;
import javax.swing.*;

public class UserHandling {

    public HashingOpenAddQuadratic hashing = new HashingOpenAddQuadratic(1000);

    public UserHandling() {
        filing();
    }

    private void filing() {
        try {
            FileInputStream fstream = new FileInputStream("src/Users/Users.csv");
            try ( DataInputStream in = new DataInputStream(fstream)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    String[] user = strLine.split(",");
                    hashing.insert(new User(Long.parseLong(user[0]), user[1], user[2],
                            user[3], user[4], new ImageIcon(user[5]).getImage()));
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public User loginUser(long ID, String pw) {
        User user = (User) hashing.search(ID + "");
        if (user != null && user.ID == ID && user.password.equals(pw) == true) {
            return user;
        } else {
            return null;
        }
    }
    
    public User getUser(long ID) {
        User user = (User) hashing.search(ID + "");
        if (user != null && user.ID == ID) {
            return user;
        } else {
            return null;
        }
    }

}
