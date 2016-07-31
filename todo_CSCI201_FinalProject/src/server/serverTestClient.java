package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class serverTestClient extends JFrame{
	
	client cThread;
	private ScrollTextPane stp;
	
	public static void main(String [] args){

		@SuppressWarnings("unused")
		serverTestClient stc = new serverTestClient();
	}
	
	public serverTestClient(){
		super("Test Client");

		String hostname = (String)JOptionPane.showInputDialog(
                this,
                "Please input the hostname:",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");
		
		String portString = (String)JOptionPane.showInputDialog(
                this,
                "Please input the port (Warning: No validation used):",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");
		
		int port = Integer.parseInt(portString);
		
		JTextField jtf = new JTextField();
		add(jtf, BorderLayout.NORTH);
		
		stp = new ScrollTextPane();
		add(stp, BorderLayout.CENTER);
		
		cThread = new client(hostname, port, this.getTextArea());
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cThread.send(jtf.getText());
				jtf.setText("");
			}
			
		});
		add(sendButton, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setVisible(true);
		
	}
	
	public JTextArea getTextArea(){
		return stp.getTextArea();
	}
}

class client extends Thread{
	private ObjectInputStream mInputReader;
	private ObjectOutputStream mOutputWriter;
	private JTextArea ta;
	
	public client(String hostname, int port, JTextArea ta) {
		this.ta = ta;
		Socket s = null;
		try {
			s = new Socket(hostname, port);
			mOutputWriter = new ObjectOutputStream(s.getOutputStream());
			mInputReader = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void send(String message){
		try {
			mOutputWriter.writeObject(message);
			mOutputWriter.flush();
		} catch (IOException e) {
			ta.append("Error sending message. IOE: " + e.getMessage());
		}
	}
	
	public void run() {
		try {
			while(true) {
				Object o = mInputReader.readObject();
				if(o instanceof String){
					String message = (String) o;
					ta.append(message + '\n');
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
}