package free;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import twaver.TWaverUtil;
import twaver.network.ui.NodeUI;

public class FreeNodeUI extends NodeUI {

    private int imageContentWidth = 100;
    private int moduleIconY = 12;
    private Image selectedImage = FreeUtil.getImage("module_node_selected.png");

    public FreeNodeUI(FreeNetwork network, FreeNode node) {
        super(network, node);
    }

    @Override
    public void paintBody(Graphics2D g2d) {
        super.paintBody(g2d);

        FreeNode node = (FreeNode) this.getNode();
        if (node.isSelected()) {
            g2d.drawImage(selectedImage, node.getLocation().x, node.getLocation().y, null);
        }

        Rectangle bounds = element.getBounds();
        if (node.getModuleIcon() != null) {
            Image image = TWaverUtil.getImage(node.getModuleIcon());
            if (image != null) {
                int x = bounds.x + (imageContentWidth - image.getWidth(null)) / 2;
                int y = bounds.y + moduleIconY;
                g2d.drawImage(image, x, y, null);
            }
        }
    }
}
