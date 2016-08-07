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
	private JPanel mPrivacyPanel;
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
		UIManager.put("ComboBox.border", BorderFactory.createLineBorder(Constants.redColor,1));

		
		
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
		mPublicRB.setSelected(!to.getIsPrivate());
		mPublicRB.setForeground(Constants.redColor);
		mPublicRB.setBackground(Constants.greyColor);		
		
		mPrivateRB = new JRadioButton("Private");
		mPrivateRB.setFont(mFont.deriveFont(20));
		mPrivateRB.setSelected(to.getIsPrivate());
		mPrivateRB.setForeground(Constants.redColor);
		mPrivateRB.setBackground(Constants.greyColor);		
		
		
		mTitleText = new JTextField(15);
		mTitleText.setText(to.getTitle());
		mTitleText.setBorder(null);
		mTitleText.setFont(mFont.deriveFont(20));
		mTitleText.setForeground(Constants.redColor);
		mTitleText.setBackground(Constants.lightGreyColor);		
		
		
		mDescriptionText = new JTextArea(5, 15);
		mDescriptionText.setBorder(null);
		mDescriptionText.setText(to.getDescription());
		mDescriptionText.setFont(mFont.deriveFont(0, 14));
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
		mPointsText.setText(Integer.toString(to.getPoints()));
		mPointsText.setBorder(null);
		mPointsText.setFont(mFont);
		mPointsText.setForeground(Constants.redColor);
		mPointsText.setBackground(Constants.lightGreyColor);
		
		
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
		mPriorityBox.setSelectedItem(to.getPriority());
		mPriorityBox.setForeground(Constants.redColor);
		mPriorityBox.setBackground(Constants.greyColor);		
		
		
		mListBox = new JComboBox<String>(mListVector);
		mListBox.setFont(mFont.deriveFont(2));
		mListBox.setSelectedIndex(currList);
		mListBox.setForeground(Constants.redColor);
		mListBox.setBackground(Constants.greyColor);		
		
		
		mMainPanel =  new JPanel();
		
		mTitlePanel =  new JPanel();
		
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
				int priority = Integer.parseInt(mPriorityBox.getSelectedItem().toString());
				String list = mListBox.getSelectedItem().toString();
				int currListID = mListIDVector.get(mListBox.getSelectedIndex());
				String description = mDescriptionText.getText();
				
				System.out.println(description);
				
				int points = Integer.parseInt(mPointsText.getText());
				
				mTO.setTitle(title);
				mTO.setPriority(priority);
				mTO.setIsPrivate(isPrivate);
				mTO.setListName(list);
				mTO.setListID(currListID);
				mTO.setPoints(points);
				mTO.setDescription(description);
				
				
				int currPlace = 0;
				for(int i = 0; i < mTU.getTodoLists().size(); i ++){
					if(mTU.getTodoLists().get(i).getID() == currListID){
						currPlace = i;
					}
				}
				
				for(int i = 0; i < mTU.getTodoLists().size(); i++){
					for(int j = 0; j < mTU.getTodoLists().get(i).getAllTodos().size();j++){
						if(mTU.getTodoLists().get(i).getAllTodos().get(j).equals(mTO)){
							mTU.getTodoLists().get(i).getAllTodos().remove(j);
						}
					}
				}
				
				mTU.getTodoLists().get(currPlace).addTodo(mTO);
	
				if(!mTU.getName().equals(Constants.GUEST_USER)){
					TodoClientListener.get().sendUser(mTU);
				}
				
				mMainPage.updatePage();
				setVisible(false);

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
