package obscurum.placeholders;

import obscurum.display.Display;
import obscurum.items.Item;

/**
 * This models an item placeholder.
 * @author Alex Ghita
 */
public class NullItem extends Item {
  /**
   * Class constructor specifying placeholder values.
   */
  public NullItem() {
    super("Null Item", 'x', Display.BLACK, "", 1, 1, 0);
  }
}
