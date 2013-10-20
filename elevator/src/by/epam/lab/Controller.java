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
	private boolean working;

	public Controller(Building building, Elevator elevator) {
		super();
		this.building = building;
		this.elevator = elevator;
		this.nextFloor = elevator.getCurrentFloor();
		this.outOnNextFloor = 0;
		this.working = false;
		this.direction = Direction.UP;
	}

	public boolean isWorking() {
		return working;
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

	public void changeNextFloor() {
		Floor passengerFloor;
		nextFloor = null;
		outOnNextFloor = 0;
		for (Passenger passenger : elevator.getElevatorContainer()) {
			passengerFloor = passenger.getDestFloor();
			if (nextFloor == null)
				nextFloor = passengerFloor;
			int i = nextFloor.compareTo(passengerFloor);
			if (i == 0) {
				outOnNextFloor++;
			} else if ((direction.intDirection) * i > 0) {
				nextFloor = passengerFloor;
				outOnNextFloor = 1;
			}
		}
		System.out.println("NEW NEXT FLOOR " + nextFloor);
	}

	public boolean addPassenger(Passenger passenger) {
		Floor passengerFloor = passenger.getCurrentFloor();
		TransportationTask.decReadyThreads();
		synchronized (this) {
			try {
				if (elevator.getCurrentFloor().compareTo(
						passenger.getDestFloor())
						* direction.intDirection > 0)
					return false;
				if (!elevator.addPassenger(passenger))
					return false;

				passengerFloor.removeDispatchPassenger(passenger);
			} finally {
				this.notify(); // maybe better notifyAll?
			}
		}
		return true;
	}

	public boolean removePassenger(Passenger passenger) {
		Floor passengerFloor = passenger.getDestFloor();
		synchronized (this) {
			try {
				if (!elevator.removePassenger(passenger))
					return false;

				outOnNextFloor--;
				this.notify();
			} finally {
				passengerFloor.addArrivalPassenger(passenger);
			}
		}
		return true;
	}

	public void doJob() {
		List<Floor> floors = building.getFloors();
		Iterator<Floor> itr = floors.iterator();
		Floor floor;
		int isElevatorMoved = 0;
		working = true;
		Object waitObject;
		try {
			while (isElevatorMoved < 2) {
				while (itr.hasNext()) {
					floor = elevator.getCurrentFloor();
					waitObject = floor.getDispatchStoryContainer();
					synchronized (waitObject) {
						if (floor.hasPassengers()) {
							TransportationTask.setReadyThreads(floor
									.getDispatchStoryContainer().size());
							waitObject.notifyAll();

						}
					}
					synchronized (this) {
						while (TransportationTask.getReadyThreads() != 0) {
							System.out.println("Controller go sleep " + floor);
							this.wait();
							System.out.println("Controller wakeup " + floor);
						}
						changeNextFloor();

					}

					System.out.println("Controller search nextFloor");
					while (itr.hasNext()) {
						floor = itr.next();
						if (floor.equals(nextFloor)
								|| (elevator.hasPlaces() && floor
										.hasPassengers())) {
							
							isElevatorMoved = 0;
							waitObject = floor.getArrivalStoryContainer();
							synchronized (waitObject) {
								elevator.move(floor);
								waitObject.notifyAll();
							}
							if (floor.compareTo(nextFloor) == 0) {
								synchronized (this) {
									while (outOnNextFloor != 0) {
										this.wait();
									}
								}
							}
							break;
						}
					}

				}
				switch (direction) {
				case UP:
					itr = new ReverseIterator<Floor>(floors);
					direction = Direction.DOWN;
					isElevatorMoved++;
					break;
				case DOWN:
					itr = floors.iterator();
					direction = Direction.UP;
					isElevatorMoved++;
					break;
				}
			}
		} catch (InterruptedException e) {

		}
	}
}
