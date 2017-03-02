package free;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import twaver.Element;
import twaver.Group;
import twaver.list.TList;

public class FreeList extends TList {

    public FreeList() {
        init();
    }

    private void init() {
        this.setFont(FreeUtil.FONT_12_BOLD);
        this.setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
        this.setBackground(FreeUtil.LIST_BACKGROUND);
        this.setCellRenderer(new FreeListRenderer(this));
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //move mouse, select over item.
        MouseInputListener mouseListener = new MouseInputAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                Element element = getElementByPoint(e.getPoint());
                getDataBox().getSelectionModel().clearSelection();
                if (element != null) {
                    //ignore group.
                    if (!(element instanceof Group)) {
                        element.setSelected(true);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                getDataBox().getSelectionModel().clearSelection();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Element element = getDataBox().getSelectionModel().lastElement();
                if (element != null) {
                    if (!(element instanceof Group)) {
                        ActionListener action = (ActionListener) element.getUserObject();
                        String command = (String) element.getBusinessObject();

                        if (action != null) {
                            ActionEvent event = new ActionEvent(element, 0, command);
                            action.actionPerformed(event);
                        }
                    }
                }
            }
        };
        this.addMouseMotionListener(mouseListener);
        this.addMouseListener(mouseListener);
    }
}
