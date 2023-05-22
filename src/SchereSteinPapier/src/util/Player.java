package SchereSteinPapier.src.util;

public class Player extends Thread {
  private Table table;

  public Player(Table table) {
    this.table = table;
  }

  @Override
  public void run() {
    try {
      while (!isInterrupted()) {
        int gameObject = (int) (3 * Math.random());
        table.play(GameObject.values()[gameObject]);
      }
    }catch (InterruptedException ex) {
      System.err.println(this.getName() + " wurde erfolgreich interrupted!");
    }
  }
}
