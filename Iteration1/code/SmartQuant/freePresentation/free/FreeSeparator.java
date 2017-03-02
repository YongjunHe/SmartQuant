package free;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import javax.swing.JSeparator;

public class FreeSeparator extends JSeparator {

    private String imageName = "separator_background.png";
    private String backgroundImageURL = FreeUtil.getImageURL(imageName);
    private Image image = FreeUtil.getImage(imageName);
    private TexturePaint paint = FreeUtil.createTexturePaint(backgroundImageURL);

    public FreeSeparator() {
    }

    public FreeSeparator(int direction) {
        super(direction);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(paint);
        int x = 0;
        int y = 0;
        int width = this.getWidth();
        int height = image.getHeight(null);
        g2d.fillRect(x, y, width, height);
    }
}
