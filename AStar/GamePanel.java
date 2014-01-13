package AStar;

import javax.swing.JPanel;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all UI elements and is the root of the program's logic. Fills itself with a grid of {@link GridCell} objects using dimensions defined by the constant values in this class.
 * Each {@link GridCell} can be interacted with by the user's mouse and put into any of the four valid states.
 * 
 * @author Christopher Sheaf
 */
public class GamePanel extends JPanel {
    public GridCell[][] grid;
    
    /**
     * Creates the grid of GridCells and 
     */
    public GamePanel() {
        grid = new GridCell[gridSizeX][gridSizeY];
        this.setLayout(new GridLayout(gridSizeY, gridSizeX));
        
        for (int y = 0; y < gridSizeY; y++) {
            for (int x = 0; x < gridSizeX; x++) {
                grid[x][y] = new GridCell(x, y, x + " by " + y);
                this.add(grid[x][y]);
            }
        }
        
        this.registerKeybindings();
    }
    
    /**
     * Registers the enter key as the "findPath" action, which creates a new {@link Pathfinder} object, retrieves the shortest path as a List, and prints the path to the console.
     * In an actual application, the shortest path List would then be used for calculating directions or moving an entity in a game world.
     */
    public void registerKeybindings() {
        Action findPathAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Searching for Path...");
                Pathfinder path = new Pathfinder(GamePanel.getSearchSpace(grid));
                List<PathNode> finalPath = path.findPath();
                GamePanel.this.repaint();
                System.out.println("The resulting path that was found is:");
                for (PathNode node : finalPath) {
                    System.out.println("Node " + node.xCoord + " by " + node.yCoord);
                }
            }
        };
        
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "findPath");
        this.getActionMap().put("findPath", findPathAction);
    }
    
    /**
     * Strips out the PathNodes present in an array of GridCells and returns them in a seperate array ready to be used by a {@link Pathfinder} object
     */
    public static PathNode[][] getSearchSpace(GridCell[][] input) {
        PathNode[][] searchSpace = new PathNode[input.length][input[0].length];
        
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length; y++) {
                searchSpace[x][y] = input[x][y].pathNode;
            }
        }
        
        return searchSpace;
    }
    
    public static final int gridSizeX = 16;
    public static final int gridSizeY = 12;
}
