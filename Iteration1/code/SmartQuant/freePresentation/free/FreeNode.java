package free;

import java.awt.event.ActionListener;
import twaver.Node;
import twaver.TDataBox;

public class FreeNode extends Node {

    private int labelYOffsetSingleLine = -30;
    private int labelYOffsetMultiLine = -38;
    private int labelXOffset = -12;
    private String defaultImage = FreeUtil.getImageURL("module_node.png");
    private String moduleIcon = null;
    private String buttonIcon1 = null;
//    private String buttonIcon2 = null;
//    private String buttonIcon3 = null;
    private String buttonTooltip1 = null;
//    private String buttonTooltip2 = null;
//    private String buttonTooltip3 = null;
    private ActionListener action = null;
    private ActionListener buttonAction1 = null;
//    private ActionListener buttonAction2 = null;
//    private ActionListener buttonAction3 = null;
    private String actionCommand1 = null;
//    private String actionCommand2 = null;
//    private String actionCommand3 = null;
    private FreeNodeButtonAttachment buttonAttachment = new FreeNodeButtonAttachment(this);
    private String networkName = null;
    private TDataBox shortcutData = null;

    public FreeNode() {
        init();
    }

    public FreeNode(Object id) {
        super(id);
        init();
    }

    public FreeNodeButtonAttachment getButtonAttachment() {
        return buttonAttachment;
    }

    private void init() {
        this.setImage(defaultImage);
        this.putBorderVisible(false);
        this.putLabelFont(FreeUtil.FONT_12_BOLD);
        this.putLabelColor(FreeUtil.DEFAULT_TEXT_COLOR);
        this.putLabelHighlightable(false);
        this.putLabelYOffset(labelYOffsetSingleLine);
        this.putLabelXOffset(labelXOffset);
    }

    @Override
    public String getUIClassID() {
        return FreeNodeUI.class.getName();
    }

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
        this.updateUI();
        updateComponentAttachment();
    }

    public String getButtonIcon1() {
        return buttonIcon1;
    }

    public void setButtonIcon1(String buttonIcon1) {
        this.buttonIcon1 = buttonIcon1;
        this.updateUI();
        updateComponentAttachment();
    }

//    public String getButtonIcon3() {
//        return buttonIcon3;
//    }
//
//    public void setButtonIcon3(String buttonIcon3) {
//        this.buttonIcon3 = buttonIcon3;
//        this.updateUI();
//        updateComponentAttachment();
//    }

//    public String getButtonIcon2() {
//        return buttonIcon2;
//    }
//
//    public void setButtonIcon2(String buttonIcon2) {
//        this.buttonIcon2 = buttonIcon2;
//        this.updateUI();
//        updateComponentAttachment();
//    }

    private void updateComponentAttachment() {
        buttonAttachment.updateProperties();
    }

    public String getButtonTooltip1() {
        return buttonTooltip1;
    }

    public void setButtonTooltip1(String buttonTooltip1) {
        this.buttonTooltip1 = buttonTooltip1;
        updateComponentAttachment();
    }

//    public String getButtonTooltip2() {
//        return buttonTooltip2;
//    }
//
//    public void setButtonTooltip2(String buttonTooltip2) {
//        this.buttonTooltip2 = buttonTooltip2;
//        updateComponentAttachment();
//    }
//
//    public String getButtonTooltip3() {
//        return buttonTooltip3;
//    }
//
//    public void setButtonTooltip3(String buttonTooltip3) {
//        this.buttonTooltip3 = buttonTooltip3;
//        updateComponentAttachment();
//    }

    public ActionListener getButtonAction1() {
        return buttonAction1;
    }

    public void setButtonAction1(ActionListener buttonAction1) {
        this.buttonAction1 = buttonAction1;
    }

//    public ActionListener getButtonAction2() {
//        return buttonAction2;
//    }
//
//    public void setButtonAction2(ActionListener buttonAction2) {
//        this.buttonAction2 = buttonAction2;
//    }
//
//    public ActionListener getButtonAction3() {
//        return buttonAction3;
//    }
//
//    public void setButtonAction3(ActionListener buttonAction3) {
//        this.buttonAction3 = buttonAction3;
//    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
        if (networkName != null && networkName.contains("<br>")) {
            this.putLabelYOffset(this.labelYOffsetMultiLine);
        } else {
            this.putLabelYOffset(labelYOffsetSingleLine);
        }
    }

    public String getActionCommand1() {
        return actionCommand1;
    }

    public void setActionCommand1(String actionCommand1) {
        this.actionCommand1 = actionCommand1;
    }

//    public String getActionCommand2() {
//        return actionCommand2;
//    }
//
//    public void setActionCommand2(String actionCommand2) {
//        this.actionCommand2 = actionCommand2;
//    }
//
//    public String getActionCommand3() {
//        return actionCommand3;
//    }
//
//    public void setActionCommand3(String actionCommand3) {
//        this.actionCommand3 = actionCommand3;
//    }

    public ActionListener getAction() {
        return action;
    }

    public void setAction(ActionListener action) {
        this.action = action;
    }

    public boolean isShortcutLoaded() {
        return shortcutData == null;
    }

    public TDataBox getShortcuts() {
        return this.shortcutData;
    }

    public void setShortcuts(TDataBox shortcutData) {
        this.shortcutData = shortcutData;
    }
}
