package by.epam.lab;

import java.util.Iterator;
import java.util.List;

import by.epam.lab.utils.ReverseIterator;

public class Controller {
	private enum Direction {
		UP(1), DOWN(-1);
		private final int intDirection;

		private Direction(int i) {
			intDirection = i;
		}
	}

	private final Building building;
	private final Elevator elevator;
	private Direction direction;
	private Floor nextFloor;
	private int outOnNextFloor;

	public Controller(Building building, Elevator elevator) {
		super();
		this.building = building;
		this.elevator = elevator;
		this.nextFloor = elevator.getCurrentFloor();
		this.outOnNextFloor = 0;
	}

	public Building getBuilding() {
		return building;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public Floor getFloor() {
		return elevator.getCurrentFloor();
	}

	public boolean addPassenger(Passenger passenger) {
		Floor passengerFloor = passenger.getCurrentFloor();

		synchronized (elevator) {
			if (!elevator.addPassenger(passenger))
				return false;
		}
		synchronized (this) {

			int i = nextFloor.compareTo(passengerFloor);
			if (i == 0) {
				outOnNextFloor++;
			} else if ((direction.intDirection) * i > 0) {
				nextFloor = passengerFloor;
				outOnNextFloor = 1;
			}
		}
		synchronized (passengerFloor) {
			passengerFloor.removeDispatchPassenger(passenger);
		}
		this.notifyAll();
		return true;
	}

	public boolean removePassenger(Passenger passenger) {
		Floor passengerFloor = passenger.getDestFloor();

		synchronized (elevator) {
			if (!elevator.removePassenger(passenger))
				return false;
		}
		synchronized (this) {
			outOnNextFloor--;
		}
		this.notifyAll();
		synchronized (passengerFloor) {
			passengerFloor.addArrivalPassenger(passenger);
		}
		return true;
	}

	public void goNextFloor(Floor floor) throws InterruptedException {

		elevator.move(floor);
		floor.getArrivalStoryContainer().notifyAll();
		while (outOnNextFloor != 0) {
			this.wait();
		}
		floor.getDispatchStoryContainer().notifyAll();
		while (elevator.hasPlaces() || floor.hasPassengers()) {
			this.wait();
		}
	}

	public void doJob() {
		List<Floor> floors = building.getFloors();
		Iterator<Floor> itr = floors.iterator();
		Floor floor;
		boolean isElevatorMoved = true;
		try {
			while (isElevatorMoved) {
				isElevatorMoved = false;
				while (itr.hasNext()) {
					floor = elevator.getCurrentFloor();
					if (floor.hasPassengers()) {
						floor.getDispatchStoryContainer().notifyAll();
						while (elevator.hasPlaces() || floor.hasPassengers()) {
							this.wait();
						}
					}
					while (itr.hasNext()) {
						floor = itr.next();
						if (floor.equals(nextFloor) || (elevator.hasPlaces()
								&& floor.hasPassengers())){
							elevator.move(floor);
							isElevatorMoved = true;
							break;
						}
					}
					floor.getArrivalStoryContainer().notifyAll();
					while (outOnNextFloor != 0) {
						this.wait();
					}
				}
				switch (direction) {
				case UP:
					itr = new ReverseIterator<Floor>(floors);
					direction = Direction.DOWN;
					break;
				case DOWN:
					itr = floors.iterator();
					direction = Direction.UP;
					break;
				}
			}
		} catch (InterruptedException e) {

		}
	}
}
