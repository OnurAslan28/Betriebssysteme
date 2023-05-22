package praktikum3a;

import java.util.Comparator;
import java.util.LinkedList;

public class Student extends Thread{
    private LinkedList<Register> registers;

    Student(LinkedList<Register> registers) {
        this.registers = registers;
    }


    @Override
    public void run() {
        try {
            while (!isInterrupted()) {

                registers.sort(Comparator.comparingInt(Register::getQueueLength));
                registers.get(0).enter();
                eat();
                enjoyLife();
            }
        } catch (InterruptedException e) {
            System.err.println(this.getName() + " wurde erfolgreich interrupted!");
        }
    }

    private void eat() throws InterruptedException {
        int sleepTime = (int) (1000 * Math.random());
        Thread.sleep(sleepTime);
    }

    private void enjoyLife() throws InterruptedException {
        int sleepTime = (int) (1000 * Math.random());
        Thread.sleep(sleepTime);
    }
}