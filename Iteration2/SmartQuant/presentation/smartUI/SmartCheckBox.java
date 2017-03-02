package smartUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;

public class SmartCheckBox extends JCheckBox {

	public SmartCheckBox() {
		super();
		// TODO Auto-generated constructor stub
		
		setUI(new SmartCheckBoxUI());
		setBackground(new Color(246, 254, 240));
	}

	public SmartCheckBox(String text) {
		super(text);
		// TODO Auto-generated constructor stub
		
		setUI(new SmartCheckBoxUI());
		setBackground(new Color(246, 254, 240));
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		System.out.println("paintcomponent");
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(1));
		
		//draw square
		g2d.setColor(new Color(179, 197, 135));
		g2d.drawLine(1, 4, 15, 4);
		g2d.drawLine(1, 19, 15, 19);
		g2d.drawLine(1, 4, 1, 19);
		g2d.drawLine(15, 4, 15, 19);
		
		//draw checkmark
		if (isSelected()) {
			System.out.println("打钩");
			g2d.setColor(new Color(13, 12, 12));
			g2d.drawLine(3, 13, 6, 16);
			g2d.drawLine(6, 16, 13, 7);
		} else {
			System.out.println("取消打钩");
		}
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println("in");
				setBackground(new Color(219, 207, 202, 50));
			}

			@Override
			public void mouseExited(MouseEvent e) { 
				System.out.println("out");
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
		System.out.println("paint");
		super.paint(g);
	}

	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		System.out.println("repaint1");
		super.repaint(tm, x, y, width, height);
	}


}
