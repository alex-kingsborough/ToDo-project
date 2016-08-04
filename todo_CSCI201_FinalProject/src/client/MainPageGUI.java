package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class MainPageGUI extends JPanel {
	
	public static void main(String [] args){ //TODO FIX THIS ALL
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
	}
	
	//MEMBER VARIABLES
	//Panes and Panels and GUI stuff
	JTabbedPane mMainTabbedPane;
	JPanel mMainEastPanel;
	JPanel mSocialPanel;
	JButton mAddTodoButton;
	String [] mTableHeaders = { "Finished", "Title", "Description", "Private", "Priority", "Points" };
	
	//Necessary user variables
	//These next variables are hypotheticals, don't know what types we are using yet
	TodoUser mUser;
	Vector<String> mTabTitles;
	Vector<TodoObject> mAllTodos;
	//Object[][] mTableData;
	/*
	//TODO
	*/
	
	
	//Constructor
	public MainPageGUI(){ //TODO make it take in a User object to fill all necessary variables
		mMainTabbedPane = new JTabbedPane();
		mMainEastPanel = new JPanel();
		mTabTitles = new Vector<String>();
		

		//Fill user variables from user
		//TODO replace with filling from User
		//TODO CHANGE THIS LINE TO GET THE USER FROM THE CLIENT
		mUser = new TodoUser(); 
		Vector<TodoObject> tempTodoVec = new Vector<TodoObject>();
		for(int i =0;i<6;i++){
			String tempTitle = "TITLE"+i;
			TodoObject tempTodo = new TodoObject(tempTitle, "mid", true, "Work", "I LIKE TO HAVE FUN", i);
			tempTodoVec.add(tempTodo);
		}
		mUser.setTodoList(tempTodoVec);
		mAllTodos = mUser.getTodoList();
		
		//mTabTitles = mUser.getTabTitles();mTabTitles = new Vector<String>();
		mTabTitles.add("Work");
		mTabTitles.add("Play");
		//mTableData = mUser.getTodoArray();
		/*mTableData = new Object[][]
				{{"Kathy", "Smith",
		     "Snowboarding", new Integer(5), new Boolean(false),new Integer(123)},
			    {"John", "Doe",
			     "Rowing", new Integer(3), new Boolean(true),new Integer(123)},
			    {"Sue", "Black",
			     "Knitting", new Integer(2), new Boolean(false),new Integer(123)},
			    {"Jane", "White",
			     "Speed reading", new Integer(20), new Boolean(true),new Integer(123)},
			    {"Joe", "Brown",
			     "Pool", new Integer(10), new Boolean(false),new Integer(123)}
			};*/
		
		
		createTabbedPane();
		createEastPanel();
		setLayout(new BorderLayout());
		mMainEastPanel.setPreferredSize(new Dimension(150, this.getHeight()));
		add(mMainEastPanel, BorderLayout.EAST);
		add(mMainTabbedPane, BorderLayout.CENTER);
	}
	
	private void createTabbedPane(){//Creating the Tabbed pane which is the bulk of the Main Page
		//Creating a tab with appropriate title for each TabTitle in the User, then filling each tab with a scroll pane that is filled with a table of Todos
		for(int i=0; i<mTabTitles.size(); i++){
			Vector<TodoObject> tabTodos = new Vector<TodoObject>();
			for(TodoObject currTodo : mAllTodos){
				System.out.println(mTabTitles.elementAt(i));
				System.out.println(currTodo.getList());
				if(currTodo.getList().equals(mTabTitles.elementAt(i))){
					tabTodos.add(currTodo);
				}
			}
			
			Object[][] currTableData = new Object[tabTodos.size()][6];
			for(int j=0;j<tabTodos.size();j++){
				TodoObject moveTodo = tabTodos.get(i);
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
			
			mMainTabbedPane.add(mTabTitles.get(i),mScrollPane);
		}
	}
	
	private void createEastPanel(){ //Creating and filling out the panel on the east holding the add button and Social Panel
		mAddTodoButton = new JButton("Add Todo");
		mAddTodoButton.setPreferredSize(new Dimension(mMainEastPanel.getWidth(),21));
		//TODO add action listener to open new todo dialog when pressed, visuals
		
		createSocialPanel();
		
		mMainEastPanel.setLayout(new BorderLayout());
		mMainEastPanel.add(mAddTodoButton, BorderLayout.NORTH);
		mMainEastPanel.add(mSocialPanel, BorderLayout.CENTER);
	}
	
	private void createSocialPanel(){ //Creating and filling out the Social Panel that is on every page
		mSocialPanel = new JPanel();
		//TODO TEMPORARY TO HAVE SOMETHING THERE FOR SOCIAL PANEL
		JTextArea mSocialText = new JTextArea();
		mSocialText.setLineWrap(true);
		mSocialText.setEditable(false);
		mSocialText.setText("SAMPLE TEXT TO COVER A BIT OF THE SOCIAL AREA, THIS WILL PROBABLY BE FILLED WITH A TABLE,"
				+ " AND I THINK THE WHOLE CREATE SOCIAL PANEL THING WILL HAVE TO BE PACKAGE AND PROBABLY IN THE JFRAME CLASS"
				+ " SO THAT EVERY GUI ON THE CARD LAYOUT CAN ADD IT TO THE RIGHT PART OF THEIR PANEL.");
		
		mSocialPanel.add(mSocialText);
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
	

	/*
	//BOTH OF THESE CLASSES BELOW ARE FROM http://stackoverflow.com/questions/13833688/adding-jbutton-to-jtable
	//PROBABLY NEED UPDATE AND CHANGES TO MAKE IT THE WAY WE WANT, LEAVING FOR NOW THOUGH
	class FinishedCellRenderer extends JRadioButton implements TableCellRenderer {

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        if (isSelected) {
	            setForeground(table.getSelectionForeground());
	            setBackground(table.getSelectionBackground());
	        } else {
	            setForeground(table.getForeground());
	            setBackground(table.getBackground());//UIManager.getColor("Button.background"));
	        }
	        setText((value == null) ? "" : value.toString());
	        return this;
	    }
	}
	
	class ButtonRenderer extends JButton implements TableCellRenderer {

	    public ButtonRenderer() {
	    //    setOpaque(true);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        if (isSelected) {
	            setForeground(table.getSelectionForeground());
	            setBackground(table.getSelectionBackground());
	        } else {
	            setForeground(table.getForeground());
	            setBackground(table.getBackground());//UIManager.getColor("Button.background"));
	        }
	        setText((value == null) ? "" : value.toString());
	        return this;
	    }
	}

	class ButtonEditor extends DefaultCellEditor {

	    protected JButton button;
	    private String label;
	    private boolean isPushed;

	    public ButtonEditor(JCheckBox checkBox) {
	        super(checkBox);
	        button = new JButton();
	        button.setOpaque(true);
	    }

	    @Override
	    public Component getTableCellEditorComponent(JTable table, Object value,
	            boolean isSelected, int row, int column) {
	        if (isSelected) {
	            button.setForeground(table.getSelectionForeground());
	            button.setBackground(table.getSelectionBackground());
	        } else {
	            button.setForeground(table.getForeground());
	            button.setBackground(table.getBackground());
	        }
	        label = (value == null) ? "" : value.toString();
	        button.setText(label);
	        isPushed = true;
	        return button;
	    }

	    @Override
	    public Object getCellEditorValue() {
	        if (isPushed) {
	            JOptionPane.showMessageDialog(button, label + ": Ouch!");
	        }
	        isPushed = false;
	        return label;
	    }

	    @Override
	    public boolean stopCellEditing() {
	        isPushed = false;
	        return super.stopCellEditing();
	    }

	    @Override
	    protected void fireEditingStopped() {
	        super.fireEditingStopped();
	    }
	}*/
}
