package AStar;

import java.awt.*;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

/**
 * A utility class containing static methods for creating the system window and initializing the {@link GamePanel}.
 * The {@link GamePanel} contains all UI elements and is the root of the program's logic.
 * 
 * @author Christopher Sheaf
 */
public class AStarTest {
    /**Default width of the window, in pixels*/
    public static final int screenWidth = 800;
    /**Default height of the window, in pixels*/
    public static final int screenHeight = 600;
    
    /**
     * Create the {@link GamePanel} and use a LayoutManager to fill the entire parent container with it.
     * 
     * @param pane The parent component of which the {@link GamePanel} will fill.
     */
    public static void addComponentsToPane(Container pane) { 
        GamePanel panel = new GamePanel();
        
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.BOTH;
        //request any extra horizontal and vertical space:
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        pane.add(panel, c);
    }
    
    /**
     * Sets up the system window and calls addComponentsToPane() on it to add the UI
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("A-Star Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(screenWidth, screenHeight));
        
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Spins off a new thread to create and set up the window and UI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}