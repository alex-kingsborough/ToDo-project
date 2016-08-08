package client;

import java.io.Serializable;
import java.util.Vector;

public class TodoList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8906355305626818802L;
	/**
	 * 
	 */
	
	private int todolistID;
	private String name;
	private Vector<TodoObject> todos;
	private boolean isActive;
	
	public TodoList(int ID, String name)
	{
		this.todolistID = ID;
		this.name = name;
		
		isActive = true;
		todos = new Vector<TodoObject>();
	}
	
	public void addTodo(TodoObject to)
	{
		todos.add(to);
	}
	
	public Vector<TodoObject> getAllTodos()
	{
		return todos;
	}
	
	public int getID()
	{
		return todolistID;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	public void setActive(boolean b){
		isActive = b;
	}
	
	public String getName() 
	{
		return name;
	}
}
