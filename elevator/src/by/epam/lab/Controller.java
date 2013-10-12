package by.epam.lab;

import java.util.Iterator;
import java.util.List;

public class Controller {
     private final Building building;
     private final Elevator elevator;
	public Controller(Building building, Elevator elevator) {
		super();
		this.building = building;
		this.elevator = elevator;
	}
	
	
	
    public Building getBuilding() {
		return building;
	}



	public Elevator getElevator() {
		return elevator;
	}
    public Floor getFloor(){
    	return elevator.getCurrentFloor();
    }
	public boolean addPassenger(Passenger passenger){
    	return elevator.addPassenger(passenger);
    }
    public boolean removePassenger(Passenger passenger){
    	return elevator.removePassenger(passenger);
    }
    public void goNextFloor(Floor floor){
    	while (elevator.hasPlaces()){
    		elevator.move(floor);
    	 	}
    	floor.getArrivalStoryContainer().notifyAll();
    	floor.getDispatchStoryContainer().notifyAll();
    }
    public void doJob(){
    	List<Floor> floors =building.getFloors();
    	Iterator<Floor> itr = floors.iterator(); //listiterator
    	Floor floor;
    	while(itr.hasNext()){
    		floor = itr.next();
    		goNextFloor(floor);
    	}
    }
}
