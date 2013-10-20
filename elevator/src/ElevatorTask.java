

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

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
		doJob(storiesNumber, elevatorCapacity, passengersNumber, animationBoost);
		
		Random random = new Random();
		boolean flag = true;
		boolean allOK = true;
		PrintWriter writer = null;
		try {
			 writer = new PrintWriter("out.txt");
		
		for (int i = 0 ; i<10000 ; i++){
			
			storiesNumber =1 + random.nextInt(100);
			elevatorCapacity =1 + random.nextInt(100);
			passengersNumber = 1 + random.nextInt(10000);
			writer.printf("start i = %d storiesNumber  = %d, elevatorCapacity  = %d, passengersNumber = %d, animationBoost = %d\n", i,storiesNumber, elevatorCapacity, passengersNumber, animationBoost);
			writer.flush();
			flag = doJob(storiesNumber, elevatorCapacity, passengersNumber, animationBoost);
			writer.printf("end i = %d storiesNumber  = %d, elevatorCapacity  = %d, passengersNumber = %d, animationBoost = %d veryfy = %b\n", i,storiesNumber, elevatorCapacity, passengersNumber, animationBoost,flag);
		    writer.flush();
			if (!flag) allOK = false;
		
		}
		 System.out.println("ALL OK "+ allOK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		  if (writer != null) writer.close();	
		}
		
		
	}
	
	public static boolean doJob(int storiesNumber, int elevatorCapacity, int passengersNumber, int animationBoost){
		Building building = Building.getBuilding(storiesNumber, elevatorCapacity);
		building.fillBuilding(passengersNumber, animationBoost);
		building.startElevator();
		return building.verify();
	}

}
