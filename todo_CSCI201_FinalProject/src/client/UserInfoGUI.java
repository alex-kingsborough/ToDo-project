package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import constants.Constants;

public class UserInfoGUI extends JPanel {
	
	
	private static final long serialVersionUID = 676767676761L;
	private JLabel mUsernameLabel;
	private JLabel mNameLabel;
	private JLabel mEmailLabel;
	private JLabel mPointsLabel;
	@SuppressWarnings("unused")
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
	DefaultListModel<String> mListModel;
	JScrollPane mFriendsScrollPane;
	
	//public UserInfoGUI(TodoUser tu) {
	//	PortalManager.mUser = tu;
	//}
	
	public UserInfoGUI(TodoUser tu) {
		setLayout(new BorderLayout());
		mUsernameLabel = new JLabel("Username: " + PortalManager.mUser.getUsername());
		mUsernameLabel.setFont(mUsernameLabel.getFont().deriveFont(24f));
		mUsernameLabel.setForeground(Constants.goldColor);
		JPanel mTopPanel = new JPanel();
		mTopPanel.add(mUsernameLabel);
		mTopPanel.setBackground(Constants.greyColor);
		
		add(mTopPanel, BorderLayout.NORTH);
		
		JPanel mCenterPanel = new JPanel(new GridLayout(1,2));
		JPanel mInfoPanel = new JPanel();
		//mInfoPanel.setLayout(new BoxLayout(mInfoPanel, BoxLayout.Y_AXIS));
		mInfoPanel.setLayout(new GridLayout(7,1));
		mInfoPanel.setBackground(Constants.greyColor);
		mNameLabel = new JLabel("Name: " + PortalManager.mUser.getName() + "                         ");
		mNameLabel.setFont(mNameLabel.getFont().deriveFont(15f));
		mNameLabel.setForeground(Constants.goldColor);
		
		mEmailLabel = new JLabel("Email: " + PortalManager.mUser.getEmail());
		mEmailLabel.setFont(mEmailLabel.getFont().deriveFont(15f));
		mEmailLabel.setForeground(Constants.goldColor);
		
		mPointsLabel = new JLabel("Total Points: " + PortalManager.mUser.getTotalPoints());
		mPointsLabel.setFont(mPointsLabel.getFont().deriveFont(15f));
		mPointsLabel.setForeground(Constants.goldColor);
		
		mInfoPanel.add(mNameLabel);
		mInfoPanel.add(mEmailLabel);
		mInfoPanel.add(mPointsLabel);
		mInfoPanel.add(Box.createGlue());
		
		JPanel mAboutMePanel = new JPanel(new GridLayout(1,1));
		mAboutMeTextArea = new JTextArea();
		mAboutMeTextArea.setLineWrap(true); //TODO Marshall, you'll probably want to add this, but I noticed it fucks with the alignment
		//of the other things. I didn't want to mess with stuff much so just leaving it here commented out. -Luc
		mAboutMeTextArea.setText(PortalManager.mUser.getAboutMe());
		mAboutMeTextArea.setEditable(false);
		mAboutMeTextArea.setBackground(Constants.lightGreyColor);
		mAboutMeTextArea.setForeground(Constants.redColor);
		mAboutMePanel.add(mAboutMeTextArea);
		LineBorder border = new LineBorder ( Constants.goldColor, 1, true );
	    TitledBorder tborder = new TitledBorder ( border, "About Me", TitledBorder.CENTER,
	    										TitledBorder.DEFAULT_POSITION, new Font ( "Sans Serif", Font.BOLD, 14),
	    										Constants.goldColor );
		mAboutMePanel.setBorder(tborder); //give about me new titled border
		mAboutMePanel.setBackground(Constants.greyColor);
		mInfoPanel.add(mAboutMePanel);
		//mInfoPanel.add(Box.createGlue());
		mInfoPanel.add(Box.createGlue());
		
		JPanel mFriendsPanel = new JPanel();
		mFriendsPanel.setLayout(new BoxLayout(mFriendsPanel, BoxLayout.Y_AXIS));
		mFriendsLabel = new JLabel("Friends");
		mFriendsLabel.setForeground(Constants.goldColor);
		mFriendsLabel.setFont(mFriendsLabel.getFont().deriveFont(20f));
		mFriendsPanel.add(mFriendsLabel);
		mFriendsPanel.setBackground(Constants.greyColor);
		mListModel = new DefaultListModel<String>();
		
		System.out.println("PortalManager.mUser.getFriendList().size(): " + PortalManager.mUser.getFriendList().size());
		TodoClientListener.lock.lock();
		try {
			for(Integer i: PortalManager.mUser.getFriendList()) {
				TodoClientListener.get().send(Constants.REQUEST_USERNAME_BY_ID + " " + i);
				String response = TodoClientListener.get().readLine();
				System.out.println("response: " + response);
				if(!response.equals(Constants.FAIL_MESSAGE)) {
					String username = response.split(" ")[1];
					if(!username.equals(PortalManager.mUser.getUsername())) {
						mListModel.addElement(username);
					}
				}
			}
		} finally {
			TodoClientListener.lock.unlock();
			System.out.println("got rid of the friends lock");
		}
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
		mFriendsList.setBackground(Constants.lightGreyColor);
		mFriendsList.setForeground(Constants.redColor);
		
		mFriendsScrollPane = new JScrollPane(mFriendsList);
		mFriendsScrollPane.setBorder(new LineBorder(Constants.lightGreyColor));

		mFriendsPanel.add(mFriendsScrollPane);
		//mFriendsPanel.add(mFriendsList);
		
		mCenterPanel.add(mInfoPanel);
		mCenterPanel.add(mFriendsPanel);
		mCenterPanel.setBackground(Constants.greyColor);
		
		add(mCenterPanel, BorderLayout.CENTER);
		
		JPanel mBottomPanel = new JPanel(new GridLayout(2,1));
		JPanel mAddFriendPanel = new JPanel(new FlowLayout());
		mAddFriendPanel.setBackground(Constants.greyColor);
		mAddFriendLabel = new JLabel("Add Friend: ");
		mAddFriendLabel.setForeground(Constants.goldColor);
		mAddFriendLabel.setBackground(Constants.greyColor);
		mAddFriendTextField = new JTextField(8);
		mAddFriendTextField.setBackground(Constants.lightGreyColor);
		mAddFriendTextField.setForeground(Constants.redColor);
		mAddFriendTextField.setBorder(new LineBorder(Constants.lightGreyColor));

		mAddFriendButton = new JButton("Add");
		mAddFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String friendName = mAddFriendTextField.getText();
				TodoClientListener.lock.lock();
				try {
					System.out.println("Add friend in this lock");
					if(!friendName.isEmpty()) {
						mAddFriendTextField.setText("");
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
							if(!friendName.equals(PortalManager.mUser.getUsername())) {
								PortalManager.mUser.addFriend(userID);
								TodoClientListener.get().sendUser(PortalManager.mUser);
								PortalManager.mUser = TodoClientListener.get().readTodoUser();
								mListModel.addElement(friendName);
							//TodoUser mNewFriend = TodoClientListener.get().readTodoUser();
							}
						}
					} 
				} finally {
					TodoClientListener.lock.unlock();
					System.out.println("Add friend out of this lock");
				}
			}
		});
		mAddFriendPanel.add(mAddFriendLabel);
		mAddFriendPanel.add(mAddFriendTextField);
		mAddFriendPanel.add(mAddFriendButton);
		
		mBottomPanel.add(mAddFriendPanel);
		
		JPanel mRemoveFriendPanel = new JPanel(new FlowLayout());
		mRemoveFriendPanel.setBackground(Constants.greyColor);
		mRemoveFriendLabel = new JLabel("Remove Friend: ");
		mRemoveFriendLabel.setForeground(Constants.goldColor);
		mRemoveFriendTextField = new JTextField(8);
		mRemoveFriendTextField.setBackground(Constants.lightGreyColor);
		mRemoveFriendTextField.setForeground(Constants.redColor);
		mRemoveFriendTextField.setBorder(new LineBorder(Constants.lightGreyColor));
		mRemoveFriendButton = new JButton("Remove");
		mRemoveFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				TodoClientListener.lock.lock();
				try {
					System.out.println("Removing Friend In Lock");
					String friendName = mRemoveFriendTextField.getText();
					if(!friendName.isEmpty()) {
						mRemoveFriendTextField.setText("");
						String request = Constants.REMOVE_FRIEND_REQUEST + " " + friendName;
						System.out.println("Friend Name: " + friendName);
						TodoClientListener.get().send(request);
						System.out.println("request: " + request);
						String response = TodoClientListener.get().readLine();
						System.out.println("response: " + response);
						if(response.startsWith(Constants.FAIL_MESSAGE)) {
							JOptionPane.showMessageDialog(mRemoveFriendButton, "Failed to removes friend: " + friendName,
														"Failure", JOptionPane.ERROR_MESSAGE);
						} else {
							String [] parameters = response.split(" ");
							int userID = Integer.parseInt(parameters[1]);
							System.out.println("userID: " + userID);
							if(!friendName.equals(PortalManager.mUser.getUsername())) {
								PortalManager.mUser.removeFriend(userID);
								TodoClientListener.get().sendUser(PortalManager.mUser);
								PortalManager.mUser = TodoClientListener.get().readTodoUser();
								mListModel.removeElement(friendName);
								//TodoUser mNewFriend = TodoClientListener.get().readTodoUser();
							}
						}
					} 
				} finally {
					TodoClientListener.lock.unlock();
					System.out.println("Removing Friend Out of Lock");
				}
			}
		});
		mRemoveFriendPanel.add(mRemoveFriendLabel);
		mRemoveFriendPanel.add(mRemoveFriendTextField);
		mRemoveFriendPanel.add(mRemoveFriendButton);
		
		mBottomPanel.add(mRemoveFriendPanel);
		
		add(mBottomPanel, BorderLayout.SOUTH);
	}
	
	public void updatePoints() {
		mPointsLabel.setText("Total points: " + PortalManager.mUser.getTotalPoints());
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

