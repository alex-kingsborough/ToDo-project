package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import constants.Constants;


public class AddTodoItem extends JFrame {
	private static final long serialVersionUID = 1376543;
	
	private JLabel mTitleLabel;
	private JLabel mPriorityLabel;
	private JLabel mPointsLabel;
	private JLabel mPrivacyLabel;
	private JLabel mListLabel;
	private JLabel mDescriptionLabel;
	private JButton mSaveButton;
	private JTextField mTitleText;
	private JRadioButton mPublicRB;
	private JTextArea mDescriptionText;
	private JComboBox<Integer> mPriorityBox;
	private JComboBox<String> mListBox;
	private JRadioButton mPrivateRB;
	private JPanel mMainPanel;
	private JPanel mPrivacyPanel;
	private Vector<Integer> mPriorityVector;
	private Vector<String> mListVector;
	private Vector<Integer> mListIDVector;
	private boolean isPrivate;
	private JTextField mPointsText;
	private Font mFont;
	private int listID = 0;
	private MainPageGUI mMainPage;
	
	public AddTodoItem(){
		super("Add Todo");
		
		
		
		
		//making comboBox look good
		UIManager.put("ComboBox.background", Constants.greyColor);
		UIManager.put("ComboBox.buttonBackground", Constants.greyColor);
		UIManager.put("ComboBox.buttonDarkShadow", Constants.greyColor);
		UIManager.put("ComboBox.buttonHighlight", Constants.goldColor);
		UIManager.put("ComboBox.controlForeground", Color.black);
		UIManager.put("ComboBox.disabledBackground", Constants.greyColor);
		UIManager.put("ComboBox.disabledForeground", Constants.greyColor);
		UIManager.put("ComboBox.foreground", Constants.redColor);
		UIManager.put("ComboBox.selectionForeground", Constants.redColor);
		UIManager.put("ComboBox.selectionBackground", Constants.goldColor);
		UIManager.put("ComboBox.border", BorderFactory.createLineBorder(Constants.redColor,0));
		UIManager.put("ComboBox.borderPaintsFocus", false);
		UIManager.put("Button.border", BorderFactory.createLineBorder(Constants.redColor,0));

		mMainPage = PortalManager.mMainPage;
		setSize(400, 300);
		setLocation(800, 400);
		AddTodo();
		addPublicRBEvents();
		addPrivateRBEvents();
		
		
		
		setVisible(true);
	}
	
