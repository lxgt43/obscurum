package obscurum.placeholders;

import java.awt.event.KeyEvent;
import obscurum.display.terminal.AsciiPanel;
import obscurum.screens.Screen;

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
