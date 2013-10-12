package by.epam.lab;

import java.util.List;

public class Building {
      private final List<Floor> Floors;
      private final Elevator elevator;
	public Building(List<Floor> floors, Elevator elevator) {
		super();
		Floors = floors;
		this.elevator = elevator;
	}
	public List<Floor> getFloors() {
		return Floors;
	}
	public Elevator getElevator() {
		return elevator;
	}
	
      
}
