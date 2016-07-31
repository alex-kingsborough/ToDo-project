package client;


import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SignInGUI extends JPanel {

	private static final long serialVersionUID = 34334212342151611L;
	private JButton loginButton;
	private JButton signupButton;
	private JButton offlineButton;
	
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		loginButton = new JButton("LOGIN");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to login page
			}
		});
		signupButton = new JButton("SIGNUP");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to signup page
			}
		});
		JPanel topButtonPanel = new JPanel(new FlowLayout());
		topButtonPanel.add(loginButton);
		topButtonPanel.add(signupButton);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(topButtonPanel, gbc);
		
		offlineButton = new JButton("GUEST");
		offlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to main page in Guest Mode
			}
		});

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 2;
		add(offlineButton, gbc);
	}
	
}
