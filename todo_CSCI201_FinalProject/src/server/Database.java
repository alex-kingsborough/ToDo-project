package server;

import com.mysql.jdbc.Driver;

import client.TodoObject;

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
	
	static {
		sDatabase = new Database();
	}
	
	private final static String newAccount = "INSERT INTO USERS(USERNAME,HASHWORD,ACTUALNAME,EMAIL,DOB,ABOUTME) VALUES(?,?,?,?,?,?)";
	private final static String selectUser = "SELECT HASHWORD FROM USERS WHERE USERNAME=?";
	private final static String getUserTodos ="SELECT T.TODONAME, T.TODOPRIORITY, T.TODOPRIVATE, L.LISTNAME, T.TODODESC, T.TODOPOINTS, T.TODOFINISHED FROM TODOS T, USERS U, LISTS L WHERE T.USERID=U.USERID AND U.USERID=L.USERID AND T.LISTID=L.LISTID AND U.USERNAME=?";
	private final static String addTodo = "INSERT INTO TODOS(userID,listID,todoPoints,todoPriority,todoDesc,todoName,todoFinished,todoPrivate) VALUES(?,?,?,?,?,?,?,?)";
	private final static String getUserID = "SELECT USERID FROM USERS WHERE USERNAME=?";
	private final static String getListID = "SELECT LISTID FROM LISTS WHERE LISTNAME=?";
	
	private Connection con;
	
	{
		try{
			new Driver();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fdunlap?user=root&password=root");
		} catch(SQLException sqle){
			System.out.println("SQL:"+sqle.getMessage());
		}
	}
	
	public void stop() {
		try {con.close();} catch (SQLException e) {e.printStackTrace();}
	}
	
	public boolean signup(String username, String hashword, String actualName, int dob, String email, String aboutme) {
		try {
			{ //check if the user exists
				PreparedStatement ps = con.prepareStatement(selectUser);
				ps.setString(1,username);
				ResultSet result = ps.executeQuery();
				if(result.next()) {//if we have any results, don't let the user sign up.
					return false;
				}
			}
			{ //sign up
				PreparedStatement ps = con.prepareStatement(newAccount);
				ps.setString(1, username);
				ps.setString(2, hashword);
				ps.setString(3, actualName);
				ps.setInt(4, dob);
				ps.setString(5, email);
				ps.setString(6, aboutme);
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
	
	public Vector<TodoObject> getUserTodos(String username){
		
		Vector<TodoObject> retvec = new Vector<TodoObject>();
		
		try {
			PreparedStatement ps = con.prepareStatement(getUserTodos);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				String name = result.getString("TODONAME");
				String priority = result.getString("TODOPRIORITY");
				boolean isPrivate = result.getBoolean("TODOPRIVATE");
				String list = result.getString("LISTNAME");
				String desc = result.getString("TODODESC");
				int points = result.getInt("TODOPOINTS");
				boolean isFinished = result.getBoolean("TODOFINISHED");
				TodoObject to = new TodoObject(name, username, priority, isPrivate, list, desc, points);
				if(isFinished) to.setCompleted(isFinished);
				retvec.add(to);
			}
		} catch (SQLException e) {
			System.out.println("SQLE: " + e.getMessage());
		}
		
		return retvec;
	}
	
	public int getUserID(String username){
		try{
			PreparedStatement ps = con.prepareStatement(getUserID);
			ps.setString(1,username);
			ResultSet result = ps.executeQuery();
			return result.getInt("userID");
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
			ps.setInt(1, getUserID(username);
			ps.setInt(2, getListID(to.getList()));
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
}