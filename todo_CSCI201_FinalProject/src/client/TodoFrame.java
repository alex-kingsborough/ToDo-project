package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
<<<<<<< HEAD
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
=======
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
>>>>>>> 2d055a31f366b0cbcf466a154776cd60f97846d4

import constants.Constants;

public class TodoFrame extends JFrame implements Navigator {
	
	private static final long serialVersionUID = 1290395190L;
	
	//private TodoUser tempUser; //TODO temporary user for working on functionality/communication between classes
	private TodoUser mTodoUser;
	
	public static void main(String[] args) {
<<<<<<< HEAD
		//new TodoFrame("Todo: a CS201 project worth 100%");
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			SwingUtilities.invokeLater(() -> { new TodoFrame("Todo: a CS201 project worth 100%s").setVisible(true); });
		}
=======
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new TodoFrame("Todo: a CS201 project worth 100%");
>>>>>>> 2d055a31f366b0cbcf466a154776cd60f97846d4
	}
	
	public TodoFrame(String string) {
		super(string);

		add(new SignInGUI(this));
		
		setSize(800, 500);
		setMinimumSize(new Dimension(800, 500));
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void toPortal(TodoUser tu) {
		mTodoUser = tu;
		
		getContentPane().removeAll();
		
		//TODO REMOVE THIS, JUST TO HAVE A USER TO WORK WITH
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
