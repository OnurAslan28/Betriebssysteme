package SchereSteinPapier.src.monitor;

import SchereSteinPapier.src.util.GameResult;
import SchereSteinPapier.src.util.Player;
import SchereSteinPapier.src.util.Referee;

import java.util.LinkedList;
import java.util.stream.Stream;

public class SchereSteinPapier2_1 {
	private final static int SIMULATION_TIME = 1000;

	private TableMonitor tableMonitor = new TableMonitor();

	public static void main(String[] args) {
		new SchereSteinPapier2_1().startGame();
	}

	private void startGame() {
		Referee referee = new Referee(tableMonitor);
		LinkedList<Player> players = new LinkedList<>();

		for (int i = 0; i < 2; i++) {
			Player player = new Player(tableMonitor);
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

		int[] results = tableMonitor.getResults();
		Stream.of(GameResult.values())
				.forEach(gameResult -> System.out.println(gameResult + ": " + results[gameResult.ordinal()]));
	}
}
