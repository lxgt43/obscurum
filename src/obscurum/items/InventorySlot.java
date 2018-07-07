package obscurum.items;

import java.lang.Math;
import obscurum.placeholders.NullItem;

/**
 * This models an inventory slot, which can hold an item of a certain type. The
 * amount of items of that type that can be held in an inventory slot depends
 * on the item's stack size.
 * @author Alex Ghita
 */
public class InventorySlot {
  private Item item;
  private int amount;

  /**
   * Default class constructor that initialises the slot with an item
   * placeholder.
   */
  public InventorySlot() {
    clear();
  }

  /**
   * Class constructor specifying an item type. One item of this type will be
   * added to the slot.
   * @param item
   */
  public InventorySlot(Item item) {
    this(item, 1);
  }

  /**
   * Class constructor specifying an item type and the number of such items to
   * be held in the slot.
   * @param item
   * @param amount
   */
  public InventorySlot(Item item, int amount) {
    set(item, amount);
  }

  /**
   * Gets the item held in the slot.
   * @return
   */
  public Item getItem() {
    return item;
  }

  /**
   * Gets the number of items held in the slot.
   * @return
   */
  public int getAmount() {
    return amount;
  }

  /**
   * Sets the item and the amount contained in this slot.
   * @param item
   * @param amount
   */
  public void set(Item item, int amount) {
    // Check for illegal arguments.
    if (item == null || item instanceof NullItem) {
      throw new IllegalArgumentException(
          "Item cannot be null. Use clear() to empty an inventory slot.");
    }
    if (amount < 1 || amount > item.getStackSize()) {
      throw new IllegalArgumentException("Amount " + amount +
          " must be between 1 and " + item.getStackSize() + ".");
    }

    this.item = item;
    this.amount = amount;
  }

  /**
   * Checks whether there is an item contained in the slot.
   * @return
   */
  public boolean isEmpty() {
    return item.isOfType(new NullItem());
  }

  /**
   * Gets the number of items that can be added to the slot.
   * @return
   */
  public int getAddableAmount() {
    return item.getStackSize() - amount;
  }

  /**
   * Adds a number of items of the same type as the one found in the slot.
   * @param amount
   */
  public void add(int amount) {
    // Check for illegal arguments.
    int addableAmount = getAddableAmount();
    if (addableAmount == 0) {
      throw new IllegalArgumentException("Slot is full.");
    }
    if (amount < 1 || amount > addableAmount) {
      throw new IllegalArgumentException("Amount " + amount +
          " must be between 1 and " + addableAmount + ".");
    }

    this.amount += amount;
  }

  /**
   * Removes a number of items from the slot.
   * @param amount
   */
  public void remove(int amount) {
    // Check for illegal arguments.
    if (this.amount == 0) {
      throw new IllegalArgumentException("Slot is empty.");
    }
    if (amount < 1 || amount > this.amount) {
      throw new IllegalArgumentException("Amount " + amount +
          " must be between 1 and " + this.amount + ".");
    }

    if (this.amount == amount) {
      clear();
    } else {
      this.amount -= amount;
    }
  }

  /**
   * Clears the inventory slot.
   */
  public void clear() {
    item = new NullItem();
    amount = 1;
  }

  /**
   * Gets a formatted string version of the item, containing its name and
   * amount in brackets.
   * @return
   */
  public String toString() {
    if (this.isEmpty()) {
      return "";
    }
    return item.getName() + " (" + amount + ")";
  }
}
