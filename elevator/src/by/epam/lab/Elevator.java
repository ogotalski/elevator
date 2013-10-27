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
	if (capacity < 1) throw new IllegalArgumentException("Elevator capacity must be greater than 0"); 
	this.currentFloor = currentFloor;
	this.capacity = capacity;
	elevatorContainer = new ArrayList<Passenger>(capacity);
    }

    public synchronized void  move(Floor floor) {
	currentFloor = floor;
	System.out
		.println("=========================================================");
	for (Passenger passenger : elevatorContainer)
	    System.out.println("ELEVATOR MOVED " + currentFloor.getId()
		    + passenger);
	System.out
		.println("=========================================================");
    }

    public List<Passenger> getElevatorContainer() {
		return elevatorContainer;
	}
    public synchronized Passenger[] getElevatorPassengers() {
		return elevatorContainer.toArray(new Passenger[elevatorContainer.size()]);
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
	    System.out.println("ELEVATOR IN passenger " + passenger
		    + " places " + elevatorContainer.size() + "/" + capacity
		    + " floor " + currentFloor.getId());

	    return true;
	} else {
	    return false;
	}
    }

    public synchronized boolean removePassenger(Passenger passenger) {
	if (passenger.getDestFloor().equals(currentFloor)) {
	    passenger.setCurrentFloor(currentFloor);
	    elevatorContainer.remove(passenger);
	    System.out.println("ELEVATOR OUT passenger " + passenger
		    + " places " + elevatorContainer.size() + " floor "
		    + currentFloor.getId());

	    return true;
	} else
	    return false;
    }

}
