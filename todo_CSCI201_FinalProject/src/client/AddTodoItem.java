package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


/* This needs a vector of the different lists that the current user has 
 * to give the user an option of what list to add the new todo to.
 * 
 * 
 * 
*/
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
	
	public AddTodoItem(TodoUser tu, MainPageGUI mMPGUI){
		super("Add Todo");
		mTU = tu;
		mMainPage = mMPGUI;
		setSize(400, 300);
		setLocation(800, 400);
		AddTodo();
		addPublicRBEvents();
		addPrivateRBEvents();
	
	}
	
	private void AddTodo() {
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
		mPrivateRB = new JRadioButton("Private");
		mPrivateRB.setFont(mFont.deriveFont(20));
		mTitleText = new JTextField(15);
		mTitleText.setFont(mFont.deriveFont(20));
		mDescriptionText = new JTextArea(5, 15);
		mDescriptionText.setFont(mFont.deriveFont(0, 10));
		mPriorityVector = new Vector<Integer>();
		mListVector = new Vector<String>();
		mListIDVector = new Vector<Integer>();
		for(int i = 10; i > 0; i--){
			mPriorityVector.addElement(i);
		}
		mPointsText = new JTextField(15);
		String name;
		for(int i = 0; i < mTU.getTodoLists().size(); i ++){
			name = mTU.getTodoLists().get(i).getName();
			listID = mTU.getTodoLists().get(i).getID();
			mListVector.addElement(name);
			mListIDVector.addElement(listID);
		}
		
		
		mPriorityBox = new JComboBox<Integer>(mPriorityVector);
		mPriorityBox.setFont(mFont.deriveFont(20));
		mListBox = new JComboBox<String>(mListVector);
		mListBox.setFont(mFont.deriveFont(2));
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
				int priority = Integer.parseInt(mPriorityBox.getSelectedItem().toString());
				String list = mListBox.getSelectedItem().toString();
				int currListID = mListIDVector.get(mListBox.getSelectedIndex());
				String description = mDescriptionText.getText();
				
				int points = Integer.parseInt(mPointsText.getText()); ;// ALEX I DIDN't want to mess with all your implementation, just
				
				TodoObject mTodoObject = new TodoObject(title, priority, isPrivate, currListID, list, description, points, mTU.getID(), false);
				int currPlace = 0;
				for(int i = 0; i < mTU.getTodoLists().size(); i ++){
					if(mTU.getTodoLists().get(i).getID() == currListID){
						currPlace = i;
					}
				}
				
				mTU.getTodoLists().get(currPlace).addTodo(mTodoObject);
	
				mMainPage.updatePage();
				
				setVisible(false);
				
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
