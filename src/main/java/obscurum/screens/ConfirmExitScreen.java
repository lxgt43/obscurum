package obscurum.screens;

import java.awt.event.KeyEvent;

import obscurum.GameMain;
import obscurum.creatures.Player;
import obscurum.display.terminal.AsciiPanel;
import obscurum.environment.Level;

/**
 * This models a screen which asks the player whether they are sure they want
 * to quit the game.
 * @author Alex Ghita
 */
public class ConfirmExitScreen extends Screen {
  public ConfirmExitScreen(Level[] world, Player player) {
    super(world, player);
  }

  @Override
  public void displayOutput(AsciiPanel terminal) {
    terminal.writeCenter("Are you sure you want to quit? [Y/N]",
        GameMain.SCREEN_HEIGHT_IN_CHARACTERS / 2);
  }

  @Override
  public Screen respondToUserInput(KeyEvent key) {
    switch (key.getKeyCode()) {
      case KeyEvent.VK_N:
        return new PlayScreen(world, player);
      case KeyEvent.VK_Y:
        return new EndScreen(player, false);
      default:
        return this;
    }
  }
}
