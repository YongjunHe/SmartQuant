package smartUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

import twaver.TWaverUtil;

public class SmartUtil {
	
    public static final int DEFAULT_BUTTON_SIZE = 20;
    public static final Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
    public static final int LIST_SHRINKED_WIDTH = 37;
    public static final int OUTLOOK_SHRINKED_WIDTH = 37;
    public static final int DEFAULT_SPLIT_WIDTH = 4;
    public static final int TABLE_CELL_LEADING_SPACE = 5;
    public static final Color DEFAULT_SELECTION_COLOR = new Color(253, 192, 47);
    public static final Color BUTTON_ROVER_COLOR = new Color(196, 196, 197);
    public static final Color TABLE_HEADER_BACKGROUND_COLOR = new Color(239, 240, 241);
    public static final Color TABLE_HEADER_BORDER_BRIGHT_COLOR = Color.white;
    public static final Color TABLE_HEADER_BORDER_DARK_COLOR = new Color(215, 219, 223);
    public static final Color TABLE_ODD_ROW_COLOR = new Color(233, 231, 235);
    public static final Color TABLE_TEXT_COLOR = new Color(74, 74, 81);
    public static final Color NETWORK_BACKGROUND = new Color(226, 228, 229);
    public static final Color TAB_BOTTOM_LINE_COLOR = new Color(167, 173, 175);
    public static final Color OUTLOOK_TEXT_COLOR = new Color(120, 120, 125);
    public static final Color OUTLOOK_SPLIT_COLOR = new Color(174, 171, 162);
    public static final Color LIST_SPLIT_COLOR = new Color(105, 113, 120);
    public static final Color LIST_BACKGROUND = new Color(175, 174, 176);
    public static final Color LIST_TEXT_COLOR = new Color(49, 52, 58);
    public static final Color CONTENT_PANE_BACKGROUND = new Color(92, 153, 45);
    public static final Color MENUITEM_SELECTED_BACKGROUND = new Color(166, 188, 140);
    public static final Color MENUITEM_BACKGROUND = new Color(228, 235, 218);
    public static final Color DEFAULT_TEXT_COLOR = new Color(37, 81, 54);
    public static final Color NO_COLOR = new Color(0, 0, 0, 0);
    public static final Font TABLE_HEADER_FONT = new Font("����", Font.BOLD, 11);
    public static final Font TABLE_CELL_FONT = new Font("����", Font.PLAIN, 11);
    public static final Font FONT_14_BOLD = new Font("����", Font.BOLD, 14);
    public static final Font FONT_12_BOLD = new Font("����", Font.BOLD, 12);
    public static final Font FONT_14_PLAIN = new Font("����", Font.PLAIN, 14);
    public static final Font FONT_12_PLAIN = new Font("����", Font.PLAIN, 12);
    private static final String IMAGE_URL_PREFIX = "/images/";
    
    
    public static TexturePaint createTexturePaint(String imageURL) {
        return createTexturePaint(TWaverUtil.getImage(imageURL));
    }

    public static TexturePaint createTexturePaint(Image image) {
        int imageWidth = image.getWidth(null)	;
        int imageHeight = image.getHeight(null);
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return new TexturePaint(bi, new Rectangle(0, 0, imageWidth, imageHeight));
    }
    
    public static String getImageURL(String imageName) {
        return IMAGE_URL_PREFIX + imageName;
    }
	
    public static String getStringAttribute(org.w3c.dom.Node node, String name) {
        org.w3c.dom.Node attribute = node.getAttributes().getNamedItem(name);
        if (attribute != null) {
            return attribute.getNodeValue();
        } else {
            return null;
        }
    }

    public static Icon getIconAttribute(org.w3c.dom.Node node, String name) {
        String iconURL = getStringAttribute(node, name);
        if (iconURL != null && !iconURL.isEmpty()) {
            return TWaverUtil.getIcon(iconURL);
        }
        return null;
    }

    public static int getIntAttribute(org.w3c.dom.Node node, String name) {
        String value = getStringAttribute(node, name);
        if (value != null && !value.isEmpty()) {
            return Integer.valueOf(value);
        }
        return 0;
    }

}
