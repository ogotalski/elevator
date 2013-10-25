package by.epam.lab.view;

import java.awt.Button;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {
	private static final int MAX_BOOST = 100;
	private JTextField storiesNumberField;
	private JTextField elevatorCapacityField;
	private JTextField passengersNumberField;
	private JSlider animationBoostSlider;
	private JButton button;
	
	public ControlPanel(int storiesNumber, int elevatorCapacity,
			int passengersNumber, int animationBoost) {
		super();
			
		this.setLayout(new FlowLayout());
		JLabel label = new JLabel("Stories Number");
		 storiesNumberField = new JTextField(storiesNumber);
		this.add(label);
		this.add(storiesNumberField);
		label = new JLabel("Elevator Capacity");
		 elevatorCapacityField = new JTextField(elevatorCapacity);
		this.add(label);
		this.add(elevatorCapacityField);
		label = new JLabel("Passengers Number");
		 passengersNumberField = new JTextField(passengersNumber);
		this.add(label);
		this.add(passengersNumberField);
		label = new JLabel("Animation Boost");
		 animationBoostSlider = new JSlider(0,MAX_BOOST);
		animationBoostSlider.setValue(animationBoost);
		this.add(label);
		this.add(animationBoostSlider);
		button = new JButton("Start");
		this.add(button);
		this.setVisible(true);
		
	}
	public String getStoriesNumber() {
		return storiesNumberField.getText();
	}
	
	public String getElevatorCapacity() {
		return elevatorCapacityField.getText();
	}
	
	public String getPassengersNumber() {
		return passengersNumberField.getText();
	}
	
	public int getAnimationBoost() {
		return animationBoostSlider.getValue();
	}
	public JButton getButton(){
		return button;
	}
	
	
}
