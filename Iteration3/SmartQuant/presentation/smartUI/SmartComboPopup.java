package smartUI;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboPopup;

public class SmartComboPopup extends BasicComboPopup {

	public SmartComboPopup(JComboBox combo) {
		super(combo);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void configurePopup() {
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        setBorderPainted( false );
        setOpaque( false );
        add( scroller );
        setDoubleBuffered( true );
        setFocusable( false );
	}

	@Override
	protected JScrollPane createScroller() {
		JScrollPane sp = new SmartScrollPane( list,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		sp.setHorizontalScrollBar(null);
		return sp;
	}

	@Override
	protected void configureList() {
		  list.setFont( comboBox.getFont() );
	        list.setForeground( comboBox.getForeground() );
	        list.setBackground( new Color(255, 245, 247) );
	        list.setSelectionForeground( UIManager.getColor( "ComboBox.selectionForeground" ) );
	        list.setSelectionBackground( new Color(185, 210, 190) );
	        list.setBorder( null );
	        list.setCellRenderer( comboBox.getRenderer() );
	        list.setFocusable( false );
	        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
	        setListSelection( comboBox.getSelectedIndex() );
	        installListListeners();
	}
	
	 /**
     * Sets the list selection index to the selectedIndex. This
     * method is used to synchronize the list selection with the
     * combo box selection.
     *
     * @param selectedIndex the index to set the list
     */
    private void setListSelection(int selectedIndex) {
        if ( selectedIndex == -1 ) {
            list.clearSelection();
        }
        else {
            list.setSelectedIndex( selectedIndex );
            list.ensureIndexIsVisible( selectedIndex );
        }
    }
    
}
