package free;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.PixelGrabber;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import twaver.Group;
import twaver.Node;
import twaver.PixelFilter;
import twaver.TDataBox;
import twaver.TWaverConst;
import twaver.TWaverUtil;

public class FreeUtil {

    public static final int DEFAULT_ICON_SIZE = 16;
    public static Icon BLANK_ICON = new Icon() {

        public void paintIcon(Component c, Graphics g, int x, int y) {
            //nothing.
        }

        public int getIconWidth() {
            return DEFAULT_ICON_SIZE;
        }

        public int getIconHeight() {
            return DEFAULT_ICON_SIZE;
        }
    };
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
    public static final Font TABLE_HEADER_FONT = new Font("黑体", Font.BOLD, 11);
    public static final Font TABLE_CELL_FONT = new Font("黑体", Font.PLAIN, 11);
    public static final Font FONT_14_BOLD = new Font("宋体", Font.BOLD, 14);
    public static final Font FONT_12_BOLD = new Font("宋体", Font.BOLD, 12);
    public static final Font FONT_14_PLAIN = new Font("宋体", Font.PLAIN, 14);
    public static final Font FONT_12_PLAIN = new Font("宋体", Font.PLAIN, 12);
    private static final String IMAGE_URL_PREFIX = "/free/images/";

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

    public static Image getImage(String imageName) {
        return TWaverUtil.getImage(getImageURL(imageName));
    }

    public static ImageIcon getImageIcon(String imageName) {
        return TWaverUtil.getImageIcon(getImageURL(imageName));
    }

    public static BufferedImage createDropShadow(BufferedImage image, int size) {
        BufferedImage shadow = new BufferedImage(
                image.getWidth() + 4 * size,
                image.getHeight() + 4 * size,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = shadow.createGraphics();
        g2.drawImage(image, size * 2, size * 2, null);

        g2.setComposite(AlphaComposite.SrcIn);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, shadow.getWidth(), shadow.getHeight());

        g2.dispose();

        shadow = getGaussianBlurFilter(size, true).filter(shadow, null);
        shadow = getGaussianBlurFilter(size, false).filter(shadow, null);

        return shadow;
    }

    public static ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        int size = radius * 2 + 1;
        float[] data = new float[size];

        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        Kernel kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

    public static Image getNodeShadowImage(Node node) {
        Object value = node.getUserProperty("shadow");
        if (value instanceof Image) {
            return (Image) value;
        }
        return null;
    }

    public static void setNodeShadowImage(Node node, Image shadowImage) {
        node.putUserProperty("shadow", shadowImage);
    }

