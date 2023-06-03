import java.time.LocalDateTime;

public class Plane implements Runnable{
    private int planeID;
    private Airport airport;
    private Gate gate;
    private boolean isEmergency = false;
    private Simulation simulator;
    private LocalDateTime arrivalTime;


    public Plane(int planeID, Airport airport) {
        this.planeID = planeID;
        this.airport = airport;
        gate = new Gate();
        simulator = new Simulation(this);
        this.arrivalTime = LocalDateTime.now();
    }

    @Override
    public void run() {
        simulator.runSimulation();
    }

    public int getPlaneID() {
        return planeID;
    }

    public void requestLanding() throws InterruptedException {
        System.out.println(Main.airport.planeQueue);
        checkEmergency();
        if (isEmergency) {
            Main.airport.planeQueue.addFirst(this);
            System.out.printf("PLANE\t: PLANE %d is requesting permission for emergency land due to the fuel shortage.\n", planeID);
        } else {
            Main.airport.planeQueue.offer(this);
            System.out.printf("PLANE\t: PLANE %d is requesting permission to land.\n", planeID);
        }
        airport.runway.landingAcquire(this);
        Airport.printAction(String.format("PLANE %d is landing on the RUNWAY", planeID));
        Thread.sleep(1000);
    }

    public void requestTakeOff() throws InterruptedException {
        // Request permission to take off
        System.out.printf("PLANE\t: PLANE %d is requesting permission to take off.\n", planeID);
        airport.runway.takeOffAcquire(this);

        // Release gate and move to runway
        Airport.printAction(String.format("PLANE %d is moving to runway from GATE %c", planeID, gate.getGateID()));
        Thread.sleep(2000);
        gate.release(this);

        // Release runway and take off
        Airport.printAction(String.format("PLANE %d is taking off on the RUNWAY", planeID));
        Thread.sleep(2000);
        airport.runway.takeOffRelease(this);
    }

    public void requestAssignGate() throws InterruptedException {
        System.out.printf("PLANE\t: PLANE %d is requesting a GATE to dock.\n", planeID);
        gate = gate.acquire(this);      // Acquire gate for plane
        simulator.setGate(gate);            // Set gate for simulation
        System.out.printf("ATC\t\t: REQUEST SUCCESSFUL, Plane %d can dock to GATE %c.\n", planeID, gate.getGateID());
        Airport.printAction(String.format("PLANE %d is moving to GATE %c for docking", planeID, gate.getGateID()));
        airport.runway.landingRelease();
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void checkEmergency() {
        boolean condition1 = airport.gatesSemaphore[0].availablePermits() == 0;
        boolean condition2 = airport.gatesSemaphore[1].availablePermits() == 0;
        boolean condition3 = airport.gatesSemaphore[2].availablePermits() == 0;
        int trueCount = 0;
        if (condition1) {
            trueCount++;
        }
        if (condition2) {
            trueCount++;
        }
        if (condition3) {
            trueCount++;
        }
        if (trueCount >= 2 && Main.airport.planeQueue.size() == 2) {
            isEmergency = true;
        }
    }
}
