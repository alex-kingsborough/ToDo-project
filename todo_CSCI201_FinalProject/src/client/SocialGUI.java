package client;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class SocialGUI extends JPanel {

	/**
	 * Social GUI shows social tabs and panel
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JTable friendsTodo, publicTodo, popularTodo;
	
	
	public SocialGUI() {
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		createFriendsTab();
		createPublicTab();
		createPopularTab();
	}
	
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
		JScrollPane jsp = new JScrollPane(friendsTodo);
		tabbedPane.add(jsp, "Friends");
	}
	
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
		JScrollPane jsp = new JScrollPane(publicTodo);
		tabbedPane.add(jsp, "Public");
		
	}
	
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
		JScrollPane jsp = new JScrollPane(popularTodo);
		tabbedPane.add(jsp, "Popular");

	}
	
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
	
	
	
}
