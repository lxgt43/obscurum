package obscurum.creatures;

import java.lang.Math;
import obscurum.items.Item;

/**
 * This models an entry in a loot table, i.e. a tuple consisting of an item,
 * a drop chance, and quantity bounds.
 * @author Alex Ghita
 */
public class LootTableEntry {
  Item item;
  double chance;
  int minAmount;
  int maxAmount;
  boolean uniqueSpawn;
  boolean spawned;

  public LootTableEntry(Item item, double chance, int minAmount,
      int maxAmount, boolean uniqueSpawn) {
    // min and max amount should be <= stack size
    this.item = item;
    this.chance = chance;
    this.minAmount = minAmount;
    this.maxAmount = maxAmount;
    this.uniqueSpawn = uniqueSpawn;
    this.spawned = false;
  }

  public Item getItem() {
    return item;
  }

  public double getChance() {
    return chance;
  }

  public int getRandomAmount() {
    return minAmount + (int)(Math.random() * (maxAmount - minAmount + 1));
  }

  public boolean isUniqueSpawn() {
    return uniqueSpawn;
  }

  public boolean isSpawned() {
    return spawned;
  }

  public void setSpawned() {
    spawned = true;
  }
}
