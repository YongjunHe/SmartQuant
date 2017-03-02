package smartUI;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JToolBar;


public class newSmartToolBar extends JToolBar {

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		setBorderPainted(false);
		setBackground(new Color(255, 255, 255));
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}
	
}
