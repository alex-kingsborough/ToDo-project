package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class MainPageGUI extends JPanel {
	
	public static void main(String [] args){ //TODO FIX THIS ALL
		JFrame mTesting = new JFrame("test");
		mTesting.add(new MainPageGUI());
		
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
	String [] mTableHeaders = { "Finished", "Title", "Description", "Privacy", "Priority" };
	
	//Necessary user variables
	//These next variables are hypotheticals, don't know what types we are using yet
	
	Vector<String> mTabTitles;
	/*
	Vector<Todo> mAllTodos;
	//TODO
	*/
	
	
	//Constructor
	public MainPageGUI(){ //TODO make it take in a User object to fill all necessary variables
		mMainTabbedPane = new JTabbedPane();
		mMainEastPanel = new JPanel();
		mTabTitles = new Vector<String>();
		//Fill user variables from user
		//TODO replace with filling from User
		mTabTitles = new Vector<String>();
		mTabTitles.add("Work");
		mTabTitles.add("Play");
		
		
		createTabbedPane();
		createEastPanel();
		setLayout(new BorderLayout());
		add(mMainEastPanel, BorderLayout.EAST);
		add(mMainTabbedPane, BorderLayout.CENTER);
	}
	
	private void createTabbedPane(){
		for(int i=0; i<mTabTitles.size(); i++){
			
			JTable mTable = new JTable();
			DefaultTableModel mDefaultTable = new DefaultTableModel(0,0);
			mDefaultTable.setColumnIdentifiers(mTableHeaders);
			mTable.setModel(mDefaultTable);
			//TODO properly fill the Default Table from Todo vector  
			for (int j = 1; j<50; j++) {
				JRadioButton mFinishedButton = new JRadioButton("Finished");
				mDefaultTable.addRow(new Object[] { "Finished", "Title button",
						"Description String","Privacy String", "Priority Int" });
			}

	        mTable.getColumn("Title").setCellRenderer(new ButtonRenderer());
	        mTable.getColumn("Title").setCellEditor(new ButtonEditor(new JCheckBox()));
			
			JScrollPane mScrollPane = new JScrollPane(mTable);
			mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			mMainTabbedPane.add(mTabTitles.get(i),mScrollPane);
		}
	}
	
	private void createEastPanel(){ //Creating and filling out the panel on the east holding the add button and Social Panel
		mAddTodoButton = new JButton("Add Todo");
		mAddTodoButton.setPreferredSize(new Dimension(100,21));
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
	
	//BOTH OF THESE CLASSES BELOW ARE FROM http://stackoverflow.com/questions/13833688/adding-jbutton-to-jtable
	//PROBABLY NEED UPDATE AND CHANGES TO MAKE IT THE WAY WE WANT, LEAVING FOR NOW THOUGH
	class ButtonRenderer extends JButton implements TableCellRenderer {

	    public ButtonRenderer() {
	        setOpaque(true);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        if (isSelected) {
	            setForeground(table.getSelectionForeground());
	            setBackground(table.getSelectionBackground());
	        } else {
	            setForeground(table.getForeground());
	            setBackground(UIManager.getColor("Button.background"));
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
	        button.setOpaque(false);
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
	}
}
