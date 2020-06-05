package obscurum.items;

/**
 * This models a modifier, i.e. an attribute added to an item's name, which
 * also influences its properties.
 * @author Alex Ghita
 */
public class Modifier {
  private String name;
  private double[] changes;

  public Modifier(String name, double[] changes) {
    this.name = name;
    this.changes = changes;
  }

  public String getName() {
    return name;
  }

  public double[] getChanges() {
    return changes;
  }
}
