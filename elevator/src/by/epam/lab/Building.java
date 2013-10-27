package by.epam.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static by.epam.lab.utils.AppLogger.*;

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
		if (storiesNumber <= 1)
			throw new IllegalArgumentException(
					"Stories number must be greater than 1");
		if (elevatorCapacity <= 0)
			throw new IllegalArgumentException(
					"Elevator capacity must be greater than 0");

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
		if (passengersNumber < 1)
			throw new IllegalArgumentException(
					"Passengers number must be greater than 0");

		Floor[] floorsArr = floors.toArray(new Floor[floors.size()]);
		Random random = new Random();
		Floor curFloor, destFloor;
		int destId, curId;
		int size = floorsArr.length;
		for (int i = 0; i < passengersNumber; i++) {
			curId = random.nextInt(size);
			curFloor = floorsArr[curId];
			while ((destId = random.nextInt(size)) == curId)
				;
			destFloor = floorsArr[destId];
			new Passenger(curFloor, destFloor);
		}
	}

	public void startElevator(int animationBoost) throws InterruptedException {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		for (Floor floor : floors) {
			for (Passenger passenger : floor.getDispatchStoryContainer()) {
				TransportationTask.incReadyThreads();
				new Thread(group, new TransportationTask(controller, passenger))
						.start();

			}
		}

		synchronized (controller) {
			while (TransportationTask.getReadyThreads() != 0)
				controller.wait();

		}
		controller.doJob(animationBoost);

	}

	public boolean verify() {
		boolean isOk = true;
		synchronized (controller) {

			for (Floor floor : floors) {
				for (Passenger passenger : floor.getArrivalStoryContainer()) {
					if (passenger.getCurrentFloor() != passenger.getDestFloor()
							|| passenger.getTransportationState() != TransportationState.COMPLETED) {
						isOk = false;
						LOG.error("VERIFY ERROR :" + passenger);
					}

				}
				if (!floor.getDispatchStoryContainer().isEmpty()) {
					LOG.error("VERIFY ERROR :" + floor.getId() + "size "
							+ floor.getDispatchStoryContainer().size());
					isOk = false;
				}

			}
		}
		if (isOk)
			LOG.info("VERIFY COMLETED");
		else
			LOG.error("VERIFY ERROR");

		return isOk;
	}

}
