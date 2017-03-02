package free;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalTextFieldUI;
import twaver.TWaverUtil;

public class FreeTextField extends JTextField {

    private String backgroundImageURL = FreeUtil.getImageURL("textfield_background.png");
    private Image backgroundLeftImage = FreeUtil.getImage("textfield_background_left.png");
    private Image backgroundRightImage = FreeUtil.getImage("textfield_background_right.png");
    private ImageIcon backgroundImageIcon = TWaverUtil.getImageIcon(backgroundImageURL);
    private TexturePaint paint = FreeUtil.createTexturePaint(backgroundImageURL);
    private Border border = BorderFactory.createEmptyBorder(1, 3, 1, 3);
    private Font font = FreeUtil.FONT_12_PLAIN;

    public FreeTextField() {
        this(null);
    }

    public FreeTextField(String text) {
        super(text);
        init();
    }

    private void init() {
        this.setBorder(border);
        this.setUI(new MetalTextFieldUI() {

            @Override
            protected void paintBackground(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(paint);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                //paint left side image.
                g2d.drawImage(backgroundLeftImage, 0, 0, null);

                //paint right side image.
                g2d.drawImage(backgroundRightImage, getWidth() - backgroundRightImage.getWidth(null), 0, null);
            }
        });
        this.setFont(font);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, backgroundImageIcon.getIconHeight());
    }
}
