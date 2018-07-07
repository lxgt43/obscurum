package obscurum.items.armour;

import java.lang.Math;
import obscurum.display.Display;
import obscurum.display.DisplayTile;
import obscurum.items.Equipment;
import obscurum.items.Item;

/**
 * This models a piece of armour that can be equipped in the legs slot.
 * @author Alex Ghita
 */
public class IronLeggings extends Armour {
  public IronLeggings(int level) {
    super("Iron Leggings", (char)225, Display.WHITE,
        "Leg armour made out of iron.", 2 + level, Item.UNCOMMON,
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), Equipment.LEGS, 4 + level);
  }

  public IronLeggings() {
    super("Iron Leggings", (char)225, Display.WHITE,
        "Leg armour made out of iron.", 2, Item.UNCOMMON,
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), Equipment.LEGS, 4);
  }
}
