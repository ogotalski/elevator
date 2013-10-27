package by.epam.lab;
import static by.epam.lab.utils.AppLogger.*;
public class TransportationTask implements Runnable {
	private static int readyThreads = 0;
	private Controller controller;
	private final Passenger passenger;

	public TransportationTask(Controller controller, Passenger passenger) {
		super();
		this.controller = controller;
		this.passenger = passenger;
	}

	public synchronized static void incReadyThreads() {
		readyThreads++;
	}

	public synchronized static int getReadyThreads() {
		return readyThreads;
	}

	public synchronized static void decReadyThreads() {
		readyThreads--;
	}

	public synchronized static void setReadyThreads(int i) {
		readyThreads = i;
	}

	@Override
	public void run() {

		passenger.setTransportationState(TransportationState.IN_PROGRESS);

		try {
			Floor floor = passenger.getCurrentFloor();
			Object waitObject = floor.getDispatchStoryContainer();
			synchronized (controller) {
				decReadyThreads();
				LOG.trace("ready " + passenger);
				//System.out.println("ready " + passenger);
				controller.notifyAll();

			}
			synchronized (waitObject) {
				while (!controller.isWorking())
					waitObject.wait();
			}

			synchronized (waitObject) {
				LOG.trace(passenger + " want to go");
				while (!controller.addPassenger(passenger)) {
					LOG.trace(passenger + " go sleep1 "
							+ Thread.currentThread().getId());
					waitObject.wait();
					LOG.trace(passenger + " want to go");
				}
			}
			floor = passenger.getDestFloor();
			waitObject = floor.getArrivalStoryContainer();
			synchronized (waitObject) {
				LOG.trace(passenger + " want to out");
				while (!controller.removePassenger(passenger)) {
					LOG.trace(passenger + " go sleep2 "
							+ Thread.currentThread().getId());
					waitObject.wait();
					LOG.trace(passenger + " want to out");
				}
				passenger.setTransportationState(TransportationState.COMPLETED);
			}

		} catch (InterruptedException e) {
			passenger.setTransportationState(TransportationState.ABORTED);
			LOG.trace("Transportation  interrupded " + passenger);
			
		}

	}
}
