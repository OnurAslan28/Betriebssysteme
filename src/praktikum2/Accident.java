package praktikum2;

public abstract class Accident extends Thread {
    int accidentTime;

    @Override
    public void run() {
        // Ermittel eine zufaellige Unfallzeit
        this.accidentTime = (int) Math.abs(Math.random() * 1000 + 1);
        try {
            // Warte die zufaellige Unfallzeit ab
            Thread.sleep(this.accidentTime);
        } catch (InterruptedException ex) {
            // muss für sleep implementiert werden
            System.out.println(String.format("%s: Class Accident ", ex));
        }
        onAccident();
    }

    /**
     * Unfallzeit
     *
     * @return accidentTime
     */

    public int getAccidentTime() {
        return this.accidentTime;
    }

    public abstract void onAccident();

}
