package by.epam.lab.view.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.epam.lab.view.ControlPanel;

public class StartActionListener implements ActionListener {
    private ControlPanel controlPanel;
    
	public StartActionListener(ControlPanel controlPanel) {
		super();
		this.controlPanel = controlPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//verify
		controlPanel.getButton().setLabel("Abort");

	}

}
