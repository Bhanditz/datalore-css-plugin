package jetbrains.datapad.css;

import jetbrains.datapad.css.processor.CompilerProcessor;
import jetbrains.datapad.css.processor.ConcatProcessor;
import jetbrains.datapad.css.processor.Context;
import jetbrains.datapad.css.processor.ImportsProcessor;
import jetbrains.datapad.css.processor.Processor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Compiler {

  private List<Processor> myProcessors = new LinkedList<>();

  public Compiler() {
    this(false);
  }

  public Compiler(boolean compress) {
    myProcessors.add(new ImportsProcessor());
    myProcessors.add(new ConcatProcessor());
    myProcessors.add(new CompilerProcessor(compress));
  }

  public Context compile(Path input) {
    if (!Files.exists(input) || !Files.isReadable(input)) {
      throw new RuntimeException("Can't open file " + input.toString());
    }

    Context context = new Context(input);
    for (Processor processor : myProcessors) {
      processor.execute(context);
    }
    return context;
  }
}
