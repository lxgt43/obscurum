package obscurum.screens;

import obscurum.creatures.Creature;
import obscurum.creatures.Player;
import obscurum.environment.Level;
import obscurum.placeholders.NullCreature;

/**
 * This models an aim screen for ranged weapon attacks.
 * @author Alex Ghita
 */
public class RangedAttackAimScreen extends AimScreen {
  public RangedAttackAimScreen(Level[] world, Player player, int maxRange) {
    super(world, player, maxRange);
  }

  protected void performAction() {
    if (!player.getTarget().isOfType(new NullCreature())) {
      player.attackTarget();
    }
  }
}
