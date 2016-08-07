package client;

import java.util.Vector;
import java.io.Serializable;

public class TodoUser implements Serializable {
	
	private static final long serialVersionUID = 7564829241L;
	
	//All Member variables:
	//General User info:
	private int mID;
	private String mUsername;
	private String mPassword;
	private String mName;
	private String mEmail;
	private String mAboutMe;
	
	//Objects of the User:
	Vector<TodoList> allTodoLists;
	Vector<Integer> mFriendList;
	
	private int mTotalPoints;
	
	//set mID to 0 if we still need to query db to get info about user
	public TodoUser(int ID, String username, String password, String email)
	{
		mID = ID;
		mUsername = username;
		mPassword = password;
		mEmail = email;
	}
	
	public TodoUser(String username, String name, String password, String email, String aboutMe) {
		mUsername = username;
		mName = name;
		mPassword = password;
		mEmail = email;
		mAboutMe = aboutMe;
	}
	
	public void setTodoLists(Vector<TodoList> tlv)
	{
		allTodoLists = tlv;
	}

	public void addTodoList(TodoList inTodoList){
		allTodoLists.add(inTodoList);
	}
	
	public Vector<TodoList> getTodoLists(){
		return allTodoLists;
	}
	
	public void setID(int inID){
		mID=inID;
	}
	
	public int getID(){
		return mID;
	}
	
	public void setUsername(String inUsername){
		mUsername=inUsername;
	}
	
	public String getUsername(){
		return mUsername;
	}
	
	public void setPassword(String inPassword){
		mPassword=inPassword;
	}
	
	public String getPassword(){
		return mPassword;
	}
	
	public void setName(String inName){
		mName=inName;
	}
	
	public String getName(){
		return mName;
	}
	
	public void setEmail(String inEmail){
		mEmail=inEmail;
	}
	
	public String getEmail(){
		return mEmail;
	}
	
	public void setAboutMe(String inAboutMe){
		mAboutMe=inAboutMe;
	}
	
	public String getAboutMe(){
		return mAboutMe;
	}
	
	public Vector<Object[][]> getTodoArray() {//Returns a 2D array of todo objects
		Vector<Object[][]> vectorTodoArray = new Vector<Object[][]>();
		
		for (TodoList list : allTodoLists)
		{
			Vector<TodoObject> currTodoList = list.getAllTodos();
			
			Object[][] todoArray = new Object[currTodoList.size()][6];
			
			//loop through the user's todo list and add it to a 2D array
			for (int i=0; i < currTodoList.size(); i++)
			{
				TodoObject currTodo = currTodoList.get(i);
				todoArray[i][0] = currTodo.getCompleted();
				todoArray[i][1] = currTodo.getTitle();
				todoArray[i][2] = currTodo.getDescription();
				todoArray[i][3] = currTodo.getIsPrivate();
				todoArray[i][4] = currTodo.getPriority();
				todoArray[i][5] = currTodo.getPoints();
			}
			
			vectorTodoArray.add(todoArray);
		
		}
		
		return vectorTodoArray;
	}
	
	public void setFriendList(Vector<Integer> inFriendList){
		mFriendList=inFriendList;
	}
	
	public Vector<Integer> getFriendList(){
		return mFriendList;
	}
	
	public void setTotalPoints(int inTotalPoints){
		mTotalPoints=inTotalPoints;
	}
	
	public int getTotalPoints(){
		return mTotalPoints;
	}
}
