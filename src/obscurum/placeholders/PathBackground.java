package obscurum.placeholders;

import java.awt.Color;
import obscurum.display.Display;
import obscurum.environment.background.BackgroundTile;

/**
 * This models a placeholder tile used in dungeon generation, which designates
 * path tiles between rooms.
 * @author Alex Ghita
 */
public class PathBackground extends BackgroundTile {
  /**
   * Class constructor specifying placeholder values.
   */
  public PathBackground() {
    super("Path Background", 'x', Display.GREEN, Display.BLACK);
  }
}
