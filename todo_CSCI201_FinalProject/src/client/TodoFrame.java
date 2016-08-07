package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class TodoFrame extends JFrame implements Navigator {
	
	private static final long serialVersionUID = 1290395190L;
	
	private TodoUser tempUser; //TODO temporary user for working on functionality/communication between classes
	
	public static void main(String[] args) {
		new TodoFrame("Todo Frame");
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
	public void toPortal() {
		getContentPane().removeAll();
		
		//TODO REMOVE THIS, JUST TO HAVE A USER TO WORK WITH
		tempUser = new TodoUser(1,"Jeff","pass","email");
		TodoList playList = new TodoList(0,"Play");
		for(int i =0;i<6;i++){
			String tempTitle = "TITLE "+i;
			TodoObject tempTodo = new TodoObject(tempTitle, 1, true, 0, playList.getName(), "I LIKE TO HAVE FUN", i,1);
			playList.addTodo(tempTodo);
		}
		TodoList workList = new TodoList(1,"Work");
		for(int i =0;i<10;i++){
			String tempTitle = i+" TITLE";
			TodoObject tempTodo = new TodoObject(tempTitle, 5, false, 0, workList.getName(), "I WorkHard", 100-i,1);
			workList.addTodo(tempTodo);
		}
		Vector<TodoList> tempTodoListVec = new Vector<TodoList>();
		tempTodoListVec.add(playList);
		tempTodoListVec.add(workList);
		tempUser.setTodoLists(tempTodoListVec);
		//END OF STUFF TO REMOVE
		
		getContentPane().add(new SocialSidebar(tempUser, this), BorderLayout.EAST); //I ADDED THE TODOUSER BECAUSE IT IS NOW NECESSARY FOR SOCIAL SIDEBAR CONSTRUCTOR

		JMenuBar mTestBar = new JMenuBar();
		setJMenuBar(mTestBar);
		
		getContentPane().add(new PortalManager(tempUser, mTestBar), BorderLayout.CENTER);
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
