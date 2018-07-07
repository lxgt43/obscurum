package obscurum.environment.background;

import java.awt.Color;
import obscurum.creatures.Creature;
import obscurum.display.asciiPanel.AsciiPanel;

/**
 * This models a trap, which looks similar to a given source background tile,
 * and which triggers when the player steps on it.
 * @author Alex Ghita
 */
public abstract class Trap extends BackgroundTile {
  BackgroundTile sourceTile;

  public Trap() {
    super("Trap", (char)250, AsciiPanel.red, AsciiPanel.black);
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
