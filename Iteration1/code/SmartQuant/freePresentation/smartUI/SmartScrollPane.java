package smartUI;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class SmartScrollPane extends JScrollPane {

	@Override
	public JScrollBar createHorizontalScrollBar() {
		JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		scrollBar.setUI(new SmartScrollBarUI());
		return scrollBar;
	}

	@Override
	public JScrollBar createVerticalScrollBar() {
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		scrollBar.setUI(new SmartScrollBarUI());
		return scrollBar;
	}
	
}
