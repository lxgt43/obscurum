package obscurum.creatures;

import obscurum.items.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This models a loot table, i.e. what items a creature can drop at death, and
 * the probabilities and quantities associated with these items.
 * @author Alex Ghita
 */
public class LootTable {
  ArrayList<LootTableEntry> options;

  public LootTable(LootTableEntry[] options) {
    // all chances in a table should sum up to 1
    this.options = new ArrayList<LootTableEntry>(Arrays.asList(options));
  }

  public void fillInventory(Inventory inv) {
    fillInventory(inv, (int)(Math.random() * (inv.getSize() + 1)));
  }

  public void fillInventory(Inventory inv, int numOfSlots) {
    for (int i = 0; i < numOfSlots; i++) {
      double chance = Math.random();
      double cumulatedChance = 0;
      for (LootTableEntry entry : options) {
        cumulatedChance += entry.getChance();
        if (entry.isUniqueSpawn() && entry.isSpawned()) {
          continue;
        }
        if (chance < cumulatedChance) {
          inv.setSlot(i, entry.getItem(), entry.getRandomAmount());
          entry.setSpawned();
          break;
        }
      }
    }
  }
}
