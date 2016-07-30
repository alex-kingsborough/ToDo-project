package client;
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class SocialWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SocialWindow()
	{
		super("Todo");
		setSize(500,500);
		
		SocialGUI socialTabs = new SocialGUI();
		setLayout(new BorderLayout());
		add(socialTabs);
		
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		new SocialWindow();
	}
}
