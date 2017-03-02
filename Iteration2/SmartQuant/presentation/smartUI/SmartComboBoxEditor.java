package smartUI;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;


public class SmartComboBoxEditor extends BasicComboBoxEditor {

	@Override
	protected JTextField createEditorComponent() {
		JTextField editor = new SmartTextField("",9);
	    editor.setBorder(null);
	    return editor;
	}
	
}
