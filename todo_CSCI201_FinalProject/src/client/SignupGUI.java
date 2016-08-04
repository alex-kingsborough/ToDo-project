package client;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupGUI extends JPanel {
	private static final long serialVersionUID = 34432233333L;
	private JLabel usernameLabel;
	private JTextField usernameField; 
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JLabel repeatLabel;
	private JPasswordField repeatPasswordField;
	private JButton loginButton;
	private JLabel mNameLabel;
	private JTextField mNameField;
	private JLabel mEmailLabel;
	private JTextField mEmailField;
	private JLabel mDOBLabel;
	private JComboBox<String> mDOBMonth;
	private JComboBox<Integer> mDOBDay;
	private JComboBox<Integer> mDOBYear;
	private Navigator mNav;
	
	{
		setLayout(new GridBagLayout());
		loginButton = new JButton("SIGNUP");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean validPasswordFormat;
				String username = usernameField.getText();
				if(username.isEmpty()) { return; }
				String password = new String(passwordField.getPassword());
				if(password.isEmpty()) { return; }
				String repeatPassword = new String(passwordField.getPassword());
				if(repeatPassword.isEmpty()) { return; }
				if(!password.equals(repeatPassword)) { return; } //password fields must match
				//If client is online
					//CITE: http://stackoverflow.com/questions/1559751/regex-to-make-sure-that-the-string-contains-at-least-one-lower-case-char-upper
					//using regex to determine 1 number and 1 uppercase letter
					validPasswordFormat = password.matches("^(?=.*[A-Z])(?=.*\\d).+$");//need two \\ before d not one \ like stackoverflow example
					if(validPasswordFormat) {
						//build signup request to send to server
						//send signup request
						//flush signup request
						//read response from server
						//case: SIGNUP SUCCESS
							//signup user and go to editor
							mNav.toMain();
						//case: SIGNUP FAILURE
							//JOptionPane.showMessageDialog(loginButton, "Username or password is \ninvalid.",
							//							"Sign-up Failed", JOptionPane.ERROR_MESSAGE);
			
					
					} else if (!validPasswordFormat) {
						JOptionPane.showMessageDialog(SignupGUI.this, 
								"Password must contain at least: 1-Number 1-Uppercase Letter", 
								"Sign-up Failed", JOptionPane.WARNING_MESSAGE);
					}
				//CLIENT IS NOT ONLINE
					JOptionPane.showMessageDialog(loginButton, "Server cannot be reached.\nProgram in offline mode.",
							"Log-in Failed", JOptionPane.WARNING_MESSAGE);
					//set client to GUEST
					//mNav.toMain();
					
			} //end actionPerformed()
		});
	
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JPanel usernamePanel = new JPanel(new FlowLayout());
		usernameLabel = new JLabel("USERNAME: ");
		usernamePanel.add(usernameLabel);
		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernamePanel.add(usernameField);
		add(usernamePanel, gbc);
		
		JPanel passwordPanel = new JPanel(new FlowLayout());
		passwordLabel = new JLabel("PASSWORD: ");
		passwordPanel.add(passwordLabel);
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordPanel.add(passwordField);
		gbc.gridy = 1;
		add(passwordPanel, gbc);
		
		JPanel repeatPanel = new JPanel(new FlowLayout());
		repeatLabel = new JLabel("     REPEAT:  ");
		repeatPanel.add(repeatLabel);
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setColumns(10);
		repeatPanel.add(repeatPasswordField);
		gbc.gridy = 2;
		add(repeatPanel, gbc);
		
		JPanel mNamePanel = new JPanel(new FlowLayout());
		mNameLabel = new JLabel("       NAME:   ");
		mNamePanel.add(mNameLabel);
		mNameField = new JTextField();
		mNameField.setColumns(10);
		mNamePanel.add(mNameField);
		
		gbc.gridy = 3;
		add(mNamePanel, gbc);
		
		JPanel mEmailPanel = new JPanel(new FlowLayout());
		mEmailLabel = new JLabel("       EMAIL:   ");
		mEmailPanel.add(mEmailLabel);
		mEmailField = new JTextField();
		mEmailField.setColumns(10);
		mEmailPanel.add(mEmailField);
		
		gbc.gridy = 4;
		add(mEmailPanel, gbc);
		
		JPanel mDOBPanel = new JPanel(new FlowLayout());
		mDOBLabel = new JLabel("DOB: ");
		mDOBPanel.add(mDOBLabel);
		
		mDOBMonth = new JComboBox<String>();
		mDOBMonth.addItem("January");
		mDOBMonth.addItem("February");
		mDOBMonth.addItem("March");
		mDOBMonth.addItem("April");
		mDOBMonth.addItem("May");
		mDOBMonth.addItem("June");
		mDOBMonth.addItem("July");
		mDOBMonth.addItem("August");
		mDOBMonth.addItem("September");
		mDOBMonth.addItem("October");
		mDOBMonth.addItem("November");
		mDOBMonth.addItem("December");
		mDOBPanel.add(mDOBMonth);

		mDOBDay = new JComboBox<Integer>();
		for(int i=1; i < 32; i++) {
			mDOBDay.addItem(new Integer(i));
		}
		mDOBPanel.add(mDOBDay);

		mDOBYear = new JComboBox<Integer>();
		for(int i=2004; i > 1804; i--) {
			mDOBYear.addItem(new Integer(i));
		}
		mDOBPanel.add(mDOBYear);
		
		gbc.gridy = 5;
		add(mDOBPanel, gbc);

		
		gbc.gridy = 6;
		add(loginButton, gbc);
		
		
	}
	
	public SignupGUI(Navigator nav) {
		mNav = nav;
	}

}
