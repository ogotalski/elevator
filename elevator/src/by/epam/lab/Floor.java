package by.epam.lab;

import java.util.ArrayList;
import java.util.List;

public class Floor implements Comparable<Floor> {
	private static int nextId = 1;
	private final int id;
	private List<Passenger> dispatchStoryContainer;
	private List<Passenger> arrivalStoryContainer;

	public Floor() {
		super();
		synchronized (Floor.class) {
			id = nextId++;
		}
		dispatchStoryContainer = new ArrayList<Passenger>();
		arrivalStoryContainer = new ArrayList<Passenger>();
	}

	public List<Passenger> getDispatchStoryContainer() {
		return dispatchStoryContainer;
	}

	public void setDispatchStoryContainer(List<Passenger> dispatchStoryContainer) {
		this.dispatchStoryContainer = dispatchStoryContainer;
	}

	public List<Passenger> getArrivalStoryContainer() {
		return arrivalStoryContainer;
	}

	public void setArrivalStoryContainer(List<Passenger> arrivalStoryContainer) {
		this.arrivalStoryContainer = arrivalStoryContainer;
	}

	public int getId() {
		return id;
	}

	public void addArrivalPassenger(Passenger passenger) {
		arrivalStoryContainer.add(passenger);
	}

	public void removeDispatchPassenger(Passenger passenger) {
		dispatchStoryContainer.remove(passenger);
	}

	public void addDispatchPassenger (Passenger passenger){
		dispatchStoryContainer.add(passenger);
	}
	public boolean hasPassengers() {
		if (dispatchStoryContainer.size() > 0)
			return true;
		else
			return false;
	}

	@Override
	public int compareTo(Floor o) {
		return o == null ? 1 : id - o.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Floor ");
		builder.append(id);
		return builder.toString();
	}

}
