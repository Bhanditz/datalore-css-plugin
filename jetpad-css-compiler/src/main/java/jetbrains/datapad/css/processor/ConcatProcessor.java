package jetbrains.datapad.css.processor;

import jetbrains.datapad.css.Utils;

public class ConcatProcessor implements Processor {
  @Override
  public void execute(Context context) {
    context.setOutput(Utils.concat(context.getImportContents()));
  }
}
