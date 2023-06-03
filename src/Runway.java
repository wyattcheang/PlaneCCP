import java.util.Objects;

public class Runway {
    private boolean inUse;

    public Runway() {
        this.inUse = false;
    }

    public synchronized void landingAcquire(Plane plane) throws InterruptedException {
        while ((inUse || !Objects.equals(Main.airport.planeQueue.peek(), plane)) ||
                Main.airport.getAllGateSemaphore()[0].availablePermits() == 0 &&
                Main.airport.getAllGateSemaphore()[1].availablePermits() == 0 &&
                Main.airport.getAllGateSemaphore()[2].availablePermits() == 0) {
            if (Gate.checkGateFulledOccupied())
                System.out.printf("ATC\t\t: All GATES are currently being used. PLANE %d please wait for a moment\n", plane.getPlaneID());
            else
                System.out.printf("ATC\t\t: RUNWAY is not available. PLANE %d please wait for a moment\n", plane.getPlaneID());
            wait();
        }
        inUse = true;
        System.out.printf("ATC\t\t: REQUEST SUCCESSFUL, PLANE %d is allowed to use the RUNWAY.\n", plane.getPlaneID());
        Main.airport.planeQueue.remove();
    }

    public synchronized void landingRelease() {
        inUse = false;
        Airport.printAction("RUNWAY landing released");
        notifyAll();
    }

    public synchronized void takeOffAcquire(Plane plane) throws InterruptedException {
        while (inUse) {
            System.out.printf("ATC\t\t: RUNWAY is not available. PLANE %d please wait for a moment\n", plane.getPlaneID());
            wait();
        }
        inUse = true;
        System.out.printf("ATC\t\t: REQUEST SUCCESSFUL, PLANE %d is allowed to use the RUNWAY.\n", plane.getPlaneID());
    }

    public synchronized void takeOffRelease(Plane plane){
        inUse = false;
        Airport.printAction("RUNWAY take off released");
        notifyAll();
    }

}
