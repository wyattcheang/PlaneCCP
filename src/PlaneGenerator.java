import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlaneGenerator implements Runnable{
    private final Airport airport;
    public PlaneGenerator(Airport airport) {
        this.airport = airport;
    }

    @Override
    public void run() {
        for(int i = 1; i <= airport.NUM_PLANES; i++){
            Plane plane = new Plane(i, airport);
            Thread threadPlane = new Thread(plane);
            try{
                int time = new Random().nextInt(4);
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            threadPlane.start();
        }
    }
}
