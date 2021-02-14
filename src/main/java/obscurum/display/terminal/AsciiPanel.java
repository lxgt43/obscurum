package obscurum.display.terminal;

import lombok.NonNull;
import obscurum.display.DisplayCharacter;
import obscurum.display.DisplayColour;
import obscurum.display.DisplayTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

public class AsciiPanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(AsciiPanel.class);

    public static final int NUM_OF_GLYPHS = 256;

    private static final int GLYPH_WIDTH_IN_PIXELS = 9;
    private static final int GLYPH_HEIGHT_IN_PIXELS = 16;

    private final int widthInCharacters;
    private final int heightInCharacters;
    private final String boundsDisplay;
    private final DisplayColour defaultBackgroundColor;
    private final DisplayColour defaultForegroundColor;

    private Image offscreenBuffer;
    private Graphics offscreenGraphics;
    private final Point cursor = new Point(0, 0);
    private final List<BufferedImage> glyphs;
    private final Map<Point, DisplayTile> display;

    private int lastMultilineY;

    public AsciiPanel(int widthInCharacters,
                      int heightInCharacters,
                      @NonNull List<BufferedImage> glyphs,
                      @NonNull Dimension panelSize,
                      @NonNull DisplayColour defaultBackgroundColor,
                      @NonNull DisplayColour defaultForegroundColor) {
        super();

        if (widthInCharacters < 1)  {
            throw new IllegalArgumentException(String.format("Width must be greater than 0, but %d provided", widthInCharacters));
        } else if (heightInCharacters < 1) {
            throw new IllegalArgumentException(String.format("Height must be greater than 0, but %d provided", heightInCharacters));
        } else if (glyphs.isEmpty()) {
            throw new IllegalArgumentException("Glyphs list cannot be empty");
        }

        this.widthInCharacters = widthInCharacters;
        this.heightInCharacters = heightInCharacters;
        this.boundsDisplay = String.format("[x: (0, %d), y: (0, %d)]", widthInCharacters, heightInCharacters);
        this.glyphs = glyphs;
        this.defaultBackgroundColor = defaultBackgroundColor;
        this.defaultForegroundColor = defaultForegroundColor;
        setPreferredSize(panelSize);

        this.display = new HashMap<>();

        for (int x = 0; x < this.widthInCharacters; x++) {
            for (int y = 0; y < this.heightInCharacters; y++) {
                this.display.put(new Point(x, y), new DisplayTile(DisplayCharacter.SPACE, this.defaultForegroundColor, this.defaultBackgroundColor));
            }
        }
    }

    @Override
    public void update(@NonNull Graphics g) {
        paint(g);
    }

    @Override
    public void paint(@NonNull Graphics g) {
        if (offscreenBuffer == null) {
            offscreenBuffer = createImage(this.getWidth(), this.getHeight());
            offscreenGraphics = offscreenBuffer.getGraphics();
        }

        display.forEach((point, tile) -> {
            LookupOp lookupOp = getLookupOp(tile.getBackgroundColour(), tile.getForegroundColour());
            BufferedImage glyphImage = lookupOp.filter(glyphs.get(tile.getDisplayCharacter().getCharacter()), null);
            offscreenGraphics.drawImage(glyphImage, point.x * GLYPH_WIDTH_IN_PIXELS, point.y * GLYPH_HEIGHT_IN_PIXELS, null);
        });

        g.drawImage(offscreenBuffer,0,0,this);
    }

    private LookupOp getLookupOp(@NonNull DisplayColour backgroundColour, @NonNull DisplayColour foregroundColour) {
        List<Integer> backgroundComponents = backgroundColour.getComponents();
        List<Integer> foregroundComponents = foregroundColour.getComponents();
        int numOfColourComponents = backgroundComponents.size();

        byte[][] lookupTable = new byte[numOfColourComponents][NUM_OF_GLYPHS];
        for (int i = 0; i < numOfColourComponents; i++) {
            Arrays.fill(lookupTable[i], foregroundComponents.get(i).byteValue());
            lookupTable[i][0] = backgroundComponents.get(i).byteValue();
        }

        return new LookupOp(new ByteLookupTable(0, lookupTable), null);
    }

    public void clear() {
        clear(' ', new Point(0, 0), new Point(widthInCharacters, heightInCharacters), defaultForegroundColor, defaultBackgroundColor);
    }

    public void clear(char character, @NonNull Point topLeftCorner, @NonNull Point bottomRightCorner) {
        clear(character, topLeftCorner, bottomRightCorner, defaultForegroundColor, defaultBackgroundColor);
    }

    public void clear(char character, @NonNull Point topLeftCorner, @NonNull Point bottomRightCorner, @NonNull DisplayColour foregroundColour, @NonNull DisplayColour backgroundColour) {
        if (character >= glyphs.size()) {
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.size() + "].");
        } else if (!isInBounds(topLeftCorner)) {
            throw new IllegalArgumentException(String.format("Top left corner %s must be within bounds %s", topLeftCorner, boundsDisplay));
        } else if (!isInBounds(bottomRightCorner)) {
            throw new IllegalArgumentException(String.format("Bottom right corner %s must be within bounds %s", topLeftCorner, boundsDisplay));
        }

        for (int x = topLeftCorner.x; x < bottomRightCorner.x; x++) {
            for (int y = topLeftCorner.y; y < bottomRightCorner.y; y++) {
                write(character, x, y, foregroundColour, backgroundColour);
            }
        }
    }

    public boolean isInBounds(@NonNull Point point) {
        return (0 <= point.x && point.x <= widthInCharacters) && (0 <= point.y && point.y <= heightInCharacters);
    }

    public void write(char character, int x, int y, DisplayColour foregroundColour) {
        write(character, x, y, foregroundColour, defaultBackgroundColor);
    }

    public void write(char character, int x, int y, @NonNull DisplayColour foregroundColour, @NonNull DisplayColour backgroundColour) {
        if (character >= glyphs.size()) {
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.size() + "].");
        } else if (x < 0 || x >= widthInCharacters) {
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")");
        } else if (y < 0 || y >= heightInCharacters) {
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");
        }

        display.put(new Point(x, y), new DisplayTile(DisplayCharacter.of(character), foregroundColour, backgroundColour));

        cursor.x = (x + 1) % widthInCharacters;
        cursor.y = y;
    }

    public void write(String string, int x, int y) {
        write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    public void write(String string, int x, int y, DisplayColour foregroundColour) {
        write(string, x, y, foregroundColour, defaultBackgroundColor);
    }

    public void write(@NonNull String string, int x, int y, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foregroundColour, backgroundColour);
        }
    }

    public void writeCenter(String string, int y) {
        writeCenter(string, y, defaultForegroundColor, defaultBackgroundColor);
    }

    public void writeCenter(String string, int y, DisplayColour foregroundColour) {
        writeCenter(string, y, foregroundColour, defaultBackgroundColor);
    }

    public void writeCenter(@NonNull String string, int y, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        int x = (widthInCharacters - string.length()) / 2;

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foregroundColour, backgroundColour);
        }
    }

    public void writeMultiline(String string, int x, int y) {
        writeMultiline(string, x, y, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    public void writeMultiline(String string, int x, int y, int xBound, int yBound) {
        writeMultiline(string, x, y, xBound, yBound, defaultForegroundColor, defaultBackgroundColor);
    }


    public void writeMultiline(String string, int x, int y, int xBound, int yBound, DisplayColour foregroundColour) {
        writeMultiline(string, x, y, xBound, yBound, foregroundColour, defaultBackgroundColor);
    }

    public void writeMultiline(@NonNull String string, int x, int y, int xBound, int yBound, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        if (x > xBound || y > yBound) {
            throw new IllegalArgumentException("Top left coordinates must be less than the bound coordinates.");
        } else if (x > widthInCharacters || xBound > widthInCharacters || y > heightInCharacters || yBound > heightInCharacters) {
            throw new IllegalArgumentException("Arguments exceed terminal size.");
        }

        String[] words = string.split(" ");
        int currentX = x;
        int currentY = y;

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("\n")) {
                currentX = x;
                currentY++;
                continue;
            }
            if (i < words.length - 1) {
                words[i] = words[i] + " ";
            }
            if (currentX + words[i].length() < xBound) {
                if (currentX + words[i].length() == xBound - 1) {
                    words[i] = words[i].trim();
                }
            } else {
                currentX = x;
                currentY++;
                if (currentY >= yBound) {
                    throw new IndexOutOfBoundsException("Not enough space to print.");
                }
            }
            write(words[i], currentX, currentY, foregroundColour, backgroundColour);
            currentX += words[i].length();
        }
        lastMultilineY = currentY;
    }

    public int getLastMultilineY() {
        return lastMultilineY;
    }
}