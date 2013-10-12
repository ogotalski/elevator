package by.epam.lab;

import java.util.ArrayList;

import java.util.List;

public class Elevator {
	private Floor currentFloor;
	public final int capacity;

	private final List<Passenger> elevatorContainer;

	public Elevator(Floor floor) {
		super();
		capacity = 1;
		currentFloor = floor;
		elevatorContainer = new ArrayList<Passenger>(capacity);
	}

	public Elevator(Floor currentFloor, int capacity) {
		super();
		this.currentFloor = currentFloor;
		this.capacity = capacity;
		elevatorContainer = new ArrayList<Passenger>(capacity);
	}

	public void move(Floor floor) {
		currentFloor = floor;
	}

	public Floor getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(Floor currentFloor) {
		this.currentFloor = currentFloor;
	}

	public synchronized boolean hasPlaces() {
		if (capacity >= elevatorContainer.size())
			return true;
		else
			return false;
	}

	public boolean addPassenger(Passenger passenger) {
		if (passenger.getCurrentFloor().equals(currentFloor) && hasPlaces()) {
			elevatorContainer.add(passenger);
			return true;
		} else {
			return false;
		}
}

	public boolean removePassenger(Passenger passenger) {
			if (passenger.getDestFloor().equals(currentFloor)) {
				elevatorContainer.remove(passenger);
				return true;
			} else
				return false;
		}
		
}
