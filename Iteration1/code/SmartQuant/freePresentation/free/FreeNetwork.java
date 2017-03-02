package free;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import twaver.DataBoxAdapter;
import twaver.DataBoxEvent;
import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.Element;
import twaver.Generator;
import twaver.MovableFilter;
import twaver.Node;
import twaver.SelectableFilter;
import twaver.TDataBox;
import twaver.TWaverConst;
import twaver.TWaverUtil;
import twaver.network.TNetwork;
import twaver.network.background.ColorBackground;

public class FreeNetwork extends TNetwork {

    private Color canvasColor = FreeUtil.NETWORK_BACKGROUND;
    private int shadowSize = 6;
    private float shadowOpacity = 0.3f;

    public FreeNetwork() {
        init();
    }

    public FreeNetwork(TDataBox box) {
        super(box);
        init();
    }

    private void init() {
        this.setToolbar(null);
        this.setBorder(null);
        this.getCanvasScrollPane().setBorder(null);
        this.setNetworkBackground(new ColorBackground(canvasColor));
        this.addMovableFilter(new MovableFilter() {

            public boolean isMovable(Element elmnt) {
                return false;
            }
        });
        //add selection listener, make sure only one element can be selected.
        getDataBox().getSelectionModel().addDataBoxSelectionListener(new DataBoxSelectionListener() {

            private boolean adjusting = false;

            public void selectionChanged(DataBoxSelectionEvent e) {
                if (adjusting) {
                    return;
                }
                if (getDataBox().getSelectionModel().size() > 1) {
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            if (getDataBox().getSelectionModel().size() > 1) {
                                adjusting = true;
                                getDataBox().getSelectionModel().setSelection(getDataBox().getSelectionModel().lastElement());
                                adjusting = false;
                            }
                        }
                    });
                }
            }
        });
        this.addSelectableFilter(new SelectableFilter() {

            public boolean isSelectable(Element element) {
                return element instanceof FreeNode;
            }
        });
        //add this listener, when node added, create shadow image for it.
        this.getDataBox().addDataBoxListener(new DataBoxAdapter() {

            @Override
            public void elementAdded(DataBoxEvent e) {
                Element element = e.getElement();
                if (element instanceof Node) {
                    Node node = (Node) element;
                    updateShadow(node);

                    if (element instanceof FreeNode) {
                        FreeNode freeNode = (FreeNode) element;
                        FreeNodeButtonAttachment attachment = freeNode.getButtonAttachment();
                        if (attachment != null) {
                            getCanvas().add(attachment);
                            attachment.updateBounds();
                        }
                    }
                }
            }

            @Override
            public void elementRemoved(DataBoxEvent e) {
                Element element = e.getElement();
                if (element instanceof FreeNode) {
                    FreeNode freeNode = (FreeNode) element;
                    JComponent attachment = freeNode.getButtonAttachment();
                    if (attachment != null) {
                        getCanvas().remove(attachment);
                    }
                }
            }
        });

        //if location changed, move attachment as well.
        this.getDataBox().addElementPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                Object source = evt.getSource();
                if (source instanceof FreeNode) {
                    if (TWaverUtil.getPropertyName(evt).equalsIgnoreCase(TWaverConst.PROPERTYNAME_LOCATION)) {
                        FreeNode node = (FreeNode) source;
                        FreeNodeButtonAttachment attachment = node.getButtonAttachment();
                        attachment.updateBounds();
                    }
                }
            }
        });

        this.addCanvasCushion(new FreeNetworkShadowCushion(this));

        //display label on network with node's network name.
        this.setElementLabelGenerator(new Generator() {

            public Object generate(Object o) {
                if (o instanceof FreeNode) {
                    FreeNode node = (FreeNode) o;
                    return node.getNetworkName();
                } else {
                    return ((Element) o).getName();
                }
            }
        });

        //when node selected, perform node action listener.
        this.getDataBox().getSelectionModel().addDataBoxSelectionListener(new DataBoxSelectionListener() {

            public void selectionChanged(DataBoxSelectionEvent e) {
                if (e.getBoxSelectionModel().size() == 1) {
                    Element element = e.getBoxSelectionModel().lastElement();
                    if (element instanceof FreeNode) {
                        FreeNode node = (FreeNode) element;
                        ActionListener nodeAction = (ActionListener) node.getAction();
                        if (nodeAction != null) {
                            ActionEvent event = new ActionEvent(FreeNetwork.this, 0, null);
                            nodeAction.actionPerformed(event);
                        }
                    }
                }
            }
        });
    }

    private void updateShadow(Node node) {
        if (node != null) {
            ImageIcon imageIcon = node.getImage();
            if (imageIcon != null) {
                String urlString = node.getImageURL();
                URL url = getClass().getResource(urlString);
                try {
                    BufferedImage imageSource = GraphicsUtilities.loadCompatibleImage(url);
                    BufferedImage imageDest = FreeUtil.createDropShadow(imageSource, shadowSize);

                    BufferedImage imageShadow = new BufferedImage(imageDest.getWidth(),
                            imageDest.getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = imageShadow.createGraphics();
                    g2d.setComposite(AlphaComposite.SrcOver.derive(shadowOpacity));
                    g2d.drawImage(imageDest, 0, 0, null);
                    g2d.dispose();

                    FreeUtil.setNodeShadowImage(node, imageShadow);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getShadowSize() {
        return shadowSize;
    }
}
