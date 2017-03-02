package free;

import javax.swing.ImageIcon;

public class FreeStatusMessageLabel extends FreeStatusLabel {

    private static final ImageIcon ICON_ORANGE = FreeUtil.getImageIcon("statusbar_message_light_orange.png");
    private static final ImageIcon ICON_RED = FreeUtil.getImageIcon("statusbar_message_light_red.png");
    private static final ImageIcon ICON_GREEN = FreeUtil.getImageIcon("statusbar_message_light_green.png");

    @Override
    protected void init() {
        super.init();
        this.setFont(FreeUtil.FONT_14_BOLD);
        this.setGreenLight();
    }

    public void setRedLight() {
        this.setIcon(ICON_RED);
    }

    public void setGreenLight() {
        this.setIcon(ICON_GREEN);
    }

    public void setOrangeLight() {
        this.setIcon(ICON_ORANGE);
    }
}
