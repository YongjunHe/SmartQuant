package free;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FreeStatusLabel extends JLabel {

    public FreeStatusLabel() {
        this(null, null);
    }

    public FreeStatusLabel(String text) {
        this(text, null);
    }

    public FreeStatusLabel(Icon icon) {
        this(null, icon);
    }

    public FreeStatusLabel(String text, Icon icon) {
        super(text, icon, SwingConstants.LEADING);
        init();
    }

    protected void init() {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.setFont(FreeUtil.FONT_12_BOLD);
        this.setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.setIconTextGap(5);
    }
}
