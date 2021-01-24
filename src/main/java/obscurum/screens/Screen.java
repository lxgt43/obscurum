package obscurum.screens;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import obscurum.GameMain;
import obscurum.creatures.Player;
import obscurum.display.Display;
import obscurum.display.DisplayColour;
import obscurum.display.terminal.AsciiPanel;
import obscurum.environment.Level;
import obscurum.placeholders.NullCreature;

/**
 * This models a game screen, which displays information and responds to user
 * input by changing screens to modify the output.
 * @author Alex Ghita
 */
public abstract class Screen {
    protected Level[] world;
    protected Player player;

    /**
     * Default constructor. To be used by screens which do not record the game
     * state (world and player), such as the main menu screen.
     */
    public Screen() {
        super();
    }

    /**
     * Class constructor for screens which take into account the game world and
     * player, such as the play screen or the inventory screen.
     * @param world
     * @param player
     */
    public Screen(Level[] world, Player player) {
        // Check for illegal arguments.
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null.");
        }
        if (player == null || player.isOfType(new NullCreature())) {
            throw new IllegalArgumentException("Player cannot be null.");
        }

        this.world = world;
        this.player = player;
    }

    /**
     * Displays the screen's output. This will vary from text to the actual game,
     * depending on the screen.
     * @param terminal
     */
    public abstract void displayOutput(AsciiPanel terminal);

    /**
     * Responds to the user's keyboard input and returns an appropriate screen.
     * This will handle actions such as moving around, which will return the same
     * screen, or pressing a button that changes the screen.
     * @param key
     * @return the screen to be displayed as a consequence of pressing a button
     */
    public abstract Screen respondToUserInput(KeyEvent key);

    /**
     * Draws a rectangular frame of the given size starting from the given top
     * left coordinates.
     * @param terminal
     * @param topLeft
     * @param width
     * @param height
     * @param single true if the border will be a single line, false if double
     */
    protected void drawBorders(AsciiPanel terminal, Point topLeft, int width, int height, boolean single, DisplayColour foreground, DisplayColour background) {
        // Check for illegal arguments.
        if (terminal == null) {
            throw new IllegalArgumentException("Terminal cannot be null.");
        }
        if (!isInBounds(topLeft)) {
            throw new IllegalArgumentException("Top left corner must be between " +
                    "(0, 0) and (" + GameMain.SCREEN_WIDTH + ", " + GameMain.SCREEN_HEIGHT +
                    ").");
        }
        if (topLeft.x + width > GameMain.SCREEN_WIDTH) {
            throw new IllegalArgumentException("Border width exceeds screen width " +
                    GameMain.SCREEN_WIDTH +  ".");
        }
        if (topLeft.y + height > GameMain.SCREEN_HEIGHT) {
            throw new IllegalArgumentException("Border height exceeds screen height "
                    + GameMain.SCREEN_HEIGHT + ".");
        }

        char hLine = single ? Display.SH_LINE : Display.DH_LINE;
        char vLine = single ? Display.SV_LINE : Display.DV_LINE;
        char tlCorner = single ? Display.STL_CORNER : Display.DTL_CORNER;
        char trCorner = single ? Display.STR_CORNER : Display.DTR_CORNER;
        char blCorner = single ? Display.SBL_CORNER : Display.DBL_CORNER;
        char brCorner = single ? Display.SBR_CORNER : Display.DBR_CORNER;

        // Print the horizontal lines.
        for (int x = topLeft.x + 1; x < topLeft.x + width - 1; x++) {
            terminal.write(hLine, x, topLeft.y, foreground, background);
            terminal.write(hLine, x, topLeft.y + height - 1, foreground, background);
        }
        // Print the vertical lines.
        for (int y = topLeft.y + 1; y < topLeft.y + height - 1; y++) {
            terminal.write(vLine, topLeft.x, y, foreground, background);
            terminal.write(vLine, topLeft.x + width - 1, y, foreground, background);
        }
        // Print the corners.
        terminal.write(tlCorner, topLeft.x, topLeft.y, foreground, background);
        terminal.write(trCorner, topLeft.x + width - 1, topLeft.y, foreground,
                background);
        terminal.write(blCorner, topLeft.x, topLeft.y + height - 1, foreground,
                background);
        terminal.write(brCorner, topLeft.x + width - 1, topLeft.y + height - 1,
                foreground, background);
    }

    protected void writeHorizontalLine(AsciiPanel terminal, int startX,
                                       int width, int y) {
        for (int x = startX; x <= startX + width; x++) {
            char c = x == startX ? Display.DV_SR_INTERSECT :
                    (x == startX + width ? Display.DV_SL_INTERSECT : Display.SH_LINE);

            terminal.write(c, x, y, Display.FG_WINDOW_FRAME,
                    Display.BG_WINDOW_FRAME);
        }
    }

    /**
     * Checks whether the given location is on the screen.
     * @param x
     * @param y
     * @return
     */
    protected boolean isInBounds(int x, int y) {
        return isInBounds(new Point(x, y));
    }

    /**
     * Checks whether the given point is on the screen.
     * @param p
     * @return
     */
    protected boolean isInBounds(Point p) {
        if (p.x < 0 || p.x >= GameMain.SCREEN_WIDTH || p.y < 0 ||
                p.y >= GameMain.SCREEN_HEIGHT) {
            return false;
        }
        return true;
    }

    protected ArrayList<String[]> readCSV(String fileName) {
        ArrayList<String[]> inputData = new ArrayList<String[]>();
        try {
            FileReader r = new FileReader("src/main/resources/" + fileName);
            BufferedReader br = new BufferedReader(r);
            String line;

            while ((line = br.readLine()) != null) {
                inputData.add(line.split(","));
            }
            br.close();
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputData;
    }
}
