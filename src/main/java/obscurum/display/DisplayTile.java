package obscurum.display;

import java.awt.Color;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class DisplayTile {
    private final DisplayCharacter displayCharacter;
    private final Color foregroundColour;
    private final Color backgroundColour;

    public DisplayTile(@NonNull DisplayCharacter displayCharacter) {
        this(displayCharacter, Display.WHITE, Display.BLACK);
    }

    public DisplayTile(@NonNull DisplayCharacter displayCharacter, @NonNull Color foregroundColour) {
        this(displayCharacter, foregroundColour, Display.BLACK);
    }

    public DisplayTile(@NonNull DisplayCharacter displayCharacter, @NonNull Color foregroundColour, @NonNull Color backgroundColour) {
        this.displayCharacter = displayCharacter;
        this.foregroundColour = foregroundColour;
        this.backgroundColour = backgroundColour;
    }
}
