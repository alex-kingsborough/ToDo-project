package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;



public class ClientServerCommunicator extends Thread {
	
	private Vector<String> recievedStrings = null;
	Socket s;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public ClientServerCommunicator(String hostname, int port) {
		s = null;
		recievedStrings = new Vector<String>();
		try {
			s = new Socket(hostname, port);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void close(){
		if(s != null){
			try {
				s.close();
			} catch (IOException e) {
				System.out.println("Exception thrown when closing socket.");
				e.printStackTrace();
			}
		}
		if(oos != null){
			try {
				oos.close();
			} catch (IOException e) {
				System.out.println("Exception thrown when closing OutputStream.");
				e.printStackTrace();
			}
		}
		if(ois != null){
			try {
				ois.close();
			} catch (IOException e) {
				System.out.println("Exception thrown when closing InputStream.");
				e.printStackTrace();
			}
		}
	}
	
	public void send(String line){
		try {
			oos.writeObject(line);
		} catch (IOException e) {
			System.out.println("Exception thrown when sending message: " + line);
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		try {
			while(true) {
				
				//This is where we read in stuff from the server.
				Object o = ois.readObject();
				if(o instanceof String){
					String line = (String) o;
					if(line.startsWith("SOME PREFIX: ")){
						/*
						 * TODO the thing associated with the prefix.
						 */
					}
				} else if(o instanceof TodoUser){
					TodoUser tu = (TodoUser) o;
					/*
					 * TODO what do we do when we receive a user?
					 */
				} else if(o instanceof TodoObject[]){
					TodoObject [] todoArr = (TodoObject[]) o ;
					/*
					 * TODO What do we do when we receive a TODOobject?
					 */
				} 
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public boolean authenticateUser(String username, String password){
		
		/*
		 * TODO Implement with JDBC
		 */
		
		return false;
	}
	
	public TodoUser requestUser(String username){
		
		/*
		 * TODO Implement with JDBC
		 */
		
		return null;
	}
	
	public void updateUserOnServer(TodoUser tu){
		
		/*
		 * TODO send the user object to the server
		 * TODO Have the server populate/update changes in the SQL database.
		 * Actually, this should be a pretty easy operation.
		 * I just need the SQL setup.
		 */
	}
	
	public boolean isConnected(){
		if(s != null){
			return !s.isClosed();
		} else {
			return false;
		}
	}
}
