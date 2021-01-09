package obscurum.environment.background;

import obscurum.display.terminal.AsciiPanel;

/**
 * This models a basic floor tile.
 * @author Alex Ghita
 */
public class Floor extends BackgroundTile {
  public Floor() {
    super("Floor", (char)250, AsciiPanel.red, AsciiPanel.black);
  }
}
