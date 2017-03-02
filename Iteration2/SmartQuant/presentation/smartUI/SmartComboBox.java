package smartUI;

import java.awt.Graphics;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class SmartComboBox extends JComboBox {

	public SmartComboBox() {
		super();
		// TODO Auto-generated constructor stub
		
		setUI(new SmartComboBoxUI());
//		setEnabled(false);
	}

	public SmartComboBox(ComboBoxModel aModel) {
		super(aModel);
		// TODO Auto-generated constructor stub
		
		setUI(new SmartComboBoxUI());
	}

	public SmartComboBox(Object[] items) {
		super(items);
		// TODO Auto-generated constructor stub
		
		setUI(new SmartComboBoxUI());
	}

}
