package obscurum.environment.foreground;

import java.awt.Color;
import obscurum.environment.Tile;

/**
 * This models a foreground tile, which can sit on background tiles.
 * Foreground tiles can be anything ranging from walls or other inanimate
 * obstacles to players and other creatures.
 * @author Alex Ghita
 */
public abstract class ForegroundTile extends Tile {
  protected int health;
  protected int baseHealth;
  protected int maxHealth;
  protected int armour;
  protected boolean invulnerable;
  protected boolean opaque;

  /**
   * Class constructor specifying the standard tile attributes, as well as
   * health, armour, whether the tile is imprevious to attacks, and whether it
   * obstructs vision.
   * @param name
   * @param glyph
   * @param foregroundColour
   * @param health
   * @param armour
   * @param invulnerable
   * @param opaque
   */
  public ForegroundTile(String name, char glyph, Color foregroundColour,
      int baseHealth, int armour, boolean invulnerable, boolean opaque) {
    super(name, glyph, foregroundColour);

    // Check for illegal arguments.
    if (baseHealth < 1) {
      throw new IllegalArgumentException("Base health " + baseHealth +
          " must be at least 1.");
    }
    if (armour < 0) {
      throw new IllegalArgumentException("Armour " + armour +
          " must be non-negative.");
    }

    this.health = baseHealth;
    this.baseHealth = baseHealth;
    this.maxHealth = baseHealth;
    this.armour = armour;
    this.invulnerable = invulnerable;
    this.opaque = opaque;
  }

  /**
   * Gets the tile's health.
   * @return
   */
  public int getHealth() {
    return health;
  }

  public int getBaseHealth() {
    return baseHealth;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  /**
   * Gets the tile's armour.
   * @return
   */
  public int getArmour() {
    return armour;
  }

  /**
   * Returns true if the tile cannot be affected by attacks, or false
   * otherwise.
   * @return
   */
  public boolean isInvulnerable() {
    return invulnerable;
  }

  /**
   * Returns true if the tile is opaque, or false otherwise. Opaque means that
   * creatures cannot see past this tile in a straight line.
   * @return
   */
  public boolean isOpaque() {
    return opaque;
  }

  /**
   * Sets the tile's health.
   * @param health
   */
  public void setHealth(int health) {
    if (health < 0) {
      throw new IllegalArgumentException("Health " + health +
          " must not be negative.");
    }
    this.health = health;
  }

  /**
   * Sets the tile's armour.
   * @param armour
   */
  public void setArmour(int armour) {
    if (armour < 0) {
      throw new IllegalArgumentException("Armour " + armour +
          " must be non-negative.");
    }
  }

  /**
   * Sets the tile's invulnerability status.
   * @param invulnerable
   */
  public void setInvulnerable(boolean invulnerable) {
    this.invulnerable = invulnerable;
  }

  /**
   * Makes the tile opaque or transparent.
   * @param opaque
   */
  public void setOpaque(boolean opaque) {
    this.opaque = opaque;
  }

  /**
   * Deals damage to the tile, and returns the damage dealt.
   * @param damage
   * @return amount of damage dealt; might be less than the argument: if the
   *         target is invulnerable, it will take no damage; if it's health is
   *         lower than the argument, its health will not drop below 0
   */
  public int getDamaged(int damage) {
    if (invulnerable) {
      return 0;
    }

    int oldHealth = health;
    health = Math.max(0, health - damage);
    return oldHealth - health;
  }

  /**
   * Heals the tile for a given amount. The new health cannot exceed its
   * maximum health.
   * @param healing
   * @param return how much the target was healed
   */
  public int getHealed(int healing) {
    int oldHealth = health;
    health = Math.min(maxHealth, health + healing);
    return health - oldHealth;
  }
}
