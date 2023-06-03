import java.util.concurrent.Semaphore;

public class Gate implements Runnable{
    private Airport airport;
    private char gateID;
    private Semaphore gateSemaphore;
    public Gate() {
        this.airport = Main.airport;
    }

    public Gate(char gateID, Semaphore gateSemaphore, Airport airport) {
        this.gateID = gateID;
        this.gateSemaphore = gateSemaphore;
        this.airport = Main.airport;
    }

    public Gate acquire(Plane plane) throws InterruptedException {
        for (Gate gate : airport.gatesList) {
            if (gate.gateSemaphore.tryAcquire()) {
                // If a gate is available, assign it to the plane and return it
                return gate;
            }
        }

        // If no gate is available, wait until one becomes available
        Semaphore anyGateSemaphore = new Semaphore(0);
        for (Semaphore gateSemaphore : airport.getAllGateSemaphore()) {
            new Thread(() -> {
                try {
                    gateSemaphore.acquire();
                    anyGateSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        anyGateSemaphore.acquire();
        return acquire(plane); // Try again to assign a gate
    }

    public void release(Plane plane) throws InterruptedException {
        synchronized (airport.planeQueue) {
            gateSemaphore.release(); // Release permit back to semaphore
            airport.planeQueue.notifyAll();
            }
    }

    @Override
    public void run() {
        System.out.printf("ATC\t\t: GATE %c is available\n", gateID);
    }

    public char getGateID() {
        return gateID;
    }

    public static boolean checkGateFulledOccupied() {
        if (Main.airport.getAllGateSemaphore()[0].availablePermits() == 0 &&
                Main.airport.getAllGateSemaphore()[1].availablePermits() == 0 &&
                Main.airport.getAllGateSemaphore()[2].availablePermits() == 0)
            return true;
        else
            return false;
    }
}
