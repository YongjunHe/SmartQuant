package free;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class FreeListPane extends JPanel {

    private FreeList list = new FreeList();
    private JPanel split = new JPanel(new BorderLayout());
    private FreeHeader header = new FreeHeader() {

        @Override
        public void setShrink(boolean shrinked) {
            super.setShrink(shrinked);

            if (shrinked) {
                split.setCursor(Cursor.getDefaultCursor());
            } else {
                split.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
            }
        }
    };
    private FreeListSplitListener splitListener = new FreeListSplitListener(header);

    public FreeListPane() {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());

        JPanel rightInsetPane = new JPanel();
        rightInsetPane.setPreferredSize(new Dimension(FreeUtil.DEFAULT_SPLIT_WIDTH - 2, 0));
        rightInsetPane.setBackground(FreeUtil.LIST_BACKGROUND);
        this.add(rightInsetPane, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        split.setBorder(new Border() {

            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(FreeUtil.LIST_SPLIT_COLOR);
                g.drawLine(x, y, x, y + height);
            }

            public Insets getBorderInsets(Component c) {
                return new Insets(0, 1, 0, 0);
            }

            public boolean isBorderOpaque() {
                return true;
            }
        });
        split.setOpaque(true);
        split.setPreferredSize(new Dimension(FreeUtil.DEFAULT_SPLIT_WIDTH, 0));
        split.setBackground(FreeUtil.LIST_BACKGROUND);
        split.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        split.addMouseListener(this.splitListener);
        split.addMouseMotionListener(splitListener);

        this.add(split, BorderLayout.WEST);
        this.add(list, BorderLayout.CENTER);
    }

    public FreeList getList() {
        return list;
    }

    public void setTitle(String title) {
        header.setTitle(title);
    }

    public String getTitle() {
        return header.getTitle();
    }

    public void setShrink(boolean shrinked) {
        this.header.setShrink(shrinked);
    }

    public boolean isShrinked() {
        return this.header.isShrinked();
    }
}
