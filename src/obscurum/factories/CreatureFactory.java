package obscurum.factories;

import java.awt.Point;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import obscurum.creatures.*;
import obscurum.creatures.ai.*;
import obscurum.display.asciiPanel.AsciiPanel;
import obscurum.environment.Level;
import obscurum.environment.foreground.EmptyTile;
import obscurum.environment.foreground.ForegroundTile;

/**
 * This models a creature factory, which has several methods to spawn all the
 * available creatures in the game.
 * @author Alex Ghita
 */
public class CreatureFactory {
  private Level level;

  public CreatureFactory(Level level) {
    this.level = level;
  }

  public Player newPlayer() {
    ArrayList<ForegroundTile> transparentTiles =
        new ArrayList<ForegroundTile>();
    transparentTiles.add(new EmptyTile());
    Player player = new Player(level, level.getRandomEmptyLocation(), 16, 5,
        100, 10, 0, 10, 5, 5, 5, 5);
    PlayerAI ai = new PlayerAI(player, 1, transparentTiles);

    level.add(player);

    return player;
  }

  public Zombie newZombie(int powerLevel) {
    return newZombie(powerLevel, level.getRandomEmptyLocation());
  }

  public Zombie newZombie(int powerLevel, Point spawnPoint) {
    HashMap<String, Double> aiOptions = new HashMap<String, Double>();
    aiOptions.put("Idle", 0.1);
    aiOptions.put("Random Walk", 0.1);
    aiOptions.put("Passive", 0.2);
    aiOptions.put("Aggressive", 0.2);
    aiOptions.put("Hit And Run", 0.2);
    aiOptions.put("Teleport", 0.2);
    return newZombie(powerLevel, spawnPoint, aiOptions);
  }

  public Zombie newZombie(int powerLevel, Point spawnPoint,
      HashMap<String, Double> aiOptions) {
    Zombie zombie = new Zombie(level, spawnPoint, powerLevel, 5,
        powerLevel * 5 + 5, powerLevel * 5, 5 + (int)(powerLevel * 3.5),
        5 + (int)(powerLevel * 3.5), 5 + (int)(powerLevel * 3.5),
        5 + (int)(powerLevel * 3.5), 5 + (int)(powerLevel * 3.5));
    ArrayList<ForegroundTile> transparentTiles =
        new ArrayList<ForegroundTile>();
    transparentTiles.add(new EmptyTile());
    transparentTiles.add(zombie);
    CreatureAI ai = pickAI(zombie, aiOptions, transparentTiles);

    zombie.setAI(ai);
    level.add(zombie);

    return zombie;
  }

  public Goblin newGoblin(int powerLevel) {
    return newGoblin(powerLevel, level.getRandomEmptyLocation());
  }

  public Goblin newGoblin(int powerLevel, Point spawnPoint) {
    HashMap<String, Double> aiOptions = new HashMap<String, Double>();
    aiOptions.put("Idle", 0.1);
    aiOptions.put("Random Walk", 0.1);
    aiOptions.put("Passive", 0.2);
    aiOptions.put("Aggressive", 0.2);
    aiOptions.put("Hit And Run", 0.3);
    aiOptions.put("Teleport", 0.1);
    return newGoblin(powerLevel, spawnPoint, aiOptions);
  }

  public Goblin newGoblin(int powerLevel, Point spawnPoint,
      HashMap<String, Double> aiOptions) {
    Goblin goblin = new Goblin(level, spawnPoint, powerLevel,
        5 + (int)(powerLevel * 0.1), powerLevel * 8, powerLevel + 2,
        7 + (int)(powerLevel * 2.25), 7 + (int)(powerLevel * 2.25),
        7 + (int)(powerLevel * 2.25), 7 + (int)(powerLevel * 2.25),
        7 + (int)(powerLevel * 2.25));
    ArrayList<ForegroundTile> transparentTiles =
        new ArrayList<ForegroundTile>();
    transparentTiles.add(new EmptyTile());
    transparentTiles.add(goblin);
    CreatureAI ai = pickAI(goblin, aiOptions, transparentTiles);

    goblin.setAI(ai);
    level.add(goblin);

    return goblin;
  }

  private CreatureAI pickAI(Creature user, HashMap<String, Double> aiOptions,
      ArrayList<ForegroundTile> transparentTiles) {
    double chance = Math.random();
    double totalChance = 0;
    String aiName = "";

    for (String aiOption : aiOptions.keySet()) {
      totalChance += aiOptions.get(aiOption);
      if (chance < totalChance) {
        aiName = aiOption;
        break;
      }
    }
    switch (aiName) {
      case "Aggressive":
        return new AggressiveAI(user, CreatureAI.SIMPLE, transparentTiles);
      case "Hit And Run":
        return new HitAndRunAI(user, CreatureAI.SIMPLE, transparentTiles);
      case "Idle":
        return new IdleAI(user, CreatureAI.SIMPLE);
      case "Passive":
        return new PassiveAI(user, CreatureAI.SIMPLE, transparentTiles);
      case "Random Walk":
        return new RandomWalkAI(user, CreatureAI.SIMPLE, transparentTiles);
      default:
        return new TeleportAI(user, CreatureAI.SIMPLE, transparentTiles);
    }
  }
}
