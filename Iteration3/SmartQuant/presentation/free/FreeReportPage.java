package free;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class FreeReportPage extends FreePagePane {

    private FreeTable table = new FreeTable();
    private JScrollPane scroll = new JScrollPane(table);
    private int descriptionHeight = 25;
    private JTextField lbDescription = new JTextField() {

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            return new Dimension(size.width, descriptionHeight);
        }
    };
    private Border descriptionBorder = BorderFactory.createEmptyBorder(0, FreeUtil.TABLE_CELL_LEADING_SPACE, 0, 0);
    private Color descriptionTextColor = new Color(120, 123, 154);
    private Color descriptionBackgroundColor = new Color(226, 228, 229);

    public FreeReportPage() {
        init();
    }

    private void init() {
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.getCenterPane().add(scroll, BorderLayout.CENTER);
        this.getCenterPane().add(lbDescription, BorderLayout.NORTH);

        JLabel lbCorner = new JLabel();
        lbCorner.setOpaque(true);
        lbCorner.setBackground(descriptionBackgroundColor);
        this.scroll.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, lbCorner);

        this.lbDescription.setForeground(descriptionTextColor);
        this.lbDescription.setBackground(descriptionBackgroundColor);
        this.lbDescription.setOpaque(true);
        this.lbDescription.setBorder(descriptionBorder);
        lbDescription.setEditable(false);
        lbDescription.setFont(FreeUtil.FONT_12_BOLD);
    }

    public void setDescription(String description) {
        this.lbDescription.setText(description);
    }

    public JTable getTable() {
        return table;
    }
}
