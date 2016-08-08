package server;

import com.mysql.jdbc.Driver;

import client.TodoList;
import client.TodoObject;
import client.TodoUser;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	@SuppressWarnings("unused")
	private final static String getUserTodos ="SELECT T.TODOTITLE, T.TODOPRIORITY, T.TODOPRIVATE, L.LISTNAME, T.TODODESC, T.TODOPOINTS, T.todoIsCompleted FROM TODOS T, USERS U, LISTS L WHERE T.USERID=U.USERID AND U.USERID=L.USERID AND T.LISTID=L.LISTID AND U.USERNAME=?";
	private final static String addTodo = "INSERT INTO TODOS(userID,listID,todoPoints,todoPriority,todoDesc,todoTitle,todoIsCompleted,todoPrivate) VALUES(?,?,?,?,?,?,?,?)";
	private final static String getUserID = "SELECT USERID FROM USERS WHERE USERNAME=?";
	private final static String getListID = "SELECT LISTID FROM LISTS WHERE LISTNAME=? AND USERID=?";
	private final static String getLatestPublicTodos = "Select * FROM todos WHERE todoPrivate=false ORDER BY createdAt DESC LIMIT 50";
	private final static String getAllUserFriends = "SELECT * FROM friendship WHERE fromID=?";
	private final static String getUserTodosById = "SELECT * FROM TODOS WHERE userID=?";
	private final static String getListNameByUserAndID = "SELECT l.listName FROM lists l, users u, todos t WHERE l.listID=? AND u.userID=?";
	private final static String updateUserInfo = "UPDATE USERS SET actualname=?, email=?, points=?, aboutme=? WHERE userID=?";
	private final static String updateUserLists = "UPDATE LISTS SET listName=?, isActive=? WHERE userID=?";
	private final static String updateUserTodos = "UPDATE TODOS SET todoPoints=?, todoPriority=?, todoDesc=?, todoTitle=?, todoIsCompleted=?, todoPrivate=? WHERE userID=?";
	private final static String getUsernameByID = "SELECT USERNAME FROM USERS WHERE USERID=?";
	private final static String removeFriend = "DELETE FROM FRIENDSHIP WHERE fromID=? AND toID=?";
	private final static String addFriend = "INSERT INTO FRIENDSHIP(fromID,toID,createdAt) VALUES(?,?,?)";
	private final static String addList = "INSERT INTO LISTS(userID, listName, isActive) VALUES(?,?,?)";
	private final static String todoExists = "SELECT * FROM TODOS WHERE userID=? AND todoTitle=? AND listID=?";



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

	public String getUsernameByID(int id)
	{
		String username = "";
		try{
			PreparedStatement ps = con.prepareStatement(getUsernameByID);
			ps.setInt(1,id);
			ResultSet result = ps.executeQuery();
			while(result.next()){
				username = result.getString("username");
			}
			return username;
		}	catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}
		return username;
	}

	public int getListID(String list, int userID){
		try{
			PreparedStatement ps = con.prepareStatement(getListID);
			ps.setString(1,list);
			ps.setInt(2, userID);
			ResultSet result = ps.executeQuery();
			while (result.next())
				return result.getInt("listID");
		}	catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}

		return 0;
	}


	//(userID,listID,todoPoints,todoPriority,todoDesc,todoName,todoFinished,todoPrivate)
	//TodoObject Construtor: public TodoObject(String _title, int _priority, boolean _isPrivate, int _listID, 
	//String _listName, String _desc, int _points, int _userID, boolean _isCompleted)
	/* deprecated
	public void addTodo(TodoObject to, String username){
		try{
			PreparedStatement ps = con.prepareStatement(addTodo);
			ps.setInt(1, getUserID(username));
			ps.setInt(2, to.getListID());
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
	*/
	
	public void addTodoOther(TodoObject to, String username, int listID){
		try{
			PreparedStatement ps = con.prepareStatement(addTodo);
			ps.setInt(1, getUserID(username));
			ps.setInt(2, listID);
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

	public String getListName(int userID, int listID){
		String retStr = "";

		try{
			PreparedStatement ps = con.prepareStatement(getListNameByUserAndID);
			ps.setInt(1, listID);
			ps.setInt(2, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				retStr = rs.getString("listName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retStr;
	}

	//gets all user info with just username
	//including all todos
	public TodoUser getUserInfo(String mUsername){
		TodoUser newUser = null;
		PreparedStatement ps = null;

		try{
			ps = con.prepareStatement(selectUser);
			ps.setString(1, mUsername);
			ResultSet result = ps.executeQuery();
			while(result.next())
			{
				int id = result.getInt("userID");
				String username = result.getString("username");
				String actualname = result.getString("actualname");
				String email = result.getString("email");
				String password = result.getString("hashword");
				String aboutme = result.getString("aboutme");
				newUser = new TodoUser(username,actualname,password,email,aboutme);
				newUser.setID(id);
				//get all todos
				newUser.setTodoLists(getUserTodoLists(newUser));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserTodos: " + e.getMessage());
					e.printStackTrace();
				}
		}

		ps = null;
		try {
			ps = con.prepareStatement(getAllUserFriends);
			ps.setInt(1, getUserID(mUsername));
			ResultSet result = ps.executeQuery();

			//now iterate through all friends
			while (result.next()) newUser.addFriend(result.getInt("toID"));
		} catch (SQLException sqle){
			System.out.println("SQLE in getting Friends: " + sqle.getMessage());
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserTodos: " + e.getMessage());
					e.printStackTrace();
				}
		}

		return newUser;
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
				int id = result.getInt("listID");
				String name = result.getString("listName");
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
				/*
				 *  TodoObject Constructor: 
				 *	public TodoObject(String _title, int _priority, boolean _isPrivate, int _listID, 
				 *	String _listName, String _desc, int _points, int _userID, boolean _isCompleted)
				 *
				 */
				while(result.next())
				{
					//loop through all todos, create todo objects and add them to tl
					String name = result.getString("todoTitle");
					String description = result.getString("todoDesc");
					boolean isComplete = result.getBoolean("todoIsCompleted");
					int priority = result.getInt("todoPriority");
					boolean isPrivate = result.getBoolean("todoPrivate");
					int points = result.getInt("todoPoints");
					int listId = result.getInt("listID");
					TodoObject to = new TodoObject(name, priority, isPrivate, listId, getListName(user.getID(), listId), description, points, user.getID(), isComplete);
					tl.addTodo(to);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return todolistVector;
	}

	//returns a vector of all the todos of friends
	public Vector<TodoObject> getFriendsTodos(int _userId)
	{
		Vector<TodoObject> friendTodos = new Vector<TodoObject>();		
		//get all friends
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(getAllUserFriends);
			ps.setInt(1, _userId);
			ResultSet result = ps.executeQuery();

			//now iterate through all friends
			while (result.next())
			{
				//get a friends todos
				int userId = result.getInt("toId");
				ps = con.prepareStatement(getUserTodosById);
				ps.setInt(1, userId);
				ResultSet todoResult = ps.executeQuery();

				//now iterate through all of that friends's todos
				/*
				 *  TodoObject Constructor: 
				 *	public TodoObject(String _title, int _priority, boolean _isPrivate, int _listID, 
				 *	String _listName, String _desc, int _points, int _userID, boolean _isCompleted)
				 *
				 */
				while (todoResult.next())
				{
					String name = todoResult.getString("todoTitle");
					String description = todoResult.getString("todoDesc");
					boolean isComplete = todoResult.getBoolean("todoIsCompleted");
					int priority = todoResult.getInt("todoPriority");
					boolean isPrivate = todoResult.getBoolean("todoIsCompleted");
					int points = todoResult.getInt("todoPoints");
					int listId = todoResult.getInt("listID");
					TodoObject to = new TodoObject(name, priority, isPrivate, listId, getListName(userId, listId), description, points, userId, isComplete);
					to.setUsername(getUsernameByID(userId));
					friendTodos.add(to);
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return friendTodos;
	}

	//get all public todos
	public Vector<TodoObject> getLatestPublic()
	{
		Vector<TodoObject> retvec = new Vector<TodoObject>();

		try {
			PreparedStatement ps = con.prepareStatement(getLatestPublicTodos);
			ResultSet todoResult = ps.executeQuery();
			while(todoResult.next()) {
				String name = todoResult.getString("todoTitle");
				String description = todoResult.getString("todoDesc");
				boolean isComplete = todoResult.getBoolean("todoIsCompleted");
				int priority = todoResult.getInt("todoPriority");
				boolean isPrivate = todoResult.getBoolean("todoPrivate");
				int points = todoResult.getInt("todoPoints");
				int listId = todoResult.getInt("listID");
				int userId = todoResult.getInt("userID");
				TodoObject to = new TodoObject(name, priority, isPrivate, listId, getListName(userId, listId), description, points, userId, isComplete);
				to.setUsername(getUsernameByID(userId));
				retvec.add(to);
			}
		} catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}

		return retvec;
	}

	public void updateAll(TodoUser tu){
		updateUserInfo(tu);
		updateUserLists(tu);
		updateUserTodos(tu);
		updateUserFriends(tu);
	}




	//"UPDATE USERS SET actualname=?, email=?, points=?, aboutme=? WHERE userID=?"
	private void updateUserInfo(TodoUser tu) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(updateUserInfo);
			ps.setString(1,tu.getName());
			ps.setString(2, tu.getEmail());
			ps.setInt(3, tu.getTotalPoints());
			ps.setString(4, tu.getAboutMe());
			ps.setInt(5, getUserID(tu.getUsername()));
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLE in updateUserInfo: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserInfo: " + e.getMessage());
					e.printStackTrace();
				}
		}
	}



	//updateUserLists = "UPDATE LISTS SET listName=?, isActive=? WHERE userID=?"
	//addList = "INSERT INTO LISTS(userID, listName, isActive) VALUES(?,?,?)";
	private void updateUserLists(TodoUser tu) {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try{
			ps = con.prepareStatement(updateUserLists);
			ps2 = con.prepareStatement(addList);
			int userID = getUserID(tu.getUsername());
			ps.setInt(3, userID);
			ps2.setInt(1, userID);
			for(TodoList tl : tu.getTodoLists()){
				if(getListID(tl.getName(), userID) != 0){
					ps.setString(1, tl.getName());
					ps.setBoolean(2, tl.isActive());
					ps.executeUpdate();
				} else {
					ps2.setString(2, tl.getName());
					ps2.setBoolean(3, tl.isActive());
					ps2.executeUpdate();
				}
			}
		} catch (SQLException e){
			System.out.println("SQLE in updateUserLists: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserLists: " + e.getMessage());
					e.printStackTrace();
				}
			if(ps2 != null)
				try {
					ps2.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserLists: " + e.getMessage());
					e.printStackTrace();
				}
		}

	}

	//UPDATE TODOS SET todoPoints=?, todoPriority=?, todoDesc=?, todoTitle=?, todoIsCompleted=?, todoPrivate=? WHERE userID=?";
	private void updateUserTodos(TodoUser tu) {
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement(updateUserTodos);
			int userID = getUserID(tu.getUsername());
			ps.setInt(7, userID);
			for(TodoList tl : tu.getTodoLists()){
				int listID = getListID(tl.getName(), userID);
				for(TodoObject to : tl.getAllTodos()){
					if(todoExists(to, userID, listID)){
						ps.setInt(1, to.getPoints());
						ps.setInt(2, to.getPriority());
						ps.setString(3, to.getDescription());
						ps.setString(4, to.getTitle());
						ps.setBoolean(5, to.getCompleted());
						ps.setBoolean(6, to.getIsPrivate());
						ps.executeUpdate();
					} else 
						addTodoOther(to, tu.getUsername(), listID);
				}
			}
		} catch (SQLException e){
			System.out.println("SQLE in updateUserLists: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserTodos: " + e.getMessage());
					e.printStackTrace();
				}
		}

	}

	private boolean todoExists(TodoObject to, int userID, int listID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement(todoExists);
			ps.setInt(1, userID);
			ps.setString(2, to.getTitle());
			ps.setInt(3, listID);
			rs = ps.executeQuery();
			if(rs.next()) return true;
		} catch (SQLException e){
			System.out.println("SQLE in todoExists: " + e.getMessage());
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserLists: " + e.getMessage());
					e.printStackTrace();
				}
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserLists: " + e.getMessage());
					e.printStackTrace();
				}
		}
		return false;
	}

	private void updateUserFriends(TodoUser tu) {
		Vector<Integer> exFriends = new Vector<Integer>();
		Vector<Integer> tuFriends = tu.getFriendList();

		//get all friends
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(getAllUserFriends);
			ps.setInt(1, getUserID(tu.getUsername()));
			ResultSet result = ps.executeQuery();

			//now iterate through all friends
			while (result.next()) exFriends.add(result.getInt("toID"));
		} catch (SQLException sqle){
			System.out.println("SQLE in getting Friends: " + sqle.getMessage());
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserTodos: " + e.getMessage());
					e.printStackTrace();
				}
		}

		try{
			ps = con.prepareStatement(removeFriend);

			for(Integer friend : exFriends){
				if(!tuFriends.contains(friend)){
					ps.setInt(1, tu.getID());
					ps.setInt(2, friend);
					ps.executeUpdate();
				}
			}

		} catch (SQLException sqle){
			System.out.println("SQLE in getting Friends: " + sqle.getMessage());
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserTodos: " + e.getMessage());
					e.printStackTrace();
				}
		}

		try{
			ps = con.prepareStatement(addFriend);

			Timestamp ts = getCurrentTimeStamp();
			ps.setDate(3, new Date(ts.getTime()));
			for(Integer friend : tuFriends){
				if(!exFriends.contains(friend)){
					ps.setInt(1, tu.getID());
					ps.setInt(2, friend);
					ps.executeUpdate();
				}
			}

		} catch (SQLException sqle){
			System.out.println("SQLE in getting Friends: " + sqle.getMessage());
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLE in Closing updateUserTodos: " + e.getMessage());
					e.printStackTrace();
				}
		}

	}
}