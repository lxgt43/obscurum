package obscurum.items.weapons;

import obscurum.display.Display;
import obscurum.display.DisplayTile;
import obscurum.items.Item;

/**
 * This models a bow, which is a ranged weapon.
 * @author Alex Ghita
 */
public class Bow extends Weapon {
  private static DisplayTile[][] standardDepiction = {
      {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile('(', Display.BROWN),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' ')},
      {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile('\\', Display.BROWN),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' ')},
      {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(')', Display.BROWN), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' ')},
      {new DisplayTile('>', Display.RED), new DisplayTile('>', Display.RED),
       new DisplayTile('-'), new DisplayTile('-'), new DisplayTile('-'),
       new DisplayTile('-'), new DisplayTile('-'), new DisplayTile('-'),
       new DisplayTile('-'), new DisplayTile('-'),
       new DisplayTile('>', Display.GREEN)},
      {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(')', Display.BROWN), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' ')},
      {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile('/', Display.BROWN),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' ')},
      {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(')', Display.BROWN),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
       new DisplayTile(' ')}
  };

  public Bow(int level) {
    super("Bow", ')', Display.GREEN, "A bow.", 2 + level, Item.UNCOMMON,
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), (int)(Math.random() * (3 + level)),
        (int)(Math.random() * (3 + level)), Weapon.RANGED, 3 + level * 2,
        5 + level * 2, 1);
    this.depiction = standardDepiction;
  }

  public Bow() {
    super("Bow", ')', Display.GREEN, "A bow.", 3, Item.UNCOMMON,
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), (int)(Math.random() * 3),
        (int)(Math.random() * 3), Weapon.RANGED, 5, 7, 1);
    this.depiction = standardDepiction;
  }
}
