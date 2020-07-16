package obscurum.items.weapons;

import obscurum.display.Display;
import obscurum.items.Equipment;
import obscurum.items.Item;

/**
 * This models a bow, which is a melee weapon.
 * @author Alex Ghita
 */
public class Sword extends Weapon {
  public Sword(int level) {
    super("Sword", '!', Display.MAGENTA, "A sword.", 1 + level, Item.UNCOMMON,
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), Weapon.MELEE, 2 + level * 2,
        12 + level * 2, Equipment.SWORD);
  }

  public Sword() {
    super("Sword", '!', Display.MAGENTA, "A sword.", 2, Item.UNCOMMON,
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), Weapon.MELEE, 2, 12, 0);
  }
}
