package free;

import javax.swing.ListSelectionModel;
import twaver.Element;
import twaver.TDataBox;
import twaver.VisibleFilter;
import twaver.list.TList;

public class FreeOutlookList extends TList {

    private FreeOutlookBar bar = null;

    public FreeOutlookList(FreeOutlookBar bar, TDataBox box) {
        super(box);
        this.bar = bar;
        init();
    }

    private void init() {
        this.setCellRenderer(new FreeOutlookListRenderer(this));
        this.setFont(FreeUtil.FONT_12_BOLD);
        this.setForeground(FreeUtil.OUTLOOK_TEXT_COLOR);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.addVisibleFilter(new VisibleFilter() {

            public boolean isVisible(Element element) {
                return element instanceof FreeNode;
            }
        });
    }

    public FreeOutlookBar getFreeOutlookBar() {
        return bar;
    }
}
