package jetbrains.datapad.css.compiler;

import java.util.List;

public final class Utils {
  public static String concat(List<String> content) {
    StringBuilder builder = new StringBuilder();
    for (String item : content) {
      builder.append(item);
    }
    return builder.toString();
  }

  private Utils() {
  }
}
