package obscurum.display;

/**
 * This models an entry in a scrollable list.
 * @author Alex Ghita
 */
public class ListEntry {
  private String text;
  private int index;

  public ListEntry(String text, int index) {
    this.text = text;
    this.index = index;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getIndex() {
    return index;
  }
}
