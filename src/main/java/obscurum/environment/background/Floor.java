package obscurum.environment.background;

import java.awt.Color;
import obscurum.display.asciiPanel.AsciiPanel;

/**
 * This models a basic floor tile.
 * @author Alex Ghita
 */
public class Floor extends BackgroundTile {
  public Floor() {
    super("Floor", (char)250, AsciiPanel.red, AsciiPanel.black);
  }
}
