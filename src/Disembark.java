import java.util.Random;

public class Disembark extends Thread {
    private Plane plane;
    private Gate gate;

    public Disembark(Plane plane, Gate gate) {
        this.plane = plane;
        this.gate = gate;
    }

    @Override
    public void run() {
//        int passenger = new Random().nextInt(16) + 35;  // Generate random number of passengers between 35 and 50
        int count = 0;
        Airport.printAction(String.format("PLANE %d starts to disembark the passengers at GATE %c", plane.getPlaneID(), gate.getGateID()));
        try {
            Thread.sleep(new Random().nextInt(301) + 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        while (count < 100){
//            count += 20;
//            try {
//                Thread.sleep(new Random().nextInt(301) + 100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Airport.printAction(String.format("The disembarkation process for PLANE %d: %d%%", plane.getPlaneID(), count));
//        }
        Airport.printAction(String.format("The disembarkation process for PLANE %d has been completed, Total %d passenger.", plane.getPlaneID(), count));
    }
}
