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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

public class AsciiPanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(AsciiPanel.class);

    private static final int GLYPH_WIDTH_IN_PIXELS = 9;
    private static final int GLYPH_HEIGHT_IN_PIXELS = 16;

    private final int widthInCharacters;
    private final int heightInCharacters;
    private final DisplayColour defaultBackgroundColor;
    private final DisplayColour defaultForegroundColor;

    private Image offscreenBuffer;
    private Graphics offscreenGraphics;
    private final Point cursor = new Point(0, 0);
    private final List<BufferedImage> glyphs;
    private final Map<Point, DisplayTile> display;

    private int lastMultilineX;
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

    private void setCursorX(int cursorX) {
        if (cursorX < 0 || cursorX >= widthInCharacters) {
            throw new IllegalArgumentException("cursorX " + cursorX + " must be within range [0," + widthInCharacters + ").");
        }

        this.cursor.x = cursorX;
    }

    private void setCursorY(int cursorY) {
        if (cursorY < 0 || cursorY >= heightInCharacters) {
            throw new IllegalArgumentException("cursorY " + cursorY + " must be within range [0," + heightInCharacters + ").");
        }

        this.cursor.y = cursorY;
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        if (g == null)
            throw new NullPointerException();

        if (offscreenBuffer == null){
            offscreenBuffer = createImage(this.getWidth(), this.getHeight());
            offscreenGraphics = offscreenBuffer.getGraphics();
        }

        display.forEach((point, tile) -> {
            LookupOp lookupOp = setColors(tile.getBackgroundColour().getColour(), tile.getForegroundColour().getColour());
            BufferedImage glyphImage = lookupOp.filter(glyphs.get(tile.getDisplayCharacter().getCharacter()), null);
            offscreenGraphics.drawImage(glyphImage, point.x * GLYPH_WIDTH_IN_PIXELS, point.y * GLYPH_HEIGHT_IN_PIXELS, null);
        });

        g.drawImage(offscreenBuffer,0,0,this);
    }

    private LookupOp setColors(Color bgColor, Color fgColor) {
        byte[] a = new byte[256];
        byte[] r = new byte[256];
        byte[] g = new byte[256];
        byte[] b = new byte[256];

        byte bgr = (byte) (bgColor.getRed());
        byte bgg = (byte) (bgColor.getGreen());
        byte bgb = (byte) (bgColor.getBlue());

        byte fgr = (byte) (fgColor.getRed());
        byte fgg = (byte) (fgColor.getGreen());
        byte fgb = (byte) (fgColor.getBlue());

        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                a[i] = (byte) 255;
                r[i] = bgr;
                g[i] = bgg;
                b[i] = bgb;
            } else {
                a[i] = (byte) 255;
                r[i] = fgr;
                g[i] = fgg;
                b[i] = fgb;
            }
        }

        byte[][] table = {r, g, b, a};
        return new LookupOp(new ByteLookupTable(0, table), null);
    }

    public AsciiPanel clear() {
        return clear(' ', 0, 0, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    public AsciiPanel clear(char character) {
        return clear(character, 0, 0, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    public AsciiPanel clear(char character, int x, int y, int width, int height) {
        return clear(character, x, y, width, height, defaultForegroundColor, defaultBackgroundColor);
    }

    public AsciiPanel clear(char character, int x, int y, int width, int height, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        if (character >= glyphs.size()) {
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.size() + "].");
        } else if (x < 0 || x >= widthInCharacters) {
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")");
        } else if (y < 0 || y >= heightInCharacters) {
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");
        } else if (width < 1) {
            throw new IllegalArgumentException("width " + width + " must be greater than 0.");
        } else if (height < 1) {
            throw new IllegalArgumentException("height " + height + " must be greater than 0.");
        } else if (x + width > widthInCharacters) {
            throw new IllegalArgumentException("x + width " + (x + width) + " must be less than " + (widthInCharacters + 1) + ".");
        } else if (y + height > heightInCharacters) {
            throw new IllegalArgumentException("y + height " + (y + height) + " must be less than " + (heightInCharacters + 1) + ".");
        }

        for (int xo = x; xo < x + width; xo++) {
            for (int yo = y; yo < y + height; yo++) {
                write(character, xo, yo, foregroundColour, backgroundColour);
            }
        }

        return this;
    }

    public AsciiPanel write(char character) {
        return write(character, cursor.x, cursor.y, defaultForegroundColor, defaultBackgroundColor);
    }

    public AsciiPanel write(char character, DisplayColour foregroundColour) {
        return write(character, cursor.x, cursor.y, foregroundColour, defaultBackgroundColor);
    }

    public AsciiPanel write(char character, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        return write(character, cursor.x, cursor.y, foregroundColour, backgroundColour);
    }

    public AsciiPanel write(char character, int x, int y) {
        return write(character, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    public AsciiPanel write(char character, int x, int y, DisplayColour foregroundColour) {
        return write(character, x, y, foregroundColour, defaultBackgroundColor);
    }

    public AsciiPanel write(char character, int x, int y, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        if (character >= glyphs.size()) {
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.size() + "].");
        }

        if (x < 0 || x >= widthInCharacters) {
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")");
        }

        if (y < 0 || y >= heightInCharacters) {
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");
        }

        if (foregroundColour == null) foregroundColour = defaultForegroundColor;
        if (backgroundColour == null) backgroundColour = defaultBackgroundColor;

        display.put(new Point(x, y), new DisplayTile(DisplayCharacter.of(character), foregroundColour, backgroundColour));

        setCursorX((x + 1) % widthInCharacters);
        setCursorY(y);
        return this;
    }

    public AsciiPanel write(String string) {
        return write(string, cursor.x, cursor.y, defaultForegroundColor, defaultBackgroundColor);
    }

    public AsciiPanel write(String string, DisplayColour foregroundColour) {
        return write(string, cursor.x, cursor.y, foregroundColour, defaultBackgroundColor);
    }

    public AsciiPanel write(String string, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        return write(string, cursor.x, cursor.y, foregroundColour, backgroundColour);
    }

    public AsciiPanel write(String string, int x, int y) {
        return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    public AsciiPanel write(String string, int x, int y, DisplayColour foregroundColour) {
        return write(string, x, y, foregroundColour, defaultBackgroundColor);
    }

    public AsciiPanel write(String string, int x, int y, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        if (string == null) {
            throw new NullPointerException("string must not be null.");
        } else if (x + string.length() >= widthInCharacters) {
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + ".");
        } else if (x < 0 || x >= widthInCharacters) {
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ").");
        } else if (y < 0 || y >= heightInCharacters) {
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ").");
        }

        if (foregroundColour == null) {
            foregroundColour = defaultForegroundColor;
        }

        if (backgroundColour == null) {
            backgroundColour = defaultBackgroundColor;
        }

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foregroundColour, backgroundColour);
        }

        return this;
    }

    public void writeCenter(String string, int y) {
        writeCenter(string, y, defaultForegroundColor, defaultBackgroundColor);
    }

    public void writeCenter(String string, int y, DisplayColour foregroundColour) {
        writeCenter(string, y, foregroundColour, defaultBackgroundColor);
    }

    public void writeCenter(String string, int y, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        if (string == null) {
            throw new NullPointerException("string must not be null.");
        } else if (string.length() >= widthInCharacters) {
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + ".");
        } else if (y < 0 || y >= heightInCharacters) {
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ").");
        }

        int x = (widthInCharacters - string.length()) / 2;

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foregroundColour, backgroundColour);
        }
    }

    public void writeMultiline(String string, int x, int y) {
        writeMultiline(string, x, y, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    public void writeMultiline(String string, int x, int y, DisplayColour foregroundColour) {
        writeMultiline(string, x, y, widthInCharacters, heightInCharacters, foregroundColour, defaultBackgroundColor);
    }

    public void writeMultiline(String string, int x, int y, int xBound, int yBound) {
        writeMultiline(string, x, y, xBound, yBound, defaultForegroundColor, defaultBackgroundColor);
    }


    public void writeMultiline(String string, int x, int y, int xBound, int yBound, DisplayColour foregroundColour) {
        writeMultiline(string, x, y, xBound, yBound, foregroundColour, defaultBackgroundColor);
    }

    public void writeMultiline(String string, int x, int y, int xBound, int yBound, DisplayColour foregroundColour, DisplayColour backgroundColour) {
        if (string == null) {
            throw new NullPointerException("string must not be null");
        } else if (x > xBound || y > yBound) {
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
        lastMultilineX = currentX;
        lastMultilineY = currentY;
    }

    public int getLastMultilineX() {
        return lastMultilineX;
    }

    public int getLastMultilineY() {
        return lastMultilineY;
    }
}