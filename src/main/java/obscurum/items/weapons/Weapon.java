package obscurum.items.weapons;

import java.awt.Color;
import java.lang.Math;
import obscurum.display.DisplayTile;
import obscurum.items.Equipment;

/**
 * This models a weapon, which has an attack value and can be equipped in the
 * weapon slot.
 * @author Alex Ghita
 */
public abstract class Weapon extends Equipment {
  public static final int MELEE = 0;
  public static final int RANGED = 1;

  private int weaponType;
  private int minDamage;
  private int maxDamage;

  public Weapon(String name, char glyph, Color colour, String description,
      int requiredLevel, int quality, int strength, int agility, int stamina,
      int spirit, int intellect, int weaponType, int minDamage, int maxDamage,
      int depictionType) {
    super(name, glyph, colour, description, requiredLevel, quality,
        Equipment.WEAPON, strength, agility, stamina, spirit, intellect,
        depictionType);

    // Check for illegal arguments.
    if (weaponType != MELEE && weaponType != RANGED) {
      throw new IllegalArgumentException("Weapon type " + weaponType +
          " must be " + MELEE + " or " + RANGED + ".");
    }
    if (minDamage < 1) {
      throw new IllegalArgumentException("Minimum damage " + minDamage +
          " must be at least 1.");
    }
    if (minDamage > maxDamage) {
      throw new IllegalArgumentException("Minimum damage " + minDamage +
          " must not be greater than maximum damage " + maxDamage + ".");
    }

    this.weaponType = weaponType;
    this.minDamage = (int)((double)minDamage * modifier.getChanges()[5]);
    this.maxDamage = (int)((double)maxDamage * modifier.getChanges()[5]);
    this.itemLevel = computeItemLevel();
  }

  public int dealDamage() {
    return (int)(Math.random() * (maxDamage - minDamage)) + minDamage;
  }

  public int getWeaponType() {
    return weaponType;
  }

  @Override
  public String getProperties() {
    String output = super.getProperties();

    if (output != "") {
      output += ", ";
    }
    output += "DMG:" + minDamage + "-" + maxDamage;
    return output;
  }

  @Override
  protected int computeItemLevel() {
    return super.computeItemLevel() + (minDamage + maxDamage) / 5;
  }
}
