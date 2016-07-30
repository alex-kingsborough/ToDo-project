package client;

import java.util.Vector;

public class TodoUser {
	
	//All Member variables:
	//General User info:
	private int mID;
	private String mUsername;
	private String mPassword;
	private String mName;
	private String mEmail;
	private String mBirthday;
	private String mAboutMe;
	
	//Objects of the User:
	Vector<TodoObject> mTodoList;
	Vector<Integer> mFriendList;
	Vector<String> mTabTitles;
	private int mTotalPoints;
	
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
	
	public void setBirthday(String inBirthday){
		mBirthday=inBirthday;
	}
	
	public String getBirthday(){
		return mBirthday;
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
	
	public void setTodoList(Vector<TodoObject> inTodoList){
		mTodoList=inTodoList;
	}
	
	public Vector<TodoObject> getTodoList(){
		return mTodoList;
	}
	
	public Object[][] getTodoArray() {//Returns a 2D array of todo objects
		Object[][] todoArray = new Object[mTodoList.size()][6];
		
		//loop through the user's todo list and add it to a 2D array
		for (int i=0; i < mTodoList.size(); i++)
		{
			TodoObject currTodo = mTodoList.get(i);
			todoArray[i][0] = currTodo.getCompleted();
			todoArray[i][1] = currTodo.getTitle();
			todoArray[i][2] = currTodo.getDescription();
			todoArray[i][3] = currTodo.getIsPrivate();
			todoArray[i][4] = currTodo.getPriority();
			todoArray[i][5] = currTodo.getPoints();
		}
		return todoArray;
	}
	
	public void setFriendList(Vector<Integer> inFriendList){
		mFriendList=inFriendList;
	}
	
	public Vector<Integer> getFriendList(){
		return mFriendList;
	}
	
	public void setTabTitles(Vector<String> inTabTitles){
		mTabTitles=inTabTitles;
	}
	
	public Vector<String> getTabTitles(){
		return mTabTitles;
	}
	
	public void setTotalPoints(int inTotalPoints){
		mTotalPoints=inTotalPoints;
	}
	
	public int getTotalPoints(){
		return mTotalPoints;
	}
}
