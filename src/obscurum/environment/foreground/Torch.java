package obscurum.environment.foreground;

import java.awt.Point;
import java.util.ArrayList;
import obscurum.creatures.util.Line;
import obscurum.display.Display;
import obscurum.environment.Level;

/**
 * This models a torch, which provides sight on its nearby tiles to all
 * creatures that can see them.
 * @author Alex Ghita
 */
public class Torch extends ForegroundTile {
  int lineOfSight;
  Level level;
  Point location;

  public Torch(int lineOfSight, Level level, Point location) {
    super("Torch", (char)15, Display.ORANGE, 1, 0, true, true);

    // Check for illegal arguments.
    if (lineOfSight < 1) {
      throw new IllegalArgumentException(
          "Line of sight " + lineOfSight + " must be at least 1.");
    }
    this.lineOfSight = lineOfSight;
    this.level = level;
    this.location = location;
  }

  public boolean canSee(Point target) {
    int distance = (location.x - target.x) * (location.x - target.x) +
        (location.y - target.y) * (location.y - target.y);
    Line sightRay;
    ArrayList<Point> linePoints;

    if (distance > lineOfSight * lineOfSight) {
      return false;
    }
    sightRay = new Line(location, target);
    sightRay.plotLine();
    linePoints = sightRay.getPoints();
    for (Point p : linePoints) {
      if (level.getForegroundTile(p).isOpaque() &&
          !p.equals(target) && !p.equals(location)) {
        return false;
      }
    }
    return true;
  }

  public Point getLocation() {
    return location;
  }
}
