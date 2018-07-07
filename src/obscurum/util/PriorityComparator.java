package obscurum.util;

import java.util.Comparator;

/**
 * This models a comparator for the A* priority queue.
 * @author Alex Ghita
 */
public class PriorityComparator implements Comparator<PathTileData> {
  @Override
  public int compare(PathTileData a, PathTileData b) {
    if (a.getPriority() < b.getPriority()) {
        return -1;
    }
    if (a.getPriority() > b.getPriority()) {
        return 1;
    }
    return 0;
  }
}
