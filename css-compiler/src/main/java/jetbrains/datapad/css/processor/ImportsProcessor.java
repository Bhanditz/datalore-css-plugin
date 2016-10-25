package jetbrains.datapad.css.processor;

import jetbrains.datapad.css.Utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportsProcessor implements Processor {
  private static Pattern myImportPattern = Pattern.compile("@import\\s+\"(.*\\.\\./)?(.*)\";");

  @Override
  public void execute(Context context) {
    try {
      execute(context.getInputPath(), context);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private void execute(Path input, Context context) throws IOException {
    if (context.getImportPaths().contains(input)) {
      return;
    }

    context.getImportPaths().add(input);
    String content = new String(Files.readAllBytes(input));
    List<GlobPattern> patterns = readImports(content);

    content = removeImports(content);
    context.getImportContents().add(content);

    List<Path> imports = findFiles(input.getParent(), patterns);
    for (Path current : imports) {
      execute(current, context);
    }
  }

  private List<GlobPattern> readImports(String content) {
    Matcher matcher = myImportPattern.matcher(content);
    List<GlobPattern> result = new ArrayList<>();
    while(matcher.find()) {
      String relativePath = matcher.group(1);
      String pattern = matcher.group(2);
      if (!pattern.endsWith(Utils.EXTENSION)) {
        pattern = pattern + "." + Utils.EXTENSION;
      }
      GlobPattern glob = new GlobPattern(relativePath, pattern);
      result.add(glob);
    }
    return result;
  }

  private String removeImports(String content) {
    Matcher matcher = myImportPattern.matcher(content);
    return matcher.replaceAll("");
  }

  private List<Path> findFiles(Path parent, List<GlobPattern> patterns) throws IOException {
    List<Path> files = new ArrayList<>();

    for (GlobPattern pattern : patterns) {
      List<Path> matches = findFiles(parent, pattern);
      for (Path match : matches) {
        files.add(match);
      }
    }
    return files;
  }

  private List<Path> findFiles(Path parent, GlobPattern pattern) throws IOException {
    final List<Path> result = new ArrayList<>();
    final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern.getPattern());
    final String relativePath = pattern.getRelativePath() != null ? pattern.getRelativePath() : "";
    final Path dir = parent.resolve(relativePath);

    if (!Files.exists(dir)) {
      return result;
    }

    Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        if (matcher.matches(dir.relativize(path))) {
          result.add(path);
        }
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
      }
    });
    return result;
  }
}
