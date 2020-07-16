package obscurum.items.armour;

import obscurum.display.Display;
import obscurum.items.Equipment;
import obscurum.items.Item;

/**
 * This models a piece of armour that can be equipped in the head slot.
 * @author Alex Ghita
 */
public class IronHelmet extends Armour {
  public IronHelmet(int level) {
    super("Iron Helmet", (char)127, Display.WHITE,
        "A helmet made out of iron.", 2 + level, Item.UNCOMMON,
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), Equipment.HEAD, 3 + level * 3);
  }

  public IronHelmet() {
    super("Iron Helmet", (char)127, Display.WHITE,
        "A helmet made out of iron.", 2, Item.UNCOMMON,
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), Equipment.HEAD, 5);
  }
}
