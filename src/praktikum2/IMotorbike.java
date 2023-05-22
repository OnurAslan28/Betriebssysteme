package praktikum2;

public interface IMotorbike extends Runnable, Comparable<Motorbike> {

    public int getTotalRuntime();

    public String getMotorbikeName();



}