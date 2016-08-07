package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.colorchooser.ColorSelectionModel;

import constants.Constants;

public class TodoFrame extends JFrame implements Navigator {
	
	private static final long serialVersionUID = 1290395190L;
	
	//private TodoUser tempUser; //TODO temporary user for working on functionality/communication between classes
	private TodoUser mTodoUser;
	
	public static void main(String[] args) {

		//new TodoFrame("Todo: a CS201 project worth 100%");
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			SwingUtilities.invokeLater(() -> { new TodoFrame("Todo: a CS201 project worth 100%").setVisible(true); });
		}
		
	}
	
	public TodoFrame(String string) {
		super(string);

		add(new SignInGUI(this));
		
		setSize(800, 500);
		setMinimumSize(new Dimension(800, 500));
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Making tabbedpane look nice
		UIManager.put("TabbedPane.shadow",Constants.goldColor);
		UIManager.put("TabbedPane.darkShadow",Constants.goldColor);
		UIManager.put("TabbedPane.light",Constants.goldColor);
		UIManager.put("TabbedPane.highlight",Constants.goldColor);
		UIManager.put("TabbedPane.tabAreaBackground",Constants.redColor);
		UIManager.put("TabbedPane.unselectedBackground",Constants.redColor);
		UIManager.put("TabbedPane.background",Constants.lightGreyColor);
		UIManager.put("TabbedPane.foreground",Constants.goldColor);
		UIManager.put("TabbedPane.focus",Constants.redColor);
		UIManager.put("TabbedPane.contentAreaColor",Constants.redColor);
		UIManager.put("TabbedPane.selected",Constants.redColor);
		UIManager.put("TabbedPane.selectHighlight",Constants.goldColor);
		UIManager.put("TabbedPane.borderHightlightColor",Constants.goldColor);
		
		//Making scrollpane look nice
		UIManager.put("ScrollPane.background",Constants.lightGreyColor);
		UIManager.put("ScrollPane.foreground",Constants.greyColor);
		UIManager.put("ScrollPane.viewportBorder",BorderFactory.createLineBorder(Constants.greyColor,0));
		//UIManager.put("ScrollBar.background",Constants.goldColor);
		UIManager.put("ScrollBar.darkShadow",Constants.greyColor);
		//UIManager.put("ScrollBar.foreground",Constants.redColor);
		UIManager.put("ScrollBar.highlight",Constants.greyColor);
		UIManager.put("ScrollBar.shadow",Constants.greyColor);
		UIManager.put("ScrollBar.thumb",Color.GREEN);
		UIManager.put("ScrollBar.thumbDarkShadow",Constants.greyColor);
		UIManager.put("ScrollBar.thumbHighlight",Constants.greyColor);
		UIManager.put("ScrollBar.thumbShadow",Constants.greyColor);
		UIManager.put("ScrollBar.track",Color.GREEN);
		UIManager.put("ScrollBar.trackForeground",Color.GREEN);
		UIManager.put("ScrollBar.trackHighlight",Color.GREEN);
		UIManager.put("ScrollBar.trackHighlightForeground",Color.GREEN);
		
		
		
		//Making tables look nice
		UIManager.put("Table.background",Constants.lightGreyColor);
		UIManager.put("Table.darkShadow",Constants.goldColor);
		UIManager.put("Table.focusCellBackground",Constants.lightGreyColor);
		UIManager.put("Table.focusCellForeground",Constants.goldColor);
		UIManager.put("Table.focusCellHighlightBorder",Constants.redColor);
		UIManager.put("Table.foreground",Constants.goldColor);
		UIManager.put("Table.gridColor",Constants.greyColor);
		UIManager.put("Table.highlight",Constants.redColor);
		UIManager.put("Table.light",Constants.redColor);
		UIManager.put("Table.selectionBackground",Constants.lightGreyColor);
		UIManager.put("Table.selectionForeground",Constants.goldColor);
		UIManager.put("Table.scrollPaneBorder", BorderFactory.createLineBorder(Constants.redColor,1));
		UIManager.put("Table.shadow",Constants.goldColor);
		UIManager.put("TableHeader.background", Constants.lightGreyColor);
		UIManager.put("TableHeader.foreground", Constants.redColor);
		UIManager.put("TableHeader.cellBorder", BorderFactory.createLineBorder(Constants.greyColor));
	}
	
	@Override
	public void toPortal(TodoUser tu) {
		mTodoUser = tu;
		
		getContentPane().removeAll();
		
		/*//TODO REMOVE THIS, JUST TO HAVE A USER TO WORK WITH
		//tempUser = new TodoUser(1,"Jeff","pass","email");
		/*
		TodoList playList = new TodoList(0,"Play");
		for(int i =0;i<6;i++){
			String tempTitle = "TITLE "+i;
			TodoObject tempTodo = new TodoObject(tempTitle, 1, true, 0, playList.getName(), "I LIKE TO HAVE FUN", i, 1, false);
			playList.addTodo(tempTodo);
			
		}
		TodoList workList = new TodoList(1,"Work");
		for(int i =0;i<10;i++){
			String tempTitle = i+" TITLE";
			TodoObject tempTodo = new TodoObject(tempTitle, 5, false, 0, workList.getName(), "I WorkHard", 100-i, 1, true);
			workList.addTodo(tempTodo);
		}
		Vector<TodoList> tempTodoListVec = new Vector<TodoList>();
		tempTodoListVec.add(playList);
		tempTodoListVec.add(workList);
		mTodoUser.setTodoLists(tempTodoListVec);
		//END OF STUFF TO REMOVE
		 * 
		 */
		
		JMenuBar mTestBar = new JMenuBar();
		setJMenuBar(mTestBar);
		
		//getContentPane().add(new PortalManager(mTodoUser, mTestBar), BorderLayout.CENTER);
		getContentPane().add(new PortalManager(mTodoUser, mTestBar), BorderLayout.CENTER);
		getContentPane().add(new SocialSidebar(mTodoUser, this, PortalManager.mMainPage), BorderLayout.EAST); //I ADDED THE TODOUSER BECAUSE IT IS NOW NECESSARY FOR SOCIAL SIDEBAR CONSTRUCTOR
		revalidate();
		repaint();
	}

	@Override
	public void toPortal() {
		System.out.println("Guest user");
		System.out.println(TodoClientListener.get().getUsername());
		
		getContentPane().removeAll();

		JMenuBar mGuestBar = new JMenuBar();
		setJMenuBar(mGuestBar);
		getContentPane().add(new PortalManager(mGuestBar), BorderLayout.CENTER);
		
		revalidate();
		repaint();
	}
	
	@Override
	public void toLogin() {
		getContentPane().removeAll();
		getContentPane().add(new LoginGUI(this));
		revalidate();
		repaint();
	}

	@Override
	public void toSignup() {
		getContentPane().removeAll();
		getContentPane().add(new SignupGUI(this));
		revalidate();
		repaint();
	}
}
