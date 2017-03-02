package free;

import java.awt.Color;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class FreeToolbarRoverButton extends FreeToolbarButton {

    private Color roverDyeColor = new Color(86, 146, 61);

    public FreeToolbarRoverButton() {
        this(null, null);
    }

    public FreeToolbarRoverButton(String text) {
        this(text, null);
    }

    public FreeToolbarRoverButton(Icon icon) {
        this(null, icon);
    }

    public FreeToolbarRoverButton(String text, Icon icon) {
        this.setText(text);
        this.setIcon(icon);
    }

    @Override
    public void setIcon(final Icon icon) {
        super.setIcon(icon);

        //generate rover icon and pressed icon.
        if (icon == null) {
            this.setPressedIcon(null);
            this.setRolloverIcon(null);
        } else {
            Image image = FreeUtil.iconToImage(icon);
            Icon roverIcon = FreeUtil.createDyedIcon(new ImageIcon(image), roverDyeColor);
            Icon pressedIcon = FreeUtil.createMovedIcon(roverIcon);
            this.setRolloverIcon(roverIcon);
            this.setPressedIcon(pressedIcon);
        }
    }
}
