package obscurum.environment.background;

import obscurum.creatures.Creature;
import obscurum.display.DisplayColour;

public abstract class Trap extends BackgroundTile {
  BackgroundTile sourceTile;

  public Trap() {
    super("Trap", (char)250, DisplayColour.RED.getColour(), DisplayColour.BLACK.getColour());
    sourceTile = new Floor();
  }

  public Trap(BackgroundTile sourceTile) {
    super("Trap", sourceTile.getGlyph(),
        sourceTile.getForegroundColour().brighter(),
        sourceTile.getBackgroundColour());
    this.sourceTile = sourceTile;
  }

  public BackgroundTile getSourceTile() {
    return sourceTile;
  }

  public abstract void trigger(Creature target);
}
