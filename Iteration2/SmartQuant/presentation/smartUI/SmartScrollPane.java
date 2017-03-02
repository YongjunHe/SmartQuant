package smartUI;


import java.awt.Component;

import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class SmartScrollPane extends JScrollPane {
	
	public SmartScrollPane(Component c){
		super(c);
	}


	public SmartScrollPane() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SmartScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void paintBorder(Graphics g) {
		// TODO Auto-generated method stub
		
		Border border = BorderFactory.createLineBorder(new Color(193, 198, 195), 1, 
				true);
		if (border != null) {
			border.paintBorder(this, g, 0, 0, getWidth(), getHeight());
		}
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		getViewport().setBackground(new Color(255, 255, 255));

	}


	@Override
	public JScrollBar createHorizontalScrollBar() {
		JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		scrollBar.setUI(new SmartScrollBarUI());
		return scrollBar;
	}

	@Override
	public JScrollBar createVerticalScrollBar() {
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		scrollBar.setUI(new SmartScrollBarUI());
		return scrollBar;
	}
	
}
