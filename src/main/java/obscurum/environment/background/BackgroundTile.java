package obscurum.environment.background;

import java.awt.Color;

import obscurum.environment.Tile;

/**
 * This models a background tile, which is usually a floor tile or something
 * similar on which foreground tiles can be placed.
 * @author Alex Ghita
 */
public abstract class BackgroundTile extends Tile {
  private Color backgroundColour;

  /**
   * Class constructor. Specifies the tile's appearance.
   * @param name
   * @param glyph
   * @param foregroundColour
   * @param backgroundColour
   */
  public BackgroundTile(String name, char glyph, Color foregroundColour,
      Color backgroundColour) {
    super(name, glyph, foregroundColour);

    // Check for illegal arguments.
    if (backgroundColour == null) {
      throw new IllegalArgumentException("Background colour cannot be null.");
    }

    this.backgroundColour = backgroundColour;
  }

  /**
   * Gets the tile's background colour.
   * @return
   */
  public Color getBackgroundColour() {
    return backgroundColour;
  }
}
