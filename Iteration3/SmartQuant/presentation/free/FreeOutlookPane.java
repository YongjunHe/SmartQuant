package free;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

import twaver.swing.TableLayout;

public class FreeOutlookPane extends JPanel {

    private FreeOutlookHeader header = new FreeOutlookHeader() {

        @Override
        public void setShrink(boolean shrinked) {
            super.setShrink(shrinked);

            shrinkChanged(shrinked);
        }
    };
    private TableLayout barPaneLayout = new TableLayout();
    private JPanel barPane = new JPanel(barPaneLayout);
    private JPanel split = new JPanel();
    private int splitWidth = 1;
    private Color splitColor = new Color(166, 172, 174);
    private JPanel additionalPane = new JPanel(new BorderLayout());
    private Hashtable<Component, Integer> componentLayoutRows = new Hashtable();
    private JPanel centerPane = new JPanel(new BorderLayout());
    private FreeOutlookSplitListener splitListener = new FreeOutlookSplitListener(header);
    private Color additionalPaneBackground = new Color(217, 218, 219);


    public FreeOutlookPane() {
        init();
    }

    private void init() {
        //setup split
        split.setPreferredSize(new Dimension(splitWidth, 0));
        split.setOpaque(true);
        split.setBackground(splitColor);
        split.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        split.addMouseListener(this.splitListener);
        split.addMouseMotionListener(splitListener);

        //setup additional pane.
        additionalPane.setBackground(additionalPaneBackground);
        additionalPane.setPreferredSize(new Dimension(0, 0));
        additionalPane.setBorder(new Border() {

            //draw only top line.
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(FreeUtil.OUTLOOK_SPLIT_COLOR);
                g.drawLine(0, 0, width, 0);
            }

            public Insets getBorderInsets(Component c) {
                return new Insets(1, 0, 0, 0);
            }

            public boolean isBorderOpaque() {
                return true;
            }
        });

        //setup center pane.
        centerPane.add(barPane, BorderLayout.NORTH);
        centerPane.add(additionalPane, BorderLayout.CENTER);

        barPaneLayout.insertColumn(0, TableLayout.FILL);

        //setup myself.
        this.setLayout(new BorderLayout());
        this.add(header, BorderLayout.NORTH);
        this.add(centerPane, BorderLayout.CENTER);
        this.add(split, BorderLayout.EAST);
    }

    public FreeOutlookBar addBar(String title, Icon icon, Icon selectedIcon,MouseListener mouselistener) {
        FreeOutlookBar bar = new FreeOutlookBar(this,mouselistener);
        bar.setSelected(false);
        bar.setTitle(title);
        bar.setIcon(icon);
        bar.setSelectedIcon(selectedIcon);
        int rowCount = barPaneLayout.getRow().length;

        barPaneLayout.insertRow(rowCount, TableLayout.PREFERRED);
        barPane.add(bar, "0," + rowCount);
        componentLayoutRows.put(bar, rowCount);
        rowCount++;

        barPaneLayout.insertRow(rowCount, TableLayout.MINIMUM);
        barPane.add(bar.getContentComponent(), "0," + rowCount);
        componentLayoutRows.put(bar.getContentComponent(), rowCount);

        return bar;
    }

    public void updateLayoutConstraint(Component component, boolean selected) {
        int rowIndex = componentLayoutRows.get(component);
        double constraint = TableLayout.FILL;
        if (!selected) {
            constraint = TableLayout.MINIMUM;
        }
        barPaneLayout.setRow(rowIndex, constraint);
    }

    public JComponent getAdditionalPane() {
        return additionalPane;
    }

    public void setAdditionalPaneVisible(boolean visible) {
        centerPane.remove(barPane);
        centerPane.remove(additionalPane);
        if (visible) {
            centerPane.add(barPane, BorderLayout.NORTH);
            centerPane.add(additionalPane, BorderLayout.CENTER);
        } else {
            centerPane.add(barPane, BorderLayout.CENTER);
        }
    }

    public void closeAllBars() {
        for (int i = 0; i < barPane.getComponentCount(); i++) {
            Component c = barPane.getComponent(i);
            if (c instanceof FreeOutlookBar) {
                FreeOutlookBar bar = (FreeOutlookBar) c;
                if (bar.isSelected()) {
                    bar.setSelected(false);
                }
            }
        }
    }

    public FreeOutlookBar getSelectedBar() {
        for (int i = 0; i < barPane.getComponentCount(); i++) {
            Component c = barPane.getComponent(i);
            if (c instanceof FreeOutlookBar) {
                FreeOutlookBar bar = (FreeOutlookBar) c;
                if (bar.isSelected()) {
                    return bar;
                }
            }
        }
        return null;
    }

    public FreeOutlookBar getBarByNetwork(FreeNetwork network) {
        for (int i = 0; i < barPane.getComponentCount(); i++) {
            Component c = barPane.getComponent(i);
            if (c instanceof FreeOutlookBar) {
                FreeOutlookBar bar = (FreeOutlookBar) c;
                if (bar.getNetwork() == network) {
                    return bar;
                }
            }
        }
        return null;
    }

    public void setShrink(boolean shrinked) {
        this.header.setShrink(shrinked);
    }

    public boolean isShrinked() {
        return this.header.isShrinked();
    }

    private void shrinkChanged(boolean shrinked) {
        if (shrinked) {
            split.setCursor(Cursor.getDefaultCursor());
        } else {
            split.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        }

        //inform all bar list to update cell renderer.
        for (int i = 0; i < barPane.getComponentCount(); i++) {
            Component c = barPane.getComponent(i);
            if (c instanceof FreeOutlookBar) {
                FreeOutlookBar bar = (FreeOutlookBar) c;
                bar.headerShrinkChanged(shrinked);
                FreeOutlookList list = bar.getList();
                //fire this property change to cheat the UI to revalidate
                //and recalculate prefered size, so the cell renderer
                //will be displayed in center.
                list.firePropertyChange("layoutOrientation", true, false);
            }
        }
    }

    public FreeOutlookHeader getHeader() {
        return this.header;
    }


}
