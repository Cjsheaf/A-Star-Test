package AStar;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Point;
import java.util.Collections;

public class Pathfinder {
    private ArrayList<PathNode> openList;
    private ArrayList<PathNode> closedList;
    private PathNode[][] searchSpace;
    private Point searchSpaceOffset;
    private PathNode startNode;
    private PathNode goalNode;
    
    public Pathfinder(PathNode[][] searchSpace) {
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        this.searchSpace = searchSpace;
        searchSpaceOffset = new Point(searchSpace[0][0].xCoord, searchSpace[0][0].yCoord);
        startNode = getStartNode();
        goalNode = getGoalNode();
    }
    
    public List<PathNode> findPath() {
        PathNode goalFound = null; //If the goal is found, it will be stored here
        
        openList.add(startNode); //Add the start node to the open list
        goalFound = examineSurroundingNodes(startNode); //Populate the open list for the first time
        closedList.add(startNode);
        startNode.isOnClosedList = true; //[TEST]
        openList.remove(startNode);
        Collections.sort(openList);
        
        while (goalFound == null) {
            if (openList.size() == 0) { //If there are no more nodes in our open list:
                System.out.println("There is no path to the goal"); //[TEST]
                return null; //There is no path to the goal
            }
            
            System.out.println("Evaluating node: " + openList.get(0).xCoord + " by " + openList.get(0).yCoord); //[TEST]
            
            goalFound = examineSurroundingNodes(openList.get(0));
            closedList.add(openList.get(0));
            openList.get(0).isOnClosedList = true; //[TEST]
            openList.remove(0);
            Collections.sort(openList);
        }
        
        return tracePath(goalFound);
    }
    
    public PathNode examineSurroundingNodes(PathNode referenceNode) { //Returns the goal pathnode if found, otherwise returns null
        PathNode goalFound = null; //If the goal is found, it will be stored here
        for (int x = referenceNode.xCoord - 1; x <= referenceNode.xCoord + 1; x++) { //for all x index values from one above and one below the current node's index:
            for (int y = referenceNode.yCoord - 1; y <= referenceNode.yCoord + 1; y++) { //for all y index values from one above and one below the current node's index:
                if (isIndexInsideArray(searchSpace, new Point(x, y), searchSpaceOffset) == true) { //if the current x and y is an actual index in our array:
                    if (x == referenceNode.xCoord && y == referenceNode.yCoord) { //If the index is our reference node:
                        //Do nothing
                    } else if (x == referenceNode.xCoord || y == referenceNode.yCoord) { //The node is horizontal or vertical from our referenceNode
                        goalFound = evaluatePathNode(searchSpace[x - searchSpaceOffset.x][y - searchSpaceOffset.y], referenceNode, true);
                    } else { //The node is diagonal
                        goalFound = evaluatePathNode(searchSpace[x - searchSpaceOffset.x][y - searchSpaceOffset.y], referenceNode, false);
                    }
                    if (goalFound != null) {
                        return goalFound;
                    }
                }
            }
        }
        return null;
    }
    
    public PathNode evaluatePathNode(PathNode node, PathNode referenceNode, boolean isStraight) { //Returns the goal pathnode if found, otherwise returns null
        if (node.isGoal == true) {
            System.out.println("Goal node has been found!");
            node.parentNode = referenceNode;
            return node;
        } else if (node.isPassable == true) {
            if ((closedList.contains(node) == false) && (openList.contains(node) == false)) { //If node has not already been evaluated before:
                openList.add(node);
                node.isOnOpenList = true; //[TEST]
                node.parentNode = referenceNode;
                node.distanceToGoal = LOWEST_TRAVERSE_COST * Math.max(Math.abs(node.xCoord - goalNode.xCoord), Math.abs(node.yCoord - goalNode.yCoord));
                if (isStraight == true) { //The path to the node is horizontal or vertical
                    node.distanceFromStart = referenceNode.distanceFromStart + node.costToTraverse;
                } else { //The path is diagonal
                    node.distanceFromStart = referenceNode.distanceFromStart + (node.costToTraverse * 1.414); //The distance to move diagonally is the sqrt(2) * the cost of moving straight, or approx. 1.414 times
                }
                node.estimatedCost = node.distanceToGoal + node.distanceFromStart;
            } else { //Else if node has already been evaulated before, check to see if this is a better path to it
                if (node.distanceFromStart > referenceNode.distanceFromStart + node.costToTraverse) {
                    node.distanceFromStart = referenceNode.distanceFromStart + node.costToTraverse;
                    node.estimatedCost = node.distanceToGoal + node.distanceFromStart;
                }
            }
        }
        return null;
    }
    
    public List<PathNode> tracePath(PathNode finalNode) {
        LinkedList<PathNode> tracedPath = new LinkedList<>();
        
        PathNode currentNode = finalNode;
        while (currentNode != null) {
            tracedPath.addFirst(currentNode);
            currentNode = currentNode.parentNode;
        }
        
        return tracedPath;
    }
    
    public PathNode getStartNode() {
        for (int x = 0; x < searchSpace.length; x++) {
            for (int y = 0; y < searchSpace[x].length; y++) {
                if (searchSpace[x][y].isStart == true) {
                    return searchSpace[x][y];
                }
            }
        }
        return null;
    }
    public PathNode getGoalNode() {
        for (int x = 0; x < searchSpace.length; x++) {
            for (int y = 0; y < searchSpace[x].length; y++) {
                if (searchSpace[x][y].isGoal == true) {
                    return searchSpace[x][y];
                }
            }
        }
        return null;
    }
    
    public static boolean isIndexInsideArray(Object array[][], Point index, Point offset) {
        if (index.x - offset.x >= 0 && index.x - offset.x < array.length) {
            if (index.y - offset.y >=0 && index.y - offset.y < array[index.x - offset.x].length) {
                return true;
            }
        }
        return false;
    }
    
    public static final int LOWEST_TRAVERSE_COST = 10;
}