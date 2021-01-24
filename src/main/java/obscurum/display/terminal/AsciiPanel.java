package obscurum.display.terminal;

import obscurum.display.DisplayColour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AsciiPanel extends JPanel {
  private static final Logger log = LoggerFactory.getLogger(AsciiPanel.class);

  private static final int GLYPH_WIDTH_IN_PIXELS = 9;
  private static final int GLYPH_HEIGHT_IN_PIXELS = 16;

  private final int widthInCharacters;
  private final int heightInCharacters;
  private final Color defaultBackgroundColor;
  private final Color defaultForegroundColor;

  private Image offscreenBuffer;
  private Graphics offscreenGraphics;
  private int cursorX;
  private int cursorY;
  private BufferedImage glyphSprite;
  private BufferedImage[] glyphs;
  private char[][] chars;
  private Color[][] backgroundColors;
  private Color[][] foregroundColors;
  private char[][] oldChars;
  private Color[][] oldBackgroundColors;
  private Color[][] oldForegroundColors;

  private int lastMultilineX;
  private int lastMultilineY;

  public AsciiPanel() {
    this(80, 24);
  }

  public AsciiPanel(int width, int height) {
    super();

    if (width < 1)
      throw new IllegalArgumentException("width " + width + " must be greater than 0.");

    if (height < 1)
      throw new IllegalArgumentException("height " + height + " must be greater than 0.");

    widthInCharacters = width;
    heightInCharacters = height;
    setPreferredSize(new Dimension(GLYPH_WIDTH_IN_PIXELS * widthInCharacters,
        GLYPH_HEIGHT_IN_PIXELS * heightInCharacters));

    defaultBackgroundColor = DisplayColour.BLACK.getColour();
    defaultForegroundColor = DisplayColour.WHITE.getColour();

    chars = new char[widthInCharacters][heightInCharacters];
    backgroundColors = new Color[widthInCharacters][heightInCharacters];
    foregroundColors = new Color[widthInCharacters][heightInCharacters];

    oldChars = new char[widthInCharacters][heightInCharacters];
    oldBackgroundColors = new Color[widthInCharacters][heightInCharacters];
    oldForegroundColors = new Color[widthInCharacters][heightInCharacters];

    glyphs = new BufferedImage[256];

    loadGlyphs();

    AsciiPanel.this.clear();
  }

  private void setCursorX(int cursorX) {
    if (cursorX < 0 || cursorX >= widthInCharacters)
      throw new IllegalArgumentException("cursorX " + cursorX +
              " must be within range [0," + widthInCharacters + ").");

    this.cursorX = cursorX;
  }

  private void setCursorY(int cursorY) {
    if (cursorY < 0 || cursorY >= heightInCharacters)
      throw new IllegalArgumentException("cursorY " + cursorY +
              " must be within range [0," + heightInCharacters + ").");

    this.cursorY = cursorY;
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

    for (int x = 0; x < widthInCharacters; x++) {
      for (int y = 0; y < heightInCharacters; y++) {
        if (oldBackgroundColors[x][y] == backgroundColors[x][y]
            && oldForegroundColors[x][y] == foregroundColors[x][y]
            && oldChars[x][y] == chars[x][y])
          continue;

        Color bg = backgroundColors[x][y];
        Color fg = foregroundColors[x][y];

        LookupOp op = setColors(bg, fg);
        BufferedImage img = op.filter(glyphs[chars[x][y]], null);
        offscreenGraphics.drawImage(img, x * GLYPH_WIDTH_IN_PIXELS, y * GLYPH_HEIGHT_IN_PIXELS, null);

        oldBackgroundColors[x][y] = backgroundColors[x][y];
        oldForegroundColors[x][y] = foregroundColors[x][y];
        oldChars[x][y] = chars[x][y];
      }
    }

    g.drawImage(offscreenBuffer,0,0,this);
  }

  private void loadGlyphs() {
    try {
      glyphSprite = ImageIO.read(AsciiPanel.class.getResource("/cp437.png"));
    } catch (IOException e) {
      System.err.println("loadGlyphs(): " + e.getMessage());
    }

    for (int i = 0; i < 256; i++) {
      int sx = (i % 32) * GLYPH_WIDTH_IN_PIXELS + 8;
      int sy = (i / 32) * GLYPH_HEIGHT_IN_PIXELS + 8;

      glyphs[i] = new BufferedImage(GLYPH_WIDTH_IN_PIXELS, GLYPH_HEIGHT_IN_PIXELS,
          BufferedImage.TYPE_INT_ARGB);
      glyphs[i].getGraphics().drawImage(glyphSprite, 0, 0, GLYPH_WIDTH_IN_PIXELS,
              GLYPH_HEIGHT_IN_PIXELS, sx, sy, sx + GLYPH_WIDTH_IN_PIXELS, sy + GLYPH_HEIGHT_IN_PIXELS, null);
    }
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

  /**
   * Clear the entire screen to whatever the default background color is.
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel clear() {
    return clear(' ', 0, 0, widthInCharacters, heightInCharacters,
        defaultForegroundColor, defaultBackgroundColor);
  }

  /**
   * Clear the entire screen with the specified character and whatever the
   * default foreground and background colors are.
   * @param character  the character to write
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel clear(char character) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    return clear(character, 0, 0, widthInCharacters, heightInCharacters,
        defaultForegroundColor, defaultBackgroundColor);
  }

  /**
   * Clear the entire screen with the specified character and whatever the
   * specified foreground and background colors are.
   * @param character  the character to write
   * @param foreground the foreground color or null to use the default
   * @param background the background color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel clear(char character, Color foreground, Color background) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    return clear(character, 0, 0, widthInCharacters, heightInCharacters,
        foreground, background);
  }

  /**
   * Clear the section of the screen with the specified character and whatever
   * the default foreground and background colors are.
   * @param character  the character to write
   * @param x          the distance from the left to begin writing from
   * @param y          the distance from the top to begin writing from
   * @param width      the height of the section to clear
   * @param height     the width of the section to clear
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel clear(char character, int x, int y, int width, int height) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    if (x < 0 || x >= widthInCharacters)
      throw new IllegalArgumentException("x " + x +
          " must be within range [0," + widthInCharacters + ").");

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y +
          " must be within range [0," + heightInCharacters + ").");

    if (width < 1)
      throw new IllegalArgumentException("width " + width +
          " must be greater than 0.");

    if (height < 1)
      throw new IllegalArgumentException("height " + height +
          " must be greater than 0.");

    if (x + width > widthInCharacters)
      throw new IllegalArgumentException("x + width " + (x + width) +
          " must be less than " + (widthInCharacters + 1) + ".");

    if (y + height > heightInCharacters)
      throw new IllegalArgumentException("y + height " + (y + height) +
          " must be less than " + (heightInCharacters + 1) + ".");


    return clear(character, x, y, width, height, defaultForegroundColor,
        defaultBackgroundColor);
  }

  /**
   * Clear the section of the screen with the specified character and whatever
   * the specified foreground and background colors are.
   * @param character  the character to write
   * @param x          the distance from the left to begin writing from
   * @param y          the distance from the top to begin writing from
   * @param width      the height of the section to clear
   * @param height     the width of the section to clear
   * @param foreground the foreground color or null to use the default
   * @param background the background color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel clear(char character, int x, int y, int width, int height,
      Color foreground, Color background) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    if (x < 0 || x >= widthInCharacters)
      throw new IllegalArgumentException("x " + x +
          " must be within range [0," + widthInCharacters + ")");

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y +
          " must be within range [0," + heightInCharacters + ")");

    if (width < 1)
      throw new IllegalArgumentException("width " + width +
          " must be greater than 0.");

    if (height < 1)
      throw new IllegalArgumentException("height " + height +
          " must be greater than 0.");

    if (x + width > widthInCharacters)
      throw new IllegalArgumentException("x + width " + (x + width) +
          " must be less than " + (widthInCharacters + 1) + ".");

    if (y + height > heightInCharacters)
      throw new IllegalArgumentException("y + height " + (y + height) +
          " must be less than " + (heightInCharacters + 1) + ".");

    for (int xo = x; xo < x + width; xo++) {
      for (int yo = y; yo < y + height; yo++) {
        write(character, xo, yo, foreground, background);
      }
    }
    return this;
  }

  /**
   * Write a character to the cursor's position. This updates the cursor's
   * position.
   * @param character  the character to write
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(char character) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    return write(character, cursorX, cursorY, defaultForegroundColor,
        defaultBackgroundColor);
  }

  /**
   * Write a character to the cursor's position with the specified foreground
   * color. This updates the cursor's position but not the default foreground
   * color.
   * @param character  the character to write
   * @param foreground the foreground color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(char character, Color foreground) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    return write(character, cursorX, cursorY, foreground,
        defaultBackgroundColor);
  }

  /**
   * Write a character to the cursor's position with the specified foreground
   * and background colors. This updates the cursor's position but not the
   * default foreground or background colors.
   * @param character  the character to write
   * @param foreground the foreground color or null to use the default
   * @param background the background color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(char character, Color foreground, Color background) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    return write(character, cursorX, cursorY, foreground, background);
  }

  /**
   * Write a character to the specified position. This updates the cursor's
   * position.
   * @param character  the character to write
   * @param x          the distance from the left to begin writing from
   * @param y          the distance from the top to begin writing from
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(char character, int x, int y) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    if (x < 0 || x >= widthInCharacters)
      throw new IllegalArgumentException("x " + x +
          " must be within range [0," + widthInCharacters + ")");

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y +
          " must be within range [0," + heightInCharacters + ")");

    return write(character, x, y, defaultForegroundColor,
        defaultBackgroundColor);
  }

  /**
   * Write a character to the specified position with the specified foreground
   * color. This updates the cursor's position but not the default foreground
   * color.
   * @param character  the character to write
   * @param x          the distance from the left to begin writing from
   * @param y          the distance from the top to begin writing from
   * @param foreground the foreground color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(char character, int x, int y, Color foreground) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    if (x < 0 || x >= widthInCharacters)
      throw new IllegalArgumentException("x " + x +
          " must be within range [0," + widthInCharacters + ")");

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y +
          " must be within range [0," + heightInCharacters + ")");

    return write(character, x, y, foreground, defaultBackgroundColor);
  }

  /**
   * Write a character to the specified position with the specified foreground
   * and background colors. This updates the cursor's position but not the
   * default foreground or background colors.
   * @param character  the character to write
   * @param x          the distance from the left to begin writing from
   * @param y          the distance from the top to begin writing from
   * @param foreground the foreground color or null to use the default
   * @param background the background color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(char character, int x, int y, Color foreground,
      Color background) {
    if (character < 0 || character >= glyphs.length)
      throw new IllegalArgumentException("character " + character +
          " must be within range [0," + glyphs.length + "].");

    if (x < 0 || x >= widthInCharacters)
      throw new IllegalArgumentException("x " + x +
          " must be within range [0," + widthInCharacters + ")");

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y +
          " must be within range [0," + heightInCharacters + ")");

    if (foreground == null) foreground = defaultForegroundColor;
    if (background == null) background = defaultBackgroundColor;

    chars[x][y] = character;
    foregroundColors[x][y] = foreground;
    backgroundColors[x][y] = background;
    cursorX = x + 1;
    cursorY = y;
    return this;
  }

  /**
   * Write a string to the cursor's position. This updates the cursor's
   * position.
   * @param string     the string to write
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(String string) {
    if (string == null)
      throw new NullPointerException("string must not be null");

    if (cursorX + string.length() >= widthInCharacters)
      throw new IllegalArgumentException("cursorX + string.length() " +
          (cursorX + string.length()) + " must be less than " +
          widthInCharacters + ".");

    return write(string, cursorX, cursorY, defaultForegroundColor,
        defaultBackgroundColor);
  }

  /**
   * Write a string to the cursor's position with the specified foreground
   * color. This updates the cursor's position but not the default foreground
   * color.
   * @param string     the string to write
   * @param foreground the foreground color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(String string, Color foreground) {
    if (string == null)
      throw new NullPointerException("string must not be null");

    if (cursorX + string.length() >= widthInCharacters)
      throw new IllegalArgumentException("cursorX + string.length() " +
          (cursorX + string.length()) + " must be less than " +
          widthInCharacters + ".");

    return write(string, cursorX, cursorY, foreground, defaultBackgroundColor);
  }

  /**
   * Write a string to the cursor's position with the specified foreground and
   * background colors. This updates the cursor's position but not the default
   * foreground or background colors.
   * @param string     the string to write
   * @param foreground the foreground color or null to use the default
   * @param background the background color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(String string, Color foreground, Color background) {
    if (string == null)
      throw new NullPointerException("string must not be null");

    if (cursorX + string.length() >= widthInCharacters)
      throw new IllegalArgumentException("cursorX + string.length() " +
          (cursorX + string.length()) + " must be less than " +
          widthInCharacters + ".");

    return write(string, cursorX, cursorY, foreground, background);
  }

  /**
   * Write a string to the specified position. This updates the cursor's
   * position.
   * @param string     the string to write
   * @param x          the distance from the left to begin writing from
   * @param y          the distance from the top to begin writing from
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(String string, int x, int y) {
    if (string == null)
      throw new NullPointerException("string must not be null");

    if (x + string.length() >= widthInCharacters)
      throw new IllegalArgumentException("x + string.length() " +
          (x + string.length()) + " must be less than " + widthInCharacters +
          ".");

    if (x < 0 || x >= widthInCharacters)
      throw new IllegalArgumentException("x " + x + " must be within range [0,"
          + widthInCharacters + ")");

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y + " must be within range [0,"
          + heightInCharacters + ")");

    return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
  }

  /**
   * Write a string to the specified position with the specified foreground
   * color. This updates the cursor's position but not the default foreground
   * color.
   * @param string     the string to write
   * @param x          the distance from the left to begin writing from
   * @param y          the distance from the top to begin writing from
   * @param foreground the foreground color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(String string, int x, int y, Color foreground) {
    if (string == null)
      throw new NullPointerException("string must not be null");

    if (x + string.length() >= widthInCharacters)
      throw new IllegalArgumentException("x + string.length() " +
          (x + string.length()) + " must be less than " + widthInCharacters +
          ".");

    if (x < 0 || x >= widthInCharacters)
      throw new IllegalArgumentException("x " + x + " must be within range [0,"
          + widthInCharacters + ")");

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y +
          " must be within range [0," + heightInCharacters + ")");

    return write(string, x, y, foreground, defaultBackgroundColor);
  }

  /**
   * Write a string to the specified position with the specified foreground and
   * background colors. This updates the cursor's position but not the default
   * foreground or background colors.
   * @param string     the string to write
   * @param x          the distance from the left to begin writing from
   * @param y          the distance from the top to begin writing from
   * @param foreground the foreground color or null to use the default
   * @param background the background color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel write(String string, int x, int y, Color foreground,
      Color background) {
    if (string == null)
      throw new NullPointerException("string must not be null.");

    if (x + string.length() >= widthInCharacters)
      throw new IllegalArgumentException("x + string.length() " +
          (x + string.length()) + " must be less than " + widthInCharacters +
          ".");

    if (x < 0 || x >= widthInCharacters)
      throw new IllegalArgumentException("x " + x + " must be within range [0,"
          + widthInCharacters + ").");

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y + " must be within range [0,"
          + heightInCharacters + ").");

    if (foreground == null)
      foreground = defaultForegroundColor;

    if (background == null)
      background = defaultBackgroundColor;

    for (int i = 0; i < string.length(); i++) {
      write(string.charAt(i), x + i, y, foreground, background);
    }
    return this;
  }

  /**
   * Write a string to the center of the panel at the specified y position.
   * This updates the cursor's position.
   * @param string     the string to write
   * @param y          the distance from the top to begin writing from
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel writeCenter(String string, int y) {
    if (string == null)
      throw new NullPointerException("string must not be null");

    if (string.length() >= widthInCharacters)
      throw new IllegalArgumentException("string.length() " + string.length() +
          " must be less than " + widthInCharacters + ".");

    int x = (widthInCharacters - string.length()) / 2;

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y + " must be within range [0,"
          + heightInCharacters + ")");

    return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
  }

  /**
   * Write a string to the center of the panel at the specified y position with
   * the specified foreground color. This updates the cursor's position but not
   * the default foreground color.
   * @param string     the string to write
   * @param y          the distance from the top to begin writing from
   * @param foreground the foreground color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel writeCenter(String string, int y, Color foreground) {
    if (string == null)
      throw new NullPointerException("string must not be null");

    if (string.length() >= widthInCharacters)
      throw new IllegalArgumentException("string.length() " + string.length() +
          " must be less than " + widthInCharacters + ".");

    int x = (widthInCharacters - string.length()) / 2;

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y + " must be within range [0,"
          + heightInCharacters + ")");

    return write(string, x, y, foreground, defaultBackgroundColor);
  }

  /**
   * Write a string to the center of the panel at the specified y position with
   * the specified foreground and background colors. This updates the cursor's
   * position but not the default foreground or background colors.
   * @param string     the string to write
   * @param y          the distance from the top to begin writing from
   * @param foreground the foreground color or null to use the default
   * @param background the background color or null to use the default
   * @return this for convenient chaining of method calls
   */
  public AsciiPanel writeCenter(String string, int y, Color foreground,
      Color background) {
    if (string == null)
      throw new NullPointerException("string must not be null.");

    if (string.length() >= widthInCharacters)
      throw new IllegalArgumentException("string.length() " + string.length() +
          " must be less than " + widthInCharacters + ".");

    int x = (widthInCharacters - string.length()) / 2;

    if (y < 0 || y >= heightInCharacters)
      throw new IllegalArgumentException("y " + y + " must be within range [0,"
          + heightInCharacters + ").");

    if (foreground == null)
      foreground = defaultForegroundColor;

    if (background == null)
      background = defaultBackgroundColor;

    for (int i = 0; i < string.length(); i++) {
      write(string.charAt(i), x + i, y, foreground, background);
    }
    return this;
  }

  /**
   * Writes a given string on multiple lines, separating words by spaces. Starts
   * printing at the given top left coordinates.
   * @param x
   * @param y
   * @return
   */
  public AsciiPanel writeMultiline(String string, int x, int y) {
    return writeMultiline(string, x, y, widthInCharacters, heightInCharacters,
        defaultForegroundColor, defaultBackgroundColor);
  }

  /**
   * Writes a given string on multiple lines, separating words by spaces. Starts
   * printing at the given top left coordinates.
   * @param x
   * @param y
   * @param foregroundColour
   * @return
   */
  public AsciiPanel writeMultiline(String string, int x, int y,
      Color foregroundColour) {
    return writeMultiline(string, x, y, widthInCharacters, heightInCharacters,
        foregroundColour, defaultBackgroundColor);
  }

  /**
   * Writes a given string on multiple lines, separating words by spaces. Starts
   * printing at the given top left coordinates, and stops at the given bound
   * coordinates.
   * @param x
   * @param y
   * @param xBound
   * @param yBound
   * @return
   */
  public AsciiPanel writeMultiline(String string, int x, int y, int xBound,
       int yBound) {
    return writeMultiline(string, x, y, xBound, yBound, defaultForegroundColor,
        defaultBackgroundColor);
  }

  /**
   * Writes a given string on multiple lines, separating words by spaces. Starts
   * printing at the given top left coordinates, and stops at the given bound
   * coordinates.
   * @param x
   * @param y
   * @param xBound
   * @param yBound
   * @param foregroundColour
   * @return
   */
  public AsciiPanel writeMultiline(String string, int x, int y, int xBound,
       int yBound, Color foregroundColour) {
    return writeMultiline(string, x, y, xBound, yBound, foregroundColour,
        defaultBackgroundColor);
  }

  /**
   * Writes a given string on multiple lines, separating words by spaces. Starts
   * printing at the given top left coordinates, and stops at the given bound
   * coordinates.
   * @param x
   * @param y
   * @param xBound
   * @param yBound
   * @param foreground
   * @param background
   * @return
   */
  public AsciiPanel writeMultiline(String string, int x, int y, int xBound,
      int yBound, Color foreground, Color background) {
    if (string == null) {
      throw new NullPointerException("string must not be null");
    }
    if (x > xBound || y > yBound) {
      throw new IllegalArgumentException(
          "Top left coordinates must be less than the bound coordinates.");
    }
    if (x > widthInCharacters || xBound > widthInCharacters ||
        y > heightInCharacters || yBound > heightInCharacters) {
      throw new IllegalArgumentException("Arguments exceed terminal size.");
    }

    String[] words = string.split(" ");
    int currentX = x;
    int currentY = y;
    boolean first = true;

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
        write(words[i], currentX, currentY, foreground, background);
        currentX += words[i].length();
      } else {
        currentX = x;
        currentY++;
        if (currentY >= yBound) {
          throw new IndexOutOfBoundsException("Not enough space to print.");
        }
        write(words[i], currentX, currentY, foreground, background);
        currentX += words[i].length();
      }
    }
    lastMultilineX = currentX;
    lastMultilineY = currentY;
    return this;
  }

  public int getLastMultilineX() {
    return lastMultilineX;
  }

  public int getLastMultilineY() {
    return lastMultilineY;
  }

}
