package obscurum.creatures.ai;

import obscurum.creatures.Creature;
import obscurum.environment.foreground.ForegroundTile;

import java.awt.*;
import java.util.ArrayList;

/**
 * This models an idle AI.
 * @author Alex Ghita
 */
public class IdleAI extends CreatureAI {
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
