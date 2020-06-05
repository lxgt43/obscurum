package obscurum.items.armour;

import java.lang.Math;
import obscurum.display.Display;
import obscurum.display.DisplayTile;
import obscurum.items.Equipment;
import obscurum.items.Item;

/**
 * This models a piece of armour that can be equipped in the feet slot.
 * @author Alex Ghita
 */
public class IronBoots extends Armour {
  public IronBoots(int level) {
    super("Iron Boots", 'b', Display.WHITE,
        "A pair of boots made out of iron.", 2 + level, Item.UNCOMMON,
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), Equipment.FEET, 1 + level * 3);
  }

  public IronBoots() {
    super("Iron Boots", 'b', Display.WHITE,
        "A pair of boots made out of iron.", 2, Item.UNCOMMON,
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), Equipment.FEET, 1);
  }
}
