package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import constants.Constants;

public class UpdateTodo extends JFrame {
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
	private JPanel mTitlePanel;
	private JPanel mPriorityPanel;
	private JPanel mPrivacyPanel;
	private JPanel mListPanel;
	private JPanel mDescriptionPanel;
	private Vector<Integer> mPriorityVector;
	private Vector<String> mListVector;
	private Vector<Integer> mListIDVector;
	private boolean isPrivate;
	private JTextField mPointsText;
	private Font mFont;
	private TodoUser mTU;
	private int listID = 0;
	private MainPageGUI mMainPage;
	private TodoObject mTO;
	
	public UpdateTodo(TodoObject to){
		super("Edit Todo");
		setSize(400, 300);
		setLocation(800, 400);
		mTU = PortalManager.mUser;
		mMainPage = PortalManager.mMainPage;
		mTO = to;
		AddTodo(to);
		addPublicRBEvents();
		addPrivateRBEvents();
		setVisible(true);
	}
	
	private void AddTodo(TodoObject to) {
		mFont = new Font("Serif", Font.PLAIN, 22);
		mMainPanel = new JPanel();
		mTitleLabel = new JLabel("Title: ");
		mTitleLabel.setFont(mFont);
		mPriorityLabel = new JLabel("Priority: ");
		mPriorityLabel.setFont(mFont);
		mPointsLabel = new JLabel ("Points: ");
		mPointsLabel.setFont(mFont);
		mPrivacyLabel = new JLabel("Privacy: ");
		mPrivacyLabel.setFont(mFont);
		mListLabel = new JLabel("List: ");
		mListLabel.setFont(mFont);
		mDescriptionLabel = new JLabel("Description: ");
		mDescriptionLabel.setFont(mFont);
		mSaveButton = new JButton("Save");
		mSaveButton.setFont(mFont.deriveFont(20));
		mPublicRB = new JRadioButton("Public");
		mPublicRB.setFont(mFont.deriveFont(20));
		mPublicRB.setSelected(!to.getIsPrivate());
		mPrivateRB = new JRadioButton("Private");
		mPrivateRB.setFont(mFont.deriveFont(20));
		mPrivateRB.setSelected(to.getIsPrivate());
		mTitleText = new JTextField(15);
		mTitleText.setText(to.getTitle());
		mTitleText.setFont(mFont.deriveFont(20));
		mDescriptionText = new JTextArea(5, 15);
		mDescriptionText.setText(to.getDescription());
		mDescriptionText.setFont(mFont.deriveFont(0, 10));
		mDescriptionText.setWrapStyleWord(true);
		mPriorityVector = new Vector<Integer>();
		mListVector = new Vector<String>();
		mListIDVector = new Vector<Integer>();
		for(int i = 10; i > 0; i--){
			mPriorityVector.addElement(i);
		}
		mPointsText = new JTextField(15);
		mPointsText.setText(Integer.toString(to.getPoints()));
		for(int i = 0; i < mTU.getTodoLists().size(); i ++){
			String name;
			name = mTU.getTodoLists().get(i).getName();
			listID = mTU.getTodoLists().get(i).getID();
			mListVector.addElement(name);
			mListIDVector.addElement(listID);
		}
		int currList = 0;
		for(int i = 0; i < mListVector.size(); i ++){
			if(mListVector.get(i).equals(to.getListName())){
				currList = i;
			}
		}

		
		mPriorityBox = new JComboBox<Integer>(mPriorityVector);
		mPriorityBox.setFont(mFont.deriveFont(20));
		mPriorityBox.setSelectedItem(10-to.getPriority());
		mListBox = new JComboBox<String>(mListVector);
		mListBox.setFont(mFont.deriveFont(2));
		mListBox.setSelectedIndex(currList);
		mMainPanel =  new JPanel();
		mTitlePanel =  new JPanel();
		mPriorityPanel =  new JPanel();
		mPrivacyPanel =  new JPanel();
		mListPanel =  new JPanel();
		mDescriptionPanel =  new JPanel();
		mMainPanel.setLayout(new GridLayout(6, 2));
		mTitlePanel.setLayout(new FlowLayout());
		mPrivacyPanel.setLayout(new FlowLayout());
		mPrivacyPanel.add(mPublicRB);
		mPrivacyPanel.add(mPrivateRB);
		mMainPanel.add(mTitleLabel);
		mMainPanel.add(mTitleText);
		mMainPanel.add(mPriorityLabel);
		mMainPanel.add(mPriorityBox);
		mMainPanel.add(mPointsLabel);
		mMainPanel.add(mPointsText);
		mMainPanel.add(mPrivacyLabel);
		mMainPanel.add(mPrivacyPanel);
		
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
				int priority = 10 - Integer.parseInt(mPriorityBox.getSelectedItem().toString());
				String list = mListBox.getSelectedItem().toString();
				int currListID = mListIDVector.get(mListBox.getSelectedIndex());
				String description = mDescriptionText.getText();
				
				int points = Integer.parseInt(mPointsText.getText()); ;// ALEX I DIDN't want to mess with all your implementation, just
				
				//TodoObject mTodoObject = new TodoObject(title, priority, isPrivate, currListID, list, description, points, mTU.getID(), false);
				mTO.setTitle(title);
				mTO.setPriority(priority);
				mTO.setIsPrivate(isPrivate);
				mTO.setListName(list);
				mTO.setListID(currListID);
				mTO.setDescription(description);
				
				
				int currPlace = 0;
				for(int i = 0; i < mTU.getTodoLists().size(); i ++){
					if(mTU.getTodoLists().get(i).getID() == currListID){
						currPlace = i;
					}
				}
				//we need to change the list it is in and remove it from the old list
				//mTU.getTodoLists().get(currPlace).addTodo(mTodoObject);
	
				mMainPage.updatePage();
				
				//need to send this to the client to add to user's todos
			}
		});
	}

	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
