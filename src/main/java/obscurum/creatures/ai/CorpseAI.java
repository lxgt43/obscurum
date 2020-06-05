package obscurum.creatures.ai;

import java.awt.Point;
import java.util.ArrayList;
import obscurum.creatures.Creature;
import obscurum.creatures.ai.CreatureAI;
import obscurum.environment.Level;
import obscurum.environment.foreground.ForegroundTile;

/**
 * This models the behaviour of dead creatures.
 * @author Alex Ghita
 */
public class CorpseAI extends CreatureAI {
  private int turnsLeft;

  public CorpseAI(Creature creature) {
    super("Corpse", creature, CreatureAI.SIMPLE,
        new ArrayList<ForegroundTile>());
    turnsLeft = 50;
  }

  public int getTurnsLeft() {
    return turnsLeft;
  }

  @Override
  public void onEnter(Point p) {}

  @Override
  public void onUpdate() {
    while (creature.getCombatCooldown() > 0) {
      creature.decrementCombatCooldown();
    }
    if (creature.getInventory().countFilledSlots() == 0) {
      turnsLeft = Math.min(10, turnsLeft);
    }
    turnsLeft--;
  }
}
