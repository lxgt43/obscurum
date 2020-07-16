package obscurum.placeholders;

import obscurum.display.asciiPanel.AsciiPanel;
import obscurum.screens.Screen;

import java.awt.event.KeyEvent;

/**
 * This models a placeholder screen.
 * @author Alex Ghita
 */
public class NullScreen extends Screen {
  @Override
  public void displayOutput(AsciiPanel terminal) {}

  @Override
  public Screen respondToUserInput(KeyEvent key) {
    return this;
  }
}
