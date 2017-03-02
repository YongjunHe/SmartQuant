package smartUI;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;


public class SmartList extends JList {

	public SmartList() {
		super();
		// TODO Auto-generated constructor stub
		init();
	}

	public SmartList(ListModel dataModel) {
		super(dataModel);
		// TODO Auto-generated constructor stub
		init();
	}

	public SmartList(Object[] listData) {
		super(listData);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init() {
		setUI(new SmartListUI());
		
	}

}
