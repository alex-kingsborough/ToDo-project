package client;

import java.util.Vector;

public class TodoList {
	
	private int todolistID;
	private String name;
	private Vector<TodoObject> todos;
	
	public TodoList(int ID, String name)
	{
		this.todolistID = ID;
		this.name = name;
		
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
	
	public String getName() 
	{
		return name;
	}
}
