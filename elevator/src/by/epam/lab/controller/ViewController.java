package by.epam.lab.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import by.epam.lab.Building;
import by.epam.lab.Elevator;
import by.epam.lab.Floor;
import by.epam.lab.view.ControlPanel;
import by.epam.lab.view.FloorView;
import by.epam.lab.view.MainFrame;
import by.epam.lab.view.Action.ButtonActionListener;


public class ViewController implements IViewController {
	private static final int DEFAULT_SLEEP = 1000;
	private int storiesNumber = 1;
	private int elevatorCapacity = 1;
	private int passengersNumber = 1;
	private int animationBoost = 0;
	private JFrame mainFrame;
	private ControlPanel controlPanel;
	private ThreadGroup threadGroup;
	private Map<Floor,FloorView> floorsMap;
	private Building building;
	public ViewController() {
		init();
		
	}
	
	public void init() {
		Properties prop = new Properties();
		try {
	           
			prop.load(new FileInputStream("config.properties"));
            storiesNumber = Integer.parseInt(prop.getProperty("storiesNumber"));
            elevatorCapacity = Integer.parseInt(prop.getProperty("elevatorCapacity"));
            passengersNumber = Integer.parseInt(prop.getProperty("passengersNumber"));
            animationBoost = Integer.parseInt(prop.getProperty("animationBoost"));

 	} catch (IOException ex) {
 		ex.printStackTrace();
     }
		
	}
    public void createView() {
    	mainFrame = new MainFrame();
		controlPanel = new ControlPanel(storiesNumber, elevatorCapacity, passengersNumber, animationBoost);
		controlPanel.getButton().addActionListener(new ButtonActionListener(this));
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);
    }
	@Override
	public void start() {
		threadGroup = new ThreadGroup("Building");
		building = Building.getBuilding(storiesNumber, elevatorCapacity);
		building.fillBuilding(passengersNumber, animationBoost);
		if (animationBoost > 0){
			FloorView view;
			floorsMap = new HashMap<Floor, FloorView>(storiesNumber);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			
			List <Floor> floors = building.getFloors();
			Elevator elevator = building.getElevator();
			for(Floor floor : floors ){
					view = new FloorView(floor.getDispatchPassengers(), floor.getArrivalPassengers());
					panel.add(view);
					floorsMap.put(floor, view);
				}
			view = floorsMap.get(elevator.getCurrentFloor());
			view.setElevatorPassengers(elevator.getElevatorPassengers());
			panel.setVisible(true);
			//floorsMap.put(elevator.getCurrentFloor(), view);
			JScrollPane pane = new JScrollPane(panel);
			mainFrame.add(pane);
		}
		controlPanel.getButton().setText("Abort");
		new Thread(threadGroup,this).start();
		//this.run();
		
	}
    public void updateView(){
    	
    }
	@Override
	public void abort() {
		JOptionPane.showMessageDialog(mainFrame, "abort");
		
	}

	@Override
	public void view() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		
		
		building.startElevator();
		if (building.verify()){
			JOptionPane.showMessageDialog(mainFrame, "Verify complite");
		}else {
			JOptionPane.showMessageDialog(mainFrame, "Verify error", "Verify",  JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
