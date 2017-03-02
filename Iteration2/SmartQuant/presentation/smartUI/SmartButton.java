package smartUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;


public class SmartButton extends JButton {

	public SmartButton() {
		super();
		// TODO Auto-generated constructor stub
		init();
	}

	public SmartButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		setUI(new SmartNormalButtonUI());
		setBorderPainted(false);
		setFocusPainted(false);
		setBackground(new Color(246, 254, 240));
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
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
	
	
}
