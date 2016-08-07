package server;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

public class ScrollTextPane extends JScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4296243818130205852L;
	
	JTextArea ta;
	
	boolean canUndo = false;
	boolean canRedo = false;

	UndoManager undo;
	
	public ScrollTextPane(){
		super();

		undo = new UndoManager();
		ta = new JTextArea();
		ta.setEditable(false);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setSelectionColor(new Color(235,138,74));
		
		setViewportView(ta);
	}
	
	public JTextArea getTextArea(){
		return ta;
	}
}
