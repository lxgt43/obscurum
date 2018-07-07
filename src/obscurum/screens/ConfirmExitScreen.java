package obscurum.screens;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.lang.Math;
import java.util.ArrayList;
import obscurum.GameMain;
import obscurum.util.Util;
import obscurum.creatures.Creature;
import obscurum.creatures.Player;
import obscurum.creatures.abilities.Spell;
import obscurum.display.Display;
import obscurum.display.ListEntry;
import obscurum.display.ScrollList;
import obscurum.display.asciiPanel.AsciiPanel;
import obscurum.environment.Level;
import obscurum.placeholders.NullItem;
import obscurum.screens.Screen;

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
        GameMain.SCREEN_HEIGHT / 2);
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
