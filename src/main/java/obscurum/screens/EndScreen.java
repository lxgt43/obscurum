package obscurum.screens;

import java.awt.event.KeyEvent;
import obscurum.GameMain;
import obscurum.creatures.Creature;
import obscurum.creatures.Player;
import obscurum.display.DisplayColour;
import obscurum.display.terminal.AsciiPanel;

/**
 * This models the end screen, which displays a message depending on whether
 * the player won or lost the game, as well as some statistics about how well
 * they did.
 * @author Alex Ghita
 */
public class EndScreen extends Screen {
    private boolean wonGame;
    private String[] youLoseText = {
            "__   __            _                  _ ",
            "\\ \\ / /           | |                | |",
            " \\ V /___  _   _  | | ___  ___  ___  | |",
            "  \\ // _ \\| | | | | |/ _ \\/ __|/ _ \\ | |",
            "  | | (_) | |_| | | | (_) \\__ \\  __/ |_|",
            "  \\_/\\___/ \\__,_| |_|\\___/|___/\\___| (_)"
    };
    private String[] youWinText = {
            "__   __                     _         _ ",
            "\\ \\ / /                    (_)       | |",
            " \\ V /___  _   _  __      ___ _ __   | |",
            "  \\ // _ \\| | | | \\ \\ /\\ / / | '_ \\  | |",
            "  | | (_) | |_| |  \\ V  V /| | | | | |_|",
            "  \\_/\\___/ \\__,_|   \\_/\\_/ |_|_| |_| (_)"
    };
    private DisplayColour[] youLoseColours = {
            DisplayColour.fromRgb("L", 60, 0, 0),
            DisplayColour.fromRgb("L", 80, 0, 0),
            DisplayColour.fromRgb("L", 100, 0, 0),
            DisplayColour.fromRgb("L", 115, 0, 0),
            DisplayColour.fromRgb("L", 95, 0, 0),
            DisplayColour.fromRgb("L", 75, 0, 0)
    };
    private DisplayColour[] youWinColours = {
            DisplayColour.fromRgb("W", 225, 220, 90),
            DisplayColour.fromRgb("W", 255, 210, 87),
            DisplayColour.fromRgb("W", 255, 204, 87),
            DisplayColour.fromRgb("W", 254, 196, 86),
            DisplayColour.fromRgb("W", 255, 188, 83),
            DisplayColour.fromRgb("W", 253, 177, 81)
    };

    public EndScreen(Player player, boolean wonGame) {
        super();
        this.wonGame = wonGame;
        this.player = player;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        String[] text = wonGame ? youWinText : youLoseText;
        DisplayColour[] colours = wonGame ? youWinColours : youLoseColours;
        for (int i = 0; i < text.length; i++) {
            terminal.writeCentred(text[i], 3 + i, colours[i]);
        }

        terminal.writeCentred("Turns played: " + player.getTurnsAlive() + ".", 12);
        terminal.writeCentred("Damage dealt: " + player.getDamageDealt() + ".", 13);
        terminal.writeCentred("Enemies killed: " + player.getEnemiesKilled() + ".",
                14);
        terminal.writeCentred("Deepest level reached: " + player.getDeepestLevel() +
                ".", 15);
        terminal.writeCentred("Power level reached: " +
                player.getAttributes()[Creature.POWER_LEVEL] + ".", 16);
        terminal.writeCentred("Press [enter] to return to the main menu.",
                GameMain.SCREEN_HEIGHT_IN_CHARACTERS - 5);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            return new MainMenuScreen();
        } else {
            return this;
        }
    }
}
