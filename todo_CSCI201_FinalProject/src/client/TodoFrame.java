package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		

		UIManager.put("TabbedPane.shadow",Constants.goldColor);
		UIManager.put("TabbedPane.darkShadow",Constants.goldColor);
		UIManager.put("TabbedPane.light",Constants.goldColor);
		UIManager.put("TabbedPane.highlight",Constants.goldColor);
		UIManager.put("TabbedPane.tabAreaBackground",Constants.goldColor);
		UIManager.put("TabbedPane.unselectedBackground",Constants.goldColor);
		UIManager.put("TabbedPane.background",Constants.redColor);
		UIManager.put("TabbedPane.foreground",Color.BLACK);
		UIManager.put("TabbedPane.focus",Constants.goldColor);
		UIManager.put("TabbedPane.contentAreaColor",Constants.goldColor);
		UIManager.put("TabbedPane.selected",Constants.goldColor);
		UIManager.put("TabbedPane.selectHighlight",Constants.goldColor);
		UIManager.put("TabbedPane.borderHightlightColor",Constants.goldColor);
	}
	
	@Override
	public void toPortal(TodoUser tu) {
		mTodoUser = tu;
		
		getContentPane().removeAll();
		
/*		//TODO REMOVE THIS, JUST TO HAVE A USER TO WORK WITH
		//tempUser = new TodoUser(1,"Jeff","pass","email");
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
