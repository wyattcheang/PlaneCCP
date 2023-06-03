import java.util.concurrent.Semaphore;
public class Fuel {

    private Airport airport;
    private Plane plane;
    private Gate gate;
    private Semaphore fuelSemaphore;

    private volatile boolean inUse;

    public Fuel(Airport airport) {
        this.airport = airport;
        this.fuelSemaphore = Main.airport.fuelSemaphore;
    }

    public Fuel(Plane plane, Gate gate) {
        this.plane = plane;
        this.gate = gate;
        this.fuelSemaphore = Main.airport.fuelSemaphore;
    }

    public void refuelAcquire() throws InterruptedException{
        System.out.printf("PLANE\t: PLANE %d is requesting refueling at GATE %c\n", plane.getPlaneID(), gate.getGateID());
        if (!fuelSemaphore.tryAcquire()) {
            System.out.printf("ATC\t\t: REFUEL TRUNK is not available. PLANE %d please wait for a moment\n", plane.getPlaneID());
            fuelSemaphore.acquire();
        }
        System.out.printf("ATC\t\t: REQUEST SUCCESSFUL, PLANE %d is allowed to use the REFUEL TRUNK.\n", plane.getPlaneID());
        refillProcess();
    }

    public void refuelRelease(){
        fuelSemaphore.release();
        Airport.printAction("REFUEL TRUNK is available");
    }

    public void refillProcess(){
        Airport.printAction(String.format("PLANE %d starts refilling fuel at GATE %c", plane.getPlaneID(), gate.getGateID()));
        int percentage = 0;
        try {
            while (percentage < 100) {
                percentage += 20;
                System.out.printf("PLANE %d\t: %d%% of fuel is refilled\n", plane.getPlaneID(), percentage);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Airport.printAction(String.format("The refilling of fuel for PLANE %d has been completed", plane.getPlaneID()));
    }
}
