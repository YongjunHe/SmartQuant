package free;

import twaver.RotatableNode;
import twaver.TWaverConst;

public class FreeLink extends RotatableNode {

    private int direction = TWaverConst.ORIENTATION_EAST;

    public FreeLink() {
        init();
    }

    public FreeLink(Object id) {
        super(id);
        init();
    }

    private void init() {
        this.putBorderVisible(false);
        this.putLabelFont(FreeUtil.FONT_12_BOLD);
        this.putLabelColor(FreeUtil.DEFAULT_TEXT_COLOR);
        this.putLabelHighlightable(false);
        this.setDirection(direction);
    }

    public void setDirection(int direction) {
        if (direction == TWaverConst.ORIENTATION_EAST) {
            this.setImage(FreeUtil.getImageURL("arrow_right.png"));
        }
        if (direction == TWaverConst.ORIENTATION_WEST) {
            this.setImage(FreeUtil.getImageURL("arrow_left.png"));
        }
        if (direction == TWaverConst.ORIENTATION_NORTH) {
            this.setImage(FreeUtil.getImageURL("arrow_up.png"));
        }
        if (direction == TWaverConst.ORIENTATION_SOUTH) {
            this.setImage(FreeUtil.getImageURL("arrow_down.png"));
        }

        if (direction == TWaverConst.ORIENTATION_NORTH_EAST) {
            this.setImage(FreeUtil.getImageURL("arrow_right_up.png"));
        }
        if (direction == TWaverConst.ORIENTATION_SOUTH_EAST) {
            this.setImage(FreeUtil.getImageURL("arrow_right_down.png"));
        }
        if (direction == TWaverConst.ORIENTATION_NORTH_WEST) {
            this.setImage(FreeUtil.getImageURL("arrow_left_up.png"));
        }
        if (direction == TWaverConst.ORIENTATION_SOUTH_WEST) {
            this.setImage(FreeUtil.getImageURL("arrow_left_down.png"));
        }

        this.direction = direction;
        this.updateUI();
    }
}
