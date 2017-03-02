package free;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class FreeSearchTextField extends FreeTextField {

    private ImageIcon icon = FreeUtil.getImageIcon("textfield_search.png");
    private FreeToolbarRoverButton button = new FreeToolbarRoverButton(icon);
    private int buttonVGap = 2;
    private int buttonHGap = 4;
    private LayoutManager layout = new LayoutManager() {

        public void addLayoutComponent(String name, Component comp) {
        }

        public void removeLayoutComponent(Component comp) {
        }

        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }

        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                Rectangle bound = getBounds();
                int x = bound.width - icon.getIconWidth() - buttonHGap * 2;
                int y = buttonVGap;
                int width = icon.getIconWidth() + buttonHGap * 2;
                int height = icon.getIconHeight() + buttonVGap * 2;
                button.setBounds(x, y, width, height);
            }
        }
    };
    private Border border = BorderFactory.createEmptyBorder(1, 3, 1, icon.getIconWidth() + buttonHGap * 2);

    public FreeSearchTextField() {
    	
        init();
    }

    private void init() {
        this.setBorder(border);
        this.setLayout(layout);
        this.add(button);
        button.setCursor(Cursor.getDefaultCursor());
        button.setRoverBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        button.setFocusable(false);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fireActionPerformed();
            }
        });
    }
}
