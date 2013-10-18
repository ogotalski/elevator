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
		System.out.println("ELEVATOR MOVED " + currentFloor.getId());
	}

	public Floor getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(Floor currentFloor) {
		this.currentFloor = currentFloor;
	}

	public boolean hasPlaces() {
		if (capacity >= elevatorContainer.size())
			return true;
		else
			return false;
	}

	public boolean addPassenger(Passenger passenger) {
		if (passenger.getCurrentFloor().equals(currentFloor) && hasPlaces()) {
			elevatorContainer.add(passenger);
			System.out.println("ELEVATOR IN passenger " + passenger.getId() + "("+passenger.getCurrentFloor().getId()+"/"+passenger.getDestFloor().getId()+")" + " places " + elevatorContainer.size() + " floor " + currentFloor );

			return true;
		} else {
			return false;
		}
}

	public boolean removePassenger(Passenger passenger) {
			if (passenger.getDestFloor().equals(currentFloor)) {
				passenger.setCurrentFloor(currentFloor);
				elevatorContainer.remove(passenger);
				System.out.println("ELEVATOR OUT passenger " + passenger.getId()+ "("+passenger.getCurrentFloor().getId()+"//"+passenger.getDestFloor().getId()+")" + " places " + elevatorContainer.size() + " floor " + currentFloor );

				return true;
			} else
				return false;
		}
		
}
