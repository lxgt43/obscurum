package obscurum.environment.background;

import obscurum.display.terminal.AsciiPanel;
import obscurum.display.Display;

/**
 * This models a basic grass tile.
 * @author Alex Ghita
 */
public class Grass extends BackgroundTile {
  public Grass() {
    super("Grass", '.', AsciiPanel.green, Display.BLACK);
  }
}
