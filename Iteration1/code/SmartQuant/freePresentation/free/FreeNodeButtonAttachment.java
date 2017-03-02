package free;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import twaver.TWaverUtil;

public class FreeNodeButtonAttachment extends JPanel {

    private FreeToolbarButton button1 = createButton();
//    private FreeToolbarButton button2 = createButton();
//    private FreeToolbarButton button3 = createButton();
    private FreeNode node = null;

    public FreeNodeButtonAttachment(FreeNode node) {
        this.node = node;
        init();
    }

    private void init() {
        this.setLayout(new GridLayout(3, 1, 0, 12));
        this.setOpaque(false);
        this.add(button1);
//        this.add(button2);
//        this.add(button3);

        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ActionListener listener = node.getButtonAction1();
                if (listener != null) {
                    ActionEvent event = createActionEvent(e, node.getActionCommand1());
                    listener.actionPerformed(event);
                }
            }
        });
//        button2.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                ActionListener listener = node.getButtonAction2();
//                if (listener != null) {
//                    ActionEvent event = createActionEvent(e, node.getActionCommand2());
//                    listener.actionPerformed(event);
//                }
//            }
//        });
//        button3.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                ActionListener listener = node.getButtonAction3();
//                if (listener != null) {
//                    ActionEvent event = createActionEvent(e, node.getActionCommand3());
//                    listener.actionPerformed(event);
//                }
//            }
//        });

        MouseListener mouseListener = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                node.setSelected(true);
            }
        };
        this.addMouseListener(mouseListener);
        this.button1.addMouseListener(mouseListener);
//        this.button2.addMouseListener(mouseListener);
//        this.button3.addMouseListener(mouseListener);
    }

    private FreeToolbarButton createButton() {
        FreeToolbarButton button = new FreeToolbarButton();
        button.setOpaque(false);
        return button;
    }

    public void updateBounds() {
        int width = this.getPreferredSize().width;
        int height = this.getPreferredSize().height;
        int x = node.getLocation().x + node.getWidth() - width - 3;
        int y = node.getLocation().y + 6;
        this.setBounds(x, y, width, height);
    }

    public void updateProperties() {
        button1.setIcon(TWaverUtil.getImageIcon(node.getButtonIcon1()));
        button1.setToolTipText(node.getButtonTooltip1());
//        button2.setIcon(TWaverUtil.getImageIcon(node.getButtonIcon2()));
//        button2.setToolTipText(node.getButtonTooltip2());
//        button3.setIcon(TWaverUtil.getImageIcon(node.getButtonIcon3()));
//        button3.setToolTipText(node.getButtonTooltip3());
    }

    private ActionEvent createActionEvent(ActionEvent e, String command) {
        return new ActionEvent(e.getSource(),
                e.getID(),
                command,
                e.getWhen(),
                e.getModifiers());
    }
}
