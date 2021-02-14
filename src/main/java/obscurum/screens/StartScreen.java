package obscurum.screens;

import java.awt.event.KeyEvent;
import obscurum.GameMain;
import obscurum.display.DisplayColour;
import obscurum.display.terminal.AsciiPanel;

/**
 * This models the game splash screen seen when first running the program.
 * @author Alex Ghita
 */
public class StartScreen extends Screen {
    @Override
    public void displayOutput(AsciiPanel terminal) {
        int lineOffset = GameMain.SCREEN_HEIGHT_IN_CHARACTERS / 5;

        // patorjk.com, doom font.
        terminal.writeCentred(
                "_____ ______  _____  _____  _   _ ______  _   _ ___  ___",
                lineOffset, DisplayColour.fromRgb("D", 82, 6, 4));
        terminal.writeCentred(
                "|  _  || ___ \\/  ___|/  __ \\| | | || ___ \\| | | ||  \\/  |",
                lineOffset + 1, DisplayColour.fromRgb("D", 100, 8, 5));
        terminal.writeCentred(
                "| | | || |_/ /\\ `--. | /  \\/| | | || |_/ /| | | || .  . |",
                lineOffset + 2, DisplayColour.fromRgb("D", 118, 10, 6));
        terminal.writeCentred(
                "| | | || ___ \\ `--. \\| |    | | | ||    / | | | || |\\/| |",
                lineOffset + 3, DisplayColour.fromRgb("D", 136, 11, 7));
        terminal.writeCentred(
                "| \\_/ || |_/ //\\__/ /| \\__/\\| |_| || |\\ \\ | |_| || |  | |",
                lineOffset + 4, DisplayColour.fromRgb("D", 154, 13, 8));
        terminal.writeCentred(
                "\\___/ \\____/ \\____/  \\____/ \\___/ \\_| \\_| \\___/ \\_|  |_/",
                lineOffset + 5, DisplayColour.fromRgb("D", 172, 14, 9));
        // www.ascii-code.com/ascii-art/art-and-design/dividers.php, richard kirk
        terminal.writeCentred(
                ".-.-.   .-.-.   .-.-.   .-.-.   .-.-.   .-.-.   .-.-.   .-.-",
                lineOffset + 7, DisplayColour.fromRgb("D", 118, 10, 6));
        terminal.writeCentred(
                "/ / \\ \\ / / \\ \\ / / \\ \\ / / \\ \\ / / \\ \\ / / \\ \\ / / \\" +
                        " \\ / / \\", lineOffset + 8, DisplayColour.fromRgb("D", 100, 8, 5));
        terminal.writeCentred(
                "`-'   `-`-'   `-`-'   `-`-'   `-`-'   `-`-'   `-`-'   `-`-'",
                lineOffset + 9, DisplayColour.fromRgb("D", 82, 6, 4));
        terminal.writeCentred("Press any key to start.",
                GameMain.SCREEN_HEIGHT_IN_CHARACTERS - 5, DisplayColour.fromRgb("D", 172, 14, 9));
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return new MainMenuScreen();
    }
}
