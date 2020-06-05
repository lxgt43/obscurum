package obscurum.environment.background;

import java.awt.Color;
import obscurum.display.asciiPanel.AsciiPanel;

/**
 * This models a custom background tile, used for tiles added through the
 * editor.
 * @author Alex Ghita
 */
public class CustomBackgroundTile extends BackgroundTile {
  public CustomBackgroundTile(String name, char glyph, Color foregroundColour,
      Color backgroundColour) {
    super(name, glyph, foregroundColour, backgroundColour);
  }
}
