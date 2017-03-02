package free;

import com.sun.awt.AWTUtilities;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import twaver.TWaverUtil;

public class FreeLoadingUI extends JDialog {

    private String imageURL = FreeUtil.getImageURL("loading.gif");
    private ImageIcon imageIcon = TWaverUtil.getImageIcon(imageURL);
    private int imageWidth = imageIcon.getIconWidth();
    private int imageHeight = imageIcon.getIconHeight();
    private static FreeLoadingUI instance = null;

    private FreeLoadingUI(Dialog parent) {
        super(parent);
        init();
    }

    private FreeLoadingUI(Frame parent) {
        super(parent);
        init();
    }

    private void init() {
        this.setModal(true);
        this.setUndecorated(true);
        AWTUtilities.setWindowOpaque(this, false);
        URL url = Class.class.getResource(imageURL);
        Icon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);
        this.getContentPane().add(label, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(imageWidth, imageHeight);
        TWaverUtil.centerWindow(this);
    }

    public static FreeLoadingUI showInstance(Dialog parent) {
        if (instance != null && instance.isShowing()) {
            instance.dispose();
        }
        instance = new FreeLoadingUI(parent);
        instance.setVisible(true);
        return instance;
    }

    public static FreeLoadingUI createInstance(Frame parent) {
        if (instance != null && instance.isShowing()) {
            instance.dispose();
        }
        instance = new FreeLoadingUI(parent);
        return instance;
    }
}
