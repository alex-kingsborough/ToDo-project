package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SocialSidebar extends JPanel implements Runnable {
	private static final long serialVersionUID = 4325615311L;
	
	private JScrollPane mSocialPanel;
	private TodoUser mUser;
	private Vector<Integer> mFriendList;

	public SocialSidebar(TodoUser inUser, TodoFrame inFrame){ //Creating and filling out the panel on the east holding the add button and Social Panel
		
		mUser = inUser;
		mFriendList = mUser.getFriendList();
		
		JButton mAddTodoButton = new JButton("Add Todo");
		mAddTodoButton.setPreferredSize(new Dimension(this.getWidth(),21));
		mAddTodoButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				AddTodoItem mati = new AddTodoItem(mUser);
				mati.setVisible(true);
			}
		});
		
		createSocialPanel();

		setPreferredSize(new Dimension(175, inFrame.getContentPane().getHeight()));
		setLayout(new BorderLayout());
		add(mAddTodoButton, BorderLayout.NORTH);
		add(mSocialPanel, BorderLayout.CENTER);
		(new Thread(this)).start();
	}
	
	private void createSocialPanel(){ //Creating and filling out the Social Panel that is on every page
		mSocialPanel = new JScrollPane();
		mSocialPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mSocialPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel mSocialGrid = new JPanel();
		mSocialGrid.setLayout(new GridLayout(100,1));
		
		for(int i=0; i<15; i++){
			JPanel mSocialItemPanel = new JPanel();
			mSocialItemPanel.setLayout(new BorderLayout());
			
			JTextArea mSocialInfo = new JTextArea();
			mSocialInfo.setPreferredSize(new Dimension(mSocialItemPanel.getWidth(), 36));
			mSocialInfo.setEditable(false);
			mSocialInfo.setLineWrap(true);
			mSocialInfo.setText("USER made a new todo named TITLE: begin description");
			
			JButton mSocialButton = new JButton();
			mSocialButton.setText("View TITLE");
			mSocialButton.setPreferredSize(new Dimension(mSocialItemPanel.getWidth(), 18));
			
			JLabel mSpaceLabel = new JLabel();
			mSpaceLabel.setPreferredSize(new Dimension(mSocialItemPanel.getWidth(), 9));
			
			mSocialItemPanel.add(mSocialInfo, BorderLayout.CENTER);
			mSocialItemPanel.add(mSpaceLabel, BorderLayout.NORTH);
			mSocialItemPanel.add(mSocialButton, BorderLayout.SOUTH);
			mSocialGrid.add(mSocialItemPanel);
		}
		
		mSocialPanel.getViewport().add(mSocialGrid);
	}
	
	//will update the table with the new todos
	public void updateBar() {
		
		//TODO: add method to get newest todos for friends
		
		
	}
	
	//call update on currently select tab every 5 seconds
	public void run() {
		while (true)
		{
			try {
				//wait for 5 seconds
				Thread.sleep(5000);

				//call update on sidebar
				updateBar();
				mSocialPanel.getVerticalScrollBar().setValue(0);
				System.out.println("Updating");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
