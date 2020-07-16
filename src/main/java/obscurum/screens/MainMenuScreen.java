package obscurum.screens;

import obscurum.GameMain;
import obscurum.display.asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

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
  private Color[] mainMenuColours = {new Color(82, 6, 4), new Color(100, 8, 5),
      new Color(118, 10, 6), new Color(136, 11, 7), new Color(154, 13, 8),
      new Color(172, 14, 9)};

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
