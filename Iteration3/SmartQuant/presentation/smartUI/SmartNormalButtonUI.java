package smartUI;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.plaf.basic.BasicButtonUI;


public class SmartNormalButtonUI extends BasicButtonUI {

	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		b.setBackground(new Color(225, 233, 220));
	}
	
}
