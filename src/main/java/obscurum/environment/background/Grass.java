package obscurum.environment.background;

import obscurum.display.DisplayColour;

public class Grass extends BackgroundTile {
  public Grass() {
    super("Grass", '.', DisplayColour.GREEN.getColour(), DisplayColour.BLACK.getColour());
  }
}
