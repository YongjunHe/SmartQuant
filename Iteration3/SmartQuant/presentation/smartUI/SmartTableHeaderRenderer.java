package smartUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class SmartTableHeaderRenderer extends DefaultTableCellRenderer {
	  private Color background = new Color(243, 244, 246);
	    private Color textColor = new Color(57, 62, 70);
	    private Color borderLightColor = new Color(255, 255, 255);
	    private Color borderDarkColor = new Color(222, 230, 217);
	    private int tableHeaderHeight = 20;
	    private Border border = new Border() {

	        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        	Graphics2D g2d = (Graphics2D)g;
	    		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	    				RenderingHints.VALUE_ANTIALIAS_ON);
	        	
	            g2d.setColor(borderDarkColor);
	            g2d.drawRect(x, y, width - 1, height - 1);
	            //draw brigher border.
	            g2d.setColor(borderLightColor);
	            g2d.drawLine(x, y, x, height - 1);
	        }

	        public Insets getBorderInsets(Component c) {
	            return new Insets(1, 5, 1, 1);
	        }

	        public boolean isBorderOpaque() {
	            return true;
	        }
	    };

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        super.getTableCellRendererComponent(table,
	                value, isSelected, hasFocus, row, column);

	        this.setBackground(background);
	        this.setForeground(textColor);
	        this.setBorder(border);
	        return this;
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        Dimension size = super.getPreferredSize();
	        return new Dimension(size.width, tableHeaderHeight);
	    }
}
