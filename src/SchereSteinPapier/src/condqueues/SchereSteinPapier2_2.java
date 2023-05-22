package SchereSteinPapier.src.condqueues;

import SchereSteinPapier.src.util.GameResult;
import SchereSteinPapier.src.util.Player;
import SchereSteinPapier.src.util.Referee;

import java.util.LinkedList;
import java.util.stream.Stream;

public class SchereSteinPapier2_2 {
  private final static int SIMULATION_TIME = 1000;

  private TableCondQueues tableCondQueues = new TableCondQueues();

  public static void main(String[] args) {
    new SchereSteinPapier2_2().startGame();
  }

  private void startGame() {
    Referee referee = new Referee(tableCondQueues);
    LinkedList<Player> players = new LinkedList<>();

    for (int i = 0; i < 2; i++) {
      Player player = new Player(tableCondQueues);
      player.setName("Player " + i);
      players.add(player);
      player.start();
    }

	referee.setName("Referee");
	referee.start();

	try {
		Thread.sleep(SIMULATION_TIME);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}

	referee.interrupt();
	
	for (Player current : players) {
		current.interrupt();
		if (current.isAlive()) {
			try {
				current.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	try {
		referee.join();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	
    int[] results = tableCondQueues.getResults();
    Stream.of(GameResult.values()).forEach(gameResult ->
        System.out.println(gameResult + ": " + results[gameResult.ordinal()]
        ));
  }
}
