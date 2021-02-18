package obscurum.screens;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import obscurum.Main;
import obscurum.creatures.Player;
import obscurum.display.Display;
import obscurum.display.DisplayColour;
import obscurum.display.terminal.AsciiPanel;
import obscurum.environment.Level;

@NoArgsConstructor
public abstract class Screen {
    protected List<Level> world;
    protected Player player;

    public Screen(@NonNull List<Level> world, @NonNull Player player) {
        this.world = world;
        this.player = player;
    }

    public abstract void displayOutput(AsciiPanel terminal);

    public abstract Screen respondToUserInput(KeyEvent key);

    protected void drawBorders(@NonNull AsciiPanel terminal, Point topLeft, int width, int height, boolean single, DisplayColour foreground, DisplayColour background) {
        if (!isInBounds(topLeft)) {
            throw new IllegalArgumentException("Top left corner must be between " +
                    "(0, 0) and (" + Main.SCREEN_WIDTH_IN_CHARACTERS + ", " + Main.SCREEN_HEIGHT_IN_CHARACTERS +
                    ").");
        }
        if (topLeft.x + width > Main.SCREEN_WIDTH_IN_CHARACTERS) {
            throw new IllegalArgumentException("Border width exceeds screen width " +
                    Main.SCREEN_WIDTH_IN_CHARACTERS +  ".");
        }
        if (topLeft.y + height > Main.SCREEN_HEIGHT_IN_CHARACTERS) {
            throw new IllegalArgumentException("Border height exceeds screen height "
                    + Main.SCREEN_HEIGHT_IN_CHARACTERS + ".");
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

    protected boolean isInBounds(int x, int y) {
        return isInBounds(new Point(x, y));
    }

    protected boolean isInBounds(Point p) {
        if (p.x < 0 || p.x >= Main.SCREEN_WIDTH_IN_CHARACTERS || p.y < 0 ||
                p.y >= Main.SCREEN_HEIGHT_IN_CHARACTERS) {
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
