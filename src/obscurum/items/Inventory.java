package obscurum.items;

import java.util.ArrayList;
import obscurum.creatures.Creature;
import obscurum.items.InventorySlot;
import obscurum.placeholders.NullCreature;
import obscurum.placeholders.NullItem;

/**
 * This models a creature's inventory, which is a list of all items held by a
 * creature. The list is made of inventory slots, which can hold one item type
 * each, and the amount of such items that can be held in one slot depends on
 * the item's type.
 * @author Alex Ghita
 */
public class Inventory {
  private ArrayList<InventorySlot> slots;
  private int size;
  private Creature owner;

  /**
   * Class constructor. Initialises an empty inventory of the given size and
   * assigns it to the given creature.
   * @param size
   * @param owner
   */
  public Inventory(int size, Creature owner) {
    // Check for illegal arguments.
    if (size < 1) {
      throw new IllegalArgumentException("Inventory size " + size +
          " must be at least 1.");
    }
    if (owner == null || owner.isOfType(new NullCreature())) {
      throw new IllegalArgumentException("Inventory owner cannot be null.");
    }

    this.size = size;
    this.owner = owner;
    slots = new ArrayList<InventorySlot>();
    for (int i = 0; i < size; i++) {
      slots.add(new InventorySlot());
    }
  }

  /**
   * Gets the inventory slot at the given index.
   * @param index
   * @return
   */
  public InventorySlot getSlot(int index) {
    return slots.get(index);
  }

  /**
   * Gets the size of the inventory
   * @return
   */
  public int getSize() {
    return size;
  }

  /**
   * Gets the item found at the given index.
   * @param index
   * @return
   */
  public Item getItem(int index) {
    return getSlot(index).getItem();
  }

  /**
   * Gets the number of items found at the given index.
   * @param index
   * @return
   */
  public int getAmount(int index) {
    return getSlot(index).getAmount();
  }

  /**
   * Sets the inventory slot found at the given index.
   * @param index
   * @param item
   * @param amount
   */
  public void setSlot(int index, Item item, int amount) {
    getSlot(index).set(item, amount);
  }

  /**
   * Gets the number of inventory slots that contain items.
   * @return
   */
  public int countFilledSlots() {
    int count = 0;

    for (int i = 0; i < slots.size(); i++) {
      if (!getSlot(i).isEmpty()) {
        count++;
      }
    }
    return count;
  }

  /**
   * Counts the number of items of the given type found in the inventory.
   * @param item
   * @return
   */
  public int countItemsOfType(Item item) {
    // Check for illegal arguments.
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }

    int amount = 0;

    for (InventorySlot slot : slots) {
      if (slot.getItem().isOfType(item)) {
        amount += slot.getAmount();
      }
    }

    return amount;
  }

  /**
   * Gets the maximum number of items of the given type that can be added to
   * the inventory.
   * @param item
   * @return
   */
  public int getAddableAmount(Item item) {
    int addableAmount = 0;

    for (InventorySlot slot : slots) {
      if (slot.isEmpty()) {
        addableAmount += item.getStackSize();
      } else if (slot.getItem().isOfType(item)) {
        addableAmount += slot.getAddableAmount();
      }
    }

    return addableAmount;
  }

  /**
   * Adds a single item to the inventory.
   * @param item
   */
  public void addItem(Item item) {
    addItem(item, 1);
  }

  /**
   * Adds a number of items of the given type to the inventory.
   * @param item
   * @param amount
   */
  public void addItem(Item item, int amount) {
    // Check for illegal arguments.
    int addableAmount = getAddableAmount(item);
    if (addableAmount == 0) {
      throw new IllegalArgumentException("Inventory is full.");
    }
    if (amount < 1 || amount > addableAmount) {
      throw new IllegalArgumentException("Amount " + amount +
          "must be between 1 and " + addableAmount + ".");
    }

    for (InventorySlot slot : slots) {
      // If all items have been added, stop.
      if (amount == 0) {
        break;
      }

      /**
       * If the current slot contains items of this type and can hold more, or
       * if it is empty, add as many items as possible to it.
       */
      int added = 0;
      if (slot.isEmpty()) {
        added = Math.min(amount, item.getStackSize());
        slot.set(item, added);
      } else if (slot.getItem().isOfType(item)) {
        added = Math.min(amount, slot.getAddableAmount());
        if (added > 0) {
          slot.add(added);
        }
      }
      amount -= added;
    }
  }

  /**
   * Removes one item of the given type.
   * @param item
   */
  public void removeItem(Item item) {
    removeItem(item, 1);
  }

  /**
   * Removes a given number of items of a given type.
   * @param item
   * @param amount
   */
  public void removeItem(Item item, int amount) {
    // Check for illegal arguments.
    int removableAmount = countItemsOfType(item);
    if (removableAmount == 0) {
      throw new IllegalArgumentException("There are no " + item.getName() +
          " items left.");
    }
    if (amount < 1 || amount > removableAmount) {
      throw new IllegalArgumentException("Amount " + amount +
          "must be between 1 and " + removableAmount + ".");
    }

    for (InventorySlot slot : slots) {
      // If all the items have been removed, stop.
      if (amount == 0) {
        break;
      }
      // If the slot contains this item type, remove as much as possible.
      if (slot.getItem().isOfType(item)) {
        int removed = Math.min(amount, slot.getAmount());
        slot.remove(removed);
        amount -= removed;
      }
    }
  }

  public void increaseSize() {
    size++;
    slots.add(new InventorySlot());
  }
}
