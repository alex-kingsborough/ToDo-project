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
	JLabel mTitleLabel;
	JLabel mPriorityLabel;
	JLabel mPrivacyLabel;
	JLabel mListLabel;
	JLabel mDescriptionLabel;
	JButton mSaveButton;
	JTextField mTitleText;
	JRadioButton mPublicRB;
	JTextArea mDescriptionText;
	JComboBox<String> mPriorityBox;
	JComboBox<String> mListBox;
	JRadioButton mPrivateRB;
	JPanel mMainPanel;
	JPanel mTitlePanel;
	JPanel mPriorityPanel;
	JPanel mPrivacyPanel;
	JPanel mListPanel;
	JPanel mDescriptionPanel;
	Vector<String> mPriorityVector;
	Vector<String> mListVector;
	boolean isPrivate;
	
	private AddTodoItem(){
		super("Add Todo");
		setSize(400, 300);
		setLocation(800, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AddTodo();
		addPublicRBEvents();
		addPrivateRBEvents();
		
	
	}
	
	private void AddTodo() {
		mMainPanel = new JPanel();
		mTitleLabel = new JLabel("Title: ");
		mTitleLabel.setFont(new Font("Serif", Font.PLAIN, 22));

		mPriorityLabel = new JLabel("Priority: ");
		mPriorityLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		mPrivacyLabel = new JLabel("Privacy: ");
		mPrivacyLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		mListLabel = new JLabel("List: ");
		mListLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		mDescriptionLabel = new JLabel("Description: ");
		mDescriptionLabel.setFont(new Font("Serif", Font.PLAIN, 22));
		mSaveButton = new JButton("Save");
		mSaveButton.setFont(new Font("Serif", Font.PLAIN, 20));
		mPublicRB = new JRadioButton("Public");
		mPublicRB.setFont(new Font("Serif", Font.PLAIN, 20));
		mPrivateRB = new JRadioButton("Private");
		mPrivateRB.setFont(new Font("Serif", Font.PLAIN, 20));
		mTitleText = new JTextField(15);
		mTitleText.setFont(new Font("Serif", Font.PLAIN, 20));
		mDescriptionText = new JTextArea(5, 15);
		mDescriptionText.setFont(new Font("Serif", Font.PLAIN, 12));
		mPriorityVector = new Vector<String>();
		mPriorityVector.add("Top");
		mPriorityVector.add("High");
		mPriorityVector.add("Medium");
		mPriorityVector.add("Low");
		mPriorityVector.add("Lowest");
		mListVector = new Vector<String>();
		mListVector.add("Public Todos");
		mListVector.add("Private Todos");
		mPriorityBox = new JComboBox<String>(mPriorityVector);
		mPriorityBox.setFont(new Font("Serif", Font.PLAIN, 20));
		mListBox = new JComboBox<String>(mListVector);
		mListBox.setFont(new Font("Serif", Font.PLAIN, 20));
		mMainPanel =  new JPanel();
		mTitlePanel =  new JPanel();
		mPriorityPanel =  new JPanel();
		mPrivacyPanel =  new JPanel();
		mListPanel =  new JPanel();
		mDescriptionPanel =  new JPanel();
		mMainPanel.setLayout(new GridLayout(5, 2));
		mTitlePanel.setLayout(new FlowLayout());
		mPrivacyPanel.setLayout(new FlowLayout());
		mPrivacyPanel.add(mPublicRB);
		mPrivacyPanel.add(mPrivateRB);
		
		mMainPanel.add(mTitleLabel);
		mMainPanel.add(mTitleText);
		
		mMainPanel.add(mPriorityLabel);
		mMainPanel.add(mPriorityBox);

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
	}
	
	public static void main(String [] args){
		AddTodoItem mtodo = new AddTodoItem();
		mtodo.setVisible(true);
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
				String title = mTitleText.getText();
				String priority = mPriorityBox.getSelectedItem().toString();
				String list = mListBox.getSelectedItem().toString();
				String description = mDescriptionText.getText();
				int points = 0;//TODO ALEX I DIDN't want to mess with all your implementation, just
				TodoObject mTodoObject = new TodoObject(title, priority, isPrivate, list, description, points);
				//need to send this to the client to add to user's todos
			}
		});
	}

}
