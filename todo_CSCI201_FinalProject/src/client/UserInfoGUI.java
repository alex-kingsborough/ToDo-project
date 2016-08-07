package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import constants.Constants;

public class UserInfoGUI extends JPanel {
	
	
	private static final long serialVersionUID = 676767676761L;
	private TodoUser mTodoUser;
	private JLabel mUsernameLabel;
	private JLabel mNameLabel;
	private JLabel mEmailLabel;
	private JLabel mPointsLabel;
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
	
	//public UserInfoGUI(TodoUser tu) {
	//	mTodoUser = tu;
	//}
	
	public UserInfoGUI(TodoUser tu) {
		mTodoUser = PortalManager.mUser;
		setLayout(new BorderLayout());
		mUsernameLabel = new JLabel("Username: " + mTodoUser.getUsername());
		mUsernameLabel.setFont(mUsernameLabel.getFont().deriveFont(24f));
		JPanel mTopPanel = new JPanel();
		mTopPanel.add(mUsernameLabel);
		
		add(mTopPanel, BorderLayout.NORTH);
		
		JPanel mCenterPanel = new JPanel(new GridLayout(1,2));
		JPanel mInfoPanel = new JPanel();
		//mInfoPanel.setLayout(new BoxLayout(mInfoPanel, BoxLayout.Y_AXIS));
		mInfoPanel.setLayout(new GridLayout(7,1));
		mNameLabel = new JLabel("Name: " + mTodoUser.getName() + "                         ");
		mNameLabel.setFont(mNameLabel.getFont().deriveFont(15f));
		
		mEmailLabel = new JLabel("Email: " + mTodoUser.getEmail());
		mEmailLabel.setFont(mEmailLabel.getFont().deriveFont(15f));
		
		mPointsLabel = new JLabel("Total Points: " + mTodoUser.getTotalPoints());
		mPointsLabel.setFont(mPointsLabel.getFont().deriveFont(15f));
		
		mInfoPanel.add(mNameLabel);
		mInfoPanel.add(mEmailLabel);
		mInfoPanel.add(mPointsLabel);
		mInfoPanel.add(Box.createGlue());
		
		JPanel mAboutMePanel = new JPanel(new GridLayout(1,1));
		mAboutMeTextArea = new JTextArea();
		mAboutMeTextArea.setLineWrap(true); //TODO Marshall, you'll probably want to add this, but I noticed it fucks with the alignment
		//of the other things. I didn't want to mess with stuff much so just leaving it here commented out. -Luc
		mAboutMeTextArea.setText(mTodoUser.getAboutMe());
		mAboutMeTextArea.setEditable(false);
		mAboutMePanel.add(mAboutMeTextArea);
		mAboutMePanel.setBorder(new TitledBorder("About Me")); //give about me new titled border
		mInfoPanel.add(mAboutMePanel);
		//mInfoPanel.add(Box.createGlue());
		mInfoPanel.add(Box.createGlue());
		
		JPanel mFriendsPanel = new JPanel();
		mFriendsPanel.setLayout(new BoxLayout(mFriendsPanel, BoxLayout.Y_AXIS));
		mFriendsLabel = new JLabel("Friends");
		mFriendsLabel.setFont(mFriendsLabel.getFont().deriveFont(20f));
		mFriendsPanel.add(mFriendsLabel);
		mListModel = new DefaultListModel();
		/*
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
		*/
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
		mAddFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String friendName = mAddFriendTextField.getText();
				if(!friendName.isEmpty()) {
					String request = Constants.ADD_FRIEND_REQUEST + " " + friendName;
					System.out.println("Friend Name: " + friendName);
					TodoClientListener.get().send(request);
					System.out.println("request: " + request);
					String response = TodoClientListener.get().readLine();
					System.out.println("response: " + response);
					if(response.startsWith(Constants.FAIL_MESSAGE)) {
						JOptionPane.showMessageDialog(mAddFriendButton, "Failed to add friend: " + friendName,
													"Failure", JOptionPane.ERROR_MESSAGE);
					} else {
						String [] parameters = response.split(" ");
						int userID = Integer.parseInt(parameters[1]);
						System.out.println("userID: " + userID);
						mTodoUser.addFriend(userID);
						TodoClientListener.get().sendUser(mTodoUser);
						TodoUser mNewFriend = TodoClientListener.get().readTodoUser();
						mListModel.addElement(friendName);
					}
				} 
			}
		});
		mAddFriendPanel.add(mAddFriendLabel);
		mAddFriendPanel.add(mAddFriendTextField);
		mAddFriendPanel.add(mAddFriendButton);
		
		mBottomPanel.add(mAddFriendPanel);
		
		JPanel mRemoveFriendPanel = new JPanel(new FlowLayout());
		mRemoveFriendLabel = new JLabel("Remove Friend: ");
		mRemoveFriendTextField = new JTextField(8);
		mRemoveFriendButton = new JButton("Remove");
		mRemoveFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
			}
		});
		mRemoveFriendPanel.add(mRemoveFriendLabel);
		mRemoveFriendPanel.add(mRemoveFriendTextField);
		mRemoveFriendPanel.add(mRemoveFriendButton);
		
		mBottomPanel.add(mRemoveFriendPanel);
		
		add(mBottomPanel, BorderLayout.SOUTH);
	}
	
	public void updatePoints() {
		mPointsLabel.setText("Total points: " + mTodoUser.getTotalPoints());
		revalidate();
		repaint();
	}

	
	public static void main(String [] args) {
		JFrame mFrame = new JFrame();
		//mFrame.add(new UserInfoGUI());
		mFrame.setSize(400, 400);
		mFrame.setVisible(true);
	}
}

