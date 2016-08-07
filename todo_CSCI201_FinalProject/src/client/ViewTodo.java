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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


/* This needs a vector of the different lists that the current user has 
 * to give the user an option of what list to add the new todo to.
 * 
 * 
 * 
*/
public class ViewTodo extends JFrame {
	private static final long serialVersionUID = 1376543;
	JLabel mTitleLabel;
	JLabel mPriorityLabel;
	JLabel mPointsLabel;
	JLabel mPrivacyLabel;
	JLabel mListLabel;
	JLabel mDescriptionLabel;
	JButton mSaveButton;
	JTextField mTitleText;
	JRadioButton mPublicRB;
	JTextArea mDescriptionText;
	JComboBox<Integer> mPriorityBox;
	JComboBox<String> mListBox;
	JRadioButton mPrivateRB;
	JPanel mMainPanel;
	JPanel mTitlePanel;
	JPanel mPriorityPanel;
	JPanel mPrivacyPanel;
	JPanel mListPanel;
	JPanel mDescriptionPanel;
	Vector<Integer> mPriorityVector;
	Vector<String> mListVector;
	boolean isPrivate;
	JTextField mPointsText;
	Font mFont;
	TodoUser mTU;
	
	public ViewTodo(TodoObject to){
		super("View Todo");
		setSize(400, 300);
		setLocation(800, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mTU = PortalManager.mUser;
		AddTodo(to);
		//addPublicRBEvents();
		//addPrivateRBEvents();
	
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
		mSaveButton = new JButton("Close");
		mSaveButton.setFont(mFont.deriveFont(20));
		mPublicRB = new JRadioButton("Public");
		mPublicRB.setSelected(!to.getIsPrivate());
		mPublicRB.setFont(mFont.deriveFont(20));
		mPrivateRB = new JRadioButton("Private");
		mPrivateRB.setSelected(to.getIsPrivate());
		mPrivateRB.setFont(mFont.deriveFont(20));
		mTitleText = new JTextField(15) {
		    @Override public void setBorder(Border border) {
		        // None
		    }
		};
		mTitleText.setText(to.getTitle());
		mTitleText.setFont(mFont.deriveFont(20));
		mTitleText.setEditable(false);
		mTitleText.setOpaque(false);
		
		mDescriptionText = new JTextArea(5, 15) {
		    @Override public void setBorder(Border border) {
		        // None
		    }
		};
		mDescriptionText.setText(to.getDescription());
		mDescriptionText.setFont(mFont.deriveFont(0, 10));
		mDescriptionText.setEditable(false);
		mDescriptionText.setOpaque(false);
		mDescriptionText.setWrapStyleWord(true);

		
		mPriorityVector = new Vector<Integer>();
		for(int i = 10; i > 0; i--){
			mPriorityVector.addElement(i);
		}
		mPointsText = new JTextField(15);
		mPointsText.setText(Integer.toString(to.getPoints()));
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
		
		/*mPriorityBox = new JComboBox<Integer>(mPriorityVector);
		mPriorityBox.setFont(mFont.deriveFont(20));
		mPriorityBox.setSelectedIndex(10-to.getPriority());
		*/
		JTextField mPriorityText = new JTextField(10-to.getPriority()) {
		    @Override public void setBorder(Border border) {
		        // None
		    }
		};
		
/*		mListBox = new JComboBox<String>(mListVector);
		mListBox.setFont(mFont.deriveFont(2));
		mListBox.setSelectedIndex(currList);
*/		
		JTextField mListText = new JTextField(mListVector.get(currList)) {
		    @Override public void setBorder(Border border) {
		        // None
		    }
		};
		JTextField mPrivacyText;
		if(isPrivate){
			mPrivacyText = new JTextField("Private") {
			    @Override public void setBorder(Border border) {
			        // No!
			    }
			};
		}else{
			mPrivacyText = new JTextField("Public") {
			    @Override public void setBorder(Border border) {
			        // No!
			    }
			};
		}
		mMainPanel =  new JPanel();
		mTitlePanel =  new JPanel();
		mPriorityPanel =  new JPanel();
		mPrivacyPanel =  new JPanel();
		mListPanel =  new JPanel();
		mDescriptionPanel =  new JPanel();
		mMainPanel.setLayout(new GridLayout(6, 2));
		mPrivacyPanel.setLayout(new FlowLayout());
		mPrivacyPanel.add(mPublicRB);
		mPrivacyPanel.add(mPrivateRB);
		
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
		addSaveEvents();
	}
	
	
	
	/*private void addPublicRBEvents(){
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
*/	
	private void addSaveEvents(){
		mSaveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
			}
		});
	}

	
/*	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}*/
}
