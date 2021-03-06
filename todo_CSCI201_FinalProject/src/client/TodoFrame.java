package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import constants.Constants;

public class TodoFrame extends JFrame implements Navigator {
	
	private static final long serialVersionUID = 1290395190L;
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
		
		setSize(800, 500);
		setMinimumSize(new Dimension(800, 500));
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Font change
		try{
			Font newFont = new Font("Comic Sans MS", Font.BOLD, 12);
			setUIFont(new javax.swing.plaf.FontUIResource(newFont));
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

		//Setting icon
		setIconImage(new ImageIcon("img/todoTaskbarIcon.png").getImage());
		
		//Making tabbedpane look nice
		UIManager.put("TabbedPane.background",Constants.lightGreyColor);
		UIManager.put("TabbedPane.borderHightlightColor",Constants.goldColor);
		UIManager.put("TabbedPane.contentAreaColor",Constants.redColor);
		UIManager.put("TabbedPane.darkShadow",Constants.goldColor);
		UIManager.put("TabbedPane.focus",Constants.redColor);
		UIManager.put("TabbedPane.foreground",Constants.goldColor);
		UIManager.put("TabbedPane.highlight",Constants.goldColor);
		UIManager.put("TabbedPane.light",Constants.goldColor);
		UIManager.put("TabbedPane.selected",Constants.redColor);
		UIManager.put("TabbedPane.selectHighlight",Constants.goldColor);
		UIManager.put("TabbedPane.shadow",Constants.goldColor);
		UIManager.put("TabbedPane.tabAreaBackground",Constants.redColor);
		UIManager.put("TabbedPane.unselectedBackground",Constants.redColor);
		
		//Making scrollpane look nice
		UIManager.put("ScrollPane.background",Constants.lightGreyColor);
		UIManager.put("ScrollPane.foreground",Constants.redColor);
		UIManager.put("ScrollPane.viewportBorder",BorderFactory.createLineBorder(Constants.greyColor,0));
		UIManager.put("ScrollBar.background",Constants.goldColor);
		UIManager.put("ScrollBar.darkShadow",Constants.redColor);
		UIManager.put("ScrollBar.foreground",Constants.redColor);
		UIManager.put("ScrollBar.highlight",Constants.redColor);
		UIManager.put("ScrollBar.shadow",Constants.greyColor);
		UIManager.put("ScrollBar.thumb", Constants.redColor);
		UIManager.put("ScrollBar.thumbDarkShadow",Constants.greyColor);
		UIManager.put("ScrollBar.thumbHighlight",Constants.greyColor);
		UIManager.put("ScrollBar.thumbShadow",Constants.greyColor);
		
		
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
		
		//Buttons
		UIManager.put("Button.border", BorderFactory.createLineBorder(Color.black,8));
		UIManager.put("Button.foreground", Constants.goldColor);
		UIManager.put("Button.select", Color.WHITE);
		
		//Option pane
		UIManager.put("OptionPane.warningDialog.titlePane.background", Constants.redColor);
				
		
		add(new SignInGUI(this));
	}
	

	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    Enumeration<Object> keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	}
	
	@Override
	public void toPortal(TodoUser tu) {
		mTodoUser = tu;
		getContentPane().removeAll();
		
		JMenuBar mTestBar = new JMenuBar();
		setJMenuBar(mTestBar);
		
		//getContentPane().add(new PortalManager(PortalManager.mUser, mTestBar), BorderLayout.CENTER);
		getContentPane().add(new PortalManager(mTodoUser, mTestBar), BorderLayout.CENTER);
		getContentPane().add(new SocialSidebar(mTodoUser, this, PortalManager.mMainPage), BorderLayout.EAST); //I ADDED THE TODOUSER BECAUSE IT IS NOW NECESSARY FOR SOCIAL SIDEBAR CONSTRUCTOR
		revalidate();
		repaint();
	}

	@Override
	public void toPortal() {
		
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
