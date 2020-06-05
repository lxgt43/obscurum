package obscurum.placeholders;

import obscurum.display.Display;
import obscurum.environment.foreground.ForegroundTile;

/**
 * This represents a foreground tile "null object".
 * @author Alex Ghita
 */
public class ForegroundLevelBound extends ForegroundTile {
  /**
   * Class constructor specifying placeholder values.
   */
  public ForegroundLevelBound() {
    super("Foreground Level Bound", 'x', Display.BLACK, 1, 0, true, true);
  }
}
