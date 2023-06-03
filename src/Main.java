import java.util.concurrent.Semaphore;


public class Main {
    public static Airport airport = new Airport();

    public static void main(String[] args) {
        airport.runway = new Runway();

        for (int i = 0; i < airport.NUM_GATES; i++){
            airport.gatesSemaphore[i] = new Semaphore(1, true);
        }

        airport.fuelSemaphore = new Semaphore(1, true);

        // Create Three Gates
        airport.gatesList.add(new Gate('A', airport.gatesSemaphore[0], airport));
        airport.gatesList.add(new Gate('B', airport.gatesSemaphore[1], airport));
        airport.gatesList.add(new Gate('C', airport.gatesSemaphore[2], airport));

        // Create Generator Plane
        PlaneGenerator generatePlane = new PlaneGenerator(airport);

        // Create Gates Threads
        Thread threadGeneratePlane = new Thread(generatePlane);
        Thread threadGateA = new Thread(airport.gatesList.get(0));
        Thread threadGateB = new Thread(airport.gatesList.get(1));
        Thread threadGateC = new Thread(airport.gatesList.get(2));

        // Start Threads
        threadGateA.start();
        threadGateB.start();
        threadGateC.start();
        System.out.println(threadGateA.isAlive());
        threadGeneratePlane.start();
    }
}
