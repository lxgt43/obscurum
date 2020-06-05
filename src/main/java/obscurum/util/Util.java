package obscurum.util;

import java.lang.Math;
import java.util.ArrayList;

/**
 * This class contains several general-purpose methods that may be of use.
 * @author Alex Ghita
 */
public class Util {
  public static int numberOfDigits(int n) {
    int answer = 0;

    do {
      answer++;
      n /= 10;
    } while (n > 0);

    return answer;
  }

  public static <E> E pickRandomElement(ArrayList<E> list) {
    double chance = 1.0 / list.size();
    double result = Math.random();

    for (int i = 0; i < list.size(); i++) {
      if (result < chance * (i + 1)) {
        return list.get(i);
      }
    }
    return list.get(list.size() - 1);
  }
}
