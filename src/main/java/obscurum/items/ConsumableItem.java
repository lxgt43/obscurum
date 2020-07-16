package obscurum.items;

import obscurum.creatures.Creature;
import obscurum.display.DisplayTile;

import java.awt.*;

/**
 * This models a consumable item, which can be used for some effect, and is
 * destroyed in the process.
 * @author Alex Ghita
 */
public abstract class ConsumableItem extends Item {
  protected DisplayTile[][] depiction;

  public ConsumableItem(String name, char glyph, Color colour,
      String description, int stackSize, int requiredLevel, int quality,
      DisplayTile[][] depiction) {
    super(name, glyph, colour, description, stackSize, requiredLevel, quality);
    this.depiction = depiction;
  }

  public DisplayTile[][] getDepiction() {
    return depiction;
  }

  public abstract void use(Creature target);
}
