package obscurum.items.armour;

import java.awt.Color;
import java.lang.Math;
import obscurum.display.DisplayTile;
import obscurum.items.Equipment;

/**
 * This models a piece of armour, which gives a defence rating and can be
 * equipped in one of the five armour slots.
 * @author Alex Ghita
 */
public abstract class Armour extends Equipment {
  protected int defence;

  public Armour(String name, char glyph, Color colour, String description,
      int requiredLevel, int quality, int strength, int agility, int stamina,
      int spirit, int intellect, int slot, int defence) {
    super(name, glyph, colour, description, requiredLevel, quality, slot,
        strength, agility, stamina, spirit, intellect, Equipment.ARMOUR);

    this.defence = (int)((double)defence * modifier.getChanges()[5]);
    this.itemLevel = computeItemLevel();
  }

  public int getDefence() {
    return defence;
  }

  @Override
  public String getProperties() {
    String output = super.getProperties();

    if (output != "") {
      output += ", ";
    }
    output += "DEF:" + defence;
    return output;
  }

  @Override
  protected int computeItemLevel() {
    return super.computeItemLevel() + defence / 3;
  }
}
