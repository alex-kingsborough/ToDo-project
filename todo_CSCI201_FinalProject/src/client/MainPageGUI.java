package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;

public class MainPageGUI extends JPanel {
	private static final long serialVersionUID = 1293859318L;
	
	/*public static void main(String [] args){ //TODO FIX THIS ALL
		JFrame mTesting = new JFrame("test");
		mTesting.add(new MainPageGUI());
		
		JMenuBar mTestBar = new JMenuBar();
		mTesting.setJMenuBar(mTestBar);
		JMenu mTestMenu = new JMenu("Menu");
		mTestMenu.setMnemonic('M');
		mTestBar.add(mTestMenu);
		JMenuItem mMainPageItem = new JMenuItem("Main Page");
		mMainPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		mTestMenu.add(mMainPageItem);
		
		
		mTesting.setSize(800, 500);
		mTesting.setLocationRelativeTo(null);
		mTesting.setVisible(true);
		mTesting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/
	
	//MEMBER VARIABLES
	//Panes and Panels and GUI stuff
	private JTabbedPane mMainTabbedPane;
	private JButton mAddTodoButton;
	private String [] mTableHeaders = { "Finished", "Title", "Description", "Private", "Priority", "Points" };
	
	//Necessary user variables
	private TodoUser mUser;
	private Vector<TodoList> mAllTodos;
	/*
	//TODO
	*/
	
	
	//Constructor
	public MainPageGUI(TodoUser inUser){
		mUser = inUser;
		
		mMainTabbedPane = new JTabbedPane();

		//Fill user variables from user
		//TODO make sure all necessary is here
		mAllTodos = mUser.getTodoLists();
		
		createTabbedPane();
		setLayout(new BorderLayout());
		add(mMainTabbedPane, BorderLayout.CENTER);
	}
	
	private void createTabbedPane(){//Creating the Tabbed pane which is the bulk of the Main Page
		//Creating a tab with appropriate title for each TabTitle in the User, then filling each tab with a scroll pane that is filled with a table of Todos
		for(int i=0; i<mAllTodos.size(); i++){
			TodoList currTodoList = mAllTodos.get(i);
			Vector<TodoObject> currTodos = currTodoList.getAllTodos();
			
			Object[][] currTableData = new Object[currTodos.size()][6];
			for(int j=0;j<currTodos.size();j++){
				TodoObject moveTodo = currTodos.get(j);
				currTableData[j][0] = moveTodo.getCompleted();
				currTableData[j][1] = moveTodo.getTitle();
				currTableData[j][2] = moveTodo.getDescription();
				currTableData[j][3] = moveTodo.getIsPrivate();
				currTableData[j][4] = moveTodo.getPriority();
				currTableData[j][5] = moveTodo.getPoints();
			}
			
			JTable mTable = new JTable(new MainPageTableModel(currTableData));
	        
			JScrollPane mScrollPane = new JScrollPane(mTable);
			mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			mMainTabbedPane.add(currTodoList.getName(),mScrollPane);
		}
	}
	public void updatePage(){
		int currIndex = mMainTabbedPane.getSelectedIndex();
		mMainTabbedPane.removeAll();
		createTabbedPane();
		mMainTabbedPane.setSelectedIndex(currIndex);
		add(mMainTabbedPane, BorderLayout.CENTER);
	}

	class MainPageTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 12616123;
		
		private String[] mColumnHeaders = mTableHeaders;
	    private Object[][] mData;
	    
	    public MainPageTableModel(Object[][] inData){
	    	super();
	    	this.mData = inData;
	    }
	    
	    @Override
	    public int getColumnCount(){
	        return mColumnHeaders.length;
	    }
	    
	    @Override
	    public int getRowCount(){
	        return mData.length;
	    }

	    @Override
	    public String getColumnName(int colnum){
	        return mColumnHeaders[colnum];
	    }

	    @Override
	    public Object getValueAt(int row, int col){
	        return mData[row][col];
	    }

	    @Override
	    public Class<? extends Object> getColumnClass(int c){
	        return getValueAt(0,c).getClass();
	    }
	}
}
