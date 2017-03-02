package smartUI;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import free.FreeOutlookBar;
import free.FreeOutlookPane;
import free.FreeUtil;
import smartUI.SmartPanel;
import twaver.TWaverUtil;
import twaver.swing.TableLayout;

public class SmartOutlookPane extends JPanel{
	
	private TableLayout barPaneLayout = new TableLayout();

	public SmartOutlookPane(){
		init();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
//		g2.setPaint(new GradientPaint(0, 0, new Color(3, 200, 100), getWidth(), 
//				getHeight(), new Color(23, 220, 120)));

		g2.setColor(new Color(255, 255, 255, 195));
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(new Color(219, 207, 202));
		g2.drawLine(getWidth()-1, 0, getWidth()-1, getHeight());
	}
	
	private void init(){
		
		this.setLayout(barPaneLayout);
		barPaneLayout.insertColumn(0,TableLayout.FILL);
		

	}
	
	public void changeIcon(int number){
    	for(int i=0;i<this.getComponentCount();i++){
    		SmartOutlookBar bar = (SmartOutlookBar) this.getComponent(i);
    		if(i==number){
    			bar.setSelected(true);
    		}else{
    			bar.setSelected(false);
    		}
    		bar.changeIcon();
    	}
	}
	
	public SmartOutlookBar addBar(String title, Icon icon, Icon selectedIcon, MouseListener listener){
		SmartOutlookBar bar = new SmartOutlookBar(title, icon, selectedIcon, this, listener);
		bar.setOpaque(false);
		int rowcount = barPaneLayout.getRow().length;
		JLabel label = new JLabel(title); 
		
		System.out.println(rowcount);
		barPaneLayout.insertRow(rowcount, TableLayout.PREFERRED);
		if(rowcount==0){
			bar.setSelected(true);
			bar.changeIcon();
		}
		
		this.add(bar, "0,"+rowcount);

		
		
		return bar;
	}
	
    public void loadOutlookPane(String xml, MouseListener barAction) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(FreeUtil.class.getResource(xml).openStream());

            Element root = doc.getDocumentElement();
            NodeList modules = root.getChildNodes();
            if (modules != null) {
                for (int i = 0; i < modules.getLength(); i++) {
                    org.w3c.dom.Node moduleNode = modules.item(i);
                    if (moduleNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        if (moduleNode.getNodeName().equalsIgnoreCase("module")) {
                            String text = SmartUtil.getStringAttribute(moduleNode, "text");
                            Icon icon = SmartUtil.getIconAttribute(moduleNode, "icon");
                            Icon iconSelected = SmartUtil.getIconAttribute(moduleNode, "selected_icon");
                            SmartOutlookBar bar = this.addBar(text, icon, iconSelected,barAction);
                            
                            
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    



}
