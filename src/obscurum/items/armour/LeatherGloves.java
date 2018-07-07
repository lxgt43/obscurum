package obscurum.items.armour;

import java.lang.Math;
import obscurum.display.Display;
import obscurum.display.DisplayTile;
import obscurum.items.Equipment;
import obscurum.items.Item;

/**
 * This models a piece of armour that can be equipped in the hands slot.
 * @author Alex Ghita
 */
public class LeatherGloves extends Armour {
  public LeatherGloves(int level) {
    super("Iron Gloves", (char)234, Display.WHITE,
        "A pair of gloves made out of iron.", 2 + level, Item.UNCOMMON,
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), Equipment.HANDS, 1 + level);
  }

  public LeatherGloves() {
    super("Iron Gloves", (char)234, Display.WHITE,
        "A pair of gloves made out of iron.", 2, Item.UNCOMMON,
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), Equipment.HANDS, 1);
  }
}
