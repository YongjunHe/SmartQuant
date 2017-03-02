package free;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import twaver.TWaverUtil;

public class FreeGarbageCollectButton extends FreeToolbarButton {

    public FreeGarbageCollectButton() {
        this.setIcon(TWaverUtil.getIcon("/free/test/gc.png"));
        this.setToolTipText("Click to call garbage collector");
        this.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.gc();
            }
        });
    }
}
