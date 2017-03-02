package free;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FreeStatusSeparator extends JLabel {

    private ImageIcon imageIcon = FreeUtil.getImageIcon("statusbar_separator.png");

    public FreeStatusSeparator() {
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.setOpaque(false);
        this.setIcon(imageIcon);
    }
}
