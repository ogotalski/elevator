package by.epam.lab.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

public class TestIFrame extends JFrame {
	public TestIFrame(){
		super("hello world");
		setBounds(100, 100, 200, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
    public static void main(String[] args) {
		
		JPanel panel = new JPanel();
		
		//jList.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS))
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		FloorView view;
		for (int i =0 ; i<50 ; i++){
			view = new FloorView(null, null);
			//view.setLayout(frame.getLayout());
			panel.add(view);
			//frame.add(new JTextField("sd", 5));
		}
		panel.setVisible(true);
		JScrollPane pane = new JScrollPane(panel);
		JFrame frame = new TestIFrame();
		frame.add(pane);
		//frame.pack();
		frame.setVisible(true);
		
		
	}
}
