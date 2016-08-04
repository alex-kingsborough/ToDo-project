package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.mysql.jdbc.Driver;

import client.TodoList;
import client.TodoObject;
import client.TodoUser;

public class MySQLDriver {
	
	private Connection con;
	private final static String selectUser = "SELECT * FROM User WHERE username=?";
	private final static String selectUserPassword = "SELECT password FROM User WHERE username=?";
	private final static String addUser = "INSERT INTO User(username,email, password) VALUES(?,?,?,?)";
	private final static String getAllTodos = "Select * FROM Todo WHERE userID=?";
	private final static String getAllTodoLists = "Select * FROM TodoList WHERE userID=?";
	private final static String getTodosFromTodoList = "Select * FROM TodoList WHERE todolistID=?";
	private final static String getLatestPublicTodos = "Select * FROM Todo WHERE isPrivate=0 LIMIT 50 ORDER BY createdAt DESC";
	
	//adapted from factory code
	public MySQLDriver()
	{
		try{
			new Driver();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void connect()
	{
		try{
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/FinalProjectTodo?user=root");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		try{
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean doesExist(TodoUser user)
	{
		try{
			PreparedStatement ps = con.prepareStatement(selectUser);
			ps.setString(1, user.getUsername());
			ResultSet result = ps.executeQuery();
			while(result.next())
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addUser(TodoUser user)
	{
		String username = user.getUsername();
		String email = user.getEmail();
		String password = user.getPassword();
		try {
			PreparedStatement ps = con.prepareStatement(addUser);
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3,  password);
			ps.executeUpdate();
			System.out.println("Database: Added user " + username);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean authUser(TodoUser user)
	{
		try{
			PreparedStatement ps = con.prepareStatement(selectUserPassword);
			ps.setString(1, user.getUsername());
			ResultSet result = ps.executeQuery();
			while(result.next())
			{
				String hashResult = result.getString(1);
				if (hashResult.equals(user.getPassword()))
				{
					System.out.println("Database: Authorized user " + user.getUsername());
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
				int userId = result.getInt(0);
				String username = result.getString(1);
				String email = result.getString(2);
				String password = result.getString(3);
				TodoUser newUser = new TodoUser(userId,username,email,password);
				newUser.setAboutMe(result.getString(4));
				
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
					String name = result.getString(3);
					String description = result.getString(4);
					boolean isComplete = result.getBoolean(5);
					int priority = result.getInt(6);
					boolean isPrivate = result.getBoolean(7);
					int points = result.getInt(8);
					TodoObject to = new TodoObject(name, priority, isPrivate, description, points);
					tl.addTodo(to);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return todolistVector;
	}
}

