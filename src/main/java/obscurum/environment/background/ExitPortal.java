package obscurum.environment.background;

import obscurum.display.Display;

/**
 * This models the exit portal through which the player must step to win.
 * @author Alex Ghita
 */
public class ExitPortal extends BackgroundTile {
  public ExitPortal() {
    super("Exit Portal", (char)233, Display.WHITE, Display.BLACK);
  }

  public ExitPortal(BackgroundTile b) {
    super("Exit Portal", (char)233, Display.YELLOW, b.getBackgroundColour());
  }
}
