package praktikum2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrossRace {

    private final static int AMOUNT_MOTORBIKE = 5;
    private final static int ROUNDS = 5;
    private static Set<Motorbike> driving = new HashSet<Motorbike>();
    private static List<Motorbike> finishedMotorbike = new ArrayList<Motorbike>();
    private static boolean isAccident = false;
    public static void main(String[] args) {

        /**
         * @param args the command line arguments
         */

        // Starte Motorbike-Threads
        for (int i = 1; i <= AMOUNT_MOTORBIKE; i++) {
            Motorbike fahrer = new Motorbike(ROUNDS, "Fahrer " + String.valueOf(i));
            driving.add(fahrer);
            fahrer.start();
        }

        // Starte Accident-Thread
        Accident accident = new Accident() {
            @Override
            public void onAccident() {
                if(!isRaceFinished())
                    System.out.println("Unfall nach " + getAccidentTime() + "ms");
                for (Motorbike motorbike : driving) {
                    motorbike.interrupt();
                    finishedMotorbike.add(motorbike);
                }
            }
        };
        accident.start();

        // Solange nicht alle Fahrer das Ziel erreicht haben
            for (Motorbike fahrer : driving) {
                // Pruefe (fuer 10ms), ob Motorbike-Threads noch laufen
                try {
                    fahrer.join();
                    // Ist der Thread beendet, befindet sich der Fahrer im Ziel
                    if (!fahrer.isAlive()) {
                        if (!finishedMotorbike.contains(fahrer)) {
                            // Fertige Motorbike-Threads merken
                            finishedMotorbike.add(fahrer);
                        }
                    }
                } catch (InterruptedException e) {
                    // Wird aufgerufen, wenn der Thread interrupted wurde (Unfall)
                    Logger.getLogger(CrossRace.class.getName()).log(Level.SEVERE, null, e);
                }
            }


        // Bei Fail alle Motorbike-Threads unterbrechen
        if (isRaceFinished() && !isAccident) {
            // Sortiere Endstand nach Fahrzeit der Fahrer
            Collections.sort(finishedMotorbike);
            System.out.println("**** Endstand ****");
            int i = 1;
            // Gebe Endstand aus
            for (Motorbike motorbike : finishedMotorbike) {
                System.out.println(
                        i++ + ".Platz: " + motorbike.getMotorbikeName() + " Zeit: " + motorbike.getTotalRuntime() + "ms");
            }
        }
    }

    public static synchronized boolean isRaceFinished() {
        return !(finishedMotorbike.size() < AMOUNT_MOTORBIKE);
    }
}