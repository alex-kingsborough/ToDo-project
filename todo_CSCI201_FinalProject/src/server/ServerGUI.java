package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ServerGUI extends JFrame {

	MainServer mServer = null;
	ScrollTextPane mLogField;
	JButton mStartStopButton;

	public ServerGUI(MainServer serv){
		super("Server");

		mServer = serv;

		mLogField = new ScrollTextPane();
		mLogField.getTextArea().setEditable(false);
		mLogField.getTextArea().setLineWrap(true);
		mLogField.getTextArea().setWrapStyleWord(true);

		add(mLogField,BorderLayout.CENTER);

		mStartStopButton = new JButton("Start");
		mStartStopButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(mStartStopButton.getText().equals("Start")){
					mStartStopButton.setText("Stop");
					mServer.startServer();
				} else if (mStartStopButton.getText().equals("Stop")){
					mStartStopButton.setText("Start");
					mServer.stopServer();
				}
			}

		});

		add(mStartStopButton, BorderLayout.SOUTH);

		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	protected void writeToLog(String line){
		JTextArea ta = mLogField.getTextArea();
		
		if(ta.getText().equals("")){
			ta.append(line);
		} else mLogField.getTextArea().append("" +'\n' + line);
	}

}
