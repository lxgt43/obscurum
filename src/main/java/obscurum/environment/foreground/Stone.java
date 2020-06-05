package obscurum.environment.foreground;

import java.awt.Color;
import obscurum.display.asciiPanel.AsciiPanel;

/**
 * This models a stone wall tile.
 * @author Alex Ghita
 */
public class Stone extends ForegroundTile {
  public Stone() {
    super("Stone", (char)177, AsciiPanel.brightBlack, 1, 0, true, true);
  }
}
