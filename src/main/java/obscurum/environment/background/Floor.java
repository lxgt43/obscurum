package obscurum.environment.background;

import obscurum.display.DisplayColour;

public class Floor extends BackgroundTile {
  public Floor() {
    super("Floor", (char)250, DisplayColour.RED.getColour(), DisplayColour.BLACK.getColour());
  }
}
