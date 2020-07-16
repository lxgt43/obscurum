package obscurum.screens;

import obscurum.creatures.Player;
import obscurum.environment.Level;
import obscurum.placeholders.NullCreature;

/**
 * This models an aim screen for casting spells at a target.
 * @author Alex Ghita
 */
public class SpellAimScreen extends AimScreen {
  private int spellIndex;

  public SpellAimScreen(Level[] world, Player player, int maxRange,
      int spellIndex) {
    super(world, player, maxRange);
    this.spellIndex = spellIndex;
  }

  protected void performAction() {
    if (!player.getTarget().isOfType(new NullCreature()) &&
        player.canCastSpell(spellIndex)) {
      player.castSpell(spellIndex);
    }
  }
}
