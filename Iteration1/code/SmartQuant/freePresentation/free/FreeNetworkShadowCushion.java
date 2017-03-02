package free;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Iterator;
import twaver.Node;
import twaver.network.CanvasCushion;

public class FreeNetworkShadowCushion implements CanvasCushion {

    private FreeNetwork network = null;

    public FreeNetworkShadowCushion(FreeNetwork network) {
        this.network = network;
    }

    public void paint(Graphics2D g2d) {
        Iterator it = network.getDataBox().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Node) {
                Node node = (Node) o;

                //paint shadow first.
                Image shadowImage = FreeUtil.getNodeShadowImage(node);
                if (shadowImage != null) {
                    int shadowSize = ((FreeNetwork) network).getShadowSize();
                    int offset = 5;
                    int x = node.getLocation().x - shadowSize * 2 + offset;
                    int y = node.getLocation().y - shadowSize * 2 + offset;
                    g2d.drawImage(shadowImage, x, y, null);
                }
            }
        }
    }
}
