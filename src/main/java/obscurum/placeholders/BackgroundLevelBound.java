package obscurum.placeholders;

import obscurum.display.Display;
import obscurum.environment.background.BackgroundTile;

/**
 * This represents a background tile "null object".
 * @author Alex Ghita
 */
public class BackgroundLevelBound extends BackgroundTile {
  /**
   * Class constructor specifying placeholder values.
   */
  public BackgroundLevelBound() {
    super("Background Level Bound", 'x', Display.BLACK, Display.BLACK);
  }
}
