package smartUI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class SmartTableCellRenderer extends DefaultTableCellRenderer {
	private Color backgroundEven = Color.white;
	   private Color backgroundOdd = new Color(225, 233, 220);
	   private Color backgroundSelected = new Color(175, 215, 237);
	   private Color selectedTextColor = new Color(21, 20, 20);
	   private Color textColor = new Color(57, 62, 70);
	   private Border border = BorderFactory.createEmptyBorder(0, 
			   5, 0, 0);

	   @Override
	   public Component getTableCellRendererComponent(JTable table, Object value,
	           boolean isSelected, boolean hasFocus, int row, int column) {
	       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	       this.setBorder(border);
	       if (!isSelected) {
	           if (row % 2 == 1) {
	               this.setBackground(backgroundOdd);
	           } else {
	               this.setBackground(backgroundEven);
	           }
	           this.setForeground(textColor);
	       } else {
	           this.setBackground(backgroundSelected);
	           this.setForeground(selectedTextColor);
	       }
	       return this;
	   }
}
