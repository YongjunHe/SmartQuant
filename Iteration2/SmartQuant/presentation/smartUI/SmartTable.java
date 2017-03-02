package smartUI;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class SmartTable extends JTable {
	 private Color verticalGridColor = new Color(243, 244, 246);
	    private SmartTableCellRenderer renderer = new SmartTableCellRenderer();
	    private SmartTableHeaderRenderer headerRenderer = new SmartTableHeaderRenderer();

	    public SmartTable() {
	        init();
	    }

	    private void init() {
	        this.getTableHeader().setDefaultRenderer(headerRenderer);
	        this.setBorder(null);
	        this.setRowSelectionAllowed(true);
	        this.setShowHorizontalLines(false);
	        this.setShowVerticalLines(true);
	        this.setGridColor(verticalGridColor);
	        this.setRowMargin(0);
	    }

	    @Override
	    public TableCellRenderer getCellRenderer(int row, int column) {
	        return renderer;
	    }

	    @Override
	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }
}
