package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class TodoFrame extends JFrame implements Navigator {
	
	private static final long serialVersionUID = 1290395190L;

	public static void main(String[] args) {
		new TodoFrame("Todo Frame");
	}
	
	public TodoFrame(String string) {
		super(string);

		add(new SignInGUI(this));
		
		setSize(800, 500);
		setMinimumSize(new Dimension(800, 500));
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void toMain() {
		getContentPane().removeAll();
		getContentPane().add(new MainPageGUI(), BorderLayout.CENTER);
		getContentPane().add(new SocialSidebar(this), BorderLayout.EAST);

		JMenuBar mTestBar = new JMenuBar();
		setJMenuBar(mTestBar);
		JMenu mTestMenu = new JMenu("Menu");
		mTestMenu.setMnemonic('M');
		mTestBar.add(mTestMenu);
		JMenuItem mMainPageItem = new JMenuItem("Main Page");
		mMainPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		mTestMenu.add(mMainPageItem);
		
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
