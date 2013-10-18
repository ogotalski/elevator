package by.epam.lab;

import javax.sql.rowset.spi.SyncResolver;

public class TransportationTask implements Runnable {
	private Controller controller;
	private final Passenger passenger;
    
    
	public TransportationTask(Controller controller, Passenger passenger) {
		super();
		this.controller = controller;
		this.passenger = passenger;
	}

	@Override
	public void run() {
		synchronized (passenger) {
			passenger.setTransportationState(TransportationState.IN_PROGRESS);
		}

		try {
			 { 
				 synchronized (controller) {
					 while (!controller.isWorking()){
					controller.wait();
				}
				 }
				while (!controller.addPassenger(passenger)) {
					
					passenger.getCurrentFloor().wait();
					
				}
				
				while (!controller.removePassenger(passenger)) {
					
					passenger.getDestFloor().wait();
					
				}
				
			}
			synchronized (passenger) {
				passenger.setTransportationState(TransportationState.COMPLETED);
			}
		} catch (InterruptedException e) {
			synchronized (passenger) {
				passenger.setTransportationState(TransportationState.ABORTED);

			}
		}

	}

}
