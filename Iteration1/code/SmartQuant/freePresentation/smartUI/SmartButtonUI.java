package smartUI; 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class SmartButtonUI extends BasicButtonUI {

	private BufferedImage image = null;

	@Override
	protected void installDefaults(AbstractButton b) {
		// TODO Auto-generated method stub
		super.installDefaults(b);
		
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
	
		AbstractButton absButton = (AbstractButton)c;
		absButton.setBackground(new Color(0, 0, 0, 0));
		absButton.setForeground(null);
		absButton.setBorderPainted(false);
		absButton.setOpaque(true);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	public void update(Graphics g, JComponent c) {
		// TODO Auto-generated method stub
		super.update(g, c);
	}

	@Override
	protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
		// TODO Auto-generated method stub
		super.paintIcon(g, c, iconRect);
		
	}

	@Override
	protected void paintText(Graphics g, JComponent c, Rectangle textRect,
			String text) {
		// TODO Auto-generated method stub
		super.paintText(g, c, textRect, text);
	}

	@Override
	protected void paintText(Graphics g, AbstractButton b, Rectangle textRect,
			String text) {
		// TODO Auto-generated method stub
		super.paintText(g, b, textRect, text);
	}

	@Override
	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect,
			Rectangle textRect, Rectangle iconRect) {
		// TODO Auto-generated method stub
		super.paintFocus(g, b, viewRect, textRect, iconRect);
	}

	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		// TODO Auto-generated method stub
		super.paintButtonPressed(g, b);
		
	}

}
