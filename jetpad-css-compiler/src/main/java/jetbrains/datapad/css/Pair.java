package jetbrains.datapad.css;

public class Pair<FirstT, SecondT> {
  public final FirstT first;
  public final SecondT second;

  public Pair(FirstT first, SecondT second) {
    this.first = first;
    this.second = second;
  }
}
