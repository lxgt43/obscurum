package obscurum.items;

import java.awt.Color;
import java.lang.Math;
import obscurum.creatures.Creature;
import obscurum.display.Display;
import obscurum.display.DisplayTile;
import obscurum.screens.InventoryScreen;

/**
 * This models a piece of equipment, which is an item that can be worn by a
 * creature to receive some sort of bonus.
 * @author Alex Ghita
 */
public abstract class Equipment extends Item {
  public static final int NUM_OF_SLOTS = 6;
  public static final int HEAD = 0;
  public static final int CHEST = 1;
  public static final int HANDS = 2;
  public static final int WEAPON = 3;
  public static final int LEGS = 4;
  public static final int FEET = 5;
  public static final int SWORD = 0;
  public static final int BOW = 1;
  public static final int ARMOUR = 2;
  public static final Modifier[][] modifierList = {
    {new Modifier("Rusty", new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.6}),
     new Modifier("Sharp", new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.2}),
     new Modifier("Jagged", new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 0.8}),
     new Modifier("Serrated", new double[]{1.0, 0.7, 1.0, 1.0, 1.0, 1.5})},
    {new Modifier("Damaged", new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 0.6}),
     new Modifier("Reinforced", new double[]{1.3, 0.8, 1.0, 1.0, 1.0, 1.3}),
     new Modifier("Deadly", new double[]{1.2, 1.2, 1.0, 1.0, 1.2, 1.5}),
     new Modifier("Shoddy", new double[]{0.7, 0.8, 0.8, 1.0, 1.0, 0.8})},
    {new Modifier("Shiny", new double[]{1.0, 1.0, 1.0, 1.2, 1.0, 1.1}),
     new Modifier("Rusty", new double[]{1.0, 1.0, 0.8, 0.9, 1.0, 0.7})}
  };
  protected int slot;
  protected int[] attributes;
  protected DisplayTile[][] depiction;
  protected int itemLevel;
  protected int depictionType;
  protected Modifier modifier;

  /**
   * Class constructor specifying the usual item characteristics, as well as
   * the slot where this item is equipped, its attributes that will benefit
   * the wearer, and its depiction that will appear in the inventory screen.
   * @param name
   * @param glyph
   * @param colour
   * @param description
   * @param slot
   * @param attributes
   * @param depictionType
   */
  public Equipment(String name, char glyph, Color colour, String description,
      int requiredLevel, int quality, int slot, int strength, int agility,
      int stamina, int spirit, int intellect, int depictionType) {
    super(name, glyph, colour, description, 1, requiredLevel, quality);

    // Check for illegal arguments.
    if (slot < HEAD || slot > FEET) {
      throw new IllegalArgumentException("Slot " + slot + " must be between " +
          HEAD + " and " + FEET + ".");
    }
    if (depictionType < SWORD || depictionType > ARMOUR) {
      throw new IllegalArgumentException("Depiction type " + depictionType +
          " must be between " + SWORD + " and " + ARMOUR + ".");
    }

    this.slot = slot;
    this.depictionType = depictionType;
    modifier = modifierList[depictionType]
        [(int)(Math.random() * modifierList[depictionType].length)];
    this.name = modifier.getName() + " " + name;
    attributes = new int[Creature.NUM_OF_ATTRIBUTES];
    attributes[Creature.STRENGTH] =
        (int)((double)strength * modifier.getChanges()[0]);
    attributes[Creature.AGILITY] =
        (int)((double)agility * modifier.getChanges()[1]);
    attributes[Creature.STAMINA] =
        (int)((double)stamina * modifier.getChanges()[2]);
    attributes[Creature.SPIRIT] =
        (int)((double)spirit * modifier.getChanges()[3]);
    attributes[Creature.INTELLECT] =
        (int)((double)intellect * modifier.getChanges()[4]);
    itemLevel = computeItemLevel();
    if (strength == 0 && agility == 0 && stamina == 0 && spirit == 0 &&
        intellect == 0) {
      quality = Item.COMMON;
    }

    depiction = new DepictionGenerator(this).generateDepiction();
  }

  /**
   * Gets the slot where the item is equipped.
   * @return
   */
  public int getSlot() {
    return slot;
  }

  /**
   * Gets the list of attributes of the item.
   * @return
   */
  public int[] getAttributes() {
    return attributes;
  }

  /**
   * Gets the item's depiction.
   * @return
   */
  public DisplayTile[][] getDepiction() {
    return depiction;
  }

  /**
   * Gets the item's level.
   * @return
   */
  public int getItemLevel() {
    return itemLevel;
  }

  public int getDepictionType() {
    return depictionType;
  }

  public Modifier getModifier() {
    return modifier;
  }

  /**
   * Returns a string with the item's property, i.e. its attributes and slot.
   * @return
   */
  public String getProperties() {
    String output = "Slot:";
    String attributeString = Display.attributesToString(attributes);

    switch (slot) {
      case 0: output += "Head"; break;
      case 1: output += "Chest"; break;
      case 2: output += "Hands"; break;
      case 3: output += "Weapon"; break;
      case 4: output += "Legs"; break;
      default: output += "Feet"; break;
    }

    output = attributeString == "" ? output : output + ", " + attributeString;

    return output;
  }

  protected int computeItemLevel() {
    int itemLevel = 0;

    for (int i = Creature.STRENGTH; i <= Creature.INTELLECT; i++) {
      itemLevel += attributes[i];
    }

    return itemLevel;
  }

}
