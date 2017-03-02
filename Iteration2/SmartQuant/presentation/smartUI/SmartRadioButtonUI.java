package smartUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.text.View;

import sun.swing.SwingUtilities2;

public class SmartRadioButtonUI extends BasicRadioButtonUI {

	@Override
	protected void installDefaults(AbstractButton b) {
		// TODO Auto-generated method stub
		super.installDefaults(b);
	}

	@Override
	public Icon getDefaultIcon() {
		// TODO Auto-generated method stub
		return super.getDefaultIcon();
	}


    /* These Dimensions/Rectangles are allocated once for all
     * RadioButtonUI.paint() calls.  Re-using rectangles
     * rather than allocating them in each paint call substantially
     * reduced the time it took paint to run.  Obviously, this
     * method can't be re-entered.
     */
    private static Dimension size = new Dimension();
    private static Rectangle viewRect = new Rectangle();
    private static Rectangle iconRect = new Rectangle();
    private static Rectangle textRect = new Rectangle();

	
	@Override
	public synchronized void paint(Graphics g, JComponent c) {
		
		 AbstractButton b = (AbstractButton) c;
	        ButtonModel model = b.getModel();

	        b.setBorderPainted(false);
	        b.setOpaque(true);
	        
	        Font f = new Font("Arial", Font.BOLD, 12);
	        b.setForeground(new Color(55, 53, 52));
	        g.setFont(f);
	        FontMetrics fm = SwingUtilities2.getFontMetrics(c, g, f);

	        Insets i = c.getInsets();
	        size = b.getSize(size);
	        viewRect.x = i.left;
	        viewRect.y = i.top;
	        viewRect.width = size.width - (i.right + viewRect.x);
	        viewRect.height = size.height - (i.bottom + viewRect.y);
	        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
	        textRect.x = textRect.y = textRect.width = textRect.height = 0;

	        Icon altIcon = b.getIcon();
	        Icon selectedIcon = null;
	        Icon disabledIcon = null;

	        String text = SwingUtilities.layoutCompoundLabel(
	            c, fm, b.getText(), altIcon != null ? altIcon : getDefaultIcon(),
	            b.getVerticalAlignment(), b.getHorizontalAlignment(),
	            b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
	            viewRect, iconRect, textRect,
	            b.getText() == null ? 0 : b.getIconTextGap());

	        // fill background
	        if(c.isOpaque()) {
	            g.setColor(b.getBackground());
	            g.fillRect(0,0, size.width, size.height);
	        }


	        // Paint the radio button
	        if(altIcon != null) {

	            if(!model.isEnabled()) {
	                if(model.isSelected()) {
	                   altIcon = b.getDisabledSelectedIcon();
	                } else {
	                   altIcon = b.getDisabledIcon();
	                }
	            } else if(model.isPressed() && model.isArmed()) {
	                altIcon = b.getPressedIcon();
	                if(altIcon == null) {
	                    // Use selected icon
	                    altIcon = b.getSelectedIcon();
	                }
	            } else if(model.isSelected()) {
	                if(b.isRolloverEnabled() && model.isRollover()) {
	                        altIcon = b.getRolloverSelectedIcon();
	                        if (altIcon == null) {
	                                altIcon = b.getSelectedIcon();
	                        }
	                } else {
	                        altIcon = b.getSelectedIcon();
	                }
	            } else if(b.isRolloverEnabled() && model.isRollover()) {
	                altIcon = b.getRolloverIcon();
	            }

	            if(altIcon == null) {
	                altIcon = b.getIcon();
	            }

	            altIcon.paintIcon(c, g, iconRect.x, iconRect.y);

	        } else {
//	            getDefaultIcon().paintIcon(c, g, iconRect.x, iconRect.y);
	        }


	        // Draw the Text
	        if(text != null) {
	            View v = (View) c.getClientProperty(BasicHTML.propertyKey);
	            if (v != null) {
	                v.paint(g, textRect);
	            } else {
	                paintText(g, b, textRect, text);
	            }
	            if(b.hasFocus() && b.isFocusPainted() &&
	               textRect.width > 0 && textRect.height > 0 ) {
	                paintFocus(g, textRect, size);
	            }
	        }
	}

	@Override
	protected void paintFocus(Graphics g, Rectangle textRect, Dimension size) {
		// TODO Auto-generated method stub
		super.paintFocus(g, textRect, size);
	}

	@Override
	protected void paintIcon(Graphics g, AbstractButton b, Rectangle iconRect) {
		// TODO Auto-generated method stub
		super.paintIcon(g, b, iconRect);
	}

	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		// TODO Auto-generated method stub
		super.paintButtonPressed(g, b);
	}
	
}
