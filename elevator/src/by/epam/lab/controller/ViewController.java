package by.epam.lab.controller;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import by.epam.lab.Building;
import by.epam.lab.Elevator;
import by.epam.lab.Floor;
import by.epam.lab.utils.ReverseIterator;
import by.epam.lab.utils.TextAreaAppender;
import by.epam.lab.view.ControlPanel;
import by.epam.lab.view.FloorView;
import by.epam.lab.view.LogViewFrame;
import by.epam.lab.view.MainFrame;
import by.epam.lab.view.Action.ButtonActionListener;
import by.epam.lab.view.Action.ButtonActionListener.ButtonActions;
import by.epam.lab.view.Action.UpdateListener;

import static by.epam.lab.utils.AppLogger.*;

public class ViewController implements IViewController {
	private static final int SLEEP_MULTIPLIER = 25;
	private static final int DEFAULT_SLEEP = 1000;
	private int storiesNumber = 1;
	private int elevatorCapacity = 1;
	private int passengersNumber = 1;
	private int animationBoost = 0;
	private MainFrame mainFrame;
	private ControlPanel controlPanel;
	private ThreadGroup threadGroup;
	private Map<Floor, FloorView> floorsMap;
	private Building building;
	private Timer timer;

	public ViewController() {
		init();

	}

	public void init() {
		Properties prop = new Properties();
		try {

			prop.load(new FileInputStream("config.properties"));
			storiesNumber = Integer.parseInt(prop.getProperty("storiesNumber"));
			elevatorCapacity = Integer.parseInt(prop
					.getProperty("elevatorCapacity"));
			passengersNumber = Integer.parseInt(prop
					.getProperty("passengersNumber"));
			animationBoost = Integer.parseInt(prop
					.getProperty("animationBoost"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void createView() {
		mainFrame = new MainFrame();
		int maxBoost = DEFAULT_SLEEP / SLEEP_MULTIPLIER;
		controlPanel = new ControlPanel(storiesNumber, elevatorCapacity,
				passengersNumber, animationBoost, maxBoost);
		controlPanel.addButtonActionListener(new ButtonActionListener(this));
		controlPanel.setButtonAction(ButtonActions.START_ACTION);
		mainFrame.addComponent(controlPanel);
		mainFrame.setVisible(true);
	}

	@Override
	public void start() {
		try {
			getParamsFromControlPanel();
			threadGroup = new ThreadGroup("Building");
			building = Building.getBuilding(storiesNumber, elevatorCapacity);
			building.fillBuilding(passengersNumber);

			if (animationBoost > 0) {
				drawFloorPanel();

			}
			drawLogPanel();
			controlPanel.setButtonAction(ButtonActions.ABORT_ACTION);
			new Thread(threadGroup, this).start();
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(mainFrame,
					e.getMessage(), "Input error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void drawLogPanel() {
		JTextArea logArea = new JTextArea();
          
		JScrollPane scrollPane = new JScrollPane(logArea);
		scrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));
		mainFrame.addComponent(scrollPane);
		mainFrame.repaint();
		LOG.addAppender(new TextAreaAppender(logArea));
	}

	private void getParamsFromControlPanel() {
		storiesNumber = Integer.parseInt(controlPanel.getStoriesNumber());
		elevatorCapacity = Integer.parseInt(controlPanel.getElevatorCapacity());
		passengersNumber = Integer.parseInt(controlPanel.getPassengersNumber());
		animationBoost = controlPanel.getAnimationBoost();
	}

	private void drawFloorPanel() {
		floorsMap = new HashMap<Floor, FloorView>(storiesNumber);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		FloorView.setNumPassengersView(building.getElevator().getCapacity());
		fillFloorsMap(panel);
		JScrollPane pane = new JScrollPane(panel);
		mainFrame.addComponent(pane);
		timer = new Timer(getSleepTime(animationBoost),
				new UpdateListener(this));
		timer.start();
	}

	private void fillFloorsMap(JPanel panel) {
		FloorView view;
		List<Floor> floors = building.getFloors();
		Elevator elevator = building.getElevator();
		ReverseIterator<Floor> iterator = new ReverseIterator<Floor>(floors);
		Floor floor;
		while (iterator.hasNext()) {
			floor = iterator.next();
			view = new FloorView(floor.getDispatchPassengers(),
					floor.getArrivalPassengers());
			panel.add(view);
			floorsMap.put(floor, view);
		}
		view = floorsMap.get(elevator.getCurrentFloor());
		view.setElevatorPassengers(elevator.getElevatorPassengers());
		panel.setVisible(true);
	}

	public void updateView() {

		for (Floor floor : building.getFloors()) {
			floorsMap.get(floor).update(floor.getDispatchPassengers(),
					floor.getArrivalPassengers());

		}
		Elevator elevator = building.getElevator();
		floorsMap.get(elevator.getCurrentFloor()).setElevatorPassengers(
				elevator.getElevatorPassengers());
		mainFrame.repaint();
	}

	@Override
	public void abort() {
		threadGroup.interrupt();
		timer.stop();
	}

	@Override
	public void viewLog() {
		new LogViewFrame();

	}

	private int getSleepTime(int animationBoost) {
		return animationBoost > 0 ? DEFAULT_SLEEP
				- (SLEEP_MULTIPLIER * animationBoost - 1) : 0;
	}

	@Override
	public void run() {
		try {
			building.startElevator(getSleepTime(animationBoost));
			if (building.verify()) {
				JOptionPane.showMessageDialog(mainFrame, "Verify complite");
			} else {
				JOptionPane.showMessageDialog(mainFrame, "Verify error",
						"Verify", JOptionPane.ERROR_MESSAGE);
			}
		} catch (InterruptedException e) {
			System.out.println("Elevator interrupded");
		} finally {
			controlPanel.setButtonAction(ButtonActions.VIEW_LOG_ACTION);
		}

	}

}
