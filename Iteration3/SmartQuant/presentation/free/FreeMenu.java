package free;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.border.Border;

public class FreeMenu extends JMenu {

    private Color backgroundColor = FreeUtil.MENUITEM_BACKGROUND;
    private Color foregroundColor = FreeUtil.DEFAULT_TEXT_COLOR;
    private int borderThickness = 1;
    private Border border = BorderFactory.createLineBorder(backgroundColor, borderThickness);
    private int preferredHeight = 25;

    public FreeMenu() {
        init();
    }

    public FreeMenu(String text) {
        super(text);
        init();
    }

    private void init() {
        this.setForeground(foregroundColor);
        this.setFont(FreeUtil.FONT_14_BOLD);
        this.setOpaque(true);
        this.setBackground(backgroundColor);
        this.setBorder(border);
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (this.isSelected()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(FreeUtil.MENUITEM_SELECTED_BACKGROUND);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            super.paintComponent(g);
        } else {
            super.paintComponent(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, preferredHeight);
    }
}
