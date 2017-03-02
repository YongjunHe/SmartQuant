package smartUI;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import smartUI.SmartLabel;

public class SmartOutlookBar extends JPanel{
	private MouseListener changeListener = null;
	private boolean selected = false;
	private Icon icon = null;
	private Icon selectedIcon = null;
	private String title = null;
	private JLabel iconLabel;
	private JLabel titleLabel;
    private SmartOutlookPane pane;


        
	public SmartOutlookBar(String title, Icon icon, Icon selecedIcon,SmartOutlookPane pane, MouseListener listener){
		this.title = title;
		this.icon = icon;
		this.selectedIcon = selecedIcon;
		this.changeListener = listener;
		this.pane = pane;
		init();
	}
	
	
	private void init(){
		iconLabel = new JLabel(icon);
		titleLabel = new JLabel(title, SwingConstants.CENTER);
		this.setLayout(new BorderLayout());
		this.add(iconLabel,BorderLayout.CENTER);
		//this.add(titleLabel,BorderLayout.SOUTH);
		this.addMouseListener(changeListener);

	}
	
	public void changeIcon(){
		if(selected){
            remove(iconLabel);
            iconLabel = new JLabel(selectedIcon);
            add(iconLabel,BorderLayout.CENTER);

            updateUI();
		}else{
            remove(iconLabel);
            iconLabel = new JLabel(icon);
            add(iconLabel,BorderLayout.CENTER);

            updateUI();
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}



	public Icon getIcon() {
		return icon;
	}


	public void setIcon(Icon icon) {
		this.icon = icon;
	}


	public Icon getSelectedIcon() {
		return selectedIcon;
	}


	public void setSelectedIcon(Icon selectedIcon) {
		this.selectedIcon = selectedIcon;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
