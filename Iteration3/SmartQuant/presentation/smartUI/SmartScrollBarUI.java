package smartUI;

import static sun.swing.SwingUtilities2.drawHLine;
import static sun.swing.SwingUtilities2.drawRect;
import static sun.swing.SwingUtilities2.drawVLine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class SmartScrollBarUI extends BasicScrollBarUI {

	@Override
	public void paint(Graphics g, JComponent c) {
		// TODO Auto-generated method stub
		super.paint(g, c);
		
//		incrButton.setSize((int)trackRect.getWidth() + 1, 20);
//		decrButton.setSize((int)trackRect.getWidth() + 1, 20);
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		  return new SmartArrowButton(orientation,
				  new Color(241, 248, 235),
				  new Color(107, 90, 82),
				  new Color(85, 72, 64),
				  new Color(85, 72, 64));
	}
	
	
	@Override
	protected JButton createDecreaseButton(int orientation) {
		  return new SmartArrowButton(orientation,
				  new Color(241, 248, 235),
				  new Color(107, 90, 82),
				  new Color(85, 72, 64),
				  new Color(85, 72, 64));
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		 if(thumbBounds.isEmpty() || !scrollbar.isEnabled())     {
	            return;
	        }

	        int w = thumbBounds.width;
	        int h = thumbBounds.height;

	        Graphics2D g2d = (Graphics2D)g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        g2d.translate(thumbBounds.x, thumbBounds.y);

	        g2d.setColor(new Color(203, 203, 203));
	        drawRect(g, 0, 0, w - 1, h - 1);
	        g2d.setColor(new Color(201, 207, 202));
	        g2d.fillRect(0, 0, w - 1, h - 1);

	        g2d.setColor(new Color(203, 203, 203));
	        drawVLine(g2d, 1, 1, h - 2);
	        drawHLine(g2d, 2, w - 3, 1);
	        
	        g2d.setColor(new Color(208, 207, 202));
	        drawHLine(g2d, 2, w - 2, h - 2);
	        drawVLine(g2d, w - 2, 1, h - 3);

	        g2d.translate(-thumbBounds.x, -thumbBounds.y);
	}

	@Override
	protected void paintDecreaseHighlight(Graphics g) {
	}

	@Override
	protected void paintIncreaseHighlight(Graphics g) {
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(new Color(241, 248, 235));
	    g2d.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);

	    if(trackHighlight == DECREASE_HIGHLIGHT)        {
	        paintDecreaseHighlight(g);
	    }
	    else if(trackHighlight == INCREASE_HIGHLIGHT)           {
	        paintIncreaseHighlight(g);
	    }
	}
	
}
