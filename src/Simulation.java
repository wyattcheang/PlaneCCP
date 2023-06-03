public class Simulation {
    private Airport airport;
    private Plane plane;
    private Gate gate;

    public Simulation(Plane plane) {
        this.airport = Main.airport;
        this.plane = plane;
    }

    public void runSimulation() {
        try{
            plane.requestLanding();
            long startTime = System.currentTimeMillis();
            plane.requestAssignGate();

            Fuel f = new Fuel(plane, gate);
            f.refuelAcquire();
            f.refuelRelease();
            Disembark d = new Disembark(plane, gate);
            d.start();
            RefillingCleaning rc = new RefillingCleaning(plane, gate);
            rc.start();
            Embark e = new Embark(plane, gate);
            e.start();

            plane.requestTakeOff();
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            Airport.printAction(String.format("PLANE %d has completed the landing and take of process in %d milliseconds", plane.getPlaneID(), totalTime));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }
}

