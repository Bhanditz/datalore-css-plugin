package jetbrains.datapad.css.compiler.processor;

public class GlobPattern {
  private String myRelativePath;
  private String pattern;

  public GlobPattern(String relativePath, String pattern) {
    this.myRelativePath = relativePath;
    this.pattern = pattern;
  }

  public String getRelativePath() {
    return myRelativePath;
  }

  public void setRelativePath(String relativePath) {
    this.myRelativePath = relativePath;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
}
