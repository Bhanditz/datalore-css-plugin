package jetbrains.datapad.css.plugin;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

public class CssBundle {
  @Parameter
  private File source;
  @Parameter
  private File dest;

  public File getSource() {
    return source;
  }

  public File getDest() {
    return dest;
  }
}
