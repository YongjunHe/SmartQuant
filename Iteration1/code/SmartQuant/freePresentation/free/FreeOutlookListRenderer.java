package free;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import twaver.list.TListCellRenderer;

public class FreeOutlookListRenderer extends TListCellRenderer {

    private Color selectedColor = FreeUtil.DEFAULT_SELECTION_COLOR;
    private Border normalBorder = BorderFactory.createEmptyBorder(3, 19, 3, 2);
    private Border shrinkedBorder = BorderFactory.createEmptyBorder(3, 2, 3, 2);

    public FreeOutlookListRenderer(FreeOutlookList list) {
        super(list);
    }

    @Override
    public Component getListCellRendererComponent(JList list,
            Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        this.setToolTipText(this.getText());
        FreeOutlookList outlookList = (FreeOutlookList) list;
        if (outlookList.getFreeOutlookBar().getFreeOutlookPane().isShrinked()) {
            this.setBorder(shrinkedBorder);
            this.setText(null);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setIconTextGap(0);
        } else {
            this.setBorder(normalBorder);
            this.setHorizontalAlignment(SwingConstants.LEADING);
            this.setIconTextGap(5);
        }

        if (isSelected) {
            this.setBackground(selectedColor);
        }
        return this;
    }
}