    public static FreeMenuBar loadMenuBar(String xml, ActionListener action) {
        FreeMenuBar menuBar = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(FreeUtil.class.getResource(xml).openStream());

            Element root = doc.getDocumentElement();
            NodeList rootMenus = root.getChildNodes();
            if (rootMenus != null) {
                menuBar = new FreeMenuBar();
                for (int i = 0; i < rootMenus.getLength(); i++) {
                    org.w3c.dom.Node menu = rootMenus.item(i);
                    if (menu.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        if (menu.getNodeName().equalsIgnoreCase("menu")) {
                            String text = getStringAttribute(menu, "text");
                            FreeRootMenu rootMenu = new FreeRootMenu();
                            rootMenu.setText(text);
                            menuBar.add(rootMenu);

                            processMenuItem(menu, rootMenu, action);
                        }
                        if (menu.getNodeName().equalsIgnoreCase("logo")) {
                            String tooltip = getStringAttribute(menu, "tooltip");
                            String imageURL = getStringAttribute(menu, "image");

                            //add company logo here
                            menuBar.add(Box.createGlue());
                            JLabel label = new JLabel(TWaverUtil.getImageIcon(imageURL));
                            label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                            label.setToolTipText(tooltip);
                            menuBar.add(label);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return menuBar;
    }

    private static void processMenuItem(org.w3c.dom.Node menu, JMenuItem parentMenu, ActionListener action) {
        //menu items of a menu.
        NodeList children = menu.getChildNodes();
        if (children != null) {
            for (int j = 0; j < children.getLength(); j++) {
                org.w3c.dom.Node itemNode = children.item(j);
                if (itemNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    boolean isMenuItem = itemNode.getNodeName().equalsIgnoreCase("menuitem");
                    boolean isMenu = itemNode.getNodeName().equalsIgnoreCase("menu");
                    if (isMenuItem || isMenu) {
                        String text = getStringAttribute(itemNode, "text");
                        String tooltip = getStringAttribute(itemNode, "tooltip");
                        Icon icon = getIconAttribute(itemNode, "icon");
                        String command = getStringAttribute(itemNode, "action");

                        JMenuItem menuItem = null;

                        if (isMenu) {
                            menuItem = new FreeMenu();
                        } else {
                            menuItem = new FreeMenuItem();
                            menuItem.addActionListener(action);
                        }
                        menuItem.setText(text);
                        menuItem.setToolTipText(tooltip);
                        menuItem.setActionCommand(command);
                        menuItem.setIcon(icon);
                        parentMenu.add(menuItem);

                        if (isMenu) {
                            processMenuItem(itemNode, menuItem, action);
                        }
                    }

                }
            }
        }
    }

    private static String getStringAttribute(org.w3c.dom.Node node, String name) {
        org.w3c.dom.Node attribute = node.getAttributes().getNamedItem(name);
        if (attribute != null) {
            return attribute.getNodeValue();
        } else {
            return null;
        }
    }

    private static Icon getIconAttribute(org.w3c.dom.Node node, String name) {
        String iconURL = getStringAttribute(node, name);
        if (iconURL != null && !iconURL.isEmpty()) {
            return TWaverUtil.getIcon(iconURL);
        }
        return null;
    }

    private static int getIntAttribute(org.w3c.dom.Node node, String name) {
        String value = getStringAttribute(node, name);
        if (value != null && !value.isEmpty()) {
            return Integer.valueOf(value);
        }
        return 0;
    }

    public static void loadOutlookToolBar(String xml, FreeOutlookHeader header, ActionListener action) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(FreeUtil.class.getResource(xml).openStream());

            Element root = doc.getDocumentElement();
            NodeList buttons = root.getChildNodes();
            if (buttons != null) {
                for (int i = 0; i < buttons.getLength(); i++) {
                    org.w3c.dom.Node buttonNode = buttons.item(i);
                    if (buttonNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        if (buttonNode.getNodeName().equalsIgnoreCase("button")) {
                            String tooltip = getStringAttribute(buttonNode, "tooltip");
                            Icon icon = getIconAttribute(buttonNode, "icon");
                            String command = getStringAttribute(buttonNode, "action");
                            header.addButton(icon, tooltip, action, command);
                        }
                        if (buttonNode.getNodeName().equalsIgnoreCase("separator")) {
                            header.addSeparator();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void loadOutlookPane(String xml,
            FreeOutlookPane outlookPane,
            MouseListener barAction,
            ActionListener shortcutAction) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(FreeUtil.class.getResource(xml).openStream());

            Element root = doc.getDocumentElement();
            NodeList modules = root.getChildNodes();
            if (modules != null) {
                for (int i = 0; i < modules.getLength(); i++) {
                    org.w3c.dom.Node moduleNode = modules.item(i);
                    if (moduleNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        if (moduleNode.getNodeName().equalsIgnoreCase("module")) {
                            String text = getStringAttribute(moduleNode, "text");
                            Icon icon = getIconAttribute(moduleNode, "icon");
                            Icon iconSelected = getIconAttribute(moduleNode, "selected_icon");
                            String networkXml = getStringAttribute(moduleNode, "network");
                            FreeOutlookBar bar = outlookPane.addBar(text, icon, iconSelected,barAction);
                            
                            
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



  
    public static FreePagePane getPagePane(Component component) {
        if (component != null) {
            if (component.getParent() != null) {
                Component parent = component.getParent();
                if (parent instanceof FreePagePane) {
                    return (FreePagePane) parent;
                } else {
                    return getPagePane(parent);
                }
            }
        }
        return null;
    }

    public static Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }

    public static ImageIcon createDyedIcon(final ImageIcon icon, Color color) {
        return createDyedIcon(icon, color, false);
    }

    public static ImageIcon createDyedIcon(final ImageIcon icon, Color color, boolean useTWaverFilter) {
        if (color == null) {
            return icon;
        } else {
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            BufferedImage bi = new BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bi.createGraphics();
            icon.paintIcon(null, g2d, 0, 0);
            g2d.dispose();
            final Image dyedImage = createDyedImage(bi, color, useTWaverFilter);
            return new ImageIcon(dyedImage);
        }
    }

    public static Image createDyedImage(Image image, Color color, boolean useTWaverFilter) {
        if (color == null) {
            return image;
        } else {
            if (image != null) {
                int w = image.getWidth(null);
                int h = image.getHeight(null);

                int[] pixels = new int[w * h];
                PixelGrabber pg = new PixelGrabber(image, 0, 0, w, h, pixels, 0, w);
                try {
                    pg.grabPixels();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    return null;
                }

                BufferedImage bi = new BufferedImage(w > 1 ? w : 1,
                        h > 1 ? h : 1,
                        BufferedImage.TYPE_INT_ARGB);
                PixelFilter pixelFilter = TWaverUtil.getPixelFilter();
                for (int i = 0; i < pixels.length; i++) {
                    int pixel = pixels[i];
                    int row = i / w;
                    int col = i % w;
                    if (color != null) {
                        if (pixel != 0) {
                            if (useTWaverFilter) {
                                pixel = pixelFilter.filter(pixel, color);
                            } else {
                                pixel = color.getRGB();
                            }
                        }
                    }
                    bi.setRGB(col, row, pixel);
                }

                return bi;
            } else {
                return null;
            }
        }
    }

    public static Icon createMovedIcon(Icon icon) {
        return createMovedIcon(icon, 1, 1);
    }

    public static Icon createMovedIcon(final Icon icon, final int offsetX, final int offsetY) {
        return new Icon() {

            public void paintIcon(Component c, Graphics g, int x, int y) {
                icon.paintIcon(c, g, x + offsetX, y + offsetY);
            }

            public int getIconWidth() {
                return icon.getIconWidth();
            }

            public int getIconHeight() {
                return icon.getIconHeight();
            }
        };
    }

    public static void setupLookAndFeel() {
        Locale.setDefault(TWaverConst.EN_US);
        TWaverUtil.setLocale(TWaverConst.EN_US);
        PlasticTheme theme = new com.jgoodies.looks.plastic.theme.ExperienceGreen() {

            @Override
            public FontUIResource getControlTextFont() {
                return new FontUIResource(new Font("宋体", Font.PLAIN, 11));
            }
        };
        PlasticLookAndFeel.setPlasticTheme(theme);
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //use my tab ui.
        UIManager.getDefaults().put("TabbedPaneUI", FreeTabbedPaneUI.class.getName());
        UIManager.put("Menu.selectionBackground", FreeUtil.NO_COLOR);
        UIManager.put("MenuItem.selectionBackground", FreeUtil.MENUITEM_SELECTED_BACKGROUND);
        UIManager.put("PopupMenu.border", new FreePopupMenuBorder());
        UIManager.put("ToolTip.font", FreeUtil.FONT_14_BOLD);
        UIManager.put("TabbedPane.contentBorderInsets", FreeUtil.ZERO_INSETS);
        UIManager.put("TabbedPane.tabInsets", FreeUtil.ZERO_INSETS);
        UIManager.put("TabbedPane.selectedTabPadInsets", FreeUtil.ZERO_INSETS);
        UIManager.put("TabbedPane.tabAreaInsets", FreeUtil.ZERO_INSETS);
    }
}
