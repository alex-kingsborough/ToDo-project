package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.TodoObject;
import client.TodoUser;

public class ClientConnection extends Thread{

	private ObjectOutputStream mOutputWriter;
	private ObjectInputStream mInputReader;
	private Socket sSocket;
	boolean echo = false;
	private Database db = Database.get();
	private String username = "";

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
					if(s.startsWith("req: ")){
						if(s.endsWith("echo")){
							echo = !echo;
						}
					} if(s.startsWith("login: ")){
						String[] elements = s.split(" ");
					}
					MainServer.gui.writeToLog("Message from Server Thread: " + this.getName() + "Message: " + s);
					if(echo){
						sendMessage("Server Echo: " + s);
					}
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

	private void handleRecievedTodoObject(TodoObject to) {
		//commented out for now
		//db.addTodo(to,username);
		//MainServer.gui.writeToLog("Added todo \"" + to.getTitle() + "\" for user: " + to.getOwner());
	}

	private void handleRecievedUser(TodoUser tu){
		//db.signup(tu); sign up should go through string i believe
		
		//get all user info
		TodoUser newTu = db.getUserInfo(tu.getUsername());
		
		//TODO user does not exist in DB try to sign them up?
		if (newTu == null)
		{
			db.signup(tu);
			return;
		}
		
		//if user does exist try to write them to the socket
		try {
			mOutputWriter.writeObject(newTu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return sSocket;
	}
}


