package obscurum.creatures.ai;

import java.awt.Point;
import java.lang.Math;
import java.util.ArrayList;
import obscurum.creatures.Creature;
import obscurum.creatures.Player;
import obscurum.creatures.ai.CreatureAI;
import obscurum.environment.Level;
import obscurum.environment.foreground.EmptyTile;
import obscurum.environment.foreground.ForegroundTile;

/**
 * This models an AI which chases the player whenever in sight.
 * @author Alex Ghita
 */
public class AggressiveAI extends CreatureAI{
  private boolean tracedLastTime;

  public AggressiveAI(Creature creature, int knowledgeType,
      ArrayList<ForegroundTile> transparentTiles) {
    super("Aggressive", creature, knowledgeType, transparentTiles);
    tracedLastTime = false;
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

    ArrayList<Point> seenPoints = getSeenPoints();
    boolean foundPlayer = false;

    for (Point p : seenPoints) {
      // Check if it's the player.
      if (creature.getLevel().getForegroundTile(p).getName().equals(
          "Player")) {
        creature.setTarget((Creature)
            (creature.getLevel().getForegroundTile(p)));
        foundPlayer = true;
        break;
      }
    }
    if (foundPlayer) {
      Point targetLocation = creature.getTarget().getLocation();
      // Check if the player is in the creature's attack range.
      if (canSee(targetLocation, creature.getAttackRange())) {
        creature.attackTarget();
      // Only trace the player every other turn.
      } else if (!tracedLastTime || Math.random() > 0.5) {
        tracedLastTime = true;

        ArrayList<Point> steps = getShortestPathTo(targetLocation);

        if (steps.size() > 1) {
          Point nextStep = steps.get(steps.size() - 2);
          creature.moveBy(nextStep.x - creature.getLocation().x,
              nextStep.y - creature.getLocation().y);
        }
      } else {
        tracedLastTime = false;
        // moveInRandomDirection();
      }
    } else {
      moveInRandomDirection();
    }
  }
}
