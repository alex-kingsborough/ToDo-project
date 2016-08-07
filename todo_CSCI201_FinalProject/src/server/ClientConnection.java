package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import client.TodoObject;
import client.TodoUser;
import constants.Constants;

public class ClientConnection extends Thread{

	private ObjectOutputStream mOutputWriter;
	private ObjectInputStream mInputReader;
	private Socket sSocket;
	boolean echo = false;
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
					TodoUser temp = Database.get().getUserInfo(elements[1]);
					username = temp.getUsername();
					userID = temp.getID();
					sendMessage(Constants.AUTHENTICATED_MESSAGE);
				} else {
					sendMessage(Constants.NOT_AUTHENTICATED_MESSAGE);
				}
			}
		} else if(s.startsWith(Constants.GET_PUBLIC_TODOS)) {
			Vector<TodoObject> todovec = Database.get().getLatestPublic();
			try {
				mOutputWriter.writeObject(todovec);
			} catch (IOException e) {
				System.out.println("IOE in getPublicTodos, string send: " + e.getMessage());
				e.printStackTrace();
			}
		} else if (s.startsWith(Constants.GET_FRIENDS_TODOS)) {
			Vector<TodoObject> todovec = Database.get().getFriendsTodos(userID);
			try {
				mOutputWriter.writeObject(todovec);
			} catch (IOException e) {
				System.out.println("IOE in GetFriendsTodos Write: " + e.getMessage());
				e.printStackTrace();
			}
		} else if(s.startsWith(Constants.LOGIN_USER_REQUEST)) {
			TodoUser tu = Database.get().getUserInfo(username);
			try {
				mOutputWriter.writeObject(tu);
			} catch (IOException ioe) {
				System.out.println("IOE in LoginUserRequest Write: " + ioe.getMessage());
				ioe.printStackTrace();
			}
		}
		MainServer.gui.writeToLog("Message from Server Thread: " + this.getName() + "Message: " + s);
		if(echo){
			sendMessage("Server Echo: " + s);
		}	
	}

	private void handleRecievedTodoObject(TodoObject to) {
		//TODO: make handleRecievedTodoObject AS LIT as handleRecievedUser
		
		Database.get().addTodo(to,username);
		MainServer.gui.writeToLog("Added todo \"" + to.getTitle() + "\" for user: " + username);
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
		} else Database.get().updateAll(tu);
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


