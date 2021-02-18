package obscurum;

import obscurum.display.DisplayColour;
import obscurum.display.terminal.AsciiPanel;
import obscurum.screens.StartScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final String GLYPHS_FILE_PATH = "/cp437.png";
    private static final int GLYPH_WIDTH_IN_PIXELS = 9;
    private static final int GLYPH_HEIGHT_IN_PIXELS = 16;

    public static final int SCREEN_WIDTH_IN_CHARACTERS = 110;
    public static final int SCREEN_HEIGHT_IN_CHARACTERS = 29;

    public static void main(String[] args) throws IOException {
        AsciiPanel terminal = constructTerminal();
        GameDriver driver = new GameDriver(terminal, new StartScreen());
        driver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        driver.setVisible(true);
    }

    private static AsciiPanel constructTerminal() throws IOException {
        List<BufferedImage> glyphs = loadGlyphs();
        Dimension panelSize = new Dimension(GLYPH_WIDTH_IN_PIXELS * SCREEN_WIDTH_IN_CHARACTERS, GLYPH_HEIGHT_IN_PIXELS * SCREEN_HEIGHT_IN_CHARACTERS);
        return new AsciiPanel(SCREEN_WIDTH_IN_CHARACTERS, SCREEN_HEIGHT_IN_CHARACTERS, glyphs, panelSize, DisplayColour.BLACK, DisplayColour.WHITE);
    }

    private static List<BufferedImage> loadGlyphs() throws IOException {
        log.info("Loading glyphs from image {}", GLYPHS_FILE_PATH);
        BufferedImage glyphSprite = ImageIO.read(GameDriver.class.getResource(GLYPHS_FILE_PATH));

        return IntStream.range(0, 256)
                .mapToObj(i -> {
                    BufferedImage image = new BufferedImage(GLYPH_WIDTH_IN_PIXELS, GLYPH_HEIGHT_IN_PIXELS, BufferedImage.TYPE_INT_ARGB);
                    int sx = (i % 32) * GLYPH_WIDTH_IN_PIXELS + 8;
                    int sy = (i / 32) * GLYPH_HEIGHT_IN_PIXELS + 8;

                    image.getGraphics().drawImage(glyphSprite, 0, 0, GLYPH_WIDTH_IN_PIXELS, GLYPH_HEIGHT_IN_PIXELS, sx, sy, sx + GLYPH_WIDTH_IN_PIXELS, sy + GLYPH_HEIGHT_IN_PIXELS, null);

                    return image;
                })
                .collect(Collectors.toList());
    }
}
