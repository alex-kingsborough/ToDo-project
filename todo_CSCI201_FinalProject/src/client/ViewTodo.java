package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import constants.Constants;

public class ViewTodo extends JFrame {
	private static final long serialVersionUID = 1376543;
	private JLabel mTitleLabel;
	private JLabel mPriorityLabel;
	private JLabel mPointsLabel;
	private JLabel mPrivacyLabel;
	private JLabel mListLabel;
	private JLabel mDescriptionLabel;
	private JButton mSaveButton;
	private JTextField mTitleText;
	private JTextArea mDescriptionText;
	private JPanel mMainPanel;
	private JPanel mPrivacyPanel;
	private Vector<String> mListVector;
	private boolean isPrivate;
	private JTextField mPointsText;
	private Font mFont;
	private TodoUser mTU;
	
	public ViewTodo(TodoObject to){
		super("View Todo");
		setSize(400, 300);
		setLocation(800, 400);
		mTU = PortalManager.mUser;
		AddTodo(to);
	
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
		
		
		mSaveButton = new JButton("Close");
		mSaveButton.setFont(mFont.deriveFont(20));
		mSaveButton.setForeground(Constants.goldColor);
		mSaveButton.setBackground(Color.black);
		
		
		
		mTitleText = new JTextField(15);
		mTitleText.setBorder(null);
		mTitleText.setText(to.getTitle());
		mTitleText.setFont(mFont.deriveFont(20));
		mTitleText.setEditable(false);
		mTitleText.setOpaque(false);
		mTitleText.setForeground(Constants.redColor);
		mTitleText.setBackground(Constants.greyColor);		
		
		mDescriptionText = new JTextArea(5, 15);
		mDescriptionText.setBorder(null);
		mDescriptionText.setText(to.getDescription());
		mDescriptionText.setFont(mFont.deriveFont(0, 14));
		mDescriptionText.setEditable(false);
		mDescriptionText.setOpaque(false);
		mDescriptionText.setWrapStyleWord(true);
		mDescriptionText.setForeground(Constants.redColor);
		mDescriptionText.setBackground(Constants.greyColor);		
		

		mPointsText = new JTextField(15);
		mPointsText.setBorder(null);
		mPointsText.setText(Integer.toString(to.getPoints()));
		mPointsText.setEditable(false);
		mPointsText.setForeground(Constants.redColor);
		mPointsText.setBackground(Constants.greyColor);
		mPointsText.setFont(mFont);
		
		
		mListVector = new Vector<String>();
		
		if(mTU.getTodoLists() != null){
			for(int i = 0; i < mTU.getTodoLists().size(); i ++){
				String name;
				name = mTU.getTodoLists().get(i).getName();
				mListVector.addElement(name);
			}
		}
		
		int currList = 0;
		for(int i = 0; i < mListVector.size(); i ++){
			if(mListVector.get(i).equals(to.getListName())){
				currList = i;
				return;
			}
		}
		
		
		JTextField mPriorityText = new JTextField(to.getPriority());
		mPriorityText.setBorder(null);
		mPriorityText.setEditable(false);
		mPriorityText.setBackground(Constants.greyColor);
		mPriorityText.setForeground(Constants.redColor);
		mPriorityText.setFont(mFont);
		
		JTextField mListText = new JTextField(mListVector.get(currList));
		mListText.setBorder(null);
		mListText.setEditable(false);
		mListText.setBackground(Constants.greyColor);
		mListText.setForeground(Constants.redColor);
		mListText.setFont(mFont);
		
		JTextField mPrivacyText;
		if(isPrivate){
			mPrivacyText = new JTextField("Private");
		}else{
			mPrivacyText = new JTextField("Public");
		}
		
		mPrivacyText.setBorder(null);
		mPrivacyText.setEditable(false);
		mPrivacyText.setBackground(Constants.greyColor);
		mPrivacyText.setForeground(Constants.redColor);
		
		mMainPanel =  new JPanel();
		mMainPanel.setForeground(Constants.goldColor);
		mMainPanel.setBackground(Constants.greyColor);		
		
		
		mPrivacyPanel =  new JPanel();
		mMainPanel.setLayout(new GridLayout(6, 2));
		mPrivacyPanel.setLayout(new FlowLayout());
		mPrivacyPanel.setForeground(Constants.goldColor);
		mPrivacyPanel.setBackground(Constants.greyColor);		
		
		mMainPanel.add(mTitleLabel);
		mMainPanel.add(mTitleText);
		
		mMainPanel.add(mPriorityLabel);
		mMainPanel.add(mPriorityText);
		
		mMainPanel.add(mPointsLabel);
		mMainPanel.add(mPointsText);
		
		mMainPanel.add(mPrivacyLabel);
		mMainPanel.add(mPrivacyText);

		mMainPanel.add(mListLabel);
		mMainPanel.add(mListText);

		mMainPanel.add(mDescriptionLabel);
		mMainPanel.add(mDescriptionText);
		JPanel outsidePanel = new JPanel();
		outsidePanel.setLayout(new BorderLayout());
		outsidePanel.add(mMainPanel, BorderLayout.CENTER);
		outsidePanel.add(mSaveButton, BorderLayout.SOUTH);

		add(outsidePanel);
		setVisible(true);
		addSaveEvents();
	}
	
	
	
	private void addSaveEvents(){
		mSaveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
			}
		});
	}

}
