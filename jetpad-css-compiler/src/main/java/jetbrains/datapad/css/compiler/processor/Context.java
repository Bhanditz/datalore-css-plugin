package jetbrains.datapad.css.compiler.processor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Context {
  private Path myInputPath;
  private String myOutput;
  private List<Path> myImportPaths = new ArrayList<>();
  private List<String> myImportContents = new ArrayList<>();

  public Context(Path input) {
    myInputPath = input;
  }

  public Path getInputPath() {
    return myInputPath;
  }

  public List<Path> getImportPaths() {
    return myImportPaths;
  }

  public List<String> getImportContents() {
    return myImportContents;
  }

  public String getOutput() {
    return myOutput;
  }

  void setOutput(String output) {
    this.myOutput = output;
  }
}