	private void AddTodo() {
		isPrivate = false;
		
		mFont = new Font("Comic Sans MS", Font.PLAIN, 22);
		mMainPanel = new JPanel();
		mMainPanel.setBackground(Constants.greyColor);
		setBackground(Constants.goldColor);
		
		mTitleLabel = new JLabel("Title: ");
		mTitleLabel.setFont(mFont);
		mTitleLabel.setForeground(Constants.goldColor);
		mTitleLabel.setBackground(Constants.greyColor);		
		
		
		mPriorityLabel = new JLabel("Priority: ");
		mPriorityLabel.setFont(mFont);
		mPriorityLabel.setForeground(Constants.goldColor);
		mPriorityLabel.setBackground(Constants.greyColor);		
		
		
		mPointsLabel = new JLabel ("Points: ");
		mPointsLabel.setFont(mFont);
		mPointsLabel.setForeground(Constants.goldColor);
		mPointsLabel.setBackground(Constants.greyColor);		
		
		
		mPrivacyLabel = new JLabel("Privacy: ");
		mPrivacyLabel.setFont(mFont);
		mPrivacyLabel.setForeground(Constants.goldColor);
		mPrivacyLabel.setBackground(Constants.greyColor);		
		
		
		mListLabel = new JLabel("List: ");
		mListLabel.setFont(mFont);
		mListLabel.setForeground(Constants.goldColor);
		mListLabel.setBackground(Constants.greyColor);		
		
		mDescriptionLabel = new JLabel("Description: ");
		mDescriptionLabel.setFont(mFont);
		mDescriptionLabel.setForeground(Constants.goldColor);
		mDescriptionLabel.setBackground(Constants.greyColor);
		
		
		mSaveButton = new JButton("Save");
		mSaveButton.setFont(mFont.deriveFont(20));
		mSaveButton.setForeground(Constants.goldColor);
		mSaveButton.setBackground(Color.black);
		
		
		mPublicRB = new JRadioButton("Public");
		mPublicRB.setFont(mFont.deriveFont(20));
		mPublicRB.setForeground(Constants.redColor);
		mPublicRB.setBackground(Constants.greyColor);		
		mPublicRB.setSelected(true);
		
		mPrivateRB = new JRadioButton("Private");
		mPrivateRB.setFont(mFont.deriveFont(20));
		mPrivateRB.setForeground(Constants.redColor);
		mPrivateRB.setBackground(Constants.greyColor);
		
		
		
		mTitleText = new JTextField(15);
		mTitleText.setBorder(null);
		mTitleText.setFont(mFont.deriveFont(20));
		mTitleText.setForeground(Constants.redColor);
		mTitleText.setBackground(Constants.lightGreyColor);		
		
		
		mDescriptionText = new JTextArea(5, 15);
		mDescriptionText.setBorder(null);
		mDescriptionText.setFont(mFont.deriveFont(0, 14));
		mDescriptionText.setLineWrap(true);
		mDescriptionText.setWrapStyleWord(true);
		mDescriptionText.setForeground(Constants.redColor);
		mDescriptionText.setBackground(Constants.lightGreyColor);		
		
		
		mPriorityVector = new Vector<Integer>();
		mListVector = new Vector<String>();
		mListIDVector = new Vector<Integer>();
		
		
		for(int i = 5; i > 0; i--){
			mPriorityVector.addElement(i);
		}
		mPointsText = new JTextField(15);
		mPointsText.setBorder(null);
		mPointsText.setForeground(Constants.redColor);
		mPointsText.setBackground(Constants.lightGreyColor);
		mPointsText.setFont(mFont);
		
		String name;
		for(int i = 0; i < PortalManager.mUser.getTodoLists().size(); i ++){
			name = PortalManager.mUser.getTodoLists().get(i).getName();
			listID = PortalManager.mUser.getTodoLists().get(i).getID();
			mListVector.addElement(name);
			mListIDVector.addElement(listID);
		}
		
		
		mPriorityBox = new JComboBox<Integer>(mPriorityVector);
		mPriorityBox.setFont(mFont.deriveFont(2));
		mPriorityBox.setForeground(Constants.redColor);
		mPriorityBox.setBackground(Constants.greyColor);		
		
		
		mListBox = new JComboBox<String>(mListVector);
		mListBox.setFont(mFont.deriveFont(2));
		mListBox.setForeground(Constants.redColor);
		mListBox.setBackground(Constants.greyColor);		
		
		
		mMainPanel =  new JPanel();
		mPrivacyPanel =  new JPanel();
		
		
		mMainPanel.setLayout(new GridLayout(6, 2));
		mMainPanel.setForeground(Constants.goldColor);
		mMainPanel.setBackground(Constants.greyColor);		
		
		
		mPrivacyPanel.setLayout(new FlowLayout());
		mPrivacyPanel.add(mPublicRB);
		mPrivacyPanel.add(mPrivateRB);
		mPrivacyPanel.setForeground(Constants.goldColor);
		mPrivacyPanel.setBackground(Constants.greyColor);		
		
		mMainPanel.add(mTitleLabel);
		mMainPanel.add(mTitleText);
		
		mMainPanel.add(mPriorityLabel);
		mMainPanel.add(mPriorityBox);
		
		mMainPanel.add(mPointsLabel);
		mMainPanel.add(mPointsText);
		
		if(!PortalManager.mUser.getName().equals(Constants.GUEST_USER)){
			mMainPanel.add(mPrivacyLabel);
			mMainPanel.add(mPrivacyPanel);
		}
		
		mMainPanel.add(mListLabel);
		mMainPanel.add(mListBox);

		mMainPanel.add(mDescriptionLabel);
		mMainPanel.add(mDescriptionText);
		
		JPanel outsidePanel = new JPanel();
		outsidePanel.setLayout(new BorderLayout());
		
		outsidePanel.add(mMainPanel, BorderLayout.CENTER);
		outsidePanel.add(mSaveButton, BorderLayout.SOUTH);
		add(outsidePanel);
		addSaveEvents();
	}
	
	

	private void addPublicRBEvents(){
		mPublicRB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
			    if (mPublicRB.isSelected()) {
			    	mPrivateRB.setSelected(false);
			    	isPrivate = false;
			    }
			
			}
		});
	}

	private void addPrivateRBEvents(){
		mPrivateRB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if (mPrivateRB.isSelected()) {
			    	mPublicRB.setSelected(false);
			    	isPrivate = true;
			    }
			}
		});
	}
	
	private void addSaveEvents(){
		mSaveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				if(!isInteger(mPointsText.getText())){
					JOptionPane.showMessageDialog(
							null,
						    "Please Enter a Number in the Points Field",
						    "Error Getting Points",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}

				String title = mTitleText.getText();
				int priority = Integer.parseInt(mPriorityBox.getSelectedItem().toString());
				String list = mListBox.getSelectedItem().toString();
				int currListID = mListIDVector.get(mListBox.getSelectedIndex());
				String description = mDescriptionText.getText();
				int points = Integer.parseInt(mPointsText.getText()); ;
				
				TodoObject mTodoObject = new TodoObject(title, priority, isPrivate, currListID, list, description, points, PortalManager.mUser.getID(), false);

				int currPlace = 0;
				for(int i = 0; i < PortalManager.mUser.getTodoLists().size(); i ++){
					if(PortalManager.mUser.getTodoLists().get(i).getID() == currListID){
						currPlace = i;
					}
				}
				
				PortalManager.mUser.getTodoLists().get(currPlace).addTodo(mTodoObject);
	
				if(!PortalManager.mUser.getName().equals(Constants.GUEST_USER)){
					TodoClientListener.lock.lock();
					try {
						TodoClientListener.get().sendUser(PortalManager.mUser);
						PortalManager.mUser = TodoClientListener.get().readTodoUser();
					} finally {
						TodoClientListener.lock.unlock();
					}
				}
				mMainPage.updatePage();
				
				setVisible(false);
				
			}
		});
	}

	
	public static boolean isInteger(String mString) {
	    try { 
	        Integer.parseInt(mString); 
	    } catch(NumberFormatException nfe) { 
	        return false; 
	    } catch(NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
}
