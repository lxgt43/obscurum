package obscurum.creatures.util;

import obscurum.creatures.Creature;
import obscurum.items.Equipment;
import obscurum.items.Item;
import obscurum.placeholders.NullEquipment;

/**
 * This stores a list of a creature's equipment slots and items equipped in
 * those slots.
 * @author Alex Ghita
 */
public class EquipmentList {
  private Equipment[] slots;

  public EquipmentList() {
    slots = new Equipment[Equipment.NUM_OF_SLOTS];

    for (int i = 0; i < Equipment.NUM_OF_SLOTS; i++) {
      slots[i] = (Equipment)new NullEquipment();
    }
  }

  public Equipment getEquipment(int index) {
    return slots[index];
  }

  public void setEquipment(Item equipment, int index) {
    // Check for illegal arguments.
    if (equipment == null) {
      throw new IllegalArgumentException("Equipment cannot be null.");
    }
    if (!(equipment instanceof Equipment)) {
      throw new IllegalArgumentException("Item must be equippable.");
    }
    slots[index] = (Equipment)equipment;
  }

  public int[] getAttributes() {
    int[] attributes = new int[Creature.NUM_OF_ATTRIBUTES];

    for (int i = 0; i < Equipment.NUM_OF_SLOTS; i++) {
      for (int j = 0; j < Creature.NUM_OF_ATTRIBUTES; j++) {
        attributes[j] += slots[i].getAttributes()[j];
      }
    }

    return attributes;
  }
}
