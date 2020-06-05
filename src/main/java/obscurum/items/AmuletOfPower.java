package obscurum.items;

import obscurum.display.Display;

/**
 * This models the treasure that the player must find and escape with to win
 * the game.
 * @author Alex Ghita
 */
public class AmuletOfPower extends Item {
  public AmuletOfPower() {
    super("Amulet of Power", (char)21, Display.YELLOW,
        "Escape from the dungeon with this amulet to gain infinite power.",
        1, 1, Item.EPIC);
  }
}
