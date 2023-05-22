package SchereSteinPapier.src.util;

public class Referee extends Thread {
  private Table table;

  public Referee(Table table) {
    this.table = table;
  }

  @Override
  public void run() {
    try {
      while (!isInterrupted()) {
        table.evaluate();
      }
    } catch (InterruptedException e) {
      System.err.println(this.getName() + " wurde erfolgreich interrupted!");
    }
  }
}
