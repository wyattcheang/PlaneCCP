import java.util.Random;

public class RefillingCleaning extends Thread {
    private Plane plane;
    private Gate gate;

    public RefillingCleaning(Plane plane, Gate gate) {
        this.plane = plane;
        this.gate = gate;
    }

    @Override
    public void run() {
        int percentage = 0;
        Airport.printAction(String.format("PLANE %d starts cleaning and refilling supplies at GATE %c", plane.getPlaneID(), gate.getGateID()));
        try {
            Thread.sleep(new Random().nextInt(301) + 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        while (percentage < 100){
//            percentage += 20;
//            try {
//                Thread.sleep(new Random().nextInt(301) + 100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Airport.printAction(String.format("PLANE %s cleaning and refilling process: %d%%", plane.getPlaneID(), percentage));
//        }
        Airport.printAction(String.format("The cleaning and refilling of supplies for PLANE %d has been completed", plane.getPlaneID()));
    }
}
