package client;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class SocialGUI extends JPanel implements Runnable {

	/** ****** *** ***** ****** **** *** ***** **\
	 *  Social GUI shows social tabs and panel  * 
	 *  **************************************  *
    \*                                          */
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JTable friendsTodo, publicTodo, popularTodo;
	private JScrollPane friendSP, pubSP, popSP;
	private PortalManager pm;
	private TodoUser currUser;
	
	
	public SocialGUI(PortalManager pm, TodoUser currUser) {
		this.pm = pm;
		this.currUser = currUser;
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		createFriendsTab();
		createPublicTab();
		createPopularTab();
		
		(new Thread(this)).start();	
	}
	
	//create the friends tab
	public void createFriendsTab() {
		//TODO: Implement get FriendsTodos function
		Object[][] todos = {
				{true, "Do this", "A good description", "alex"},
				{false, "New Todo", "This is new", "alex"}
		};
		friendsTodo = new JTable(new TodoTableModel(todos));
		friendsTodo.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evnt) {
		        //TODO: open todo preview with selected row
		    	int row = friendsTodo.getSelectedRow();
		    	System.out.println("Selected this: " + friendsTodo.getValueAt(row, 1));
		     }
		});
		friendSP = new JScrollPane(friendsTodo);
		tabbedPane.add(friendSP, "Friends");
		
	}
	
	
	
	//creates the public tab
	public void createPublicTab() {
		//TODO: Implement get PublicTodos function
		Object[][] todos = {
				{true, "Title", "Public", "bill"}
		};
		publicTodo = new JTable(new TodoTableModel(todos));
		publicTodo.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evnt) {
		        //TODO: open todo preview with selected row
		    	int row = publicTodo.getSelectedRow();
		    	System.out.println("Selected this: " + publicTodo.getValueAt(row, 1));
		     }
		});
		pubSP = new JScrollPane(publicTodo);
		tabbedPane.add(pubSP, "Public");
		
	}
	
	//creates the user tab
	public void createPopularTab() {
		//TODO: Implement get popular todos function
		Object[][] todos = {
				{false, "Finish project", "The project is due", "alex"}
		};
		popularTodo = new JTable(new TodoTableModel(todos));
		popularTodo.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evnt) {
		        //TODO: open todo preview with selected row
		    	int row = popularTodo.getSelectedRow();
		    	System.out.println("Selected this: " + popularTodo.getValueAt(row, 1));
		     }
		});
		popSP = new JScrollPane(popularTodo);
		tabbedPane.add(popSP, "Popular");

	}
	
	//will update the table with the new todos
	public void updateTab( int tabId) {
		
		//tabId = 0 then update friends tab
		if (tabId == 0)
		{
			//TODO: add method to get newest todos for public
			Object[][] newTodos = {
					{true, "This", "Was", "Updated"}
			};
			
			//send request to get new todos
			Object[][] newTodos2 = getNewTodos()
			friendsTodo.setModel(new TodoTableModel(newTodos));
		}
		
		//tabId = 1 then update public tab
		else if (tabId == 1)
		{
			//TODO: add method to get newest todos for public
			Object[][] newTodos = {
					{true, "This", "Was", "Updated"}
			};
			publicTodo.setModel(new TodoTableModel(newTodos));
		}
	}
	
	//call update on currently select tab every 5 seconds
	public void run() {
		while (true)
		{
			try {
				//wait for 5 seconds
				//TODO: only update tab if at front
				Thread.sleep(5000);
				
				//call update on selected tab
				int selectedTab = tabbedPane.getSelectedIndex();
				updateTab(selectedTab);
				System.out.println("Updating " + selectedTab);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//Model for todo table
	class TodoTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		private String[] columnNames = {"Status",
                "Title",
                "Description",
                "Username"};
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
	    	return false;
	    }
	    

	}
	
	public Object[][] convertToObject(Vector<TodoObject> currTodoList)
	{		
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
		
		return todoArray;
	}
	
	
	
}
