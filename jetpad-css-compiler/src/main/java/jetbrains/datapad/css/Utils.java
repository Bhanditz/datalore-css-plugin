package jetbrains.datapad.css;

import java.util.List;

public class Utils {
  public static String concat(List<String> content) {
    StringBuilder builder = new StringBuilder();
    for (String item : content) {
      builder.append(item);
    }
    return builder.toString();
  }
}
