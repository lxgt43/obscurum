package obscurum.items.armour;

import java.lang.Math;
import obscurum.display.Display;
import obscurum.display.DisplayTile;
import obscurum.items.Equipment;
import obscurum.items.Item;

/**
 * This models a piece of armour that can be equipped in the chest slot.
 * @author Alex Ghita
 */
public class IronChestplate extends Armour {
  public IronChestplate(int level) {
    super("Iron Chestplate", (char)5, Display.WHITE,
        "A chestplate made out of iron.", 2 + level, Item.UNCOMMON,
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), Equipment.CHEST, 5 + level * 3);
  }

  public IronChestplate() {
    super("Iron Chestplate", (char)5, Display.WHITE,
        "A chestplate made out of iron.", 2, Item.UNCOMMON,
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), Equipment.CHEST, 5);
  }
}
