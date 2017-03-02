package smartUI; 

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class SmartMiniButton extends JButton {

	public SmartMiniButton() {
		super();
		// TODO Auto-generated constructor stub
		setUI(new SmartButtonUI());
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(255, 255, 255));
		g2d.setStroke(new BasicStroke(1.1f));
		g2d.drawLine(6, 17, 26, 17);
	}
	
}
