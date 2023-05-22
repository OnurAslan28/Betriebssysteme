package SchereSteinPapier.src.monitor;

import SchereSteinPapier.src.util.GameObject;
import SchereSteinPapier.src.util.GameResult;
import SchereSteinPapier.src.util.Table;

import java.util.LinkedList;

public class TableMonitor implements Table {
  private int[] results = new int[3];
  private GameObject player1;
  private GameObject player2;
  private GameResult[][] lookUpTable = new GameResult[3][3];

  TableMonitor() {
    lookUpTable[GameObject.SCHERE.ordinal()][GameObject.SCHERE.ordinal()] = GameResult.UNENTSCHIEDEN;
    lookUpTable[GameObject.SCHERE.ordinal()][GameObject.STEIN.ordinal()] = GameResult.PLAYER2;
    lookUpTable[GameObject.SCHERE.ordinal()][GameObject.PAPIER.ordinal()] = GameResult.PLAYER1;

    lookUpTable[GameObject.STEIN.ordinal()][GameObject.SCHERE.ordinal()] = GameResult.PLAYER1;
    lookUpTable[GameObject.STEIN.ordinal()][GameObject.STEIN.ordinal()] = GameResult.UNENTSCHIEDEN;
    lookUpTable[GameObject.STEIN.ordinal()][GameObject.PAPIER.ordinal()] = GameResult.PLAYER2;

    lookUpTable[GameObject.PAPIER.ordinal()][GameObject.SCHERE.ordinal()] = GameResult.PLAYER2;
    lookUpTable[GameObject.PAPIER.ordinal()][GameObject.STEIN.ordinal()] = GameResult.PLAYER1;
    lookUpTable[GameObject.PAPIER.ordinal()][GameObject.PAPIER.ordinal()] = GameResult.UNENTSCHIEDEN;
  }

  @Override
  public synchronized void play(GameObject object) throws InterruptedException {
    if (Thread.currentThread().getName().contains("0")) {
      while (player1 != null) {
        this.wait();
      }
      player1 = object;
      notifyAll();
    } else if (Thread.currentThread().getName().contains("1")) {
      while (player2 != null) {
        this.wait();
      }
      player2 = object;
      notifyAll();
    } else {
      System.err.println("Something in play went very wrong!!!");
    }
  }

  @Override
  public synchronized void evaluate() throws InterruptedException {
    while (player1 == null || player2 == null) {
      this.wait();
    }
    System.out.println("Player1: "+ player1 + " Player2: " + player2 + " Ergebnis " + (lookUpTable[player1.ordinal()][player2.ordinal()]).name()) ;
    results[(lookUpTable[player1.ordinal()][player2.ordinal()]).ordinal()]++;
    player2 = player1 = null;
    notifyAll();
  }

  @Override
  public int[] getResults() {
    return results;
  }
  
  
  
}
