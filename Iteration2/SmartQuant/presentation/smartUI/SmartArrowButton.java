package smartUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicArrowButton;

public class SmartArrowButton extends BasicArrowButton {

	private Color shadow;
    private Color darkShadow;
    private Color highlight;
	
	public SmartArrowButton(int direction) {
		super(direction);
		// TODO Auto-generated constructor stub
	}

	public SmartArrowButton(int direction, Color background, Color shadow,
			Color darkShadow, Color highlight) {
		super(direction, background, shadow, darkShadow, highlight);
		// TODO Auto-generated constructor stub
		
		this.shadow = shadow;
        this.darkShadow = darkShadow;
        this.highlight = highlight;
	}

	@Override
	public void paint(Graphics g) {
		 Graphics2D g2d = (Graphics2D)g;
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		 Color origColor;
         boolean isPressed, isEnabled;
         int w, h, size;

         w = getSize().width;
         h = getSize().height;
         origColor = g2d.getColor();
         isPressed = getModel().isPressed();
         isEnabled = isEnabled();

         g2d.setColor(new Color(241, 248, 235));
         g2d.fillRect(1, 1, w-1, h-1);

         /// Draw the proper Border
         if (getBorder() != null && !(getBorder() instanceof UIResource)) {
             paintBorder(g2d);
         } else if (isPressed) {
             g2d.setColor(shadow);
             g2d.fillRect(1, 1, w-1, h-1);
         }

         // If there's no room to draw arrow, bail
         if(h < 5 || w < 5)      {
             g2d.setColor(origColor);
             return;
         }

         if (isPressed) {
             g2d.translate(1, 1);
         }

         // Draw the arrow
         size = Math.min((h - 4) / 3, (w - 4) / 3);
         size = Math.max(size, 2);
         paintTriangle(g2d, (w - size) / 2, (h - size) / 2,
                             size, direction, isEnabled);

         // Reset the Graphics back to it's original settings
         if (isPressed) {
             g2d.translate(-1, -1);
         }
         g2d.setColor(origColor);

	}

	@Override
	public void paintTriangle(Graphics g, int x, int y, int size,
			int direction, boolean isEnabled) {
		 Graphics2D g2d = (Graphics2D)g;
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
         g2d.setStroke(new BasicStroke(2));
		
		 Color oldColor = g2d.getColor();
         int mid, i, j;

         j = 0;
         size = Math.max(size, 2);
         mid = (size / 2) - 1;

         g2d.translate(x, y);
         if(isEnabled)
        	 g2d.setColor(darkShadow);
         else
             g2d.setColor(shadow);

         switch(direction)       {
         case NORTH:
        	 if (getModel().isPressed()) {
        		 g2d.setColor(new Color(243, 244, 246));
        	 }
             g2d.drawLine(mid-2, mid+2, mid+1, mid-1);
             g2d.drawLine(mid+1, mid-1, mid+4, mid+2);
             if(!isEnabled)  {
                 g2d.setColor(highlight);
                 g2d.drawLine(mid-1+2, 1, mid+1, 1);
             }
             break;
         case SOUTH:
             if(!isEnabled)  {
                 g2d.translate(1, 1);
                 g2d.setColor(highlight);
                 for(i = size-1; i >= 0; i--)   {
                     g2d.drawLine(mid-i, j, mid+i, j);
                     j++;
                 }
                 g2d.translate(-1, -1);
                 g2d.setColor(shadow);
             }

             if (getModel().isPressed()) {
        		 g2d.setColor(new Color(243, 244, 246));
        	 }
             g2d.drawLine(mid-2, mid, mid+1, mid+3);
             g2d.drawLine(mid+1, mid+3, mid+4, mid);
             break;
         case WEST:
        	 if (getModel().isPressed()) {
        		 g2d.setColor(new Color(243, 244, 246));
        	 }
        	 g2d.drawLine(mid-2, mid, mid+1, mid-3);
             g2d.drawLine(mid-2, mid, mid+1, mid+3);
             if(!isEnabled)  {
                 g2d.setColor(highlight);
                 g2d.drawLine(1, mid-1+2, 1, mid+1);
             }
             break;
         case EAST:
             if(!isEnabled)  {
                 g2d.translate(1, 1);
                 g2d.setColor(highlight);
                 for(i = size-1; i >= 0; i--)   {
                     g2d.drawLine(j, mid-i, j, mid+i);
                     j++;
                 }
                 g2d.translate(-1, -1);
                 g2d.setColor(shadow);
             }

             if (getModel().isPressed()) {
        		 g2d.setColor(new Color(243, 244, 246));
        	 }
        	 g2d.drawLine(mid-1, mid-3, mid+2, mid);
             g2d.drawLine(mid-1, mid+3, mid+2, mid);
             break;
         }
         g2d.translate(-x, -y);
         g2d.setColor(oldColor);
     }
	
}
