package obscurum.environment.foreground;

import obscurum.display.DisplayColour;

/**
 * This models a wall.
 * @author Alex Ghita
 */
public class Wall extends ForegroundTile {
  public Wall() {
    super("Wall", (char)177, DisplayColour.YELLOW.getColour(), 1, 0, true, true);
  }
}
