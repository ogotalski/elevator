package by.epam.lab.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;

import by.epam.lab.controller.IViewController;

public class MainFrame extends JFrame {
    // private final IViewController  viewController;
     private static final Dimension DEFAULT_SIZE = new Dimension(640, 480);
	 public MainFrame()  {
		super();
		this.setTitle("Elevator Task");
		this.setPreferredSize(DEFAULT_SIZE);
		this.setSize(DEFAULT_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	 }
     
      
}
