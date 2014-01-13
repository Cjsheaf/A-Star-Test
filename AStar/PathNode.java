package AStar;

public class PathNode implements Comparable<PathNode> {
    public int xCoord;
    public int yCoord;
    public int costToTraverse;
    public double distanceToGoal;
    public double distanceFromStart;
    public double estimatedCost;
    public PathNode parentNode;
    public boolean isPassable;
    public boolean isStart;
    public boolean isGoal;
    
    public boolean isOnOpenList;
    public boolean isOnClosedList;
    
    public PathNode(int new_xCoord, int new_yCoord, int costToTraverse) {
        xCoord = new_xCoord;
        yCoord = new_yCoord;
        this.costToTraverse = costToTraverse;
        distanceToGoal = 0;
        distanceFromStart = 0;
        estimatedCost = 0;
        parentNode = null;
        isPassable = true;
        isStart = false;
        isGoal = false;
    }
    public PathNode(int new_xCoord, int new_yCoord, int costToTraverse, String specialNode) {
        xCoord = new_xCoord;
        yCoord = new_yCoord;
        this.costToTraverse = costToTraverse;
        distanceToGoal = 0;
        distanceFromStart = 0;
        estimatedCost = 0;
        parentNode = null;
        isPassable = true;
        
        switch (specialNode) {
            case "start":
                isStart = true;
                isGoal = false;
                break;
            case "goal":
                isStart = false;
                isGoal = true;
                break;
        }
    }
    
    public int compareTo(PathNode otherNode) {
        if (this.estimatedCost > otherNode.estimatedCost) {
            return 1;
        } else if (this.estimatedCost < otherNode.estimatedCost) {
            return -1;
        } else {
            return 0;
        }
    }
}
