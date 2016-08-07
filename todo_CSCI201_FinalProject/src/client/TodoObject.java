package client;

import java.io.Serializable;

public class TodoObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String title;
	private int priority;
	private boolean isPrivate;
	private boolean isCompleted;
	private int listID;
	private String listName;
	private String desc;
	private int mPoints;
	private int userID;
	private String username;
	
	public TodoObject(String _title, int _priority, boolean _isPrivate, int _listID, 
			String _listName, String _desc, int _points, int _userID, boolean _isCompleted)
	{
		title = _title;
		priority = _priority;
		isPrivate = _isPrivate;
		listID = _listID;
		listName = _listName;
		desc = _desc;
		mPoints = _points;
		userID = _userID;
		isCompleted = _isCompleted;
		username = "";
	}

	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String _username)
	{
		username = _username;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String _title){
		title = _title;
		return;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public void setPriority(int _priority){
		priority = _priority;
		return;
	}
	
	public boolean getIsPrivate(){
		return isPrivate;
	}
	
	public void setIsPrivate(boolean _isPrivate)
	{
		isPrivate = _isPrivate;
	}
	
	public String getListName(){
		return listName;
	}
	
	public void setListName(String _list){
		listName = _list;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public void setDescription(String _desc){
		desc = _desc;
	}
	
	public int getListID()
	{
		return listID;
	}
	
	public void setListID(int _listID)
	{
		listID = _listID;
	}
	
	public boolean getCompleted() {
		return isCompleted;
	}
	
	public void setCompleted(boolean c) {
		isCompleted=c;
	}
	
	public void setPoints(int inPoints){
		mPoints = inPoints;
	}
	
	public int getPoints(){
		return mPoints;
	}
	
}
