package free;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import twaver.Element;
import twaver.Group;
import twaver.Node;
import twaver.TWaverConst;
import twaver.list.TListCellRenderer;

public class FreeListRenderer extends TListCellRenderer {

    private JPanel itemRender = new JPanel(new BorderLayout());
    private int separatorHeight = 30;
    private JPanel separatorRender = new JPanel() {

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            return new Dimension(size.width, separatorHeight);
        }
    };
    private JLabel separatorLabel = new JLabel();
    private FreeSeparator separator = new FreeSeparator(SwingConstants.HORIZONTAL);
    private Color itemTextColor = FreeUtil.LIST_TEXT_COLOR;
    private Color separatorTextColor = Color.white;
    private Color itemSelectedBackground = new Color(199, 198, 200);
    private Color itemSelectedBorder = new Color(163, 163, 163);
    private Font separatorFont=FreeUtil.FONT_12_BOLD;

    public FreeListRenderer(FreeList list) {
        super(list);

        itemRender.setOpaque(false);
        itemRender.add(this, BorderLayout.CENTER);
        separatorRender.setLayout(new OverlayLayout(separatorRender));

        JPanel separatorHelpPane = new JPanel(new BorderLayout());
        separatorHelpPane.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        separatorHelpPane.add(separator);
        separatorHelpPane.setOpaque(false);
        separatorRender.setOpaque(false);
        separatorLabel.setOpaque(true);
        separatorLabel.setBackground(FreeUtil.LIST_BACKGROUND);
        separatorLabel.setForeground(separatorTextColor);
        separatorLabel.setFont(separatorFont);
        separatorLabel.setVerticalAlignment(SwingUtilities.TOP);
        separatorLabel.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        JPanel labelHelpPane = new JPanel(new BorderLayout());
        labelHelpPane.setBorder(BorderFactory.createEmptyBorder(6, 15, 0, 0));
        labelHelpPane.add(separatorLabel, BorderLayout.WEST);
        labelHelpPane.setOpaque(false);

        separatorRender.add(labelHelpPane);
        separatorRender.add(separatorHelpPane);
    }

    @Override
    public Component getListCellRendererComponent(JList list,
            Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Group) {
            String groupName = ((Group) value).getName();
            separatorLabel.setText(groupName);
            separatorRender.setToolTipText(groupName);

            //if list shrinked, only display separator line, no text.
            if (list.getParent() instanceof FreeListPane) {
                FreeListPane pane = (FreeListPane) list.getParent();
                if (pane.isShrinked()) {
                    separatorLabel.setText(" ");
                    separatorLabel.setOpaque(false);
                } else {
                    separatorLabel.setOpaque(true);
                }
            }

            return separatorRender;
        } else {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            //setup tooltip.
            this.setToolTipText(this.getText());
            if (value instanceof Node) {
                Node node = (Node) value;
                if (node.getToolTipText() != null) {
                    String tooltip = "<html>" + this.getText() + "<br>" + node.getToolTipText() + "</html>";
                    itemRender.setToolTipText(tooltip);
                }
            }

            if (list.getParent() instanceof FreeListPane) {
                FreeListPane pane = (FreeListPane) list.getParent();
                if (pane.isShrinked()) {
                    this.setBorder(BorderFactory.createEmptyBorder(2, 7, 1, 2));
                    this.setText("");
                } else {
                    this.setBorder(BorderFactory.createEmptyBorder(2, 20, 1, 2));
                }
            }
            if (isSelected) {
                this.setBackground(itemSelectedBackground);
                itemRender.setBorder(BorderFactory.createLineBorder(itemSelectedBorder));
            } else {
                itemRender.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            }
            this.setForeground(itemTextColor);

            if (((Element) value).getIconURL().equals(TWaverConst.BLANK_IMAGE)) {
                this.setIcon(FreeUtil.BLANK_ICON);
            }
            return itemRender;
        }
    }
}
