package by.epam.lab.view.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.epam.lab.controller.IViewController;


public class UpdateListener implements ActionListener {
        IViewController controller;
        public UpdateListener(IViewController controller){
                this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
                controller.updateView();
                
        }
}