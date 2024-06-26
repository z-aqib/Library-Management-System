package GUI;

import GUIWindow.StartingPage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class DrawPanel extends JPanel implements ActionListener, MouseInputListener {

    // panel features
    private Timer timer; // so a timer will be used to keep refreshing the code and repainting it
    private final int delay = 20; // the program will be refreshed after this many milliseconds
    public StartingPage window; // a window is created which contains the entire MSPaint buttons
    private Label mousePosition; // a label which displays what the mouse is doing
    private Label mouseState;

    public DrawPanel(int[] panel) {
        /*
        this is the constructor of the DrawPanel class. it intializes and sets the panel
        first, then intializes the objects to be painted on the panel.
         */
        intializePanelFeatures(); // method to intialize the features of panel
        intializeObjects(panel); // method to intialize the labels and window
    }

    private void intializePanelFeatures() {
        /*
        so this method will intialize features of the panel such as a mouse listener, 
        key listener, mouse motion listener, panel itself such as background, focusable and timer.
        this method will only be called once from the constructor
         */
        this.setBackground(Color.WHITE); // sets the background color of the panel
        this.setFocusable(true); // java frame is already set default focusable as true
        // it sets the JPanel the ability of being focused
        this.addMouseListener(this); // panel is now listening to mouse clicks
        this.addMouseMotionListener(this); // panel is now listening to mouse movement
        this.timer = new Timer(this.delay, this); // sets a timer to refresh panel 
        this.timer.start(); // starts the timer
    }

    private void intializeObjects(int[] panel) {
        /*
        this method will intialize the window which contains all the buttons
        this method will only be called once from the constructor
         */
        this.window = new StartingPage(panel); // intialize the window
        this.mousePosition = new Label("Mouse Coordinates: X=0,Y=0");
        add(this.mousePosition);
        this.mouseState = new Label("Mouse Exited   ");
        add(this.mouseState);
    }

    @Override
    public void paintComponent(Graphics g) {
        /*
        this method paints the window and labels which have been intialized
         */
        super.paintComponent(g); // super call JPanel paint as it paints the feautures of the panel
        this.mousePosition.setBounds(10, 630, 200, 20);
        this.mouseState.setBounds(220, 630, 100, 20);
        g.drawLine(0, 625, 1200, 625);
        this.window.paint(g, this);
    }

    // NOT TO BE CHANGED - for refreshing the screen constantly
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        this method paints the panel after this.delay milliseconds
         */
        Toolkit.getDefaultToolkit().sync();
        repaint();

    }

    // ABSTRACT METHODS - mouse motions
    @Override
    public void mouseClicked(MouseEvent e) {
        /*
        this method runs whenever the mouse clicks on the screen, it changes the 
        buttons state respectively
         */
        this.mousePosition.setText("Mouse Coordinates: X=" + e.getX() + ",Y=" + e.getY());
        this.mouseState.setText("Mouse Clicked ");
        this.window.mouseClicked(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*
        this method runs whenever the mouse clicks and stays clicked i.e. mouse 
        stays pressed on the screen
         */
        this.mousePosition.setText("Mouse Coordinates: X=" + e.getX() + ",Y=" + e.getY());
        this.mouseState.setText("Mouse Pressed ");
        this.window.mousePressed(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*
        this method runs whenever the mouse clicks/presses and then RELEASES the mouse
         */
        this.mousePosition.setText("Mouse Coordinates: X=" + e.getX() + ",Y=" + e.getY());
        this.mouseState.setText("Mouse Released ");
        this.window.mouseReleased(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        /*
        this method runs whenver the mouse enters the JPanel screen
         */
        this.mousePosition.setText("Mouse Coordinates: X=" + e.getX() + ",Y=" + e.getY());
        this.mouseState.setText("Mouse Entered ");
        this.window.mouseEntered(e.getX(), e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        /*
        this method runs whenver the mouse leaves the JPanel screen
         */
        this.mousePosition.setText("Mouse Coordinates: X=" + e.getX() + ",Y=" + e.getY());
        this.mouseState.setText("Mouse Exited ");
        this.window.mouseExited(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        /*
        this method runs whenever the mouse is clicked/pressed and it is moved, i.e.
        the mouse moves while being pressed/clicked together
         */
        this.mousePosition.setText("Mouse Coordinates: X=" + e.getX() + ",Y=" + e.getY());
        this.mouseState.setText("Mouse Dragged ");
        this.window.mouseDragged(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        /*
        this method runs whenever the mouse moves without being pressed or clicked
         */
        this.mousePosition.setText("Mouse Coordinates: X=" + e.getX() + ",Y=" + e.getY());
        this.mouseState.setText("Mouse Moved ");
        this.window.mouseMoved(e.getX(), e.getY());
    }

}
