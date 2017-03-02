package smartUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;

public class SmartComboBoxUI extends BasicComboBoxUI {

	@Override
	protected JButton createArrowButton() {

		JButton button = new SmartArrowButton(BasicArrowButton.SOUTH,
				new Color(246, 254, 240),
				  new Color(107, 90, 82),
				  new Color(85, 72, 64),
				  new Color(85, 72, 64));
		button.setName("ComboBox.arrowButton");
		return button;
		
	}
	
	@Override
	protected ComboPopup createPopup() {
		return new SmartComboPopup(comboBox);
	}
	
	@Override
	protected ComboBoxEditor createEditor() {
		return new SmartComboBoxEditor();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		// TODO Auto-generated method stub
		super.paint(g, c);
	}

	@Override
	public void configureArrowButton() {
		// TODO Auto-generated method stub
		super.configureArrowButton();
	}

	@Override
	protected void installComponents() {
		// TODO Auto-generated method stub
//		super.installComponents();
		
		arrowButton = createArrowButton();

        if (arrowButton != null)  {
            comboBox.add(arrowButton);
            configureArrowButton();
        }

        if ( comboBox.isEditable() ) {
            addEditor();
        }

        comboBox.add( currentValuePane );
	}

}
