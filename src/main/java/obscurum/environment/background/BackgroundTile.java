package obscurum.environment.background;

import java.awt.Color;

import lombok.Getter;
import lombok.NonNull;
import obscurum.environment.Tile;

@Getter
public abstract class BackgroundTile extends Tile {
  private final Color backgroundColour;

  public BackgroundTile(String name, char glyph, Color foregroundColour, @NonNull Color backgroundColour) {
    super(name, glyph, foregroundColour);

    this.backgroundColour = backgroundColour;
  }
}
