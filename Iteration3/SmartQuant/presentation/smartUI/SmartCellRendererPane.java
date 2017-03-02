package smartUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;


public class SmartCellRendererPane extends CellRendererPane {

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

	@Override
	public void paintComponent(Graphics g, Component c, Container p, int x,
			int y, int w, int h, boolean shouldValidate) {
		 if (c == null) {
	            if (p != null) {
	                Color oldColor = g.getColor();
//	                g.setColor(p.getBackground());
	                g.setColor(new Color(0, 0, 0));
	                g.fillRect(x, y, w, h);
	                g.setColor(oldColor);
	            }
	            return;
	        }

	        if (c.getParent() != this) {
	            this.add(c);
	        }

	        c.setBounds(x, y, w, h);

	        if(shouldValidate) {
	            c.validate();
	        }

	        boolean wasDoubleBuffered = false;
	        if ((c instanceof JComponent) && ((JComponent)c).isDoubleBuffered()) {
	            wasDoubleBuffered = true;
	            ((JComponent)c).setDoubleBuffered(false);
	        }

	        Graphics cg = g.create(x, y, w, h);
	        try {
	            c.paint(cg);
	        }
	        finally {
	            cg.dispose();
	        }

	        if (wasDoubleBuffered && (c instanceof JComponent)) {
	            ((JComponent)c).setDoubleBuffered(true);
	        }

	        c.setBounds(-w, -h, 0, 0);
	}
	
}
