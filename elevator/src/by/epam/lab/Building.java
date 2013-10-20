package by.epam.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Building {
      private final List<Floor> floors;
      private final Elevator elevator;
      private final Controller controller;
      public Building(List<Floor> floors, Elevator elevator) {
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
	
	public static Building getBuilding(int storiesNumber, int elevatorCapacity){
		
		List<Floor> floorList = new ArrayList<Floor>(storiesNumber);
		Floor floor = new Floor();
		Elevator elevator = new Elevator(floor, elevatorCapacity);
		for (int i = 1; i < storiesNumber; i++ ){
			floorList.add(new Floor());
		}
		return new Building(floorList, elevator); 
		
	}
	
	public void fillBuilding( int passengersNumber, int animationBoost){
		
		Floor[] floorsArr = floors.toArray(new Floor[floors.size()]);
		Random random = new Random();
		Floor curFloor, destFloor;
		
		int size = floorsArr.length;
		for (int i = 0; i< passengersNumber; i++){
			curFloor = floorsArr[random.nextInt(size)];
			destFloor = floorsArr[random.nextInt(size)];
			new Passenger(curFloor,destFloor);
		}
	}
	
	public void startElevator(){
		
		for(Floor floor: floors){
			for(Passenger passenger : floor.getDispatchStoryContainer()){
				TransportationTask.incReadyThreads();
				new Thread(new TransportationTask(controller, passenger)).start();
				
			}
		}
		try {
			
		synchronized (controller) {
			while (TransportationTask.getReadyThreads() != 0)
				controller.wait();
			
		}
		System.out.println("controller start ");
		controller.doJob();
		} catch (InterruptedException e) {
		}
	}
	
	public boolean verify(){
		boolean isOk = true;
		for(Floor floor : floors){
			for(Passenger passenger: floor.getArrivalStoryContainer()){
				if (passenger.getCurrentFloor() != passenger.getDestFloor() || passenger.getTransportationState() != TransportationState.COMPLETED) isOk = false;
			}
			if (!floor.getDispatchStoryContainer().isEmpty()) isOk = false; 
		}
		System.out.println("verify = " + isOk);
		return isOk;
	}
      
}
