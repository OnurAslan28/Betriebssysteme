package SchereSteinPapier.src.condqueues;

import SchereSteinPapier.src.util.GameObject;
import SchereSteinPapier.src.util.GameResult;
import SchereSteinPapier.src.util.Table;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TableCondQueues implements Table {
    private int[] results = new int[3];
    private GameObject player1;
    private GameObject player2;
    private GameResult[][] lookUpTable = new GameResult[3][3];
    private final Lock tableLock = new ReentrantLock();
    private final Condition playersFinished = tableLock.newCondition();
    private final Condition refereeFinished = tableLock.newCondition();

    TableCondQueues() {
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
    public void play(GameObject object) throws InterruptedException {
        tableLock.lockInterruptibly();
        try {
            if (Thread.currentThread().getName().contains("0")) {
                while (player1 != null) {
                    refereeFinished.await();
                }
                player1 = object;
            } else if (Thread.currentThread().getName().contains("1")) {
                while (player2 != null) {
                    refereeFinished.await();
                }
                player2 = object;
            } else {
                System.err.println("Something in play went very wrong!!!");
            }
//            if (player1 != null && player2 != null) {
               playersFinished.signal();
//            }
        } finally {
            tableLock.unlock();
        }
    }

    @Override
    public void evaluate() throws InterruptedException {
        tableLock.lockInterruptibly();
        try {
            while (player1 == null || player2 == null) {
                playersFinished.await();
            }

            System.out.println("Player1: " + player1 + " Player2: " + player2 + " Ergebnis "
                    + (lookUpTable[player1.ordinal()][player2.ordinal()]).name());

            results[(lookUpTable[player1.ordinal()][player2.ordinal()]).ordinal()]++;
            player2 = player1 = null;
            refereeFinished.signalAll();
        } finally {
            tableLock.unlock();
        }
    }

    @Override
    public int[] getResults() {
        return results;
    }
}
