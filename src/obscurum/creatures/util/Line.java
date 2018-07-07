package obscurum.creatures.util;

import java.awt.Point;
import java.lang.Math;
import java.util.ArrayList;

/**
 * This models a line based on its start and end point coordinates.
 * @author Alex Ghita
 */
public class Line {
  private ArrayList<Point> points;
  private Point start;
  private Point end;
  private int dx;
  private int dy;

  public Line(int x0, int y0, int x1, int y1) {
    this(new Point(x0, y0), new Point(x1, y1));
  }

  public Line(Point start, Point end) {
    this.start = start;
    this.end = end;

    dx = Math.abs(start.x - end.x);
    dy = Math.abs(start.y - end.y);
  }

  public void plotLine() {
    int step_x = start.x < end.x ? 1 : -1;
    int step_y = start.y < end.y ? 1 : -1;
    int x = start.x;
    int y = start.y;
    int error = dx - dy;
    int twice_error;

    points = new ArrayList<Point>();
    while (true) {
      points.add(new Point(x, y));
      if (x == end.x && y == end.y) {
        break;
      }
      twice_error = 2 * error;
      if (twice_error > -dx) {
        error -= dy;
        x += step_x;
      }
      if (twice_error < dx) {
        error += dx;
        y += step_y;
      }
    }
  }

  public ArrayList<Point> getPoints() {
    return points;
  }
}
