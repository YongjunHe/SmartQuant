package smartUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JRadioButton;

public class SmartRadioButton extends JRadioButton {

	public SmartRadioButton() {
		super();
		// TODO Auto-generated constructor stub
		
		setUI(new SmartRadioButtonUI());
		setBackground(new Color(246, 254, 240));
	}

	public SmartRadioButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
		
		setUI(new SmartRadioButtonUI());
		setBackground(new Color(246, 254, 240));
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		
		//draw external circle
		g2d.setColor(new Color(179, 197, 135));
		g2d.drawOval(1, 6, 14, 14);
		
		//draw internal circle
		if (isSelected()) {
			g2d.setColor(new Color(13, 12, 12));
			g2d.fillOval(5, 10, 7, 7);
		}
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(219, 207, 202, 50));
			}

			@Override
			public void mouseExited(MouseEvent e) { 
				setBackground(new Color(246, 254, 240));
			}
			
		});
	}

	@Override
	protected void paintChildren(Graphics g) {
		// TODO Auto-generated method stub
		super.paintChildren(g);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

	
}
