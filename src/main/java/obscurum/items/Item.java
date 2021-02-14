package obscurum.items;

import java.awt.Color;

import obscurum.creatures.Creature;
import obscurum.display.terminal.AsciiPanel;

/**
 * This models an item, which can be found in a creature's inventory and used
 * in some way by it. Players can also loot items from other creatures.
 * @author Alex Ghita
 */
public abstract class Item {
  public static final int COMMON = 0;
  public static final int UNCOMMON = 1;
  public static final int RARE = 2;
  public static final int EPIC = 3;
  protected String name;
  protected char glyph;
  protected Color colour;
  protected String description;
  protected int stackSize;
  protected int requiredLevel;
  protected int quality;

  /**
   * Class constructor specifying the item's appearance and basic attributes.
   * @param name
   * @param glyph the character to represent the item in the player's inventory
   * @param colour
   * @param description an optional brief description of what the item does
   * @param stackSize how many items of the same type can be placed in a single
   *                  inventory slot
   * @param requiredLevel required level to use the item
   * @param quality
   */
  public Item(String name, char glyph, Color colour, String description,
      int stackSize, int requiredLevel, int quality) {
    // Check for illegal arguments.
    if (name == "" || name == null) {
      throw new IllegalArgumentException("Name cannot be empty.");
    }
    if (glyph == ' ') {
      throw new IllegalArgumentException("Glyph cannot be empty.");
    }
    if (glyph < 0 || glyph >= AsciiPanel.NUM_OF_GLYPHS) {
      throw new IllegalArgumentException("Glyph must be in range 0 - " +
          AsciiPanel.NUM_OF_GLYPHS + ".");
    }
    if (colour == null) {
      throw new IllegalArgumentException("Colour cannot be null.");
    }
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null.");
    }
    if (stackSize < 1) {
      throw new IllegalArgumentException(
          "Stack size " + stackSize + " must be at least 1.");
    }
    if (requiredLevel < 1 || requiredLevel > Creature.MAX_POWER_LEVEL) {
      throw new IllegalArgumentException("Required level " + requiredLevel +
          " must be between 1 and " + Creature.MAX_POWER_LEVEL + ".");
    }
    if (quality < COMMON || quality > EPIC) {
      throw new IllegalArgumentException("Item quality " + quality +
          " must be between " + COMMON + " and " + EPIC + ".");
    }

    this.name = name;
    this.glyph = glyph;
    this.colour = colour;
    this.description = description;
    this.stackSize = stackSize;
    this.requiredLevel = requiredLevel;
    this.quality = quality;
  }

  /**
   * Gets the item's name.
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the item's glyph.
   * @return
   */
  public char getGlyph() {
    return glyph;
  }

  /**
   * Gets the item's colour.
   * @return
   */
  public Color getColour() {
    return colour;
  }

  /**
   * Gets the item's description.
   * @return
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the item's stack size.
   * @return
   */
  public int getStackSize() {
    return stackSize;
  }

  /**
   * Gets the required level to use the item.
   * @return
   */
  public int getRequiredLevel() {
    return requiredLevel;
  }

  /**
   * Gets the item's quality.
   * @return
   */
  public int getQuality() {
    return quality;
  }

  /**
   * Checks whether the item is of the same type as the argument item.
   * @param item
   * @return
   */
  public boolean isOfType(Item item) {
    // Check for illegal arguments.
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }

    return this.getClass().equals(item.getClass());
  }
}
