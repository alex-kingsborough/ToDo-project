package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class UserInfoGUI extends JPanel {
	

	private static final long serialVersionUID = 676767676761L;
	private JLabel mUsernameLabel;
	private JLabel mNameLabel;
	private JLabel mEmailLabel;
	private JLabel mDOBLabel;
	private JLabel mAboutMeLabel;
	private JTextArea mAboutMeTextArea;
	JLabel mAddFriendLabel;
	JTextField mAddFriendTextField;
	JButton mAddFriendButton;
	JLabel mRemoveFriendLabel;
	JTextField mRemoveFriendTextField;
	JButton mRemoveFriendButton;
	JLabel mFriendsLabel;
	JList<String> mFriendsList;
	DefaultListModel mListModel;
	JScrollPane mFriendsScrollPane;
	
	{
		setLayout(new BorderLayout());
		
		mUsernameLabel = new JLabel("Username: username213");
		mUsernameLabel.setFont(mUsernameLabel.getFont().deriveFont(24f));
		JPanel mTopPanel = new JPanel();
		mTopPanel.add(mUsernameLabel);
		
		add(mTopPanel, BorderLayout.NORTH);
		
		JPanel mCenterPanel = new JPanel(new GridLayout(1,2));
		JPanel mInfoPanel = new JPanel();
		mInfoPanel.setLayout(new BoxLayout(mInfoPanel, BoxLayout.Y_AXIS));
		mNameLabel = new JLabel("Name: Marshall");
		mNameLabel.setFont(mNameLabel.getFont().deriveFont(15f));
		
		mEmailLabel = new JLabel("Email: Marshall.jacobs12@gmail.com");
		mEmailLabel.setFont(mEmailLabel.getFont().deriveFont(15f));
		
		mDOBLabel = new JLabel("DOB: 06/14/93");
		mDOBLabel.setFont(mDOBLabel.getFont().deriveFont(15f));
		
		mInfoPanel.add(mNameLabel);
		mInfoPanel.add(mEmailLabel);
		mInfoPanel.add(mDOBLabel);
		mInfoPanel.add(Box.createGlue());
		
		JPanel mAboutMePanel = new JPanel(new GridLayout(1,1));
		mAboutMeTextArea = new JTextArea();
		mAboutMePanel.add(mAboutMeTextArea);
		mAboutMePanel.setBorder(new TitledBorder("About Me")); //give about me new titled border
		mInfoPanel.add(mAboutMePanel);
		mInfoPanel.add(Box.createGlue());
		mInfoPanel.add(Box.createGlue());
		
		JPanel mFriendsPanel = new JPanel();
		mFriendsPanel.setLayout(new BoxLayout(mFriendsPanel, BoxLayout.Y_AXIS));
		mFriendsLabel = new JLabel("Friends");
		mFriendsLabel.setFont(mFriendsLabel.getFont().deriveFont(20f));
		mFriendsPanel.add(mFriendsLabel);
		mListModel = new DefaultListModel();
		mListModel.addElement("Friend1");
		mListModel.addElement("Friend2");
		mListModel.addElement("Friend3");
		mListModel.addElement("Friend4");
		mListModel.addElement("Friend5");
		mListModel.addElement("Friend6");
		mListModel.addElement("Friend7");
		mListModel.addElement("Friend8");
		mListModel.addElement("Friend9");
		mListModel.addElement("Friend10");
		mListModel.addElement("Friend11");
		mListModel.addElement("Friend12");
		mListModel.addElement("Friend13");
		mListModel.addElement("Friend14");
		mListModel.addElement("Friend15");
		mListModel.addElement("Friend16");
		mListModel.addElement("Friend17");
		mListModel.addElement("Friend18");
		mListModel.addElement("Friend19");
		mListModel.addElement("Friend20");
		mListModel.addElement("Friend21");
		mListModel.addElement("Friend22");
		mListModel.addElement("Friend23");
		mListModel.addElement("Friend24");
		mListModel.addElement("Friend25");
		mListModel.addElement("Friend26");
		mListModel.addElement("Friend27");
		mListModel.addElement("Friend28");
		mListModel.addElement("Friend29");

		mFriendsList = new JList<String>(mListModel);
		mFriendsList.setFont(mFriendsList.getFont().deriveFont(14f));
		mFriendsScrollPane = new JScrollPane(mFriendsList);
		mFriendsPanel.add(mFriendsScrollPane);
		//mFriendsPanel.add(mFriendsList);
		
		mCenterPanel.add(mInfoPanel);
		mCenterPanel.add(mFriendsPanel);
		add(mCenterPanel, BorderLayout.CENTER);
		
		JPanel mBottomPanel = new JPanel(new GridLayout(2,1));
		JPanel mAddFriendPanel = new JPanel(new FlowLayout());
		mAddFriendLabel = new JLabel("Add Friend: ");
		mAddFriendTextField = new JTextField(8);
		mAddFriendButton = new JButton("Add");
		mAddFriendPanel.add(mAddFriendLabel);
		mAddFriendPanel.add(mAddFriendTextField);
		mAddFriendPanel.add(mAddFriendButton);
		
		mBottomPanel.add(mAddFriendPanel);
		
		JPanel mRemoveFriendPanel = new JPanel(new FlowLayout());
		mRemoveFriendLabel = new JLabel("Remove Friend: ");
		mRemoveFriendTextField = new JTextField(8);
		mRemoveFriendButton = new JButton("Remove");
		mRemoveFriendPanel.add(mRemoveFriendLabel);
		mRemoveFriendPanel.add(mRemoveFriendTextField);
		mRemoveFriendPanel.add(mRemoveFriendButton);
		
		mBottomPanel.add(mRemoveFriendPanel);
		
		add(mBottomPanel, BorderLayout.SOUTH);
	}
}
