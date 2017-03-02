package free;

import com.sun.awt.AWTUtilities;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import twaver.TWaverUtil;
import twaver.swing.LabelValueLayout;

public class FreeLoginUI extends JFrame {

    private ImageIcon loginButtonIcon = FreeUtil.getImageIcon("login_button.png");
    private ImageIcon loginButtonRoverIcon = FreeUtil.getImageIcon("login_button_rover.png");
    private ImageIcon loginButtonPressedIcon = FreeUtil.getImageIcon("login_button_pressed.png");
    private ImageIcon closeButtonIcon = FreeUtil.getImageIcon("login_close.png");
    private ImageIcon closeButtonRoverIcon = FreeUtil.getImageIcon("login_close_rover.png");
    private ImageIcon closeButtonPressedIcon = FreeUtil.getImageIcon("login_close_pressed.png");
    private JButton btnLogin = createTransparentButton(loginButtonIcon, loginButtonRoverIcon, loginButtonPressedIcon);
    private JButton btnClose = createTransparentButton(closeButtonIcon, closeButtonRoverIcon, closeButtonPressedIcon);
    private ImageIcon logoIcon = FreeUtil.getImageIcon("login_logo.png");
    private String logoRoverURL = FreeUtil.getImageURL("login_logo_rover.png");
    private ImageIcon logoRoverIcon = TWaverUtil.getImageIcon(logoRoverURL);
    private ImageIcon leftTopIcon = FreeUtil.getImageIcon("login_left_top.png");
    private ImageIcon leftIcon = FreeUtil.getImageIcon("login_left.png");
    private ImageIcon rightIcon = FreeUtil.getImageIcon("login_right.png");
    private int width = loginButtonIcon.getIconWidth();
    private int height = loginButtonIcon.getIconHeight() + leftIcon.getIconHeight() + logoIcon.getIconHeight();
    private JLabel logoLabel = createDraggableLabel(logoIcon);
    private JPanel inputPane = new JPanel() {

        private String backgroundImageURL = FreeUtil.getImageURL("login_background.png");
        private TexturePaint paint = FreeUtil.createTexturePaint(backgroundImageURL);

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(paint);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    };
    private MouseAdapter moveWindowListener = new MouseAdapter() {

        private Point lastPoint = null;

        @Override
        public void mousePressed(MouseEvent e) {
            lastPoint = e.getLocationOnScreen();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point point = e.getLocationOnScreen();
            int offsetX = point.x - lastPoint.x;
            int offsetY = point.y - lastPoint.y;
            Rectangle bounds = FreeLoginUI.this.getBounds();
            bounds.x += offsetX;
            bounds.y += offsetY;
            FreeLoginUI.this.setBounds(bounds);
            lastPoint = point;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == logoLabel) {
                logoLabel.setIcon(logoRoverIcon);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == logoLabel) {
                logoLabel.setIcon(logoIcon);
            }
        }
    };

    public FreeLoginUI() {
        init();
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);

        AWTUtilities.setWindowOpaque(this, false);

        JPanel centerPane = new JPanel(new BorderLayout());
        centerPane.add(btnLogin, BorderLayout.SOUTH);
        this.setContentPane(centerPane);
        this.setSize(width, height);
        TWaverUtil.centerWindow(this);

        btnLogin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                login();
            }
        });


        btnClose.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnClose.setToolTipText("Close");

        JPanel topPane = new JPanel(new BorderLayout());

        logoLabel.setOpaque(false);
        topPane.setOpaque(false);

        logoLabel.addMouseListener(moveWindowListener);
        logoLabel.addMouseMotionListener(moveWindowListener);
        topPane.addMouseListener(moveWindowListener);
        topPane.addMouseMotionListener(moveWindowListener);

        topPane.add(logoLabel, BorderLayout.CENTER);
        topPane.add(createDraggableLabel(this.leftTopIcon), BorderLayout.WEST);
        topPane.add(btnClose, BorderLayout.EAST);

        centerPane.add(createDraggableLabel(this.leftIcon), BorderLayout.WEST);
        centerPane.add(createDraggableLabel(this.rightIcon), BorderLayout.EAST);
        centerPane.add(topPane, BorderLayout.NORTH);
        centerPane.add(inputPane, BorderLayout.CENTER);

        inputPane.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        inputPane.setLayout(new LabelValueLayout(10, 12, false));
        inputPane.add(createInputLabel("Server:"));
        inputPane.add(new FreeTextField("localhost"));
        inputPane.add(createInputLabel("Company:"));
        inputPane.add(new FreeSearchTextField());
        inputPane.add(createInputLabel("User Name:"));
        inputPane.add(new FreeTextField("admin"));
        inputPane.add(createInputLabel("Password:"));
        inputPane.add(new FreePasswordField());
        inputPane.add(createInputLabel("Language:"));
        inputPane.add(new FreeTextField(TWaverUtil.getLocale().toString()));
        inputPane.add(new JLabel());

        JCheckBox cbRememberMe = new JCheckBox("Remember me");
        cbRememberMe.setOpaque(false);
        setupComponent(cbRememberMe);
        inputPane.add(cbRememberMe);

        inputPane.addMouseListener(moveWindowListener);
        inputPane.addMouseMotionListener(moveWindowListener);
    }

    private JButton createTransparentButton(ImageIcon icon, ImageIcon roverIcon, ImageIcon pressedIcon) {
        JButton button = new JButton();
        button.setBorder(null);
        button.setMargin(null);
        button.setOpaque(false);
        button.setIcon(icon);
        button.setRolloverEnabled(true);
        button.setRolloverIcon(roverIcon);
        button.setPressedIcon(pressedIcon);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setRequestFocusEnabled(false);

        return button;
    }

    private JLabel createDraggableLabel(ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.addMouseListener(moveWindowListener);
        label.addMouseMotionListener(moveWindowListener);
        return label;
    }

    private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        setupComponent(label);
        return label;
    }

    private void setupComponent(JComponent c) {
        c.setFont(FreeUtil.FONT_14_BOLD);
        c.setForeground(FreeUtil.DEFAULT_TEXT_COLOR);
    }

    protected void login() {
//        final FreeLoadingUI loadingUI = FreeLoadingUI.createInstance(this);
//        AWTUtilities.setWindowOpacity(this, 0.8f);
//        Thread thread = new Thread() {
//
//            @Override
//            public void run() {
//                final Shell shell = new Shell();
//                SwingUtilities.invokeLater(new Runnable() {
//
//                    public void run() {
//                        shell.setVisible(true);
//                        dispose();
//                        loadingUI.dispose();
//                    }
//                });
//            }
//        };
//        thread.start();
//        loadingUI.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                FreeUtil.setupLookAndFeel();
                FreeLoginUI ui = new FreeLoginUI();
                ui.setVisible(true);
            }
        });
    }
}
