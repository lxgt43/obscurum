package obscurum.screens;

import java.awt.event.KeyEvent;
import obscurum.GameMain;
import obscurum.display.DisplayColour;
import obscurum.display.terminal.AsciiPanel;

/**
 * This models the main menu screen.
 * @author Alex Ghita
 */
public class MainMenuScreen extends Screen {
    private String[] mainMenu = {
            "___  ___      _        ___  ___                 ",
            "|  \\/  |     (_)       |  \\/  |                 ",
            "| .  . | __ _ _ _ __   | .  . | ___ _ __  _   _ ",
            "| |\\/| |/ _` | | '_ \\  | |\\/| |/ _ \\ '_ \\| | | |",
            "| |  | | (_| | | | | | | |  | |  __/ | | | |_| |",
            "\\_|  |_/\\__,_|_|_| |_| \\_|  |_/\\___|_| |_|\\__,_|"
    };
    private DisplayColour[] mainMenuColours = {
            DisplayColour.fromRgb("W", 82, 6, 4),
            DisplayColour.fromRgb("W", 100, 8, 5),
            DisplayColour.fromRgb("W", 118, 10, 6),
            DisplayColour.fromRgb("W", 136, 11, 7),
            DisplayColour.fromRgb("W", 154, 13, 8),
            DisplayColour.fromRgb("W", 172, 14, 9)
    };

    @Override
    public void displayOutput(AsciiPanel terminal) {
        for (int i = 0; i < mainMenu.length; i++) {
            terminal.writeCenter(mainMenu[i], 3 + i, mainMenuColours[i]);
        }
        terminal.writeCenter("Press [enter] to start a new game.",
                GameMain.SCREEN_HEIGHT - 9);
        terminal.writeCenter("Press [e] to start the game editor.",
                GameMain.SCREEN_HEIGHT - 7);
        terminal.writeCenter("Press [q] to quit the program.",
                GameMain.SCREEN_HEIGHT - 5);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new PlayScreen();
            case KeyEvent.VK_E:
                return new GameEditorScreen();
            default:
                return this;
        }
    }
}
