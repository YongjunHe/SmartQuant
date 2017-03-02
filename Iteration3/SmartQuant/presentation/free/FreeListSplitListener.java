package free;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

public class FreeListSplitListener extends MouseInputAdapter {

    protected Point lastPoint = null;
    protected FreeHeader header = null;

    public FreeListSplitListener(FreeHeader header) {
        this.header = header;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!header.isShrinked()) {
            lastPoint = e.getPoint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        lastPoint = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!header.isShrinked()) {
            if (lastPoint != null) {
                JComponent parent = (JComponent) header.getParent();
                Dimension size = parent.getPreferredSize();
                Point thisPoint = e.getPoint();
                int xMovement = thisPoint.x - lastPoint.x;
                size.width -= xMovement;
                size.width = Math.max(size.width, FreeUtil.LIST_SHRINKED_WIDTH);
                parent.setPreferredSize(size);
                header.revalidateParent();
            }
        }
    }
}
