package obscurum.display;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor @Getter
public enum DisplayCharacter {
    NULL((char) 0),
    SPACE((char) 32),
    HASH((char) 35),
    SINGLE_QUOTE((char) 39),
    OPEN_BRACKET((char) 40),
    CLOSED_BRACKET((char) 41),
    ASTERISK((char) 42),
    PLUS((char) 43),
    COMMA((char) 44),
    MINUS((char) 45),
    PERIOD((char) 46),
    SLASH((char) 47),
    LESS_THAN((char) 60),
    EQUALS((char) 61),
    GREATER_THAN((char) 62),
    V((char) 86),
    OPEN_SQUARE_BRACKET((char) 91),
    BACKSLASH((char) 92),
    CLOSED_SQUARE_BRACKET((char) 93),
    UNDERSCORE((char) 95),
    VERTICAL_BAR((char) 124),
    TILDE((char) 126);

    private final char character;

    public static DisplayCharacter of(char character) {
        return Arrays.stream(values())
                .filter(displayCharacter -> displayCharacter.character == character)
                .findFirst()
                .orElseThrow(() -> new NullPointerException(String.format("No character found for \"%c\"", character)));
    }
}
