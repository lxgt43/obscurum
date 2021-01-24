package obscurum.screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import obscurum.GameMain;
import obscurum.creatures.Creature;
import obscurum.creatures.Player;
import obscurum.display.terminal.AsciiPanel;
import obscurum.environment.Level;

/**
 * This models a help screen, which displays some help messages depending on
 * where it is called.
 * @author Alex Ghita
 */
public class HelpScreen extends Screen {
  public static final int PLAY_HELP = 0;
  public static final int CHARACTER_HELP = 1;
  public static final int INVENTORY_HELP = 2;
  public static final int SPELLBOOK_HELP = 3;
  public static final int EDITOR_HELP = 4;
  private static final ArrayList<String> PLAY_CONTROLS =
      new ArrayList<String>(Arrays.asList(
      "Game controls:",
      "Use the arrow keys to move.",
      "To perform a melee attack on an enemy, simply walk into them.",
      // "[q] - change sight mode (for debugging).",
      // "[g] - toggle god mode (for debugging).",
      "[i] - access your inventory.",
      "[c] - access your character's information screen.",
      "[s] - open a list of spells to choose one to cast.",
      "[b] - open your spellbook and view details about your spells.",
      "[l] - open the level-up screen to improve your character.",
      "[a] - aim with a bow (needs to be equipped).",
      "[esc] - quit the current game.",
      " ",
      "Aim mode controls:",
      "Use the arrow keys to aim.",
      "[a] - fire at the target.",
      "[esc] - exit aim mode.",
      " ",
      "To win, find the Amulet of Power in the deepest level, then " +
      "return to the first level and find the exit."));
  private static final ArrayList<String> INVENTORY_CONTROLS =
      new ArrayList<String>(Arrays.asList(
      "Inventory controls:",
      "Use the arrow keys to scroll through inventory slots.",
      "[e] - use the selected item (if usable).",
      "[x] - destroy the selected item.",
      "[esc] - resume the game.",
      "The number in each filled slot represents the amount of items of " +
      "that kind found in the slot.",
      " ",
      "Item attribute keywords:",
      "STR = Strength, AGI = Agility, STA = Stamina,",
      "SPI = Spirit, INT = Intellect, DEF = Defence, DMG = Damage.",
      "Refer to the character screen help to see what each attribute does."));
  private static final ArrayList<String> CHARACTER_CONTROLS =
      new ArrayList<String>(Arrays.asList(
      "Character screen controls:",
      "Use the arrow keys to select an equipment slot.",
      "[e] - unequip the selected item.",
      "[esc] - resume the game.",
      " ",
      "The stats on the right side show your attributes.",
      "Strength increases your attack power (1 Strength = 1 Attack Power).",
      "Agility increases your dodge chance, up to a maximum of " +
      Creature.MAX_DODGE_CHANCE + "% (5 Agility = 1% Dodge Chance).",
      "Stamina increases your health (1 Stamina = 2 Health).",
      "Spirit increases your out-of-combat regeneration (2 Spirit = 1 Health" +
      "/turn, 1 Mana/turn).",
      "Intellect increases your mana and spell power (1 Intellect = 2 Mana, " +
      "1 Spell Power).",
      "Attack Power makes your attacks with weapons stronger.",
      "Spell Power makes your spells more efficient."
      ));
  private static final ArrayList<String> EDITOR_CONTROLS =
      new ArrayList<String>(Arrays.asList(
      "Editor controls:",
      (char)27 + "/" + (char)26 + " - navigate between object lists.",
      (char)25 + "/" + (char)24 + " - browse the selected list.",
      "[e] - add a new object to the selected list.",
      "[x] - delete the current object.",
      "[esc] - quit the editor and go back to the main menu."
      ));
  private static final ArrayList<String> SPELLBOOK_CONTROLS =
      new ArrayList<String>(Arrays.asList(
      "Spellbook controls:",
      (char)25 + "/" + (char)24 + " - browse the spell list.",
      "[esc] - go back."
      ));
  private int helpType;
  private ArrayList<String> controls;

  public HelpScreen(int helpType) {
    this.helpType = helpType;
    switch (helpType) {
      case PLAY_HELP:
        controls = PLAY_CONTROLS;
        break;
      case CHARACTER_HELP:
        controls = CHARACTER_CONTROLS;
        break;
      case INVENTORY_HELP:
        controls = INVENTORY_CONTROLS;
        break;
      case SPELLBOOK_HELP:
        controls = SPELLBOOK_CONTROLS;
        break;
      default:
        controls = EDITOR_CONTROLS;
        break;
    }
  }

  public HelpScreen(Level[] world, Player player, int helpType) {
    super(world, player);

    // Check for illegal arguments.
    if (helpType < PLAY_HELP || helpType > EDITOR_HELP) {
      throw new IllegalArgumentException("Help type " + helpType +
          " must be between " + PLAY_HELP + " and " + EDITOR_HELP + ".");
    }

    this.helpType = helpType;
    switch (helpType) {
      case PLAY_HELP:
        controls = PLAY_CONTROLS;
        break;
      case CHARACTER_HELP:
        controls = CHARACTER_CONTROLS;
        break;
      case INVENTORY_HELP:
        controls = INVENTORY_CONTROLS;
        break;
      case SPELLBOOK_HELP:
        controls = SPELLBOOK_CONTROLS;
        break;
      default:
        controls = EDITOR_CONTROLS;
        break;
    }
  }

  @Override
  public void displayOutput(AsciiPanel terminal) {
    int line = 1;

    for (String control : controls) {
      terminal.writeCenter(control, line);
      line++;
    }
    terminal.writeCenter("Press [esc] to go back.",
        GameMain.SCREEN_HEIGHT_IN_CHARACTERS - 5);
  }

  @Override
  public Screen respondToUserInput(KeyEvent key) {
    switch (key.getKeyCode()) {
      case KeyEvent.VK_ESCAPE:
        switch (helpType) {
          case PLAY_HELP:
            return new PlayScreen(world, player);
          case CHARACTER_HELP:
            return new CharacterScreen(world, player);
          case INVENTORY_HELP:
            return new InventoryScreen(world, player);
          case SPELLBOOK_HELP:
            return new SpellbookScreen(world, player);
          default:
            return new GameEditorScreen();
        }
      default:
        return this;
    }
  }
}
