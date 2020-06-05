package obscurum.environment.foreground;

import obscurum.display.Display;

/**
 * This models an empty foreground tile.
 * @author Alex Ghita
 */
public class EmptyTile extends ForegroundTile {
  /**
   * Class constructor with fixed values.
   */
  public EmptyTile() {
    super("Empty Tile", Display.SPACE, Display.BLACK, 1, 0, true, false);
  }
}
