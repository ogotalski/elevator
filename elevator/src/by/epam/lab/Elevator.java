package by.epam.lab;

import static by.epam.lab.utils.AppLogger.*;
import java.util.ArrayList;

import java.util.List;

public class Elevator {
	private Floor currentFloor;
	private final int capacity;

	public int getCapacity() {
		return capacity;
	}

	private final List<Passenger> elevatorContainer;

	public Elevator(Floor floor) {
		super();
		capacity = 1;
		currentFloor = floor;
		elevatorContainer = new ArrayList<Passenger>(capacity);
	}

	public Elevator(Floor currentFloor, int capacity) {
		super();
		if (capacity < 1)
			throw new IllegalArgumentException(
					"Elevator capacity must be greater than 0");
		this.currentFloor = currentFloor;
		this.capacity = capacity;
		elevatorContainer = new ArrayList<Passenger>(capacity);
	}

	public synchronized void move(Floor floor) {
		LOG.info("MOVING_ELEVATOR (from " + currentFloor + " to " + floor + ")");
		currentFloor = floor;
		LOG.trace("=========================================================");
		for (Passenger passenger : elevatorContainer)
			LOG.trace("ELEVATOR MOVED " + currentFloor.getId() + passenger);
		LOG.trace("=========================================================");
	}

	public List<Passenger> getElevatorContainer() {
		return elevatorContainer;
	}

	public synchronized Passenger[] getElevatorPassengers() {
		return elevatorContainer
				.toArray(new Passenger[elevatorContainer.size()]);
	}

	public synchronized Floor getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(Floor currentFloor) {
		this.currentFloor = currentFloor;
	}

	public boolean hasPlaces() {
		if (capacity > elevatorContainer.size())
			return true;
		else
			return false;
	}

	public synchronized boolean addPassenger(Passenger passenger) {
		if (passenger.getCurrentFloor().equals(currentFloor) && hasPlaces()) {
			elevatorContainer.add(passenger);
			LOG.info("BOADING_OF_PASSENGER ( " + passenger.getId()
					+ " on story " + currentFloor.getId() + ")");
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean removePassenger(Passenger passenger) {
		if (passenger.getDestFloor().equals(currentFloor)) {
			passenger.setCurrentFloor(currentFloor);
			elevatorContainer.remove(passenger);
			LOG.info("DEBOADING_OF_PASSENGER ( " + passenger.getId()
					+ " on story " + currentFloor.getId() + ")");
			return true;
		} else
			return false;
	}

}
