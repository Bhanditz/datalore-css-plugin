package jetbrains.datapad.css.plugin;

import jetbrains.datapad.css.compiler.Compiler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Mojo(name = "compile", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CssMojo extends AbstractMojo {
  @Parameter
  private CssBundle[] bundles;
  @Parameter(defaultValue = "false")
  private boolean compress;

  public void execute() throws MojoExecutionException {
    if (bundles == null || bundles.length == 0) {
      return;
    }
    for (CssBundle bundle : bundles) {
      compile(bundle.getSource(), bundle.getDest());
    }
  }

  private void compile(File source, File dest) throws MojoExecutionException {
    if (source == null) {
      throw new MojoExecutionException("source file is not specified");
    }
    if (dest == null) {
      throw new MojoExecutionException("dest file is not specified");
    }
    if (!source.exists()) {
      throw new MojoExecutionException("can't open file " + source.getAbsolutePath());
    }

    Compiler compiler = new Compiler(compress);
    try {
      String result = compiler.compile(Paths.get(source.getPath())).getOutput();
      Files.write(Paths.get(dest.getPath()), result.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    } catch (IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
    getLog().info("css compiled: " + source.getAbsolutePath());
  }
}
