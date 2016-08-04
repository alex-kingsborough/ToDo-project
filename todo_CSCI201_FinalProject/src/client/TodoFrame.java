package client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class TodoFrame extends JFrame implements Navigator {

	public static void main(String[] args) {
		TodoFrame mFrame = new TodoFrame("Todo Frame");
		mFrame.add(new SignInGUI(mFrame));
		
		JMenuBar mTestBar = new JMenuBar();
		mFrame.setJMenuBar(mTestBar);
		JMenu mTestMenu = new JMenu("Menu");
		mTestMenu.setMnemonic('M');
		mTestBar.add(mTestMenu);
		JMenuItem mMainPageItem = new JMenuItem("Main Page");
		mMainPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		mTestMenu.add(mMainPageItem);
		
		
		mFrame.setSize(800, 500);
		mFrame.setLocationRelativeTo(null);
		mFrame.setVisible(true);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public TodoFrame(String string) {
		super(string);
	}
	
	@Override
	public void toMain() {
		getContentPane().removeAll();
		getContentPane().add(new MainPageGUI());
		revalidate();
		repaint();
	}

	@Override
	public void toLogin() {
		getContentPane().removeAll();
		getContentPane().add(new LoginGUI(this));
		revalidate();
		repaint();
	}

	@Override
	public void toSignup() {
		getContentPane().removeAll();
		getContentPane().add(new SignupGUI(this));
		revalidate();
		repaint();
	}
}
