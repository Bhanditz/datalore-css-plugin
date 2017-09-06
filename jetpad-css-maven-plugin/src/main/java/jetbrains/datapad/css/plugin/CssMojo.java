package jetbrains.datapad.css.plugin;

import jetbrains.datapad.css.compiler.Compiler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Mojo(name = "css", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class CssMojo extends AbstractMojo {
  @Parameter(property = "sources")
  private String[] sources;
  @Parameter(property = "baseDir")
  private String baseDir;
  @Parameter(property = "targetDir")
  private String targetDir;
  @Parameter(defaultValue = "false")
  private boolean compress;

  public void execute() throws MojoExecutionException {
    getLog().info("building css files");
    getLog().info("baseDir: " + baseDir);
    getLog().info("targetDir: " + targetDir);
    if (sources == null || sources.length == 0) {
      throw new MojoExecutionException("sources list is empty");
    }
    for (String source : sources) {
      compile(Paths.get(baseDir,source), Paths.get(targetDir, toCssExt(source)));
    }
  }

  private void compile(Path sourceFile, Path destFile) throws MojoExecutionException {
    if (sourceFile == null) {
      throw new MojoExecutionException("source file is not specified");
    }
    if (destFile == null) {
      throw new MojoExecutionException("dest file is not specified");
    }
    if (!Files.exists(sourceFile)) {
      throw new MojoExecutionException("can't open file " + sourceFile.toString());
    }

    Path targetDir = destFile.getParent();
    if (!Files.exists(targetDir)) {
      try {
        Files.createDirectories(targetDir);
      } catch (IOException e) {
        throw new MojoExecutionException("can't create dir " + targetDir.toString(), e);
      }
    }

    Compiler compiler = new Compiler(compress);
    try {
      String result = compiler.compile(sourceFile).getOutput();
      Files.write(Paths.get(destFile.toString()), result.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    } catch (IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
    getLog().info("css compiled: " + destFile.toString());
  }

  private String toCssExt(String uri) {
    return uri.replaceAll(".less$", ".css");
  }
}
