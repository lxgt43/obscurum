package obscurum.environment;

import java.awt.Color;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import obscurum.display.terminal.AsciiPanel;

@Getter
public abstract class Tile {
    protected String name;
    protected char glyph;
    @Setter
    protected Color foregroundColour;

    public Tile(@NonNull String name, char glyph, @NonNull Color foregroundColour) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        } else if (glyph >= AsciiPanel.NUM_OF_GLYPHS) {
            throw new IllegalArgumentException("Glyph must be in range 0 - " + AsciiPanel.NUM_OF_GLYPHS + ".");
        }

        this.name = name;
        this.glyph = glyph;
        this.foregroundColour = foregroundColour;
    }

    public boolean isOfType(@NonNull Tile tile) {
        return this.getClass().equals(tile.getClass());
    }
}
