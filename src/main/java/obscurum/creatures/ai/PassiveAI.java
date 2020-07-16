package obscurum.creatures.ai;

import obscurum.creatures.Creature;
import obscurum.environment.Level;
import obscurum.environment.foreground.EmptyTile;
import obscurum.environment.foreground.ForegroundTile;

import java.awt.*;
import java.util.ArrayList;

/**
 * This models the behaviour of creatures that attack only when provoked.
 * @author Alex Ghita
 */
public class PassiveAI extends CreatureAI{
  public PassiveAI(Creature creature, int knowledgeType,
      ArrayList<ForegroundTile> transparentTiles) {
    super("Passive", creature, knowledgeType, transparentTiles);
  }

  @Override
  public void onEnter(Point p) {
    if (creature.getLevel().isForegroundOfType(p, new EmptyTile())) {
      creature.move(p);
    }
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    Creature target = creature.getTarget();
    Level level = creature.getLevel();

    for (int x = -1; x <= 1; x++) {
      for (int y = -1 ; y <= 1; y++) {
        Point test = new Point(creature.getLocation().x + x,
            creature.getLocation().y + y);
        if (!level.isInBounds(test) ||
            !level.getForegroundTile(test).getName().equals("Player")) {
          continue;
        }
        creature.setTarget((Creature)(level.getForegroundTile(test)));
      }
    }

    if (target.getName().equals("Player")) {
      if (canSee(target.getLocation(), creature.getAttackRange())) {
        creature.attackTarget();
      } else {
        ArrayList<Point> steps = getShortestPathTo(target.getLocation());

        if (steps.size() > 1) {
          Point nextStep = steps.get(steps.size() - 2);
          creature.moveBy(nextStep.x - creature.getLocation().x,
              nextStep.y - creature.getLocation().y);
        }
      }
    } else {
      moveInRandomDirection();
    }
  }
}
