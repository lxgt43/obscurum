package obscurum.creatures.ai;

import java.awt.Point;
import java.lang.Math;
import java.util.ArrayList;
import obscurum.creatures.Creature;
import obscurum.creatures.ai.CreatureAI;
import obscurum.environment.Level;
import obscurum.environment.foreground.EmptyTile;
import obscurum.environment.foreground.ForegroundTile;

/**
 * This models an idle AI.
 * @author Alex Ghita
 */
public class IdleAI extends CreatureAI{
  public IdleAI(Creature creature, int knowledgeType) {
    super("Idle", creature, knowledgeType, new ArrayList<ForegroundTile>());
  }

  @Override
  public void onEnter(Point p) {}

  @Override
  public void onUpdate() {
    super.onUpdate();
  }
}
