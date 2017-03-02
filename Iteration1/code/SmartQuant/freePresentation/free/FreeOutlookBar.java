package free;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TexturePaint;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.DataBoxSelectionModel;
import twaver.Element;
import twaver.TDataBox;
import twaver.TWaverUtil;

public class FreeOutlookBar extends JPanel{

    private String backgroundImageURL = FreeUtil.getImageURL("outlook_bar_background.png");
    private Image backgroundSelectedLeft = FreeUtil.getImage("outlook_bar_background_selected_left.png");
    private Image backgroundSelectedRight = FreeUtil.getImage("outlook_bar_background_selected_right.png");
    private String backgroundSelectedImageURL = FreeUtil.getImageURL("outlook_bar_background_selected.png");
    private Image backgroundImage = TWaverUtil.getImage(backgroundImageURL);
    private ImageIcon handlerIcon = FreeUtil.getImageIcon("outlook_bar_handler.png");
    private ImageIcon handlerSelectedIcon = FreeUtil.getImageIcon("outlook_bar_handler_selected.png");
    private TexturePaint paint = FreeUtil.createTexturePaint(backgroundImageURL);
    private TexturePaint selectedPaint = FreeUtil.createTexturePaint(backgroundSelectedImageURL);
    private JLabel lbHandler = new JLabel();
    private Border handlerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 16);
    private Border handlerShrinkedBorder = BorderFactory.createEmptyBorder(0, 0, 0, 22);
    private JLabel lbIcon = new JLabel();
    private JLabel lbTitle = new JLabel();
    private boolean selected = false;
    private Color titleColor = FreeUtil.OUTLOOK_TEXT_COLOR;
    private Color selectedTitleColor = Color.white;
    private MouseListener mouseListener = new MouseAdapter() {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (((JComponent) e.getSource()).contains(e.getPoint())) {
                changeStatus();
            }
        }
    };
    private FreeOutlookPane pane = null;
    private TDataBox box = new TDataBox();
    private FreeOutlookList list = new FreeOutlookList(this, box);
    private FreeNetwork network = new FreeNetwork(box);
    private JScrollPane scroll = new JScrollPane(list);
    private Color scrollBorderColor = new Color(233, 223, 207);
    private Border scrollBorder = new Border() {

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(scrollBorderColor);
            g.drawLine(0, height, x, height);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 1, 0);
        }

        public boolean isBorderOpaque() {
            return true;
        }
    };
    private Icon icon = null;
    private Icon selectedIcon = null;
    private MouseListener changeListener=null;

    public FreeOutlookBar(FreeOutlookPane pane,MouseListener changeListener) {
        this.pane = pane;
        this.changeListener=changeListener;
        init();
    }

    public JComponent getContentComponent() {
        return scroll;
    }

    private void init() {
        this.setLayout(new BorderLayout());
        lbHandler.setVerticalAlignment(SwingConstants.CENTER);
        lbHandler.setIcon(handlerIcon);
        lbHandler.setBorder(handlerBorder);
        this.add(lbHandler, BorderLayout.EAST);

        lbIcon.setVerticalAlignment(SwingConstants.CENTER);
        lbIcon.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        this.add(lbIcon, BorderLayout.WEST);

        lbTitle.setVerticalAlignment(SwingConstants.CENTER);
        lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        lbTitle.setFont(FreeUtil.FONT_14_BOLD);
        lbTitle.setForeground(titleColor);
        this.add(lbTitle, BorderLayout.CENTER);

        //this.lbHandler.addMouseListener(mouseListener);
        this.lbTitle.addMouseListener(mouseListener);
        //this.lbIcon.addMouseListener(mouseListener);
        
       // this.lbHandler.addMouseListener(changeListener);
        this.lbTitle.addMouseListener(changeListener);
       // this.lbIcon.addMouseListener(changeListener);

        scroll.setMinimumSize(new Dimension(0, 0));
        scroll.setBorder(scrollBorder);

        //add selection listener on box. when one node selected, fresh shortcuts.
        box.getSelectionModel().addDataBoxSelectionListener(new DataBoxSelectionListener() {

            public void selectionChanged(DataBoxSelectionEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        if (this.isSelected()) {
            g2d.setPaint(selectedPaint);
            if (this.selectedIcon != null) {
                lbIcon.setIcon(this.selectedIcon);
            } else {
                lbIcon.setIcon(this.getIcon());
            }
        } else {
            g2d.setPaint(paint);
            lbIcon.setIcon(this.getIcon());
        }
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (this.isSelected()) {
            //paint left side image.
            g2d.drawImage(backgroundSelectedLeft, 0, 0, null);

            //paint right side image.
            g2d.drawImage(backgroundSelectedRight, this.getWidth() - backgroundSelectedRight.getWidth(null), 0, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, backgroundImage.getHeight(null));
    }

    public void setSelected(boolean selected) {
        if (selected != this.selected) {
            if (!isSelected()) {
                pane.closeAllBars();
            }
            this.selected = selected;
            if (selected) {
                lbHandler.setIcon(handlerSelectedIcon);
                lbTitle.setForeground(selectedTitleColor);
            } else {
                lbHandler.setIcon(handlerIcon);
                lbTitle.setForeground(titleColor);
            }
//            pane.updateLayoutConstraint(this.getContentComponent(), selected);
//            pane.setAdditionalPaneVisible(!selected);
//            pane.revalidate();
        }
    }

    public void changeStatus() {
        this.setSelected(!this.isSelected());
        
    }

    public boolean isSelected() {
        return selected;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
        createAndUpdateIcon();
    }

    public Icon getSelectedIcon() {
        return this.selectedIcon;
    }

    public void setSelectedIcon(Icon selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    private void createAndUpdateIcon() {
        if (selectedIcon == null) {
            //recreate the selected with a white color dyed one.
            Color dyeColor = Color.white;
            ImageIcon imageIcon = new ImageIcon(FreeUtil.iconToImage(icon));
            selectedIcon = FreeUtil.createDyedIcon(imageIcon, dyeColor, false);
        }
        if (this.selected) {
            lbIcon.setIcon(selectedIcon);
        } else {
            lbIcon.setIcon(icon);
        }
    }

    public void setTitle(String title) {
        this.lbTitle.setText(title);
        this.lbTitle.setToolTipText(title);
        this.lbHandler.setToolTipText(title);
        this.lbIcon.setToolTipText(title);
    }

    public String getTitle() {
        return this.lbTitle.getText();
    }

    public FreeOutlookPane getFreeOutlookPane() {
        return pane;
    }

    public FreeOutlookList getList() {
        return list;
    }

    public void headerShrinkChanged(boolean headShrinked) {
        if (headShrinked) {
            this.lbHandler.setBorder(handlerShrinkedBorder);
        } else {
            this.lbHandler.setBorder(handlerBorder);
        }
    }

    public FreeNetwork getNetwork() {
        return network;
    }
}
