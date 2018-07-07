package obscurum.items;

import obscurum.display.Display;

/**
 * This models a junk item dropped by zombies.
 * @author Alex Ghita
 */
public class RottenFlesh extends Item {
  public RottenFlesh() {
    super("Rotten Flesh", 'z', Display.RED, "A zombie's flesh.", 20, 1, 0);
  }
}
