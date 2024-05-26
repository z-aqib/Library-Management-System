package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import javax.swing.*;

public class DrawFrame extends JFrame {

    public DrawPanel panel; // a panel object to add to frame

    public void runDrawFrame() {

        /*
        this method runs the drawPanel class. it creates a panel of DrawPanel class 
        which is extended from JPanel. It then intializes properties of that JPsnel
         */
        // create a draw panel object (for all the buttons)
        this.panel = new DrawPanel(new int[]{0, 0, 1200, 650});

        // add this draw panel object to this frame
        add(getPanel());

        intializeFrameProperties();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call the method to create and write to the file
                // create the file we will be writing in
                try ( PrintWriter writer = new PrintWriter(new FileWriter(
                        createFile("src/Main/Analyzing the Library.txt")))) {
                    System.out.println("");
//                    File fileWrite = new File("src/Main/Analyzing the Library.txt");
//                    FileWriter fileWriter = new FileWriter(fileWrite);

                    // prepare the file we will be writing in
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    writer.write("File Written on " + dtf.format(now));

                    writer.write("\nTotal Books in Library -> " + panel.window.bookMenu.
                            getBookHandling().BKList.getElements());

                    writer.write("\n\nANALYSING ISSUING OF BOOKS");

                    //most issued book
                    writer.write("\nThe most issued book was -> " + panel.window.bookMenu.
                            getBookHandling().getMostIssuedBook().toString());

                    //least issued book
                    writer.write("\nThe least issued book was -> " + panel.window.bookMenu.
                            getBookHandling().getLeastIssuedBook().toString());

                    // average issued per book in library
                    writer.write("\nAverage Number of Issues per book -> " + panel.window.bookMenu.
                            getBookHandling().getAverageIssuedCount());

                    writer.write("\n\nANALYSING RATING OF BOOKS");

                    // most rated book
                    writer.write("\nThe most rated book was -> " + panel.window.bookMenu.
                            getBookHandling().getMostRatedBook().toString());

                    // least rated book
                    writer.write("\nThe least rated book was -> " + panel.window.bookMenu.
                            getBookHandling().getLeastRatedBook().toString());

                    // average rated book
                    writer.write("\nAverage Rating of Books -> " + panel.window.bookMenu.
                            getBookHandling().getAverageRating());

                    writer.write("\n\nANALYSING TOTAL COUNT OF BOOKS");

                    // total books in library
                    writer.write("\nTotal Number of Books Quantity in Library -> " + panel.window.bookMenu.
                            getBookHandling().getTotalBooksQuantityInStore());

//                    System.out.println("SUCCESS: File '" + fileWrite.getName() + "' has been written on successfully. ");
                } catch (FileNotFoundException ef) {
                    System.out.println("ERROR: FileNotFound error occured '" + ef.getMessage() + "'. ");
                } catch (IOException ef) {
                    System.out.println("ERROR: IOException error occured '" + ef.getMessage() + "' . ");
                } catch (Exception ef) {
                    System.out.println("ERROR: General error occured '" + ef.getMessage() + "'. ");
                } finally {
                    System.out.println("""
                                       A file has been created known as 'Analyzing the Library.txt'. 
                                       It contains statistics of the library just before closing. 
                                       You can view it on the path 'src/Main/Analyzing the Libaray.txt'""");
                }
            }

        });

    }

    private File createFile(String name) {
        File file = new File(name);
        try {
            if (file.createNewFile() == true) {
                System.out.println("SUCCESS: File '" + file.getName() + "' is created successfully. ");
            } else {
                System.out.println("ERROR: File '" + file.getName() + "' not created, it already exists. ");
            }
        } catch (IOException e) {
            System.out.println("ERROR: IOException error occured '" + e.getMessage() + "' . ");
        } catch (Exception e) {
            System.out.println("ERROR: General error occured '" + e.getMessage() + "' . ");
        }
        return file;
    }

    private void intializeFrameProperties() {

        /*
        this method declares the JFrame properties such as size, rescalability and background. 
         */
        // now declare frame properties
        this.pack(); // sizes the frame so that all its contents are at or above their preferred sizes
        this.setSize(1215, 690); // sets the x-dimension and y-dimension of frame
        this.setResizable(true); // makes frame fixed
        this.getContentPane().setBackground(Color.WHITE); // make background white
        this.setVisible(true); // makes frame visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits out of application
        this.setTitle("DATA STRUCTURES - PROJECT"); // set name of frame
        this.setLocationRelativeTo(null); //sets frame in the middle of laptop screen

    }

    @Override
    public String toString() {
        return "DrawFrame{" + "panel=" + getPanel().toString() + '}';
    }

    // --------------------------GETTERS AND SETTERS--------------------------
    public DrawPanel getPanel() {
        return panel;
    }

    public void setPanel(DrawPanel panel) {
        this.panel = panel;
    }

}
