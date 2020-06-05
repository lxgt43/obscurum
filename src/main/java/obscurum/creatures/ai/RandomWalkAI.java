package obscurum.creatures.ai;

import java.awt.Point;
import java.lang.Math;
import java.util.ArrayList;
import obscurum.creatures.Creature;
import obscurum.environment.foreground.EmptyTile;
import obscurum.environment.foreground.ForegroundTile;

/**
 * This models an AI that moves randomly.
 * @author Alex Ghita
 */
public class RandomWalkAI extends CreatureAI{
  public RandomWalkAI(Creature creature, int knowledgeType,
      ArrayList<ForegroundTile> transparentTiles) {
    super("Random Walk", creature, knowledgeType, transparentTiles);
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
    moveInRandomDirection();
  }
}
