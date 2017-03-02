package smartUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import twaver.TWaverUtil;
import free.FreeUtil;

public class SmartToolBar extends JPanel{
    private String backgroundImageURL = SmartUtil.getImageURL("toolbar_background.png");
    private int preferredHeight = TWaverUtil.getImageIcon(backgroundImageURL).getIconHeight();
    private TexturePaint paint = SmartUtil.createTexturePaint(backgroundImageURL);
    private int buttonGap = 2;

    public SmartToolBar() {
        init();
    }

    private void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEADING, buttonGap, 0));
        this.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
//        g2d.setPaint(paint);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, preferredHeight);
    }
}
