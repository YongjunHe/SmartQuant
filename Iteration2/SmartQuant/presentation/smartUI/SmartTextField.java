package smartUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class SmartTextField extends JTextField {
	
	public SmartTextField() {
		super();
		// TODO Auto-generated constructor stub
		
	}

	public SmartTextField(String text, int columns) {
		super(text, columns);
		// TODO Auto-generated constructor stub
	}

	public SmartTextField(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	private BufferedImage buffer = null;  
	
	@Override
	protected void paintComponent(Graphics g) {
		Component window = this.getTopLevelAncestor();  
        if (window instanceof Window && !((Window)window).isOpaque()) {  
            // This is a translucent window, so we need to draw to a buffer  
            // first to work around a bug in the DirectDraw rendering in Swing.  
            int w = this.getWidth();  
            int h = this.getHeight();  
            if (buffer == null || buffer.getWidth() != w || buffer.getHeight() != h) {  
                // Create a new buffer based on the current size.  
                GraphicsConfiguration gc = this.getGraphicsConfiguration();  
                buffer = gc.createCompatibleImage(w, h, BufferedImage.TRANSLUCENT);  
            }  

            // Use the super class's paintComponent implementation to draw to  
            // the buffer, then write that buffer to the original Graphics object.  
            Graphics bufferGraphics = buffer.createGraphics();  
            try {  
                super.paintComponent(bufferGraphics);  
            } finally {  
                bufferGraphics.dispose();  
            }  
            g.drawImage(buffer, 0, 0, w, h, 0, 0, w, h, null);  
        } else {  
            // This is not a translucent window, so we can call the super class  
            // implementation directly.  
            super.paintComponent(g);  
        }          
		
		setBackground(new Color(255, 255, 255));
	}

	@Override
	protected void paintChildren(Graphics g) {
		// TODO Auto-generated method stub
		super.paintChildren(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Border border = BorderFactory.createLineBorder(new Color(193, 198, 195), 1, 
				true);
	    if (border != null) {
	    	border.paintBorder(this, g, 0, 0, getWidth(), getHeight());
	    }
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}
	
}
