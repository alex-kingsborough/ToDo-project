package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import constants.Constants;
import client.CustomScrollBar.MyScrollbarUI;

public class MainPageGUI extends JPanel {
	private static final long serialVersionUID = 1293859318L;
	
	//MEMBER VARIABLES
	//Panes and Panels and GUI stuff
	private JTabbedPane mMainTabbedPane;
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
		
		setBackground(Constants.greyColor);

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
			
			//Setting "buttons" for the table
			mTable.setCellSelectionEnabled(true);
		    ListSelectionModel cellSelectionModel = mTable.getSelectionModel();
		    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
		    	public void valueChanged(ListSelectionEvent lse) {
		    		if(!lse.getValueIsAdjusting()){
		    			if(mTable.getSelectedColumn()==0){
		    				TodoObject changeFinishedTodo = currTodos.get(mTable.getSelectedRow());
		    				if(changeFinishedTodo.getCompleted()){
		    					mUser.setTotalPoints(mUser.getTotalPoints()-changeFinishedTodo.getPoints());
		    					changeFinishedTodo.setCompleted(false);
		    				}
		    				else{
		    					mUser.setTotalPoints(mUser.getTotalPoints()+changeFinishedTodo.getPoints());
		    					changeFinishedTodo.setCompleted(true);
		    				}
		    				updatePage();
		    				if(mUser.getUsername()!=Constants.GUEST_USER){
		    					PortalManager.mUserInfoPage.updatePoints();
		    					TodoClientListener.get().sendUser(mUser);
		    					mUser = TodoClientListener.get().readTodoUser();
		    				}
		    			}
		    			if(mTable.getSelectedColumn()==1){
				    		new UpdateTodo(currTodos.get(mTable.getSelectedRow()));
		    			}
		    		}
		    	}
		    });
	        
			JScrollPane mScrollPane = new JScrollPane(mTable);

			JScrollBar sb = mScrollPane.getVerticalScrollBar();
			sb.setPreferredSize(new Dimension(16, Integer.MAX_VALUE));
			sb.setUI(new MyScrollbarUI());
			sb.getComponent(0).setBackground(Constants.redColor);
			sb.getComponent(1).setBackground(Constants.redColor);
			sb.getComponent(0).setForeground(Constants.goldColor);
			sb.getComponent(1).setForeground(Constants.goldColor);
			
			mScrollPane.getViewport().setBackground(Constants.greyColor);
			mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
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
	
	class RadioButtonRenderer implements TableCellRenderer{
		@Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JRadioButton mRadRendButton = (JRadioButton)value;
            return mRadRendButton;  
        }
	}

	class MainPageTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 12616121233L;
		
		private String[] mColumnHeaders = mTableHeaders;
	    private Object[][] mData;
	    
	    public MainPageTableModel(Object[][] inData){
	    	super();
	    	mData = inData;
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
