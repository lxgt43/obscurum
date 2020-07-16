package obscurum.screens;

import obscurum.GameMain;
import obscurum.creatures.Player;
import obscurum.creatures.abilities.Spell;
import obscurum.display.Display;
import obscurum.display.ListEntry;
import obscurum.display.ScrollList;
import obscurum.display.asciiPanel.AsciiPanel;
import obscurum.environment.Level;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * This models a screen where players can see known spells and read about their
 * effects.
 * @author Alex Ghita
 */
public class SpellbookScreen extends Screen {
  private static final int SPELL_LIST_TL_X = 0;
  private static final int SPELL_LIST_TL_Y = 0;
  private static final int SPELL_LIST_WIDTH = 40;
  private static final int SPELL_LIST_HEIGHT = GameMain.SCREEN_HEIGHT;
  private static final int DESCRIPTION_TL_X = SPELL_LIST_WIDTH - 1;
  private static final int DESCRIPTION_TL_Y = 0;
  private static final int DESCRIPTION_WIDTH = GameMain.SCREEN_WIDTH -
      SPELL_LIST_WIDTH + 1;
  private static final int DESCRIPTION_HEIGHT = SPELL_LIST_HEIGHT;
  private ScrollList scrollList;

  public SpellbookScreen(Level[] world, Player player) {
    super(world, player);
    makeScrollList();
  }

  private void makeScrollList() {
    ArrayList<ListEntry> list = new ArrayList<ListEntry>();

    for (int i = 0; i < player.getSpells().size(); i++) {
      Spell spell = player.getSpells().get(i);
      list.add(new ListEntry(spell.getName(), i));
    }

    scrollList = new ScrollList(list, SPELL_LIST_HEIGHT - 4,
        SPELL_LIST_WIDTH - 2);
  }

  private void printSpellList(AsciiPanel terminal) {
    drawBorders(terminal, new Point(SPELL_LIST_TL_X, SPELL_LIST_TL_Y),
        SPELL_LIST_WIDTH, SPELL_LIST_HEIGHT, false, Display.FG_WINDOW_FRAME,
        Display.BG_WINDOW_FRAME);
  }

  private void printSelectedSpellDescription(AsciiPanel terminal) {
    drawBorders(terminal, new Point(DESCRIPTION_TL_X, DESCRIPTION_TL_Y),
        DESCRIPTION_WIDTH, DESCRIPTION_HEIGHT, false, Display.FG_WINDOW_FRAME,
        Display.BG_WINDOW_FRAME);

    if (scrollList.getEntries().isEmpty()) {
      return;
    }

    Spell spell = player.getSpells().get(scrollList.getSelectedIndex());
    String spellType = spell.getSpellType() == Spell.COMBAT_SPELL ?
        "This is a combat spell; it will cause you to enter combat mode" +
        " when cast." : "This is a non-combat spell; it will not cause you " +
        " to enter combat mode when cast.";
    String name = spell.getName() + " (Level " + spell.getLevel() + ")";
    name = spell.isMaxLevel() ? name + " (MAX LEVEL)" : name;
    terminal.write(name, DESCRIPTION_TL_X + 1, DESCRIPTION_TL_Y + 1);
    terminal.write("Mana Cost:" + spell.getManaCost() + ".",
        DESCRIPTION_TL_X + 1, DESCRIPTION_TL_Y + 3);
    terminal.write("Cooldown:" + spell.getCooldown() + " turns.",
        DESCRIPTION_TL_X + 1, DESCRIPTION_TL_Y + 4);
    terminal.write("Maximum Range:" + spell.getMaxRange() + ".",
        DESCRIPTION_TL_X + 1, DESCRIPTION_TL_Y + 5);
    terminal.writeMultiline(spellType, DESCRIPTION_TL_X + 1,
        DESCRIPTION_TL_Y + 7);

    int lastLine = terminal.getLastMultilineY();
    terminal.writeMultiline(spell.getDescription(), DESCRIPTION_TL_X + 1,
        DESCRIPTION_TL_Y + lastLine + 2);
    terminal.writeMultiline("Spell level up bonus: " +
        spell.getLevelUpDescription(), DESCRIPTION_TL_X + 1,
        DESCRIPTION_TL_Y + lastLine + 4);
  }

  private void connectFrames(AsciiPanel terminal) {
    terminal.write(Display.DH_DB_INTERSECT, DESCRIPTION_TL_X, 0,
        Display.FG_WINDOW_FRAME, Display.BG_WINDOW_FRAME);
    terminal.write(Display.DH_DT_INTERSECT, DESCRIPTION_TL_X,
        DESCRIPTION_HEIGHT - 1, Display.FG_WINDOW_FRAME,
        Display.BG_WINDOW_FRAME);
  }

  @Override
  public void displayOutput(AsciiPanel terminal) {
    printSpellList(terminal);
    printSelectedSpellDescription(terminal);
    connectFrames(terminal);

    String help = "Press [F1] for help.";
    writeHorizontalLine(terminal, SPELL_LIST_TL_X, SPELL_LIST_WIDTH - 1,
        SPELL_LIST_HEIGHT - 3);
    terminal.write(help, (SPELL_LIST_TL_X + SPELL_LIST_WIDTH) / 2 -
        help.length() / 2, SPELL_LIST_HEIGHT - 2);
    scrollList.printList(terminal, SPELL_LIST_TL_X + 1, SPELL_LIST_TL_Y + 1);

    if (player.getSpells().size() == 0) {
      terminal.write("You don't know any spells yet.", SPELL_LIST_TL_X + 1,
          SPELL_LIST_TL_Y + 1);
    }
  }

  public Screen respondToUserInput(KeyEvent key) {
    switch (key.getKeyCode()) {
      case KeyEvent.VK_F1:
        return new HelpScreen(world, player, HelpScreen.SPELLBOOK_HELP);
      case KeyEvent.VK_ESCAPE:
        return new PlayScreen(world, player);
      case KeyEvent.VK_UP:
        scrollList.scrollUp();
        return this;
      case KeyEvent.VK_DOWN:
        scrollList.scrollDown();
        return this;
      default:
        return this;
    }
  }
}
