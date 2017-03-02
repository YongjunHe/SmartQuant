package smartUI; 

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class SmartPanel extends JPanel {
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(255, 255, 255));
		g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		
		g2d.setColor(new Color(3, 165, 100));
		g2d.fillRect(0, 0, getWidth() - 1, 30);

		int g1 = 165;
		for (int i = 0; i < 35; i++) {
			g2d.setColor(new Color(3, g1, 100));
			g2d.fillRect(0, 10 + i, getWidth() - 1, 1);
			g1++;
		}
		
		g2d.setClip(0, getHeight() - 31, getWidth() - 1, 30);
		g2d.setColor(new Color(67, 59, 69));
		g2d.fillRect(0, getHeight() - 41, getWidth() - 1, 40);
		g2d.setClip(null);
		
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.setColor(new Color(240, 251, 230));
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
