package obscurum.environment.foreground;

import obscurum.display.DisplayColour;

public class Stone extends ForegroundTile {
  public Stone() {
    super("Stone", (char)177, DisplayColour.BRIGHT_BLACK.getColour(), 1, 0, true, true);
  }
}
