package AStar;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

/**
 * A panel representing a single unit of resolution within a pathfinding grid system.
 * A path is found by chaining together as many GridCells as needed, using the eight cardinal directions.
 * 
 * @author: Christopher Sheaf
 */
public class GridCell extends JPanel {
    public PathNode pathNode;
    String name; //Used for debugging purposes to label this GridCell with its location in the array
    
    /**
     * Important Note: The xCoord and yCoord assigned to this GridCell needs to be consistent with the actual position
     * it occupies relative to the other GridCells it connects to for pathfinding to work properly.
     * 
     * @param xCoord The physical 'x' location at which this GridCell is located.
     * @param yCoord The physical 'y' location at which this GridCell is located.
     */
    public GridCell(int xCoord, int yCoord) {
        this(xCoord, yCoord, null);
    }
    public GridCell(int xCoord, int yCoord, String new_name) {
        pathNode = new PathNode(xCoord, yCoord, 10);
        name = new_name;
        
        this.addMouseListener();
    }
    
    /**
     * Adds a MouseListener that toggles passability upon left-click and cycles through the special states upon right-click.
     */
    public void addMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                switch (event.getButton()) {
                    case MouseEvent.BUTTON1: //Left mouse button
                        if (pathNode.isPassable == true) {
                            pathNode.isPassable = false;
                        } else {
                            pathNode.isPassable = true;
                        }
                        GridCell.this.repaint();
                        break;
                    case MouseEvent.BUTTON3: //Right mouse button
                        GridCell.this.cyclePathNode();
                        GridCell.this.repaint();
                        break;
                }
            }
        });
    }
    
    /**
     * On a right click, cycles the path node between three looping states: Start-> Goal-> Regular-> ...
     */
    public void cyclePathNode() {
        if (pathNode.isStart == true) {
            pathNode.isStart = false;
            pathNode.isGoal = true;
        } else if (pathNode.isGoal == true) {
            pathNode.isStart = false;
            pathNode.isGoal = false;
        } else {
            pathNode.isStart = true;
            pathNode.isGoal = false;
        }
    }
    
    /**
     * Prints out the debugging label assigned to this GridCell.
     * The label is typically this GridCell's location in the grid array.
     */
    public String toString() {
        return name;
    }
    
    /**
     * <p>Draws the GridCell in the appropriate state:</p>
     * 
     * <b>Before searching for a path:</b>
     * <ul>
     *   <li>Passable but not searched = GRAY</li>
     *   <li>Not passable = BLACK</li>
     *   <li>Start = GREEN</li>
     *   <li>Goal = RED</li>
     * </ul>
     * 
     * <b>Additional states possible after searching for a path:</b>
     * <ul>
     *   <li>Passable and searched = BLUE</li>
     *   <li>Passable and a potential search target, if the path has not yet been found = YELLOW</li>
     * </ul>
     * 
     * Additionally, draws an arrow to the parent GridCell, if one exists.
     * {@link Pathfinder} sets the parent field when searching for the shortest path.
     * 
     * @param graphics The Graphics object to which this GridCell will be drawn.
     * Should always be called by the Swing UI, unless otherwise noted here by a special circumstance.
     */
    public void paintComponent(Graphics graphics) {
        if (pathNode.isStart == true) {
            graphics.setColor(Color.GREEN);
        } else if (pathNode.isGoal == true) {
            graphics.setColor(Color.RED);
        } else if (pathNode.isPassable == true) {
            if (pathNode.isOnClosedList == true) {
                graphics.setColor(Color.BLUE);
            } else if (pathNode.isOnOpenList == true) {
                graphics.setColor(Color.YELLOW);
            } else {
                graphics.setColor(Color.GRAY);
            }
        } else {
            graphics.setColor(Color.BLACK);
        }
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        graphics.setColor(Color.MAGENTA);
        graphics.drawRect(0, 0, this.getWidth(), this.getHeight());
        
        //Draws an arrow to the parent GridCell of this GridCell:
        if (pathNode.parentNode != null) {
            graphics.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setStroke(new BasicStroke(3));
            int vectorX = pathNode.parentNode.xCoord - pathNode.xCoord;
            int vectorY = pathNode.parentNode.yCoord - pathNode.yCoord;
            int halfWidth = this.getWidth() / 2;
            int halfHeight = this.getHeight() / 2;
            g2.drawLine(halfWidth, halfHeight, halfWidth + (vectorX * halfWidth), halfHeight + (vectorY * halfHeight));
        }
        
        //Prints debug text displaying some of the pathfinding values:
        /*graphics.setColor(Color.MAGENTA);
        graphics.drawString("DG: " + String.valueOf(pathNode.distanceToGoal), 2, 12);
        graphics.drawString("DS: " + String.valueOf(pathNode.distanceFromStart), 2, 22);
        graphics.drawString("EC: " + String.valueOf(pathNode.estimatedCost), 2, 32);*/
    }
}