package obscurum.display;

import java.awt.Color;
import obscurum.environment.background.BackgroundTile;

/**
 * This models a display tile, i.e. a tile that has no properties and is used
 * exclusively for display.
 * @author Alex Ghita
 */
public class DisplayTile extends BackgroundTile {
  /**
   * Constructor specifying the tile's glyph.
   * @param glyph
   */
  public DisplayTile(char glyph) {
    this(glyph, Display.WHITE, Display.BLACK);
  }

  /**
   * Constructor specifying the tile's glyph and foreground colour.
   * @param glyph
   * @param foregroundColour
   */
  public DisplayTile(char glyph, Color foregroundColour) {
    this(glyph, foregroundColour, Display.BLACK);
  }

  /**
   * Constructor specifying the tile's glyph and colours.
   * @param glyph
   * @param foregroundColour
   * @param backgroundColour
   */
  public DisplayTile(char glyph, Color foregroundColour,
      Color backgroundColour) {
    super("Display Tile", glyph, foregroundColour, backgroundColour);
  }
}
