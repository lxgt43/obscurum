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
 * This models an AI which teleports near the target if it's out of its attack
 * range.
 * @author Alex Ghita
 */
public class TeleportAI extends CreatureAI{
  private int teleportCounter;
  private Point oldLocation;

  public TeleportAI(Creature creature, int knowledgeType,
      ArrayList<ForegroundTile> transparentTiles) {
    super("Teleport", creature, knowledgeType, transparentTiles);
    teleportCounter = 0;
    oldLocation = creature.getLocation();
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
    if (foundPlayer && teleportCounter == 0) {
      Point targetLocation = creature.getTarget().getLocation();
      // Check if the player is in the creature's attack range.
      if (canSee(targetLocation, creature.getAttackRange())) {
        creature.attackTarget();
        teleportCounter = 1;
      } else {
        ArrayList<Point> teleportPoints = new ArrayList<Point>();

        for (int x = -1; x <= 1; x++) {
          for (int y = -1; y <= 1; y++) {
            if (x != 0 && y != 0 || x == 0 && y == 0) {
              continue;
            }
            Point test = new Point(creature.getTarget().getLocation().x + x,
                creature.getTarget().getLocation().y + y);
            if (!creature.getLevel().isInBounds(test) || !creature.getLevel().
                isForegroundOfType(test, new EmptyTile())) {
              continue;
            }
            teleportPoints.add(test);
          }
        }
        if (teleportPoints.isEmpty()) {
          moveInRandomDirection();
        } else {
          oldLocation = creature.getLocation();
          creature.move(teleportPoints.get((int)(Math.random() *
              teleportPoints.size())));
        }
      }
    } else {
      moveInRandomDirection();
      teleportCounter = (teleportCounter + 1) % 6;
    }
  }
}
