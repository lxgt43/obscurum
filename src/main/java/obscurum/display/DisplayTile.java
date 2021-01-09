package obscurum.display;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class DisplayTile {
    private final DisplayCharacter displayCharacter;
    private final DisplayColour foregroundColour;
    private final DisplayColour backgroundColour;

    public DisplayTile(@NonNull DisplayCharacter displayCharacter) {
        this(displayCharacter, DisplayColour.WHITE, DisplayColour.BLACK);
    }

    public DisplayTile(@NonNull DisplayCharacter displayCharacter, @NonNull DisplayColour foregroundColour) {
        this(displayCharacter, foregroundColour, DisplayColour.BLACK);
    }

    public DisplayTile(@NonNull DisplayCharacter displayCharacter, @NonNull DisplayColour foregroundColour, @NonNull DisplayColour backgroundColour) {
        this.displayCharacter = displayCharacter;
        this.foregroundColour = foregroundColour;
        this.backgroundColour = backgroundColour;
    }
}
