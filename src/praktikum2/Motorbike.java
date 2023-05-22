package praktikum2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Motorbike extends Thread implements IMotorbike {
    private int totalRuntime;
    private int rounds;
    private String name;
    private int curRound = 0;

    /**
     * Konstruktor
     *
     * @param rounds Anzahl zu fahrenden Runden
     * @param name   Fahrer
     */
    Motorbike(int rounds, String name) {
        this.rounds = rounds;
        this.name = name;
    }

    @Override
    public void run() {
        // Fahre solange nicht alle Runden gefahren wurden und der Thread nicht
        // unterbrochen ist.
        while (curRound < this.rounds && !this.isInterrupted()) {
            // Ermittel eine zufaellige Rundenzeit
            int roundTime = (int) Math.abs(Math.random() * 100 + 1);
            try {
                // Warte die zufaellige Rundenzeit ab
                Thread.sleep(roundTime);
            } catch (InterruptedException ex) {
                this.interrupt();
                // ignore
//				Logger.getLogger(Motorbike.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Merke gesamte Fahrzeit
            this.totalRuntime += roundTime;
            // Naechste Runde
            curRound++;
        }
        if (this.isInterrupted()) {
            System.out.println(String.format("%s ist in Runde %d", this.name, this.curRound));
        }
    }

    /**
     * Fahrzeit
     *
     * @return total runtime
     */
    @Override
    public int getTotalRuntime() {
        return this.totalRuntime;
    }



    /**
     * Fahrer
     *
     * @return Fahrer
     */
    @Override
    public String getMotorbikeName() {
        return this.name;
    }

    /**
     * Fahrer erhalten eine Ordnung ueber die Fahrzeit
     *
     * @param o Motorbike
     * @return -1 | 0 | 1
     */
    @Override
    public int compareTo(Motorbike o) {
        int runtime = o.getTotalRuntime();
        return (this.totalRuntime < runtime) ? -1 : ((this.totalRuntime > runtime) ? 1 : 0);
    }

}