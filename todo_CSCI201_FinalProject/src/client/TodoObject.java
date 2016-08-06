package client;

public class TodoObject {
	
	private String title;
	private int priority;
	private boolean isPrivate;
	private boolean isCompleted;
	private int listID;
	private String desc;
	private int mPoints;
	
<<<<<<< HEAD
	//constructor without owner
<<<<<<< HEAD
	public TodoObject(String _title, int _priority, boolean _isPrivate, String list, String _desc, int _points){
=======
	public TodoObject(String _title, String _priority, boolean _isPrivate, String _desc, int _points){
>>>>>>> 6248af3dacddc589b5c6ea3d30a04dc0f0db7dc2
		title = _title;
		priority = _priority;
		isPrivate = _isPrivate;
		desc = _desc;
		
		mPoints = _points;
	}
	
	//constructor with owner
<<<<<<< HEAD
	/*public TodoObject(String _title, String _owner, boolean _isPrivate, String _list, String _desc, int _points){
		setOwner(_owner);
=======
	public TodoObject(String _title, String _owner, String _priority, boolean _isPrivate, int _list, String _desc, int _points){
		owner = _owner;
>>>>>>> 6248af3dacddc589b5c6ea3d30a04dc0f0db7dc2
=======
	
	public TodoObject(String _title, int _priority, boolean _isPrivate, int _list, String _desc, int _points){
>>>>>>> f9a5bd70f31c2657d29401eac65e712a2a10e031
		title = _title;
		priority = _priority;
		isPrivate = _isPrivate;
		listID = _list;
		desc = _desc;
		mPoints = _points;
	}*/
	
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
<<<<<<< HEAD

	/**
	 * @return the owner
	 */
	/*public String getOwner() {
		return owner;
	}*/
=======
>>>>>>> f9a5bd70f31c2657d29401eac65e712a2a10e031
	
}
