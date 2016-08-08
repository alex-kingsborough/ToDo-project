package client;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
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
		loginButton.setBorder(BorderFactory.createLineBorder(Constants.redColor,8));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to login page
				mNav.toLogin();
			}
		});
		loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
				loginButton.setBorder(BorderFactory.createLineBorder(Color.white ,8));
				loginButton.setBackground(Color.white);
				
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
				loginButton.setBorder(BorderFactory.createLineBorder(Constants.redColor,8));
				loginButton.setBackground(Constants.redColor);
		    }
		});
		
		
		signupButton = new JButton("SIGNUP");
		signupButton.setBackground(Constants.redColor);
		signupButton.setForeground(Constants.goldColor);
		signupButton.setBorder(BorderFactory.createLineBorder(Constants.redColor,8));

		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to signup page
				mNav.toSignup();
			}
		});
		signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
				signupButton.setBorder(BorderFactory.createLineBorder(Color.white ,8));
				signupButton.setBackground(Color.white);
				
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	signupButton.setBorder(BorderFactory.createLineBorder(Constants.redColor,8));
		    	signupButton.setBackground(Constants.redColor);
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
		offlineButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,8));
		offlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//go to main page in Guest Mode
				TodoClientListener.get().setUsername(Constants.GUEST_USER);
				mNav.toPortal();
			}
		});
		offlineButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	offlineButton.setBorder(BorderFactory.createLineBorder(Color.white ,8));
		    	offlineButton.setBackground(Color.white);
				
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	offlineButton.setBorder(BorderFactory.createLineBorder(Color.darkGray,8));
		    	offlineButton.setBackground(Color.darkGray);
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
