import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Airport {
	public final int NUM_PLANES = 6;
	public final int NUM_GATES = 3;
	public final Semaphore[] gatesSemaphore = new Semaphore[3];
	public Semaphore fuelSemaphore;
	public final Lock refuelTruck = new ReentrantLock();
	public final Deque<Plane> planeQueue = new ArrayDeque<>();
	public final ArrayList<Gate> gatesList = new ArrayList<>();
	public Process atc;
	public Runway runway;
	public Fuel fuel;
	public Semaphore[] getAllGateSemaphore() {
		return gatesSemaphore;
	}

//	public void addPlane(Plane plane){
//		synchronized (planeQueue){
//			if (planeQueue.size() == 2 &&(
//					gatesSemaphore[0].availablePermits() == 0 &&
//							gatesSemaphore[1].availablePermits() == 0 &&
//							gatesSemaphore[2].availablePermits() == 0
//			)){
//				planeQueue.addFirst(plane);
//
//			}else{
//				planeQueue.addLast(plane);
//			}
//		}
//		if (planeQueue.size() == 1){
//			planeQueue.notify();
//		}
//	}

//	public static String printBorder(int amount){
//		char c = '='; // the character to repeat
//		char[] arr = new char[amount];
//		Arrays.fill(arr, c);
//
//		return (new String(arr) + '\n');
//	}

	public static void printAction(String printValue) {
		synchronized (System.out) {
			int length = printValue.length();
			char c = '='; // the character to repeat
			char[] arr = new char[length + 2];
			Arrays.fill(arr, c);
			System.out.println();
			System.out.println("+" + new String(arr) + "+");
			System.out.println("| " + printValue + " |");
			System.out.println("+" + new String(arr) + "+");
		}
	}
}
