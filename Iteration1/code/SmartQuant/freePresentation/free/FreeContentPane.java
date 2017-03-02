package free;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class FreeContentPane extends JPanel {

    public FreeContentPane() {
        this.setLayout(new BorderLayout());
        setBackground(FreeUtil.CONTENT_PANE_BACKGROUND);
    }
}
