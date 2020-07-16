package obscurum.creatures;

import obscurum.creatures.ai.CreatureAI;
import obscurum.creatures.ai.IdleAI;
import obscurum.creatures.util.AIStatistics;
import obscurum.display.Display;
import obscurum.environment.Level;
import obscurum.environment.foreground.EmptyTile;
import obscurum.factories.CreatureFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This models a spawner, i.e. an invulnerable creature that constantly creates
 * other creatures.
 * @author Alex Ghita
 */
public class Spawner extends Creature {
  private ArrayList<String> creatures;
  private ArrayList<Creature> spawnedCreatures;
  private int creaturePowerLevel;
  private int maxCreatureCount;
  private int spawnCounter;
  private CreatureFactory factory;
  private HashMap<String, AIStatistics> stats;

  public Spawner(Level level, Point location, String[] creatures,
      int creaturePowerLevel, int maxCreatureCount, CreatureFactory factory) {
    super("Spawner", (char)234, Display.BRIGHT_BLUE, level, location, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1);
    setInvulnerable(true);

    this.creatures = new ArrayList<String>(Arrays.asList(creatures));
    this.creaturePowerLevel = creaturePowerLevel;
    this.maxCreatureCount = maxCreatureCount;
    this.factory = factory;
    ai = new IdleAI(this, CreatureAI.OMNISCIENT);
    spawnedCreatures = new ArrayList<Creature>();
    spawnCounter = 0;

    stats = new HashMap<String, AIStatistics>();
    String[] aiNames = {"Aggressive", "Hit And Run", "Idle", "Passive",
        "Random Walk", "Teleport"};

    for (String ai : aiNames) {
      stats.put(ai, new AIStatistics());
    }
  }

  public void update() {
    if (spawnCounter == 0 && spawnedCreatures.size() < maxCreatureCount) {
      spawnCreature();
    }
    spawnCounter = (spawnCounter + 1) % 10;

    for (int i = spawnedCreatures.size() - 1; i >= 0; i--) {
      if (!spawnedCreatures.get(i).isAlive()) {
        Creature c = spawnedCreatures.get(i);
        if (c == null) {
          continue;
        }
        String ai = c.getPreviousAIName();
        AIStatistics newStats = stats.get(ai);
        newStats.addStats(c.getTurnsAlive(), c.getTurnsAliveNearPlayer(),
            c.getDamageDealt(), c.getTimesAttacked());
        stats.replace(ai, newStats);
        spawnedCreatures.remove(i);
      }
    }
  }

  private void spawnCreature() {
    ArrayList<Point> locations = new ArrayList<Point>();
    HashMap<String, Double> aiOptions = new HashMap<String, Double>();
    double totalScore = 0.0;
    boolean haveStats = false;

    for (String ai : stats.keySet()) {
      if (stats.containsKey(ai)) {
        totalScore += stats.get(ai).getScore();
      }
    }
    if (totalScore > 0) {
      for (String ai : stats.keySet()) {
        if (stats.containsKey(ai)) {
          aiOptions.put(ai, stats.get(ai).getScore() / totalScore);
          // System.out.println(ai + ":" + stats.get(ai).getScore() / totalScore);
        }
      }
      haveStats = true;
    }

    for (int x = -2; x <= 2; x++) {
      for (int y = -2; y <= 2; y++) {
        Point test = new Point(location.x + x, location.y + y);
        if (!level.isInBounds(test) ||
            !level.isForegroundOfType(test, new EmptyTile())) {
          continue;
        }
        locations.add(test);
      }
    }
    String creature = creatures.get((int)(Math.random() * creatures.size()));
    Point spawnPoint = locations.get((int)(Math.random() * locations.size()));
    Creature c;

    switch (creature) {
      case "Zombie":
        c = haveStats ? factory.newZombie(creaturePowerLevel, spawnPoint,
            aiOptions) : factory.newZombie(creaturePowerLevel, spawnPoint);
        break;
      default:
        c = haveStats ? factory.newGoblin(creaturePowerLevel, spawnPoint,
            aiOptions) : factory.newGoblin(creaturePowerLevel, spawnPoint);
    }
    spawnedCreatures.add(c);
  }

  @Override
  public void powerUp() {
    creaturePowerLevel += 10;
    maxCreatureCount += 2;
  }

  @Override
  protected void generateInventory() {}
}
