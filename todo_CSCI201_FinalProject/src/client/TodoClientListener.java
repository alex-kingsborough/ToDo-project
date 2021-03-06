package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import constants.Constants;



public class TodoClientListener {
	
	private static TodoClientListener mInstance;

	private Vector<String> recievedStrings = null;
	Socket s;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private TodoUser tuGlobal;
	
	private String username;
	
	static Lock lock;
	
	private TodoClientListener(String hostname, int port) {
		s = null;
		recievedStrings = new Vector<String>();
		try {
			s = new Socket(hostname, port);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			lock = new ReentrantLock();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	static {
		mInstance = new TodoClientListener("localhost", 6789);
	}
	
	public static TodoClientListener get() {
		return mInstance;
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
			oos.flush();
		} catch (IOException e) {
			System.out.println("Exception thrown when sending message: " + line);
			e.printStackTrace();
		}
	}
	
	public String readLine() {
		try {
			Object o = ois.readObject();
			if(o instanceof String) {
				String line = (String) o;
				if(line.startsWith(Constants.AUTHENTICATED_MESSAGE)) {
					return line;
				}else if (line.startsWith(Constants.SUCCESS_MESSAGE)) {
					return line;
				}else if(o instanceof TodoUser){
					TodoUser tu = (TodoUser) o;
					/*
				 	* set global user to received TodoUser
				 	*/
					tuGlobal = tu;
				} else if(o instanceof TodoObject[]){
					TodoObject [] todoArr = (TodoObject[]) o ;
					/*
					 * TODO What do we do when we receive a TODOobject?
					 */
					
					//this is probably deprecated at this point
				}
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
		return " ";
	}
	
	public Vector<TodoObject> readTodoObjects(String request) {
		lock.lock();
		try {
			TodoClientListener.get().send(request);
			
			try {
				Object o = ois.readObject();
				if(o instanceof Vector<?>) {
					Vector<TodoObject> todoVec = (Vector<TodoObject>) o;
					return todoVec;
				}
			} catch (ClassNotFoundException cnfe) {
				System.out.println("cnfe: " + cnfe.getMessage());
			} catch (IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
			}
			return new Vector<TodoObject>();
		} finally {
			lock.unlock();
		}
	}
	
	public Vector<Vector<TodoObject>> readVectorTodoObjects() {
		lock.lock();
		try {
			TodoClientListener.get().send(Constants.GET_PUBLIC_TODOS);
			
			try {
				Object o = ois.readObject();
				if(o instanceof Vector<?>) {
					Vector<Vector<TodoObject>> todoVec = (Vector<Vector<TodoObject>>) o;
					return todoVec;
				}
			} catch (ClassNotFoundException cnfe) {
				System.out.println("cnfe: " + cnfe.getMessage());
			} catch (IOException ioe) {
				System.out.println("ioe: " + ioe.getMessage());
				ioe.printStackTrace();
			}
			Vector<Vector<TodoObject>> vvt = new Vector<Vector<TodoObject>>();
			vvt.add(new Vector<TodoObject>());
			vvt.add(new Vector<TodoObject>());
			return vvt;
		}  finally {
			TodoClientListener.lock.unlock();
		}
	}
	
	public TodoUser readTodoUser() {
		try {
			Object o = ois.readObject();
			if(o instanceof TodoUser) {
				TodoUser tu = (TodoUser) o;
				return tu;
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
		return null;
	}
	/*
	public void run() {
		try {
			while(true) {
				
				//This is where we read in stuff from the server.
				Object o = ois.readObject();
				if(o instanceof String){
					String line = (String) o;
					if(line.startsWith("SOME PREFIX: ")){
				
					}
				} else if(o instanceof TodoUser){
					TodoUser tu = (TodoUser) o;
				
					tuGlobal = tu;
				} else if(o instanceof TodoObject[]){
					TodoObject [] todoArr = (TodoObject[]) o ;
				} 
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	*/
	
	public void sendUser(TodoUser todoUser) {
		try {
			oos.writeObject(todoUser);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean authenticateUser(String username, String hashword){
		
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
		 * Send user object to server
		 * server will send back an updated user object
		 */
		try {
			oos.writeObject(tu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isConnected(){
		if(s != null){
			return !s.isClosed();
		} else {
			return false;
		}
	}
}
