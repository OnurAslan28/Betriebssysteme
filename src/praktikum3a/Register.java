package praktikum3a;

import java.util.concurrent.Semaphore;

class Register{
    private int counter = 0;
    private Semaphore semaphore = new Semaphore(1);
    private String name;

    int getQueueLength() {
        return semaphore.getQueueLength();
    }

    void enter() throws InterruptedException {
        semaphore.acquire();
        try {
            buy();
            counter++;
        } finally {
            semaphore.release();
        }
    }

    private void buy() throws InterruptedException {
        int sleepTime = (int) (1000 * Math.random());
        Thread.sleep(sleepTime);
    }

    int getCounter() {
        return counter;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}