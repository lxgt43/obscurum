package obscurum.screens;

import obscurum.GameMain;
import obscurum.display.asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This models the game splash screen seen when first running the program.
 * @author Alex Ghita
 */
public class StartScreen extends Screen {
  @Override
  public void displayOutput(AsciiPanel terminal) {
    int lineOffset = GameMain.SCREEN_HEIGHT / 5;

    // patorjk.com, doom font.
    terminal.writeCenter(
        "_____ ______  _____  _____  _   _ ______  _   _ ___  ___",
        lineOffset, new Color(82, 6, 4));
    terminal.writeCenter(
        "|  _  || ___ \\/  ___|/  __ \\| | | || ___ \\| | | ||  \\/  |",
        lineOffset + 1, new Color(100, 8, 5));
    terminal.writeCenter(
        "| | | || |_/ /\\ `--. | /  \\/| | | || |_/ /| | | || .  . |",
        lineOffset + 2, new Color(118, 10, 6));
    terminal.writeCenter(
        "| | | || ___ \\ `--. \\| |    | | | ||    / | | | || |\\/| |",
        lineOffset + 3, new Color(136, 11, 7));
    terminal.writeCenter(
        "| \\_/ || |_/ //\\__/ /| \\__/\\| |_| || |\\ \\ | |_| || |  | |",
        lineOffset + 4, new Color(154, 13, 8));
    terminal.writeCenter(
        "\\___/ \\____/ \\____/  \\____/ \\___/ \\_| \\_| \\___/ \\_|  |_/",
        lineOffset + 5, new Color(172, 14, 9));
    // www.ascii-code.com/ascii-art/art-and-design/dividers.php, richard kirk
    terminal.writeCenter(
        ".-.-.   .-.-.   .-.-.   .-.-.   .-.-.   .-.-.   .-.-.   .-.-",
        lineOffset + 7, new Color(118, 10, 6));
    terminal.writeCenter(
        "/ / \\ \\ / / \\ \\ / / \\ \\ / / \\ \\ / / \\ \\ / / \\ \\ / / \\" +
        " \\ / / \\", lineOffset + 8, new Color(100, 8, 5));
    terminal.writeCenter(
        "`-'   `-`-'   `-`-'   `-`-'   `-`-'   `-`-'   `-`-'   `-`-'",
        lineOffset + 9, new Color(82, 6, 4));
    terminal.writeCenter("Press any key to start.",
        GameMain.SCREEN_HEIGHT - 5, new Color(172, 14, 9));
  }

  @Override
  public Screen respondToUserInput(KeyEvent key) {
    return new MainMenuScreen();
  }
}
