package obscurum.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import obscurum.creatures.Creature;
import obscurum.creatures.Player;
import obscurum.display.Display;
import obscurum.display.DisplayColour;
import obscurum.display.ListEntry;
import obscurum.display.ScrollList;
import obscurum.display.terminal.AsciiPanel;
import obscurum.environment.Level;

/**
 * This models a subscreen where players can spend attribute points earned
 * through levelling up to permanently improve their characters.
 * @author Alex Ghita
 */
public class LevelUpScreen extends SubScreen {
    private ScrollList scrollList;
    private int[] levelUpCosts = {3, 3, 1, 1, 1, 1, 1};
    private String[] attributes = {"Line of Sight", "Inventory Size", "Strength",
            "Agility", "Stamina", "Spirit", "Intellect"};

    public LevelUpScreen(Level[] world, Player player) {
        super(world, player);

        computeWidth();
        computeHeight();
        makeScrollList();
        computeTopLeft();
    }

    @Override
    protected void computeWidth() {
        width = 38;
    }

    @Override
    protected void computeHeight() {
        // The height will be constant.
        height = 11;
    }

    private void makeScrollList() {
        ArrayList<ListEntry> list = new ArrayList<ListEntry>();

        for (int i = 0; i < attributes.length; i++) {
            int maxAttribute = i == 0 ? Creature.MAX_LINE_OF_SIGHT : i == 1 ?
                    Creature.MAX_INVENTORY_SIZE : Creature.MAX_BASIC_ATTRIBUTE;
            list.add(new ListEntry(attributes[i] + " (" +
                    player.getBaseAttributes()[i + 1] + "/" + maxAttribute + ")", i));
        }

        scrollList = new ScrollList(list, 3, width);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.clear(' ', topLeft.x + 1, topLeft.y + 1, width - 1, height - 1);
        drawBorders(terminal, topLeft, width, height);
        writeCentre(terminal, "Level up your attributes", 1);
        writeHorizontalLine(terminal, 2);

        ArrayList<ListEntry> entries = scrollList.getEntries();
        int selected = scrollList.getSelected();
        int selectedHeight = scrollList.getSelectedHeight();
        if (entries.size() > 0) {
            for (int i = 0; i < scrollList.getHeight(); i++) {
                int index = selected - selectedHeight + i;
                if (index >= entries.size()) {
                    break;
                }
                int xOffset = entries.get(index).getText().length() + 6;
                DisplayColour displayColour = levelUpCosts[index] > player.getAttributePoints()
                        ? DisplayColour.RED
                        : DisplayColour.WHITE;
                terminal.write(entries.get(index).getText() + " Cost:", topLeft.x + 2,
                        topLeft.y + i + 3);
                terminal.write(Integer.toString(levelUpCosts[index]), topLeft.x + 2 + xOffset, topLeft.y + i + 3, displayColour);
            }
            terminal.write(">", topLeft.x + 1, topLeft.y + selectedHeight + 3);
        }

        writeHorizontalLine(terminal, height - 5);
        writeCentre(terminal, "You have " + player.getAttributePoints() +
                " attribute points.", height - 4);
        writeHorizontalLine(terminal, height - 3);
        writeCentre(terminal, "Press [e] to level up attribute.", height - 2);
        writeCentre(terminal, "Press [esc] to return.", height - 1);
        terminal.repaint();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                player.setInSubScreen(false);
                return new PlayScreen(world, player);
            case KeyEvent.VK_UP:
                scrollList.scrollUp();
                return this;
            case KeyEvent.VK_DOWN:
                scrollList.scrollDown();
                return this;
            case KeyEvent.VK_E:
                int selectedAttribute = scrollList.getSelectedIndex();
                int maxAttribute = selectedAttribute == 0 ?
                        Creature.MAX_LINE_OF_SIGHT : selectedAttribute == 1 ? Creature.MAX_INVENTORY_SIZE : Creature.MAX_BASIC_ATTRIBUTE;
                if (levelUpCosts[selectedAttribute] <= player.getAttributePoints() &&
                        player.getBaseAttributes()[selectedAttribute + 1] < maxAttribute) {
                    player.setBaseAttribute(selectedAttribute + 1,
                            player.getBaseAttributes()[selectedAttribute + 1] + 1);
                    player.setAttributePoints(player.getAttributePoints() -
                            levelUpCosts[selectedAttribute]);

                    scrollList.updateSelectedEntry(attributes[selectedAttribute] + " (" +
                            player.getBaseAttributes()[selectedAttribute + 1] + "/" +
                            maxAttribute + ")");
                }
                return this;
            default:
                return this;
        }
    }
}
