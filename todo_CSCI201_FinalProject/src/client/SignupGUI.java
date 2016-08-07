package client;

import java.awt.Color;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import constants.Constants;

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
	private JTextArea mAboutMeTextArea;
	private JLabel mAboutMeLabel;
	private Navigator mNav;
	
	{
		setLayout(new GridBagLayout());
		setBackground(Constants.greyColor);
		loginButton = new JButton("SIGNUP");
		loginButton.setBackground(Color.BLACK);
		loginButton.setForeground(Constants.goldColor);
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
				String name = mNameField.getText();
				if(name.isEmpty()) { return; } //name field empty is invalid so return 
				String email = mEmailField.getText();
				if(email.isEmpty()) { return; } //email field empty is invalid so return
				String aboutMe = mAboutMeTextArea.getText();
				if(aboutMe.isEmpty()) { aboutMe = ""; } //about me can be empty
				//If client is online
				if(TodoClientListener.get().isConnected()) {
					//CITE: http://stackoverflow.com/questions/1559751/regex-to-make-sure-that-the-string-contains-at-least-one-lower-case-char-upper
					//using regex to determine 1 number and 1 uppercase letter
					validPasswordFormat = password.matches("^(?=.*[A-Z])(?=.*\\d).+$");//need two \\ before d not one \ like stackoverflow example
					if(validPasswordFormat) {
						//build todo user
						TodoUser newTodoUser = new TodoUser(username, name, Encrypt.SHA1(password), email, aboutMe);
						newTodoUser.addTodoList(new TodoList(0, "My 1st List"));
						//send signup request
						TodoClientListener.get().sendUser(newTodoUser);
						//read response from server
						String response = TodoClientListener.get().readLine();
						//case: SIGNUP SUCCESS
						if(response.equals(Constants.SUCCESS_MESSAGE)) {
							//signup user and go to editor
							TodoClientListener.get().setUsername(username);
							mNav.toPortal(newTodoUser);
						}
						//case: SIGNUP FAILURE
						else {
							JOptionPane.showMessageDialog(loginButton, "Username or password is \ninvalid.",
														"Sign-up Failed", JOptionPane.ERROR_MESSAGE);
						}
					
					} else if (!validPasswordFormat) {
						JOptionPane.showMessageDialog(SignupGUI.this, 
								"Password must contain at least: 1-Number 1-Uppercase Letter", 
								"Sign-up Failed", JOptionPane.WARNING_MESSAGE);
					}
				}
				//CLIENT IS NOT ONLINE
				else {
					JOptionPane.showMessageDialog(loginButton, "Server cannot be reached.\nProgram in offline mode.",
							"Log-in Failed", JOptionPane.WARNING_MESSAGE);
					TodoClientListener.get().setUsername(Constants.GUEST_USER);
					mNav.toPortal();
				}
			} //end actionPerformed()
		});
	
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JPanel usernamePanel = new JPanel(new FlowLayout());
		usernameLabel = new JLabel(" USERNAME: ");
		usernameLabel.setForeground(Constants.goldColor);
		usernameLabel.setBackground(Constants.greyColor);
		usernamePanel.add(usernameLabel);
		usernameField = new JTextField();
		usernameField.setBorder(null);
		usernameField.setColumns(10);
		usernameField.setForeground(Constants.redColor);
		usernameField.setBackground(Constants.lightGreyColor);
		usernamePanel.add(usernameField);
		usernamePanel.setBackground(Constants.greyColor);
		add(usernamePanel, gbc);
		
		JPanel passwordPanel = new JPanel(new FlowLayout());
		passwordLabel = new JLabel("PASSWORD: ");
		passwordLabel.setForeground(Constants.goldColor);
		passwordLabel.setBackground(Constants.greyColor);
		passwordPanel.add(passwordLabel);
		passwordField = new JPasswordField();
		passwordField.setBorder(null);
		passwordField.setColumns(10);
		passwordField.setForeground(Constants.redColor);
		passwordField.setBackground(Constants.lightGreyColor);
		passwordPanel.add(passwordField);
		passwordPanel.setBackground(Constants.greyColor);
		gbc.gridy = 1;
		add(passwordPanel, gbc);
		
		JPanel repeatPanel = new JPanel(new FlowLayout());
		repeatLabel = new JLabel("       REPEAT:  ");
		repeatLabel.setForeground(Constants.goldColor);
		repeatLabel.setBackground(Constants.greyColor);
		repeatPanel.add(repeatLabel);
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setBorder(null);
		repeatPasswordField.setColumns(10);
		repeatPasswordField.setForeground(Constants.redColor);
		repeatPasswordField.setBackground(Constants.lightGreyColor);
		repeatPanel.add(repeatPasswordField);
		repeatPanel.setBackground(Constants.greyColor);
		gbc.gridy = 2;
		add(repeatPanel, gbc);
		
		JPanel mNamePanel = new JPanel(new FlowLayout());
		mNameLabel = new JLabel("         NAME:   ");
		mNameLabel.setForeground(Constants.goldColor);
		mNameLabel.setBackground(Constants.greyColor);
		mNamePanel.add(mNameLabel);
		mNameField = new JTextField();
		mNameField.setBorder(null);
		mNameField.setColumns(10);
		mNameField.setForeground(Constants.redColor);
		mNameField.setBackground(Constants.lightGreyColor);
		mNamePanel.add(mNameField);
		mNamePanel.setBackground(Constants.greyColor);
		
		gbc.gridy = 3;
		add(mNamePanel, gbc);
		
		JPanel mEmailPanel = new JPanel(new FlowLayout());
		mEmailLabel = new JLabel("         EMAIL:   ");
		mEmailLabel.setForeground(Constants.goldColor);
		mEmailLabel.setBackground(Constants.greyColor);
		mEmailPanel.add(mEmailLabel);
		mEmailField = new JTextField();
		mEmailField.setBorder(null);
		mEmailField.setColumns(10);
		mEmailField.setForeground(Constants.redColor);
		mEmailField.setBackground(Constants.lightGreyColor);
		mEmailPanel.add(mEmailField);
		mEmailPanel.setBackground(Constants.greyColor);
		
		gbc.gridy = 4;
		add(mEmailPanel, gbc);
		
		JPanel mAboutMeLabelPanel = new JPanel(new FlowLayout());
		mAboutMeLabel = new JLabel("About Me");
		mAboutMeLabel.setForeground(Constants.goldColor);
		mAboutMeLabel.setBackground(Constants.greyColor);
		mAboutMeLabelPanel.add(mAboutMeLabel);
		mAboutMeLabelPanel.setBackground(Constants.greyColor);
		
		gbc.gridy = 5;
		add(mAboutMeLabelPanel, gbc);
		
		JPanel mAboutMePanel = new JPanel(new FlowLayout());
		mAboutMePanel.setBackground(Constants.greyColor);
		mAboutMeTextArea = new JTextArea(10,12);
		mAboutMeTextArea.setWrapStyleWord(true);
		mAboutMeTextArea.setForeground(Constants.redColor);
		mAboutMeTextArea.setBackground(Constants.lightGreyColor);
		mAboutMePanel.add(mAboutMeTextArea);
		
		gbc.gridy = 6;
		add(mAboutMePanel, gbc);
		
		gbc.gridy = 7;
		add(loginButton, gbc);
		
		
	}
	
	public SignupGUI(Navigator nav) {
		mNav = nav;
	}

}
