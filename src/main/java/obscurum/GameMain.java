package obscurum;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import obscurum.display.DisplayColour;
import obscurum.display.terminal.AsciiPanel;
import obscurum.environment.foreground.*;
import obscurum.screens.MainMenuScreen;
import obscurum.screens.StartScreen;
import obscurum.screens.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMain extends JFrame implements KeyListener {
    private static final Logger log = LoggerFactory.getLogger(GameMain.class);

    public static final int SCREEN_WIDTH_IN_CHARACTERS = 110;
    public static final int SCREEN_HEIGHT_IN_CHARACTERS = 29;
    private static final int GLYPH_WIDTH_IN_PIXELS = 9;
    private static final int GLYPH_HEIGHT_IN_PIXELS = 16;
    public static final String GLYPHS_FILE_PATH = "/cp437.png";

    private final AsciiPanel terminal;
    private Screen screen;
    public static ArrayList<ForegroundTile> foregroundTiles;

    public GameMain() throws IOException {
        super("Obscurum");

        List<BufferedImage> glyphs = loadGlyphs();
        Dimension panelSize = new Dimension(GLYPH_WIDTH_IN_PIXELS * SCREEN_WIDTH_IN_CHARACTERS, GLYPH_HEIGHT_IN_PIXELS * SCREEN_HEIGHT_IN_CHARACTERS);
        terminal = new AsciiPanel(SCREEN_WIDTH_IN_CHARACTERS, SCREEN_HEIGHT_IN_CHARACTERS, glyphs, panelSize, DisplayColour.BLACK, DisplayColour.WHITE);
        add(terminal);
        pack();
        setResizable(false);

        foregroundTiles = new ArrayList<>();
        foregroundTiles.add(new Stone());
        foregroundTiles.add(new Wall());

        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    private List<BufferedImage> loadGlyphs() throws IOException {
        List<BufferedImage> glyphs = new ArrayList<>();
        BufferedImage glyphSprite = ImageIO.read(GameMain.class.getResource(GLYPHS_FILE_PATH));

        for (int i = 0; i < 256; i++) {
            int sx = (i % 32) * GLYPH_WIDTH_IN_PIXELS + 8;
            int sy = (i / 32) * GLYPH_HEIGHT_IN_PIXELS + 8;

            glyphs.add(new BufferedImage(GLYPH_WIDTH_IN_PIXELS, GLYPH_HEIGHT_IN_PIXELS, BufferedImage.TYPE_INT_ARGB));
            glyphs.get(i).getGraphics().drawImage(glyphSprite, 0, 0, GLYPH_WIDTH_IN_PIXELS, GLYPH_HEIGHT_IN_PIXELS, sx, sy, sx + GLYPH_WIDTH_IN_PIXELS, sy + GLYPH_HEIGHT_IN_PIXELS, null);
        }

        return glyphs;
    }

    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q && screen instanceof MainMenuScreen) {
            this.dispose();
        }
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) throws IOException {
        GameMain app = new GameMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
