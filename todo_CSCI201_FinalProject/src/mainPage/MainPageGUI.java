package mainPage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPageGUI extends JPanel {
	
	public static void main(String [] args){ //TODO FIX THIS ALL
		JFrame mTesting = new JFrame("test");
		mTesting.add(new MainPageGUI());
		
		mTesting.setSize(500, 600);
		mTesting.setLocationRelativeTo(null);
		mTesting.setVisible(true);
		mTesting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//MEMBER VARIABLES
	//Panes and Panels
	
	
	//Constructor
	public MainPageGUI(){ //TODO make it take in a User object to fill all necessary variables
		createTabbedPane();
		
	}
	
}
