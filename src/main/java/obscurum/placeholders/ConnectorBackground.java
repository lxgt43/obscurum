package obscurum.placeholders;

import obscurum.display.Display;
import obscurum.environment.background.BackgroundTile;

/**
 * This models a placeholder tile used in dungeon generation, which connects
 * rooms to paths.
 * @author Alex Ghita
 */
public class ConnectorBackground extends BackgroundTile {
  /**
   * Class constructor specifying placeholder values.
   */
  public ConnectorBackground() {
    super("Connector Background", 'x', Display.BLUE, Display.BLACK);
  }
}
