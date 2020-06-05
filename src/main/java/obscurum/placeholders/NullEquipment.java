package obscurum.placeholders;

import obscurum.display.Display;
import obscurum.display.DisplayTile;
import obscurum.items.Equipment;

/**
 * This models an equipment placeholder.
 * @author Alex Ghita
 */
public class NullEquipment extends Equipment {
  /**
   * Class constructor specifying placeholder values.
   */
  public NullEquipment() {
    super("Null Equipment", 'x', Display.BLACK, "", 1, 0, 0, 0, 0, 0, 0, 0, 1);
  }
}
