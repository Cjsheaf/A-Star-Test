A-Star-Test
===========

A test implementation of the A* pathfinding algorithm for later use in subsequent projects.

<b>How to Run:</b>
 - Compile using JDK version 6 or later. It probably compiles on earlier versions, but 6 is the earliest tested version. To do so, navigate to the root directory of the project and run the command: "javac AStar/*.java"
 - Then run the program by again navigating to the root directory of the project and then running the command: "java AStar.AStarTest"

<b>How to use the program:</b>
<p>Controls:</p>
<ul>
  <li>Left-click changes whether a cell is passable or not. Gray means passable, black means impassable.</li>
  <li>Right-click cycles through the special states for a cell. Green means the cell is the starting point. Red means the cell is the goal. There <b>MUST</b> be one, and only one, starting point and goal, each.</li>
</ul>

Once the grid of cells has been set as desired, (with ONE starting point and ONE goal) pressing the "Enter" key on your keyboard will have the program use the A* pathfinding algorithm to find the shortest path from the starting point to the goal.

There are special colors during and after searching for a path:
<ul>
  <li>Passable and searched = BLUE</li>
  <li>Passable and a potential search target, if the path has not yet been found = YELLOW</li>
</ul>

Arrows in a cell point to the parent of that cell. Due to the way the algorithm works, the final path is the set of arrows followed from the goal (red square) to the starting point (green square).
