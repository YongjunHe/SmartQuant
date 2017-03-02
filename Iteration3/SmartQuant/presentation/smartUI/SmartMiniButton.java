package smartUI; 

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class SmartMiniButton extends JButton {

	public SmartMiniButton() {
		super();
		// TODO Auto-generated constructor stub
		setUI(new SmartSpecialButtonUI());
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(3, 200, 100, 100));
			}

			@Override
			public void mouseExited(MouseEvent e) { 
				setOpaque(false);
			}
			
			
		});
		
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
		g2d.setStroke(new BasicStroke(1.5f));
		g2d.drawLine(6, 17, 26, 17);
	}
	
}
