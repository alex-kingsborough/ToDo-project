package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class PortalManager extends JPanel {
	
	JMenuBar mJMenuBar;
	MainPageGUI mMainPage;
	SocialGUI mSocialPage;
	UserInfoGUI mUserInfoPage;
	
	public PortalManager(JMenuBar jmb) {
		mJMenuBar = jmb;
		setLayout(new CardLayout());
		
		JMenu mTestMenu = new JMenu("Menu");
		mTestMenu.setMnemonic('M');
		mJMenuBar.add(mTestMenu);
		JMenuItem mMainPageItem = new JMenuItem("Main Page");
		mMainPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		mMainPageItem.addActionListener(new MenuItemActionListener(this, "main"));
		mTestMenu.add(mMainPageItem);
		
		JMenuItem mSocialPageItem = new JMenuItem("Social Page");
		mSocialPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		mSocialPageItem.addActionListener(new MenuItemActionListener(this, "social"));
		mTestMenu.add(mSocialPageItem);
		
		JMenuItem mUserInfoItem = new JMenuItem("User Info");
		mUserInfoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		mUserInfoItem.addActionListener(new MenuItemActionListener(this, "user"));
		mTestMenu.add(mUserInfoItem);
		
		mMainPage = new MainPageGUI();
		mSocialPage = new SocialGUI();
		mUserInfoPage = new UserInfoGUI();
		
		add(mMainPage, "main");
		add(mSocialPage, "social");
		add(mUserInfoPage, "user");
	}
}

class MenuItemActionListener implements ActionListener {
	private JPanel mPortalManager;
	private String mPanelName;
	
	public MenuItemActionListener(JPanel portalManager, String panelName) {
		mPortalManager = portalManager;
		mPanelName = panelName;
	}
	
	public void actionPerformed(ActionEvent ae) {
		System.out.println(ae.getActionCommand());
		CardLayout cl = (CardLayout) mPortalManager.getLayout();
		cl.show(mPortalManager, mPanelName);
	}
}
