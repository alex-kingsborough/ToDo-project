package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class PortalManager extends JPanel {
	private static final long serialVersionUID = 123901585432L;
	
	JMenuBar mJMenuBar;
	JMenu mTestMenu;
	JMenuItem mMainPageItem;
	JMenuItem mSocialPageItem;
	JMenuItem mUserInfoItem;
	JMenuItem mNewTabItem;
	MainPageGUI mMainPage;
	SocialGUI mSocialPage;
	UserInfoGUI mUserInfoPage;
	private TodoUser mUser;
	
	public PortalManager(TodoUser inUser, JMenuBar jmb) {
		mUser = inUser;
		
		mJMenuBar = jmb;
		setLayout(new CardLayout());
		
		mTestMenu = new JMenu("Menu");
		mTestMenu.setMnemonic('M');
		mJMenuBar.add(mTestMenu);
		mMainPageItem = new JMenuItem("Main Page");
		mMainPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		mMainPageItem.addActionListener(new MenuItemActionListener(this, "main"));
		mTestMenu.add(mMainPageItem);
		
		mSocialPageItem = new JMenuItem("Social Page");
		mSocialPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		mSocialPageItem.addActionListener(new MenuItemActionListener(this, "social"));
		mTestMenu.add(mSocialPageItem);
		
		mUserInfoItem = new JMenuItem("User Info");
		mUserInfoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		mUserInfoItem.addActionListener(new MenuItemActionListener(this, "user"));
		mTestMenu.add(mUserInfoItem);
		
		mNewTabItem = new JMenuItem("New List Tab");
		mNewTabItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		mNewTabItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
			    String mNewTabMessage = "Please enter the title of the new tab:";
			    String mTabTitle = JOptionPane.showInputDialog(PortalManager.this, mNewTabMessage);
			    if(mTabTitle != null){
			    	int newTodoListIndex = mUser.getTodoLists().size();
			    	TodoList mNewTodoList = new TodoList(newTodoListIndex, mTabTitle);
			    	mUser.addTodoList(mNewTodoList);
			    	mMainPage.updatePage();
			    } //Else do nothing, user hit cancel
			}
		});
		mTestMenu.addSeparator();
		mTestMenu.add(mNewTabItem);
		
		mMainPage = new MainPageGUI(mUser);
		mSocialPage = new SocialGUI();
		mUserInfoPage = new UserInfoGUI();
		
		add(mMainPage, "main");
		add(mSocialPage, "social");
		add(mUserInfoPage, "user");
	}
	
	private class MenuItemActionListener implements ActionListener {
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
			updateMenuBar(mPanelName);
		}

		private void updateMenuBar(String inPanelName) {
			mTestMenu.removeAll();
			mTestMenu.add(mMainPageItem);
			mTestMenu.add(mSocialPageItem);
			mTestMenu.add(mUserInfoItem);
			if(inPanelName.equals("main")) {
				mTestMenu.addSeparator();
				mTestMenu.add(mNewTabItem);
			}
		}
	}
}