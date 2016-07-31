package server;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

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
