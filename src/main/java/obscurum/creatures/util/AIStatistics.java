package obscurum.creatures.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * This stores statistics about a certain AI type.
 * @author Alex Ghita
 */
public class AIStatistics {
  private double turnsAlive;
  private double turnsAliveNearPlayer;
  private double damageDealt;
  private double timesAttacked;
  private int samples;

  public AIStatistics() {
    turnsAlive = 0.0;
    turnsAliveNearPlayer = 0.0;
    damageDealt = 0.0;
    timesAttacked = 0.0;
    samples = 0;
  }

  public double getTurnsAlive() {
    return turnsAlive;
  }

  public double getTurnsAliveNearPlayer() {
    return turnsAliveNearPlayer;
  }

  public double getDamageDealt() {
    return damageDealt;
  }

  public double getTimesAttacked() {
    return timesAttacked;
  }

  public double getScore() {
    return turnsAlive * 0.05 + turnsAliveNearPlayer * 0.1 + damageDealt * 0.3 +
        timesAttacked * 0.55;
  }

  public void addStats(int turnsAlive, int turnsAliveNearPlayer,
      int damageDealt, int timesAttacked) {
    int oldSamples = samples;

    samples++;
    this.turnsAlive = (this.turnsAlive * oldSamples + turnsAlive) / samples;
    this.turnsAliveNearPlayer = (this.turnsAliveNearPlayer * oldSamples +
        turnsAliveNearPlayer) / samples;
    this.damageDealt = (this.damageDealt * oldSamples + damageDealt) / samples;
    this.timesAttacked = (this.timesAttacked * oldSamples + timesAttacked) /
        samples;
  }
}
