package obscurum;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import obscurum.display.asciiPanel.AsciiPanel;
import obscurum.environment.foreground.*;
import obscurum.screens.MainMenuScreen;
import obscurum.screens.StartScreen;
import obscurum.screens.Screen;

/**
 * This is the application's starting point.
 * @author Alex Ghita
 */
public class GameMain extends JFrame implements KeyListener {
  private static final long serialVersionUID = 1;

  // The terminal where things are displayed.
  private AsciiPanel terminal;
  // The currently displayed screen.
  private Screen screen;
  public static final int SCREEN_WIDTH = 110;
  public static final int SCREEN_HEIGHT = 29;
  public static final int NUM_OF_GLYPHS = 256;
  public static ArrayList<ForegroundTile> foregroundTiles;

  public GameMain() {
    super("Obscurum");
    terminal = new AsciiPanel(SCREEN_WIDTH, SCREEN_HEIGHT);
    add(terminal);
    pack();
    setResizable(false);

    foregroundTiles = new ArrayList<ForegroundTile>();
    foregroundTiles.add(new Stone());
    foregroundTiles.add(new Wall());
    // read file and add tiles

    screen = new StartScreen();
    addKeyListener(this);
    repaint();
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

  public static void main(String[] args) {
    GameMain app = new GameMain();
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    app.setVisible(true);
  }
}
