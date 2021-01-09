package obscurum.display;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE) @Getter
public class DisplayColour {
    public static final DisplayColour BLACK = new DisplayColour("Black", new Color(0, 0, 0));
    public static final DisplayColour RED = new DisplayColour("Red", new Color(128, 0, 0));
    public static final DisplayColour GREEN = new DisplayColour("Green", new Color(0, 128, 0));
    public static final DisplayColour YELLOW = new DisplayColour("Yellow", new Color(128, 128, 0));
    public static final DisplayColour ORANGE = new DisplayColour("Orange", new Color(255, 128, 0));
    public static final DisplayColour BLUE = new DisplayColour("Blue", new Color(0, 0, 128));
    public static final DisplayColour MAGENTA = new DisplayColour("Magenta", new Color(128, 0, 128));
    public static final DisplayColour CYAN = new DisplayColour("Cyan", new Color(0, 128, 128));
    public static final DisplayColour WHITE = new DisplayColour("White", new Color(192, 192, 192));
    public static final DisplayColour BROWN = new DisplayColour("Brown", new Color(120, 53, 31));
    public static final DisplayColour BRIGHT_BLACK = new DisplayColour("Bright Black", new Color(128, 128, 128));
    public static final DisplayColour BRIGHT_RED = new DisplayColour("Bright Red", new Color(255, 0, 0));
    public static final DisplayColour BRIGHT_GREEN = new DisplayColour("Bright Green", new Color(0, 255, 0));
    public static final DisplayColour BRIGHT_YELLOW = new DisplayColour("Bright Yellow", new Color(255, 255, 0));
    public static final DisplayColour BRIGHT_BLUE = new DisplayColour("Bright Blue", new Color(0, 0, 255));
    public static final DisplayColour BRIGHT_MAGENTA = new DisplayColour("Bright Magenta", new Color(255, 0, 255));
    public static final DisplayColour BRIGHT_CYAN = new DisplayColour("Bright Cyan", new Color(0, 255, 255));
    public static final DisplayColour BRIGHT_WHITE = new DisplayColour("Bright White", new Color(255, 255, 255));

    private final String name;
    private final Color colour;

    public static DisplayColour fromRgb(String name, int red, int green, int blue) {
        return new DisplayColour(name, new Color(red, green, blue));
    }
}
