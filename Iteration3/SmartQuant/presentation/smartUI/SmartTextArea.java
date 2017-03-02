package smartUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextArea;

public class SmartTextArea extends JTextArea {

	public SmartTextArea() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SmartTextArea(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		setBackground(new Color(255, 255, 255));
	}

}
