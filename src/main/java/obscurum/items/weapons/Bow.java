package obscurum.items.weapons;

import java.lang.Math;
import obscurum.display.Display;
import obscurum.display.DisplayCharacter;
import obscurum.display.DisplayColour;
import obscurum.display.DisplayTile;
import obscurum.items.Item;

/**
 * This models a bow, which is a ranged weapon.
 * @author Alex Ghita
 */
public class Bow extends Weapon {
  private static DisplayTile[][] standardDepiction = {
      {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of('('), DisplayColour.BROWN),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' '))},
      {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of('\\'), DisplayColour.BROWN),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' '))},
      {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(')'), DisplayColour.BROWN), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' '))},
      {new DisplayTile(DisplayCharacter.of('>'), DisplayColour.RED), new DisplayTile(DisplayCharacter.of('>'), DisplayColour.RED),
       new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')),
       new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')),
       new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')),
       new DisplayTile(DisplayCharacter.of('>'), DisplayColour.GREEN)},
      {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(')'), DisplayColour.BROWN), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' '))},
      {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of('/'), DisplayColour.BROWN),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' '))},
      {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(')'), DisplayColour.BROWN),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
       new DisplayTile(DisplayCharacter.of(' '))}
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
