package obscurum.creatures.abilities;

import obscurum.creatures.Creature;

/**
 * This models a spell, which has a mana cost, a cooldown, and an effect.
 * @author Alex Ghita
 */
public abstract class Spell {
  public static final int COMBAT_SPELL = 0;
  public static final int REST_SPELL = 1;
  public static final int ANYTIME_SPELL = 2;
  protected String name;
  protected String combatLogMessage;
  protected int level;
  protected int maxLevel;
  protected int manaCost;
  protected int maxRange;
  protected int cooldown;
  protected int currentCooldown;
  protected int spellType;

  public Spell(String name, int level, int maxLevel, int manaCost,
      int maxRange, int cooldown, int spellType) {
    this.name = name;
    this.level = level;
    this.maxLevel = maxLevel;
    this.manaCost = manaCost;
    this.maxRange = maxRange;
    this.cooldown = cooldown;
    this.currentCooldown = 0;
    this.spellType = spellType;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  public int getManaCost() {
    return manaCost;
  }

  public int getMaxRange() {
    return maxRange;
  }

  public int getCooldown() {
    return cooldown;
  }

  public int getCurrentCooldown() {
    return currentCooldown;
  }

  public int getSpellType() {
    return spellType;
  }

  public String getCombatLogMesssage() {
    return combatLogMessage;
  }

  public void levelUp() {
    if (level == maxLevel) {
      return;
    }
    level++;
    improveSpell();
  }

  public boolean inCooldown() {
    if (currentCooldown > 0) {
      return true;
    }
    return false;
  }

  public boolean isMaxLevel() {
    return level == maxLevel;
  }

  public void refresh() {
    if (currentCooldown > 0) {
      currentCooldown--;
    }
  }

  public void cast(Creature c, int casterSpellPower) {
    currentCooldown = cooldown;
    affect(c, casterSpellPower);
    combatLogMessage = "You've cast " + name + " on " + c.getName() + ". " +
        getEffectMessage(c);
  }

  public boolean isOfType(Spell spell) {
    return this.getClass().equals(spell.getClass());
  }

  public abstract String getEffectMessage(Creature c);

  public abstract String getDescription();

  public abstract String getLevelUpDescription();

  protected abstract void improveSpell();

  protected abstract void affect(Creature c, int casterSpellPower);
}
