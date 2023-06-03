import java.util.Random;

public class Embark extends Thread {
    private Plane plane;
    private Gate gate;

    public Embark(Plane plane, Gate gate) {
        this.plane = plane;
        this.gate = gate;
    }

    @Override
    public void run() {
        int passenger = 0;
        Airport.printAction(String.format("PLANE %d starts to embark the passengers at GATE %c", plane.getPlaneID(), gate.getGateID()));
        try {
            Thread.sleep(new Random().nextInt(301) + 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        while (passenger < 100){
////            int passengersBoarding = new Random().nextInt(51); // Generate random number of passengers between 0 and 50
//            passenger += 20;
//            try {
//                Thread.sleep(new Random().nextInt(301) + 100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Airport.printAction(String.format("The Embarkation process for PLANE %d: %d%%", plane.getPlaneID(), passenger));
//        }
        Airport.printAction(String.format("The Embarkation process for PLANE %d has been completed", plane.getPlaneID()));
    }
}
