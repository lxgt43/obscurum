package obscurum.creatures;

import java.awt.Point;
import obscurum.creatures.LootTableEntry;
import obscurum.creatures.ai.CreatureAI;
import obscurum.creatures.ai.IdleAI;
import obscurum.display.Display;
import obscurum.environment.Level;
import obscurum.items.AmuletOfPower;
import obscurum.items.Inventory;
import obscurum.items.Item;
import obscurum.items.RottenFlesh;
import obscurum.items.SpellTome;
import obscurum.items.armour.*;
import obscurum.items.weapons.Sword;
import obscurum.items.weapons.Bow;

/**
 * This models a treasure chest, i.e. a static, invulnerable creature which can
 * be looted.
 * @author Alex Ghita
 */
public class TreasureChest extends Creature {
  public TreasureChest(Level level, Point location, int powerLevel) {
    super("Treasure Chest", (char)10, Display.BROWN, level, location,
        powerLevel, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1);
    setInvulnerable(true);
    ai = new IdleAI(this, CreatureAI.OMNISCIENT);
    generateInventory();
  }

  // Special constructor for final treasure chest.
  public TreasureChest(Level level, Point location) {
    super("Treasure Chest", (char)10, Display.YELLOW, level, location, 1, 1, 1,
        1, 1, 0, 0, 1, 1, 1, 1, 1);
    setInvulnerable(true);
    ai = new IdleAI(this, CreatureAI.OMNISCIENT);
    inventory = new Inventory(1, this);
    inventory.addItem(new AmuletOfPower());
  }

  @Override
  public void generateInventory() {
    int itemLevel = Math.max(1, attributes[POWER_LEVEL]);
    inventory = new Inventory(5, this);
    LootTableEntry[] lootTables = {
      new LootTableEntry(((Item)new Sword(itemLevel)), 0.225, 1, 1, true),
      new LootTableEntry(((Item)new Bow(itemLevel)), 0.225, 1, 1, true),
      new LootTableEntry(((Item)new SpellTome()), 0.3, 1, 1, true),
      new LootTableEntry(((Item)new IronBoots(itemLevel)), 0.05, 1, 1, true),
      new LootTableEntry(((Item)new IronChestplate(itemLevel)), 0.05, 1, 1,
      true),
      new LootTableEntry(((Item)new IronHelmet(itemLevel)), 0.05, 1, 1, true),
      new LootTableEntry(((Item)new IronLeggings(itemLevel)), 0.05, 1, 1,
      true),
      new LootTableEntry(((Item)new LeatherGloves(itemLevel)), 0.05, 1, 1,
      true)};
    LootTable lootTable = new LootTable(lootTables);
    lootTable.fillInventory(inventory);
  }

  @Override
  public void powerUp() {}
}
