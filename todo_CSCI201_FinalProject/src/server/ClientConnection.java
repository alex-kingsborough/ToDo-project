package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.TodoObject;
import client.TodoUser;
import constants.Constants;

public class ClientConnection extends Thread{

	private ObjectOutputStream mOutputWriter;
	private ObjectInputStream mInputReader;
	private Socket sSocket;
	boolean echo = false;
//	private Database db = Database.get();
	private String username = "";
	private int userID = 0;

	public ClientConnection(Socket s, MainServer mainServer) {
		sSocket = s;
		try{
			mOutputWriter = new ObjectOutputStream(s.getOutputStream());
			mInputReader = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(String line) {
		try {
			mOutputWriter.writeObject(line);
			mOutputWriter.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void run() {
		try {

			/*
			 * TODO
			 * This is where we read in new objects, So any objects that are SENT from the client
			 * (i.e. the ClientServerCommunicator class) MUST be handled here. Truthfully, that's not
			 * difficult to manage with the if(o instanceof CLASS) architecture.
			 */
			
			while(true){
				Object o = mInputReader.readObject();
				if(o instanceof String){
					String s = (String) o;
					handleString(s);
				} else if (o instanceof TodoUser){
					TodoUser tu = (TodoUser) o;
					handleRecievedUser(tu);
				} else if (o instanceof TodoObject){
					TodoObject to = (TodoObject) o;
					handleRecievedTodoObject(to);
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe in run: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe in run: " + ioe.getMessage());
		}
	}

	private void handleString(String s) {
		if(s.startsWith("req: ")){
			if(s.endsWith("echo")){
				echo = !echo;
			}
		} if(s.startsWith(Constants.LOGIN_PREFIX)){
			String[] elements = s.split(" ");
			if(elements.length != 3){
				sendMessage(Constants.NOT_AUTHENTICATED_MESSAGE);
			} else {
				if(Database.get().login(elements[1], elements[2])){
					sendMessage(Constants.AUTHENTICATED_MESSAGE);
				} else {
					sendMessage(Constants.NOT_AUTHENTICATED_MESSAGE);
				}
			}
		}
		MainServer.gui.writeToLog("Message from Server Thread: " + this.getName() + "Message: " + s);
		if(echo){
			sendMessage("Server Echo: " + s);
		}	
	}

	private void handleRecievedTodoObject(TodoObject to) {
		//TODO: make handleRecievedTodoObject as lit as handleRecievedUser
		
		//commented out for now
		//db.addTodo(to,username);
		//MainServer.gui.writeToLog("Added todo \"" + to.getTitle() + "\" for user: " + to.getOwner());
	}

	private void handleRecievedUser(TodoUser tu){
		
		// check if user exists
		if (Database.get().getUserID(tu.getUsername()) == 0)
		{
			//if not try to sign them up 
			//and return the authenticated user
			if (Database.get().signup(tu))
			{
				//we signed up user
				//so let's return a populated todo object
				tu = Database.get().getUserInfo(tu.getUsername());
				sendMessage(Constants.SUCCESS_MESSAGE);
				username = tu.getUsername();
				userID = tu.getID();
			}
			else
			{
				
				
				MainServer.gui.writeToLog("Error adding user: " + tu.getUsername());
				sendMessage(Constants.FAIL_MESSAGE);
				return;
			}
		}
		
		//TODO Implement the update user part. This will also be done in the handleReceivedUser.
		
		//now that we have a populated user object write it the socket
		try {
			mOutputWriter.writeObject(tu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return sSocket;
	}
}


