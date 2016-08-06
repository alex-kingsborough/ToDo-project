package client;

public class TodoObject {
	
	private String title;
	private int priority;
	private boolean isPrivate;
	private boolean isCompleted;
	//private int listID;
	private String listName;
	private String desc;
	private int mPoints;
	private int userID;
	
	public TodoObject(String _title, int _priority, boolean _isPrivate, String _list, String _desc, int _points, int _userID){
		title = _title;
		priority = _priority;
		isPrivate = _isPrivate;
		listName = _list;
		desc = _desc;
		mPoints = _points;
		userID = _userID;
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
	
	public void setIsPrivate(boolean _isPrivate){
		isPrivate = _isPrivate;
		return;
	}
	
	
	public String getList(){
		return listName;
	}
	
	public void setList(String _list){
		listName = _list;
		return;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public void setDescription(String _desc){
		desc = _desc;
		return;
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
