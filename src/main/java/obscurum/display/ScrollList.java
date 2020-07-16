package obscurum.display;

import obscurum.display.asciiPanel.AsciiPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * This models a scrollable list used in the interface, with corresponding
 * items to each list entry.
 * @author Alex Ghita
 */
public class ScrollList {
  private ArrayList<ListEntry> entries;
  private int height;
  private int width;
  private int selected;
  private int selectedHeight;
  private String emptyLine;

  public ScrollList(ArrayList<ListEntry> entries, int height, int width) {
    this.entries = entries;
    this.height = height;
    this.width = width;
    emptyLine = "";
    for (int i = 0; i < width; i++) {
      emptyLine.concat(" ");
    }
    selected = 0;
    selectedHeight = 0;
  }

  public ArrayList<ListEntry> getEntries() {
    return entries;
  }

  public int getSelected() {
    return selected;
  }

  public int getHeight() {
    return height;
  }

  public int getSelectedHeight() {
    return selectedHeight;
  }

  public int getSelectedIndex() {
    return entries.get(selected).getIndex();
  }

  public String getSelectedEntry() {
    return entries.get(selected).getText();
  }

  public void removeSelectedEntry() {
    entries.remove(selected);
    if (selected == entries.size()) {
      scrollUp();
    }
  }

  public void updateSelectedEntry(String content) {
    entries.get(selected).setText(content);
  }

  public void printList(AsciiPanel terminal, int topLeftX, int topLeftY) {
    printList(terminal, new Point(topLeftX, topLeftY));
  }

  public void printList(AsciiPanel terminal, Point topLeft) {
    printList(terminal, topLeft, -1);
  }

  public void printList(AsciiPanel terminal, int topLeftX, int topLeftY,
                        int widthLimit) {
    printList(terminal, new Point(topLeftX, topLeftY), widthLimit);
  }

  public void printList(AsciiPanel terminal, Point topLeft, int widthLimit) {
    if (entries.size() == 0) {
      return;
    }

    int linesPrinted = 0;
    for (int i = 0; i < height; i++) {
      int index = selected - selectedHeight + i;
      if (index >= entries.size()) {
        break;
      }

      String output = entries.get(index).getText();
      if (widthLimit != -1 && output.length() >= widthLimit) {
        output = output.substring(0, widthLimit - 4) + "...";
      }

      terminal.write(output, topLeft.x + 1, topLeft.y + i);
      linesPrinted++;
    }
    for (int i = linesPrinted + 1; i < height; i++) {
      terminal.write(emptyLine, topLeft.x, topLeft.y + i);
    }
    terminal.write(">", topLeft.x, topLeft.y + selectedHeight);
  }

  public void scrollUp() {
    scroll(true);
  }

  public void scrollDown() {
    scroll(false);
  }

  private void scroll(boolean up) {
    if (up && selected > 0) {
      selected--;
      if (selectedHeight > 0) {
        selectedHeight--;
      }
    } else if (!up && selected < entries.size() - 1) {
      selected++;
      if (selectedHeight < height - 1) {
        selectedHeight++;
      }
    }
  }
}
