package by.epam.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Building {
	private final List<Floor> floors;
	private final Elevator elevator;
	private final Controller controller;

	private Building(List<Floor> floors, Elevator elevator) {
		super();
		
		this.floors = floors;
		this.elevator = elevator;
		controller = new Controller(this, getElevator());
		
	}

	public List<Floor> getFloors() {
		return floors;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public static Building getBuilding(int storiesNumber, int elevatorCapacity) {
        if (storiesNumber < 1) throw new IllegalArgumentException("Stories number must be greater than 0"); 
        if (elevatorCapacity < 1) throw new IllegalArgumentException("Elevator capacity must be greater than 0"); 
		
        List<Floor> floorList = new ArrayList<Floor>(storiesNumber);
		Floor floor = new Floor();
		Elevator elevator = new Elevator(floor, elevatorCapacity);
		floorList.add(floor);
		for (int i = 1; i < storiesNumber; i++) {
			floorList.add(new Floor());
		}
		return new Building(floorList, elevator);

	}

	public void fillBuilding(int passengersNumber) {
		if (passengersNumber < 1) throw new IllegalArgumentException("Passengers number must be greater than 0"); 
		
		Floor[] floorsArr = floors.toArray(new Floor[floors.size()]);
		Random random = new Random();
		Floor curFloor, destFloor;

		int size = floorsArr.length;
		for (int i = 0; i < passengersNumber; i++) {
			curFloor = floorsArr[random.nextInt(size)];
			destFloor = floorsArr[random.nextInt(size)];
			new Passenger(curFloor, destFloor);
		}
	}

	public void startElevator(int animationBoost) {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		for (Floor floor : floors) {
			for (Passenger passenger : floor.getDispatchStoryContainer()) {
				TransportationTask.incReadyThreads();
				new Thread(group, new TransportationTask(controller, passenger))
						.start();

			}
		}
		try {

			synchronized (controller) {
				while (TransportationTask.getReadyThreads() != 0)
					controller.wait();

			}
			System.out.println("controller start ");
			controller.doJob(animationBoost);
		} catch (InterruptedException e) {
		}
	}

	public boolean verify() {
		boolean isOk = true;
		synchronized (controller) {
			
		for (Floor floor : floors) {
			for (Passenger passenger : floor.getArrivalStoryContainer()) {
				if (passenger.getCurrentFloor() != passenger.getDestFloor()
						|| passenger.getTransportationState() != TransportationState.COMPLETED) {
					isOk = false;
					System.out.println(passenger);
				}

			}
			if (!floor.getDispatchStoryContainer().isEmpty()) {
				System.out.println(floor + "size " + floor.getDispatchStoryContainer().size());
				isOk = false;
			}

		}
		}
		
		System.out.println("verify = " + isOk);
		return isOk;
	}

}
