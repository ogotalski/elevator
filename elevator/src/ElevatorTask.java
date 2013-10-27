import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import by.epam.lab.Building;
import by.epam.lab.controller.ViewController;

public class ElevatorTask {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ViewController controller = new ViewController();
		controller.createView();
//		int storiesNumber = 1;
//		int elevatorCapacity = 1;
//		int passengersNumber = 1;
//		int animationBoost = 0;
//		try {
//			Properties prop = new Properties();
//			prop.load(new FileInputStream("config.properties"));
//			storiesNumber = Integer.parseInt(prop.getProperty("storiesNumber"));
//			elevatorCapacity = Integer.parseInt(prop
//					.getProperty("elevatorCapacity"));
//			passengersNumber = Integer.parseInt(prop
//					.getProperty("passengersNumber"));
//			animationBoost = Integer.parseInt(prop
//					.getProperty("animationBoost"));
//
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//		for (int i = 0; i < 1000; i++) {
//			Building building = Building.getBuilding(storiesNumber,
//					elevatorCapacity);
//			building.fillBuilding(passengersNumber);
//			building.startElevator(0);
//			if (!building.verify())break;
//		}
	}

}
