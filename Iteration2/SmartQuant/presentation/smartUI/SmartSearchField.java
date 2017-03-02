package smartUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SmartSearchField extends JTextField {

	private static String initStr;
	private BufferedImage image = null;

	public SmartSearchField() {
		super();
		// TODO Auto-generated constructor stub
		init();
	}

	public SmartSearchField(String text) {
		super(text);
		// TODO Auto-generated constructor stub
		initStr = text;
		setText(initStr);
		init();
	}

	public void init() {
		setForeground(new Color(151, 151, 151));
		addListener();
		
		try {
			image = ImageIO.read(new FileInputStream("presentation/smartUI/images/AKSZUAG@{ZUCI2V)D3(YTUL.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addListener() {
		 addMouseListener(new MouseAdapter() {
	 			@Override
	 			public void mouseClicked(MouseEvent e) {
	 				if (e.getX() > getWidth() - 25) {
						fireActionPerformed();
					}
	 			}
	 		});
		
		addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				setForeground(new Color(0, 0, 0));
				if(getText().equals(initStr))
				setText(null);
			}
				
			@Override
			public void focusLost(FocusEvent e) {
				if(getText().trim().isEmpty()){
				setForeground(new Color(151, 151, 151));
				setText(initStr);
				}
			}
				
		});
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					System.out.println("enter");
				}
			}
		});
		
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
         Graphics2D g2d = (Graphics2D)g;
 		 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
 				RenderingHints.VALUE_ANTIALIAS_ON);
 		 if (image != null) {
 			 g2d.drawImage(image, getWidth() - 25, 0, 25, 25, null);
	     }
         
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
