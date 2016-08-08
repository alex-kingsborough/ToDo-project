package client;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import client.CustomScrollBar.MyScrollbarUI;
import constants.Constants;

public class SocialGUI extends JPanel implements Runnable {

	/** ****** *** ***** ****** **** *** ***** **\
	 *  Social GUI shows social tabs and panel  * 
	 *  **************************************  *
    \*                                          */
	
	
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JTable friendsTodo, publicTodo;
	private JScrollPane friendSP, pubSP;
	private Vector<TodoObject> publicTodos, friendsTodos;
	
	
	public SocialGUI() {
		setBackground(Constants.greyColor);
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(Constants.greyColor);
		add(tabbedPane, BorderLayout.CENTER);
		
		createFriendsTab();
		createPublicTab();
		
		(new Thread(this)).start();
	}
	
	//create the friends tab
	public void createFriendsTab() {
		
		friendsTodo = new JTable(new TodoTableModel(new Object[0][7]));
		friendsTodo.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evnt) {
		        //TODO: open todo preview with selected row
		    	int row = friendsTodo.getSelectedRow();
		    	System.out.println(row);
		    	if (row < friendsTodos.size() && row != -1)
		    		new ViewTodo(friendsTodos.get(row));
		     }
		});
		friendSP = new JScrollPane(friendsTodo);
		friendSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		friendSP.getViewport().setBackground(Constants.greyColor);
		
		JScrollBar sb = friendSP.getVerticalScrollBar();
		sb.setPreferredSize(new Dimension(14, Integer.MAX_VALUE));
		sb.setUI(new MyScrollbarUI());
		sb.getComponent(0).setBackground(Constants.redColor);
		sb.getComponent(0).setForeground(Constants.goldColor);
		sb.getComponent(1).setBackground(Constants.redColor);
		sb.getComponent(1).setForeground(Constants.goldColor);
		
		tabbedPane.add(friendSP, "Friends");
		//updateTab(0);
		
	}
	
	
	
	//creates the public tab
	public void createPublicTab() {
		publicTodo = new JTable(new TodoTableModel(new Object[0][7]));
		publicTodo.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evnt) {
		        //TODO: open todo preview with selected row
		    	int row = publicTodo.getSelectedRow();
		    	System.out.println(row);
		    	System.out.println(publicTodos.size());
		    	//if (row < publicTodos.size() && row != -1)
		    	new ViewTodo(new TodoObject("test", 1, true, 1, "alskdfj", "laksdjf", 1, 1, false));
		     }
		});
		pubSP = new JScrollPane(publicTodo);
		pubSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pubSP.getViewport().setBackground(Constants.greyColor);
		
		tabbedPane.add(pubSP, "Public");
		//updateTab(1);
		
	}
	
	//will update the table with the new todos
	/*
	public boolean updateTab( int tabId) {
		
		//tabId = 0 then update friends tab
		if (tabId == 0)
		{
			//send request to server
			//wait for response from server
			System.out.println("hey there");
			friendsTodos = TodoClientListener.get().readTodoObjects(Constants.GET_FRIENDS_TODOS);
			Object[][] newTodos = convertToObject(friendsTodos);
			
			//send request to get new todos
			friendsTodo.setModel(new TodoTableModel(newTodos));
			return true;
		}
		
		//tabId = 1 then update public tab
		else if (tabId == 1)
		{
			//send request to server
			//wait for response from server
			System.out.println("hey there there");
			publicTodos = TodoClientListener.get().readTodoObjects(Constants.GET_PUBLIC_TODOS);
			Object[][] newTodos = convertToObject(publicTodos);
			publicTodo.setModel(new TodoTableModel(newTodos));
			return true;
		}
		return false;
	}
	*/
	
	//call update on currently select tab every 5 seconds
	public void run() {
		while (true)
		{
			try {
				//send request to server
				//wait for response from server
				
				friendsTodos = TodoClientListener.get().readTodoObjects(Constants.GET_FRIENDS_TODOS);
				Object[][] newTodos = convertToObject(friendsTodos);
				
				//send request to get new todos
				friendsTodo.setModel(new TodoTableModel(newTodos));
		
				//send request to server
				//wait for response from server
				publicTodos = TodoClientListener.get().readTodoObjects(Constants.GET_PUBLIC_TODOS);
				Object[][] newTodos2 = convertToObject(publicTodos);
				publicTodo.setModel(new TodoTableModel(newTodos2));
				
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//Model for todo table
	class TodoTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		private String[] columnNames = {
				"Status",
                "Title",
                "Description",
                "Private",
                "Priority",
                "Points",
                "Name"};
	    private Object[][] data;
	    
	    public TodoTableModel(Object[][] data)
	    {
	    	super();
	    	this.data = data;
	    }

	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	    	if (data == null)
	    		return 0;
	    	return data.length;
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }

	    public Class<? extends Object> getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	    
	    public boolean isRowSelectionAllowed()
	    {
	    	return true;
	    }
	    

	}
	
	public Object[][] convertToObject(Vector<TodoObject> currTodoList)
	{		
		if (currTodoList == null || currTodoList.size() == 0)
			return new Object[0][7];
		
		Object[][] todoArray = new Object[currTodoList.size()][7];
		
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
			todoArray[i][6] = currTodo.getUsername();
		}
		
		return todoArray;
	}
	
	
	
}
