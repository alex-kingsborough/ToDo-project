package client;

public class TodoObject {
	
	private String owner;
	private String title;
	private String priority;
	private boolean isPrivate;
	private boolean isCompleted;
	private int listID;
	private String desc;
	private int mPoints;
	
	//constructor without owner
	public TodoObject(String _title, String _priority, boolean _isPrivate, String _desc, int _points){
		title = _title;
		priority = _priority;
		isPrivate = _isPrivate;
		desc = _desc;
		mPoints = _points;
	}
	
	//constructor with owner
	public TodoObject(String _title, String _owner, String _priority, boolean _isPrivate, int _list, String _desc, int _points){
		owner = _owner;
		title = _title;
		priority = _priority;
		isPrivate = _isPrivate;
		listID = _list;
		desc = _desc;
		mPoints = _points;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String _title){
		title = _title;
		return;
	}
	
	public String getPriority(){
		return priority;
	}
	
	public void setPriority(String _priority){
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
	
	
	public int getList(){
		return listID;
	}
	
	public void setList(int _list){
		listID = _list;
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

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	
}
