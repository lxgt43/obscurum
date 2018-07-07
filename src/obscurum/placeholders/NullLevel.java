package obscurum.placeholders;

import obscurum.environment.Level;
import obscurum.environment.background.BackgroundTile;
import obscurum.environment.foreground.ForegroundTile;

/**
 * This class models a null level.
 * @author Alex Ghita
 */
public class NullLevel extends Level {
  /**
   * Class constructor specifying placeholder values.
   */
  public NullLevel() {
    super(new ForegroundTile[1][1], new BackgroundTile[1][1]);
  }
}
