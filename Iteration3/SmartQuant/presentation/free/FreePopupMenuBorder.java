package free;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

public class FreePopupMenuBorder implements Border {

    private Color borderColor = FreeUtil.MENUITEM_SELECTED_BACKGROUND;
    private Color leftSider = new Color(214, 225, 200);
    private int lineThickness = 1;
    private int leftHeaderWidth = 7;
    private Insets insets = new Insets(lineThickness, lineThickness + leftHeaderWidth, lineThickness, lineThickness);

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        //draw the header bar.
        g.setColor(leftSider);
        g.fillRect(lineThickness, lineThickness, leftHeaderWidth, height);
        //draw border line.
        g.setColor(borderColor);
        g.drawRect(0, 0, width - lineThickness, height - lineThickness);
    }

    public Insets getBorderInsets(Component c) {
        return insets;
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
