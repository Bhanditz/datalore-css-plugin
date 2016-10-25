package jetbrains.datapad.css.compiler.processor;

import org.lesscss.LessCompiler;
import org.lesscss.LessException;

public class CompilerProcessor implements Processor {
  private LessCompiler myCompiler;

  public CompilerProcessor(boolean compress) {
    myCompiler = new LessCompiler();
    if (compress) {
      myCompiler.setCompress(compress);
    }
  }

  @Override
  public void execute(Context context) {
    try {
      String output = myCompiler.compile(context.getOutput());
      context.setOutput(output);
    } catch(LessException ex) {
      context.setOutput(null);
      throw new RuntimeException(ex);
    }
  }
}
