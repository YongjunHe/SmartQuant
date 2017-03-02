package free;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import twaver.swing.SingleFiledLayout;

public class FreeOutlookHeader extends FreeHeader {

    private SingleFiledLayout toolbarLayout = new SingleFiledLayout(
            SingleFiledLayout.ROW,
            SingleFiledLayout.LEFT,
            2);
    private JPanel toolbar = new JPanel(toolbarLayout);
    private ImageIcon separatorIcon = FreeUtil.getImageIcon("toolbar_separator.png");

    public FreeOutlookHeader() {
        init();
    }

    private void init() {
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        this.add(toolbar, BorderLayout.CENTER);
    }

    public FreeToolbarButton addButton(Icon icon, String tooltip, ActionListener action, String command) {
        FreeToolbarButton button = new FreeToolbarButton();
        button.setIcon(icon);
        button.setToolTipText(tooltip);
        if (action != null) {
            button.addActionListener(action);
        }
        button.setActionCommand(command);
        toolbar.add(button);
        return button;
    }

    public void addSeparator() {
        toolbar.add(new JLabel(separatorIcon));
    }

    @Override
    protected Object getResizeHandlerLayoutConstraint() {
        return BorderLayout.EAST;
    }

    @Override
    protected Object getShrinkHandlerLayoutConstraint() {
        return BorderLayout.WEST;
    }

    @Override
    protected FreeListSplitListener createSplitListener() {
        return new FreeOutlookSplitListener(this);
    }

    @Override
    protected Border createBorder() {
        return BorderFactory.createEmptyBorder(4, 0, 0, 7);
    }

    @Override
    protected ImageIcon getShrinkIcon(boolean shrinked) {
        if (shrinked) {
            return RIGHT_ARROW_ICON;
        } else {
            return LEFT_ARROW_ICON;
        }
    }

    @Override
    protected JComponent getCenterComponent() {
        return null;
    }

    @Override
    public void setShrink(boolean shrinked) {
        super.setShrink(shrinked);
        toolbar.setVisible(!shrinked);
    }

    @Override
    protected int getShrinkedWidth() {
        return FreeUtil.OUTLOOK_SHRINKED_WIDTH;
    }

    public JPanel getToolBar() {
        return this.toolbar;
    }
}
