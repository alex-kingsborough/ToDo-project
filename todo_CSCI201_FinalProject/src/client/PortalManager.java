package client;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import constants.Constants;

public class PortalManager extends JPanel {
	private static final long serialVersionUID = 123901585432L;
	
	private JMenuBar mJMenuBar;
	private JMenu mTestMenu;
	private JMenuItem mMainPageItem;
	private JMenuItem mSocialPageItem;
	private JMenuItem mUserInfoItem;
	private JMenuItem mNewTabItem;
	private JMenuItem mNewTodoItem;
	static MainPageGUI mMainPage;
	static SocialGUI mSocialPage;
	static UserInfoGUI mUserInfoPage;
	static TodoUser mUser;
	
	public PortalManager(TodoUser inUser, JMenuBar jmb) { //FOR LOGIN USER
		mUser = inUser;
		
		mJMenuBar = jmb;
		setLayout(new CardLayout());
		
		mTestMenu = new JMenu("Menu");
		mTestMenu.setMnemonic('M');
		mJMenuBar.add(mTestMenu);
		mMainPageItem = new JMenuItem("Main Page");
		mMainPageItem.setIcon(new ImageIcon(new ImageIcon("img/userinfoIcon.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
		mMainPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		mMainPageItem.addActionListener(new MenuItemActionListener(this, "main"));
		mTestMenu.add(mMainPageItem);
		
		mSocialPageItem = new JMenuItem("Social Page");
		mSocialPageItem.setIcon(new ImageIcon(new ImageIcon("img/socialIcon.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
		mSocialPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		mSocialPageItem.addActionListener(new MenuItemActionListener(this, "social"));
		mTestMenu.add(mSocialPageItem);
		
		mUserInfoItem = new JMenuItem("User Info");
		mUserInfoItem.setIcon(new ImageIcon(new ImageIcon("img/userinfoIcon.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
		mUserInfoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		mUserInfoItem.addActionListener(new MenuItemActionListener(this, "user"));
		mTestMenu.add(mUserInfoItem);
		
		mNewTabItem = new JMenuItem("New List Tab");
		mNewTabItem.setIcon(new ImageIcon(new ImageIcon("img/addIcon.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
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
			    	TodoClientListener.get().sendUser(mUser);
			    } //Else do nothing, user hit cancel
			}
		});
		mTestMenu.addSeparator();
		mTestMenu.add(mNewTabItem);
		
		mMainPage = new MainPageGUI();
		mSocialPage = new SocialGUI();
		mUserInfoPage = new UserInfoGUI(mUser);
		
		add(mMainPage, "main");
		add(mSocialPage, "social");
		add(mUserInfoPage, "user");
	}
	
	public PortalManager(JMenuBar jmb) { //FOR GUEST USER
		mJMenuBar = jmb;
		setLayout(new CardLayout());

		mUser = new TodoUser(Constants.GUEST_USER, Constants.GUEST_USER, " ", " ", " ");
		mUser.addTodoList(new TodoList(0, "GUEST"));
		
		mTestMenu = new JMenu("Menu");
		mTestMenu.setMnemonic('M');
		mJMenuBar.add(mTestMenu);
		mMainPageItem = new JMenuItem("Main Page");
		mMainPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		mMainPageItem.addActionListener(new GuestMenuItemActionListener(this, "main"));
		mTestMenu.add(mMainPageItem);
		
		mNewTabItem = new JMenuItem("New List Tab");
		mNewTabItem.setIcon(new ImageIcon(new ImageIcon("img/addIcon.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
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
		mNewTodoItem = new JMenuItem("Add a Todo");
		mNewTodoItem.setIcon(new ImageIcon(new ImageIcon("img/todoProjectIcon.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
		mNewTodoItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				new AddTodoItem();
			}
		});
		
		mTestMenu.addSeparator();
		mTestMenu.add(mNewTabItem);
		mTestMenu.add(mNewTodoItem);
		
		mMainPage = new MainPageGUI();
		add(mMainPage, "main");
	}
	
	private class MenuItemActionListener implements ActionListener {
		private JPanel mPortalManager;
		private String mPanelName;

		public MenuItemActionListener(JPanel portalManager, String panelName) {
			mPortalManager = portalManager;
			mPanelName = panelName;
		}

		public void actionPerformed(ActionEvent ae) {
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
	
	private class GuestMenuItemActionListener implements ActionListener {
		private JPanel mPortalManager;
		private String mPanelName;
		
		public GuestMenuItemActionListener(JPanel portalManager, String panelName) {
			mPortalManager = portalManager;
			mPanelName = panelName;
		}

		public void actionPerformed(ActionEvent ae) {
			CardLayout cl = (CardLayout) mPortalManager.getLayout();
			cl.show(mPortalManager, mPanelName);
			updateMenuBar(mPanelName);
		}

		private void updateMenuBar(String inPanelName) {
			mTestMenu.removeAll();
			mTestMenu.add(mMainPageItem);
			mTestMenu.addSeparator();
			mTestMenu.add(mNewTabItem);
			mTestMenu.add(mNewTodoItem);
		}
	}
}
