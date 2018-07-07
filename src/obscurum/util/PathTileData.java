package obscurum.util;

import java.awt.Point;

/**
 * This stores data about a tile used in the A* algorithm.
 * @author Alex Ghita
 */
public class PathTileData {
  private Point location;
  private int priority;

  public PathTileData(Point location, int priority) {
    this.location = location;
    this.priority = priority;
  }

  public Point getLocation() {
    return location;
	}

  public int getPriority() {
    return priority;
  }
}
