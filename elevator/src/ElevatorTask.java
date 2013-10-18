

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import by.epam.lab.Building;

public class ElevatorTask {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties prop = new Properties();
		int storiesNumber = 0;
		int elevatorCapacity = 0;
		int passengersNumber = 0;
		int animationBoost = 0;
		try {
           
			prop.load(new FileInputStream("config.properties"));

           
             storiesNumber = Integer.parseInt(prop.getProperty("storiesNumber"));
             elevatorCapacity = Integer.parseInt(prop.getProperty("elevatorCapacity"));
             passengersNumber = Integer.parseInt(prop.getProperty("passengersNumber"));
             animationBoost = Integer.parseInt(prop.getProperty("animationBoost"));

 	} catch (IOException ex) {
 		ex.printStackTrace();
     }
		
		Building building = Building.getBuilding(storiesNumber, elevatorCapacity);
		building.fillBuilding(passengersNumber, animationBoost);
		building.startElevator();

	}

}
