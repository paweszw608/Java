package pl.polsl.lab.worldcupstatsclient.views;
import java.awt.*;
import javax.swing.*;

/**
 * View class for displaying content and components.
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public class WorldCupStatsView extends JFrame {

    
    /**
     * Non-parameter Constructor
     */
    public WorldCupStatsView() {
        this.initializeView();
    }

    /**
     * Initialises this view and it's components. Creates layout and sets window
     * elements like title, size
     */
    private void initializeView() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("World Cup");
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    /**
     * Adds component to this View frame
     * @param c component to add
     */
    void addToFrame(Component c) {
        this.add(c);
    }

    /**
     * Displays message in a dialog box
     *
     * @param msg text of the message
     * @param title title of dialog box
     */
    public void displayMessage(String msg, String title) {
        JOptionPane.showMessageDialog(this, msg, title, 1);
    }

}
