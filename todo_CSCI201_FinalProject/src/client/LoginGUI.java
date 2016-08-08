package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import constants.Constants;

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
		loginButton.setBackground(Color.BLACK);
		loginButton.setForeground(Constants.goldColor);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String username = usernameField.getText();
				if(username.isEmpty()) { return; }
				String password = new String(passwordField.getPassword());
				if(password.isEmpty()) { return; }
				//CHECK IF CLIENT IS ONLINE
				if(TodoClientListener.get().isConnected()) {
					//prepare login request
					String request = Constants.LOGIN_PREFIX + username + " " + Encrypt.SHA1(password);
					//send request to server
					TodoClientListener.lock.lock();
					String response;
					try {
						TodoClientListener.get().send(request);
						response = TodoClientListener.get().readLine();
					} finally {
						TodoClientListener.lock.unlock();
					}
					//case: LOGIN SUCCESS
					if(response.equals(Constants.AUTHENTICATED_MESSAGE)) {
						TodoClientListener.get().setUsername(username);
						//get todo user to pass into portal
						request = Constants.LOGIN_USER_REQUEST;
						TodoUser loggedInUser = null;
						TodoClientListener.lock.lock();
						try {
							TodoClientListener.get().send(request);
							loggedInUser = TodoClientListener.get().readTodoUser();
						} finally {
							TodoClientListener.lock.unlock();
						}
						mNav.toPortal(loggedInUser);
							
					}
					//case: FAILURE
					else {
						JOptionPane.showMessageDialog(loginButton, "Username or password is \ninvalid",
													"Log-in Failed", JOptionPane.ERROR_MESSAGE);
					}
				}
				//CLIENT IS NOT ONLINE
				else {
					JOptionPane.showMessageDialog(loginButton, "Server cannot be reached.\nProgram in Guest mode.",
													"Log-in Failed", JOptionPane.WARNING_MESSAGE);
					TodoClientListener.get().setUsername(Constants.GUEST_USER);
					mNav.toPortal();
				}

			}
		});
		loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
				loginButton.setBorder(BorderFactory.createLineBorder(Color.white ,8));
				loginButton.setBackground(Color.white);
				
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
				loginButton.setBorder(BorderFactory.createLineBorder(Color.black,8));
				loginButton.setBackground(Color.black);
		    }
		});
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JPanel usernamePanel = new JPanel(new FlowLayout());
		usernameLabel = new JLabel(" USERNAME: ");
		usernameLabel.setForeground(Constants.goldColor);
		usernameLabel.setBackground(Constants.greyColor);
		usernamePanel.add(usernameLabel);
		usernamePanel.setBackground(Constants.greyColor);
		
		usernameField = new JTextField(){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 6762367646379653329L;

			@Override public void setBorder(Border border) {
		        // None
		    }
		};
		usernameField.setColumns(10);
		usernamePanel.add(usernameField);
		add(usernamePanel, gbc);
		usernameField.setForeground(Constants.redColor);
		usernameField.setBackground(Constants.lightGreyColor);

		JPanel passwordPanel = new JPanel(new FlowLayout());
		passwordLabel = new JLabel(" PASSWORD: ");
		passwordLabel.setForeground(Constants.goldColor);
		passwordLabel.setBackground(Constants.greyColor);
		passwordPanel.add(passwordLabel);
		passwordPanel.setBackground(Constants.greyColor);
		passwordField = new JPasswordField(){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 8657616015155767600L;

			@Override public void setBorder(Border border) {
		        // None
		    }
		};
		passwordField.setColumns(10);
		passwordPanel.add(passwordField);
		passwordField.setForeground(Constants.redColor);
		passwordField.setBackground(Constants.lightGreyColor);

		setBackground(Constants.greyColor);
		gbc.gridy = 1;
		add(passwordPanel, gbc);
	
		gbc.gridy = 2;
		add(loginButton, gbc);
		
		
	}
	
	public LoginGUI(Navigator nav) {
		mNav = nav;
	}
}
