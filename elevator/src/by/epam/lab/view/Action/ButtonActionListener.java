package by.epam.lab.view.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.epam.lab.controller.IViewController;
import by.epam.lab.view.ControlPanel;

public class ButtonActionListener implements ActionListener {
    private IViewController viewController;
    
	
	public ButtonActionListener(IViewController viewController) {
		super();
		this.viewController = viewController;
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand() ) {
		case "Start":
			viewController.start();
			break;
		case "Abort":
			viewController.abort();
			break;	
		
		}
		;

	}

}
