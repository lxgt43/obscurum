package obscurum.environment.foreground;

import obscurum.display.terminal.AsciiPanel;

/**
 * This models a wall.
 * @author Alex Ghita
 */
public class Wall extends ForegroundTile {
  public Wall() {
    super("Wall", (char)177, AsciiPanel.yellow, 1, 0, true, true);
  }
}
