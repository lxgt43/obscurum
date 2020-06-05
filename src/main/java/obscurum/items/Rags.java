package obscurum.items;

import obscurum.display.Display;

/**
 * This models a junk item dropped by goblins.
 * @author Alex Ghita
 */
public class Rags extends Item {
  public Rags() {
    super("Rags", 'r', Display.WHITE, "Torn cloth.", 20, 1, 0);
  }
}
