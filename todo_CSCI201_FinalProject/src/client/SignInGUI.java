package client;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import constants.Constants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SignInGUI extends JPanel {

	private static final long serialVersionUID = 34334212342151611L;
	private JButton loginButton;
	private JButton signupButton;
	private JButton offlineButton;
	private Navigator mNav;
	
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		loginButton = new JButton("LOGIN");
		loginButton.setBackground(Constants.redColor);
		loginButton.setForeground(Constants.goldColor);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to login page
				mNav.toLogin();
			}
		});
		signupButton = new JButton("SIGNUP");
		signupButton.setBackground(Constants.redColor);
		signupButton.setForeground(Constants.goldColor);
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to signup page
				mNav.toSignup();
			}
		});
		JPanel topButtonPanel = new JPanel(new FlowLayout());
		topButtonPanel.add(loginButton);
		topButtonPanel.add(signupButton);
		topButtonPanel.setBackground(Constants.greyColor);
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(topButtonPanel, gbc);
		
		offlineButton = new JButton("GUEST");
		offlineButton.setBackground(Color.DARK_GRAY);
		offlineButton.setForeground(Constants.goldColor);
		offlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to main page in Guest Mode
				TodoClientListener.get().setUsername(Constants.GUEST_USER);
				mNav.toPortal();
			}
		});
		setBackground(Constants.greyColor);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 2;
		add(offlineButton, gbc);
	}
	
	SignInGUI(Navigator nav) {
		mNav = nav;
	}
}
