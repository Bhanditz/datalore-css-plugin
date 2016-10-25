package jetbrains.datapad.css.compiler.processor;

import jetbrains.datapad.css.compiler.Utils;

public class ConcatProcessor implements Processor {
  @Override
  public void execute(Context context) {
    context.setOutput(Utils.concat(context.getImportContents()));
  }
}
