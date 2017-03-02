package free;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class FreePagePane extends JPanel {

    private FreeToolBar leftToolbar = new FreeToolBar();
    private FreeToolBar rightToolbar = new FreeToolBar();
    private JPanel toolbarPane = new JPanel(new BorderLayout());
    private JPanel centerPane = new JPanel(new BorderLayout());

    public FreePagePane() {
        this(null);
    }

    public FreePagePane(JComponent contentComponent) {
        init(contentComponent);
    }

    private void init(JComponent contentComponent) {
        this.setLayout(new BorderLayout());

        toolbarPane.add(leftToolbar, BorderLayout.CENTER);
        toolbarPane.add(rightToolbar, BorderLayout.EAST);
        this.add(toolbarPane, BorderLayout.NORTH);
        this.add(centerPane, BorderLayout.CENTER);
        if (contentComponent != null) {
            centerPane.add(contentComponent, BorderLayout.CENTER);
        }
    }

    public JPanel getCenterPane() {
        return this.centerPane;
    }

    public FreeToolBar getLeftToolBar() {
        return leftToolbar;
    }

    public FreeToolBar getRightToolBar() {
        return rightToolbar;
    }
}
