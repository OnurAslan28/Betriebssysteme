package SchereSteinPapier.src.util;

public interface Table {
  void play(GameObject object) throws InterruptedException;
  void evaluate() throws InterruptedException;
  int[] getResults();
}
