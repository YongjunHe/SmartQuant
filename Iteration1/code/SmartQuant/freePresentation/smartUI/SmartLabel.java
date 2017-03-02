package smartUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class SmartLabel extends JLabel {

	public SmartLabel(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		setBackground(new Color(255, 255, 255));
		setOpaque(true);
	}

	@Override
	protected void paintChildren(Graphics g) {
		// TODO Auto-generated method stub
		super.paintChildren(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		// TODO Auto-generated method stub
		super.paintBorder(g);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}
	
}	
