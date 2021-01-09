package obscurum.environment.foreground;

import java.awt.Color;

/**
 * This models a custom foreground tile, used for tiles added through the
 * editor.
 * @author Alex Ghita
 */
public class CustomForegroundTile extends ForegroundTile {
  public CustomForegroundTile(String name, char glyph, Color foregroundColour) {
    super(name, glyph, foregroundColour, 1, 0, true, true);
  }
}
