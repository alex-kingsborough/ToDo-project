package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;

public class MainPageGUI extends JPanel {
	private static final long serialVersionUID = 1293859318L;
	
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
	public MainPageGUI(){
		mUser = PortalManager.mUser;
		
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
				
				JButton mTitleButton = new JButton(moveTodo.getTitle());
				//mTitleButton.setText();
				mTitleButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						new UpdateTodo(moveTodo);
					}
				});
				currTableData[j][1] = mTitleButton;
				
				currTableData[j][2] = moveTodo.getDescription();
				currTableData[j][3] = moveTodo.getIsPrivate();
				currTableData[j][4] = moveTodo.getPriority();
				currTableData[j][5] = moveTodo.getPoints();
			}
			
			JTable mTable = new JTable(new MainPageTableModel(currTableData, currTodos));
	        
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
		private static final long serialVersionUID = 12616121233L;
		
		private String[] mColumnHeaders = mTableHeaders;
	    private Object[][] mData;
	    private Vector<TodoObject> tableTodos;
	    
	    public MainPageTableModel(Object[][] inData, Vector<TodoObject> inTodos){
	    	super();
	    	mData = inData;
	    	tableTodos = inTodos;
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

	    /*@Override
	    public Object getValueAt(int row, int col){
	        return mData[row][col];
	    }*/
	    
	    @Override public Object getValueAt(int row, int col) {
            /*Adding components*/
	    /*	if(col == 0){
	    		JRadioButton currRadButton = new JRadioButton();
	    		currRadButton.setSelected(tableTodos.get(row).getCompleted());
	    		currRadButton.addActionListener(new ActionListener(){
	    			@Override
	    			public void actionPerformed(ActionEvent ae){
	    				
	    			}
	    		});
	    	}
	    	else if(col == 1){
	    		
	    	}
	    	else{
	    		return mData[row][col];
	    	}*/
	    	return mData[row][col];
/*
	    	switch (col) {
	    	case 0: return row;
	    	case 1: return "Text for "+row;
	    	case 2: // fall through
	    		/*Adding button and creating click listener*//*
	    	case 3: final JButton button = new JButton(mColumnHeaders[col]);
	    	button.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent arg0) {
	    			JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(button), 
	    					"Button clicked for row "+row);
	    		}
	    	});
	    	return button;
	    	default: return "Error";
	    	}*/
	    }

	    @Override
	    public Class<? extends Object> getColumnClass(int c){
	        return getValueAt(0,c).getClass();
	    }
	}
}
