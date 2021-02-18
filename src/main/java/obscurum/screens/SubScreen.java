package obscurum.screens;

import java.awt.Point;
import java.lang.Math;
import java.util.List;

import obscurum.GameMain;
import obscurum.creatures.Player;
import obscurum.display.Display;
import obscurum.display.terminal.AsciiPanel;
import obscurum.environment.Level;

/**
 * This models a subscreen, which is displayed on top of a parent screen and
 * captures all input from it.
 * @author Alex Ghita
 */
public abstract class SubScreen extends Screen {
  protected int width;
  protected int height;
  protected Point topLeft;

  public SubScreen() {
    world = null;
    player = null;
  }

  public SubScreen(List<Level> world, Player player) {
    super(world, player);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Point getTopLeft() {
    return topLeft;
  }

  protected void writeCentre(AsciiPanel terminal, String line, int y) {
    int x;

    y += topLeft.y;
    x = (width - line.length()) / 2 + topLeft.x;
    terminal.write(line, x, y);
  }

  protected void writeHorizontalLine(AsciiPanel terminal, int y) {
    for (int i = 0; i <= width; i++) {
      char c = i == 0 ? Display.DV_SR_INTERSECT :
          (i == width ? Display.DV_SL_INTERSECT : Display.SH_LINE);

      terminal.write(c, topLeft.x + i, topLeft.y + y,
          Display.FG_WINDOW_FRAME, Display.BG_WINDOW_FRAME);
    }
  }

  protected void computeTopLeft() {
    int x = player.getScreenLocation().x + 2;
    int y = Math.max(1, Math.min(player.getScreenLocation().y - 3,
        GameMain.SCREEN_HEIGHT_IN_CHARACTERS - height - 4)); // should remove the -3 later
        //it prevents the screen from overlapping with the help lines below
        //the board, but these will be removed later

    if (x + width >= GameMain.SCREEN_WIDTH_IN_CHARACTERS - 1) {
      x = player.getScreenLocation().x - width - 2;
    }
    topLeft = new Point(x, y);
  }

  protected void drawBorders(AsciiPanel terminal, Point topLeft, int width,
      int height) {
    for (int x = topLeft.x + 1; x < topLeft.x + width; x++) {
      terminal.write(Display.DH_LINE, x, topLeft.y, Display.FG_WINDOW_FRAME,
          Display.BG_WINDOW_FRAME);
      terminal.write(Display.DH_LINE, x, topLeft.y + height,
          Display.FG_WINDOW_FRAME, Display.BG_WINDOW_FRAME);
    }
    for (int y = topLeft.y + 1; y < topLeft.y + height; y++) {
      terminal.write(Display.DV_LINE, topLeft.x, y, Display.FG_WINDOW_FRAME,
          Display.BG_WINDOW_FRAME);
      terminal.write(Display.DV_LINE, topLeft.x + width, y,
          Display.FG_WINDOW_FRAME, Display.BG_WINDOW_FRAME);
    }
    terminal.write(Display.DTL_CORNER, topLeft.x, topLeft.y,
        Display.FG_WINDOW_FRAME, Display.BG_WINDOW_FRAME);
    terminal.write(Display.DTR_CORNER, topLeft.x + width, topLeft.y,
        Display.FG_WINDOW_FRAME, Display.BG_WINDOW_FRAME);
    terminal.write(Display.DBL_CORNER, topLeft.x, topLeft.y + height,
        Display.FG_WINDOW_FRAME, Display.BG_WINDOW_FRAME);
    terminal.write(Display.DBR_CORNER, topLeft.x + width, topLeft.y + height,
        Display.FG_WINDOW_FRAME, Display.BG_WINDOW_FRAME);
  }

  protected abstract void computeWidth();
  protected abstract void computeHeight();
}
