package AddTodo;

public class TodoObject {
	
	private String title;
	private String priority;
	private boolean isPrivate;
	private String list;
	private String desc;
	
	public TodoObject(String _title, String _priority, boolean _isPrivate, String _list, String _desc){
		title = _title;
		priority = _priority;
		isPrivate = _isPrivate;
		desc = _desc;
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
	
	
	public String getList(){
		return list;
	}
	
	public void setList(String _list){
		list = _list;
		return;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public void setDescription(String _desc){
		desc = _desc;
		return;
	}
	
}
