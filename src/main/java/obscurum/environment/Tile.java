package obscurum.environment;

import java.awt.Color;
import obscurum.GameMain;

/**
 * This models a game tile.
 * @author Alex Ghita
 */
public abstract class Tile {
  protected String name;
  protected char glyph;
  protected Color foregroundColour;

  /**
   * Class constructor. Specifies the name, glyph and colour of the tile.
   * @param name
   * @param glyph
   * @param foregroundColour
   */
  public Tile(String name, char glyph, Color foregroundColour) {
    // Check for illegal arguments.
    if (name == "") {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (glyph < 0 || glyph >= GameMain.NUM_OF_GLYPHS) {
      throw new IllegalArgumentException("Glyph must be in range 0 - " +
          GameMain.NUM_OF_GLYPHS + ".");
    }
    if (foregroundColour == null) {
      throw new IllegalArgumentException("Foreground colour cannot be null.");
    }

    this.name = name;
    this.glyph = glyph;
    this.foregroundColour = foregroundColour;
  }

  /**
   * Gets the tile's name.
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the tile's glyph.
   * @return
   */
  public char getGlyph() {
    return glyph;
  }

  /**
   * Gets the tile's foreground colour
   * @return
   */
  public Color getForegroundColour() {
    return foregroundColour;
  }

  public void setForegroundColour(Color foregroundColour) {
    this.foregroundColour = foregroundColour;
  }

  /**
   * Checks whether the tile is of the same type as the argument tile.
   * @param tile
   * @return
   */
  public boolean isOfType(Tile tile) {
    // Check for illegal arguments.
    if (tile == null) {
      throw new IllegalArgumentException("Tile cannot be null.");
    }

    return this.getClass().equals(tile.getClass());
  }
}
