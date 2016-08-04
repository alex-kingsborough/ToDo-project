package client;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI extends JPanel {
private static final long serialVersionUID = 456789212311L;
	
	private JLabel usernameLabel;
	private JTextField usernameField; 
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JButton loginButton;
	private Navigator mNav;
	
	{
		setLayout(new GridBagLayout());
		loginButton = new JButton("LOGIN");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String username = usernameField.getText();
				if(username.isEmpty()) { return; }
				String password = new String(passwordField.getPassword());
				if(password.isEmpty()) { return; }
				//CHECK IF CLIENT IS ONLINE
					//prepare login request
					//send request to server
					//case: LOGIN SUCCESS
						mNav.toMain();
					//case: FAILURE
						//JOptionPane.showMessageDialog(loginButton, "Username or password is \ninvalid",
						//							"Log-in Failed", JOptionPane.ERROR_MESSAGE);
				//CLIENT IS NOT ONLINE
					//JOptionPane.showMessageDialog(loginButton, "Server cannot be reached.\nProgram in offline mode.",
					//								"Log-in Failed", JOptionPane.WARNING_MESSAGE);
					//mNav.toEditor();
			}
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
	
		gbc.gridy = 2;
		add(loginButton, gbc);
		
		
	}
	
	public LoginGUI(Navigator nav) {
		mNav = nav;
	}
}
