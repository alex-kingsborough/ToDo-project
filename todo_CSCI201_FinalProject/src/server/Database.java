package server;

import com.mysql.jdbc.Driver;

import client.TodoList;
import client.TodoObject;
import client.TodoUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Database {
	
	private static Database sDatabase;
	public static Database get() {
		return sDatabase;
	}
	
	private static Connection con = null;
	
	static {
		sDatabase = new Database();
		try{
			new Driver();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TodoProject?user=root&password=root&useSSL=false");
		} catch(SQLException sqle){
			System.out.println("SQL:"+sqle.getMessage());
		}
	}
	
	private final static String newAccount = "INSERT INTO USERS(USERNAME,HASHWORD,ACTUALNAME,EMAIL,ABOUTME) VALUES(?,?,?,?,?)";
	private final static String selectUser = "SELECT * FROM USERS WHERE USERNAME=?";
	private final static String getUserTodos ="SELECT T.TODONAME, T.TODOPRIORITY, T.TODOPRIVATE, L.LISTNAME, T.TODODESC, T.TODOPOINTS, T.TODOFINISHED FROM TODOS T, USERS U, LISTS L WHERE T.USERID=U.USERID AND U.USERID=L.USERID AND T.LISTID=L.LISTID AND U.USERNAME=?";
	private final static String addTodo = "INSERT INTO TODOS(userID,listID,todoPoints,todoPriority,todoDesc,todoName,todoFinished,todoPrivate) VALUES(?,?,?,?,?,?,?,?)";
	private final static String getUserID = "SELECT USERID FROM USERS WHERE USERNAME=?";
	private final static String getListID = "SELECT LISTID FROM LISTS WHERE LISTNAME=?";
	private final static String getLatestPublicTodos = "Select * FROM todos WHERE isPrivate=0 LIMIT 50 ORDER BY createdAt DESC";
	private final static String getAllUserFriends = "SELECT * FROM friendship WHERE fromID=?";
	private final static String getUserTodosById = "SELECT * FROM todos WHERE userID=?";
	
	
	public void stop() {
		try {con.close();} catch (SQLException e) {e.printStackTrace();}
	}
	
	public boolean signup(TodoUser tu) {
		try {
			{ //check if the user exists
				PreparedStatement ps = con.prepareStatement(selectUser);
				ps.setString(1,tu.getUsername());
				ResultSet result = ps.executeQuery();
				if(result.next()) {//if we have any results, don't let the user sign up.
					return false;
				}
			}
			{ //sign up
				PreparedStatement ps = con.prepareStatement(newAccount);
				ps.setString(1, tu.getUsername());
				ps.setString(2, tu.getPassword());
				ps.setString(3, tu.getName());
				ps.setString(4, tu.getEmail());
				ps.setString(5, tu.getAboutMe());
				ps.executeUpdate();
			}
		} catch (SQLException e) {return false;}
		return true;
	}
	
	public boolean login(String username, String hashword) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUser);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				if(hashword.equals(result.getString("hashword"))) return true;
			}
		} catch (SQLException e) {return false;}
		return false;
	}
	
	/* Deprecated use getUserTodoLists() instead
	public Vector<TodoObject> getUserTodos(String username){
		
		Vector<TodoObject> retvec = new Vector<TodoObject>();
		
		try {
			PreparedStatement ps = con.prepareStatement(getUserTodos);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				String name = result.getString("TODONAME");
				int priority = result.getInt("TODOPRIORITY");
				boolean isPrivate = result.getBoolean("TODOPRIVATE");
				String list = result.getString("LISTNAME");
				String desc = result.getString("TODODESC");
				int points = result.getInt("TODOPOINTS");
				boolean isFinished = result.getBoolean("TODOFINISHED");
				TodoObject to = new TodoObject(name, priority, isPrivate, list, desc, points);
				if(isFinished) to.setCompleted(isFinished);
				retvec.add(to);
			}
		} catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}
		
		return retvec;
	}
	*/
	
	public int getUserID(String username){
		int retID = 0;
		
		try{
			PreparedStatement ps = con.prepareStatement(getUserID);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				retID = result.getInt("userID");
			}
			return retID;
		}	catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}
		
		return 0;
	}
	
	public int getListID(String list){
		try{
			PreparedStatement ps = con.prepareStatement(getListID);
			ps.setString(1,list);
			ResultSet result = ps.executeQuery();
			return result.getInt("listID");
		}	catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}
		
		return 0;
	}
	
	
	//(userID,listID,todoPoints,todoPriority,todoDesc,todoName,todoFinished,todoPrivate)
	public void addTodo(TodoObject to, String username){
		try{
			PreparedStatement ps = con.prepareStatement(addTodo);
			ps.setInt(1, getUserID(username));
			ps.setInt(2, to.getList());
			ps.setInt(3, to.getPoints());
			ps.setInt(4, to.getPriority());
			ps.setString(5, to.getDescription());
			ps.setString(6, to.getTitle());
			ps.setBoolean(7, to.getCompleted());
			ps.setBoolean(8, to.getIsPrivate());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}
	}
	
	public static java.sql.Timestamp getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
	
	//gets all user info with just username
		//including all todos
		public TodoUser getUserInfo(String mUsername)
		{
			try{
				PreparedStatement ps = con.prepareStatement(selectUser);
				ps.setString(1, mUsername);
				ResultSet result = ps.executeQuery();
				while(result.next())
				{
					String username = result.getString("username");
					String actualname = result.getString("actualname");
					String email = result.getString("email");
					String password = result.getString("hashword");
					String aboutme = result.getString("aboutme");
					TodoUser newUser = new TodoUser(username,actualname,password,email,aboutme);
					
					//get all todos
					newUser.setTodoLists(getUserTodoLists(newUser));
					
					return newUser;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//user does not exist
			return null;
		}
		
		
		//more queries
		private final static String getAllTodoLists = "Select * FROM lists WHERE userID=?";
		private final static String getTodosFromTodoList = "Select * FROM todos WHERE listID=?";
		
		//gets all todolists for the given user
		public Vector<TodoList> getUserTodoLists(TodoUser user) 
		{
			//do nothing if the user id is not set
			if (user.getID() == 0)
				return null;
			
			Vector<TodoList> todolistVector = new Vector<TodoList>();
			//get todo lists
			try{
				PreparedStatement ps = con.prepareStatement(getAllTodoLists);
				ps.setInt(1, user.getID());
				ResultSet result = ps.executeQuery();
				while(result.next())
				{
					
					int id = result.getInt(0);
					String name = result.getString(2);
					TodoList tl = new TodoList(id, name);
					todolistVector.add(tl);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//we have all todolists, now get all todos in them
			for (TodoList tl : todolistVector)
			{
				try{
					//get all todos for current todolist
					PreparedStatement ps = con.prepareStatement(getTodosFromTodoList);
					ps.setInt(1, tl.getID());
					ResultSet result = ps.executeQuery();
					while(result.next())
					{
						//loop through all todos, create todo objects and add them to tl
						String name = result.getString(6);
						String description = result.getString(5);
						boolean isComplete = result.getBoolean(7);
						int priority = result.getInt(4);
						boolean isPrivate = result.getBoolean(8);
						int points = result.getInt(3);
						int listId = result.getInt(2);
						TodoObject to = new TodoObject(name, priority, isPrivate, listId, description, points);
						tl.addTodo(to);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return todolistVector;
		}
		
		//returns a vector of all the todos of friends
		public Vector<TodoObject> getFriendsTodos(TodoUser tu)
		{
			Vector<TodoObject> friendTodos = new Vector<TodoObject>();
			
			//get all friends
			PreparedStatement ps;
			try {
				ps = con.prepareStatement(getAllUserFriends);
				ps.setInt(1, tu.getID());
				ResultSet result = ps.executeQuery();
				
				//now iterate through all friends
				while (result.next())
				{
					//get a friends todos
					int userId = result.getInt(2);
					ps = con.prepareStatement(getUserTodosById);
					ps.setInt(1, tu.getID());
					ResultSet todoResult = ps.executeQuery();
					
					//now iterate through all of that friends's todos
					while (result.next())
					{
						String name = result.getString(6);
						String description = result.getString(5);
						boolean isComplete = result.getBoolean(7);
						int priority = result.getInt(4);
						boolean isPrivate = result.getBoolean(8);
						int points = result.getInt(3);
						int listId = result.getInt(2);
						TodoObject to = new TodoObject(name, priority, isPrivate, listId, description, points);
						friendTodos.add(to);
					}
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return friendTodos;
		}
		
		//get all public todos
		
}