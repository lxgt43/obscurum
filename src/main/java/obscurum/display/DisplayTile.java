package obscurum.display;

import java.awt.Color;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import obscurum.GameMain;

@Getter
public final class DisplayTile {
    private final String name;
    private final char glyph;
    @Setter
    private Color foregroundColour;
    private final Color backgroundColour;

    public DisplayTile(char glyph) {
        this(glyph, Display.WHITE, Display.BLACK);
    }

    public DisplayTile(char glyph, Color foregroundColour) {
        this(glyph, foregroundColour, Display.BLACK);
    }

    public DisplayTile(char glyph, Color foregroundColour,
                       Color backgroundColour) {
        this("Display Tile", glyph, foregroundColour, backgroundColour);
    }

    public DisplayTile(@NonNull String name, char glyph, @NonNull Color foregroundColour, @NonNull Color backgroundColour) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        } else if (glyph >= GameMain.NUM_OF_GLYPHS) {
            throw new IllegalArgumentException("Glyph must be in range 0 - " + GameMain.NUM_OF_GLYPHS + ".");
        }

        this.name = name;
        this.glyph = glyph;
        this.foregroundColour = foregroundColour;
        this.backgroundColour = backgroundColour;
    }
}
