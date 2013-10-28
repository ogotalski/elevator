package by.epam.lab.view;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import by.epam.lab.view.Action.ButtonActionListener;
import by.epam.lab.view.Action.ButtonActionListener.ButtonActions;

public class ControlPanel extends JPanel {
	private static final String ANIMATION_BOOST = "Animation Boost";
	private static final String PASSENGERS_NUMBER = "Passengers Number";
	private static final String ELEVATOR_CAPACITY = "Elevator Capacity";
	private static final String STORIES_NUMBER = "Stories Number";
	private static final Dimension DEFAULT_SIZE = new Dimension(
			Integer.MAX_VALUE, 50);
	private JTextField storiesNumberField;
	private JTextField elevatorCapacityField;
	private JTextField passengersNumberField;
	private JSlider animationBoostSlider;
	private JButton button;

	public ControlPanel(int storiesNumber, int elevatorCapacity,
			int passengersNumber, int animationBoost, int maxBoost) {
		super();

		this.setLayout(new FlowLayout());
		this.setMaximumSize(DEFAULT_SIZE);
		JLabel label = new JLabel(STORIES_NUMBER);
		storiesNumberField = new JTextField(String.valueOf(storiesNumber), 5);
		this.add(label);
		this.add(storiesNumberField);
		label = new JLabel(ELEVATOR_CAPACITY);
		elevatorCapacityField = new JTextField(
				String.valueOf(elevatorCapacity), 5);
		this.add(label);
		this.add(elevatorCapacityField);
		label = new JLabel(PASSENGERS_NUMBER);
		passengersNumberField = new JTextField(
				String.valueOf(passengersNumber), 5);
		this.add(label);
		this.add(passengersNumberField);
		label = new JLabel(ANIMATION_BOOST);
		animationBoostSlider = new JSlider(0, maxBoost);
		animationBoostSlider.setValue(animationBoost);
		animationBoostSlider.setMajorTickSpacing(10);
		animationBoostSlider.setMinorTickSpacing(1);
		animationBoostSlider.setPaintTicks(true);
		animationBoostSlider.setPaintLabels(true);
		this.add(label);
		this.add(animationBoostSlider);
		button = new JButton(ButtonActions.START_ACTION.getActionString());
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

	public void addButtonActionListener(ActionListener listener) {
		button.addActionListener(listener);
	}

	public void setButtonAction(ButtonActions action) {
		button.setText(action.getActionString());
	}

}
