package financeui;

import impl.SummaryCheck;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import message.SummaryDateNode;
import enums.MarketType;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;
import service.SummaryCheckService;
import twaver.TWaverUtil;


public class MarketListPanel {
	public static JPanel stockMarketPanel;
	public static JTabbedPane tab;
    private static SummaryCheckService summaryCheckService=null;
    private static ArrayList<String> marketNumber;
    private static ArrayList<String> marketName;


	
	

	
	public static FreeReportPage  createMarketListPage(JTabbedPane tab){

	    MarketListPanel.tab=tab;
	    
 	    summaryCheckService=SummaryCheck.create();
	    marketName=new ArrayList<String>();
	    marketNumber=new ArrayList<String>();
//	    marketNumber.add("000001");
//	    marketNumber.add("399001");
	    marketNumber.add("000300");
//	    marketName.add("上证指数");
//	    marketName.add("深证指数");
	    marketName.add("沪深300");
	    


	    

		return createReportPage();
	}
	

	

	
    private static FreeReportPage createReportPage() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("日期");
        model.addColumn("股票名称");
        model.addColumn("股票代号");
        model.addColumn("开盘价");
        model.addColumn("收盘价");
        model.addColumn("最高价");
        model.addColumn("最低价");
        model.addColumn("后复权价");
        model.addColumn("成交量");
        
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	String nowTime=format.format(date);
    	date.setMonth(date.getMonth()-1);
    	String beginTime=format.format(date);
    	
    	int index=0;
    	for(MarketType type:MarketType.values()){

    			System.out.println(type.toString());
    			
                Iterator iterator=summaryCheckService.getSummaryMessage(type, beginTime, nowTime);
    			System.out.println(type.toString());
                while(iterator.hasNext()){
                
              	SummaryDateNode node=(SummaryDateNode) iterator.next();
              	if(!iterator.hasNext()){
                  	Vector row=new Vector();
                  	row.add(node.getDate());
                  	row.add(marketName.get(index));
                  	row.add(marketNumber.get(index));
                  	row.add(node.getOpen());
                  	row.add(node.getClose());
                  	row.add(node.getHigh());
                  	row.add(node.getLow());
                  	row.add(node.getAdj_price());
                  	row.add(node.getVolume());
                  	model.addRow(row);
              	}

//              	iterator.remove();
                }

            
    	}
        
        

    	
//    	for(int i=0;i<50;i++){
//        	Vector row=new Vector();
//        	row.add(nowTime);
//        	row.add("上证指数");
//        	row.add("399001");
//        	row.add("1.1");
//        	row.add("1.1");
//        	row.add("1.1");
//        	row.add("1.1");
//        	row.add("1.1");
//        	row.add("1.1");
//        	model.addRow(row);
//    	}
       
        
        
        
        FreeReportPage page = new FreeReportPage();
        page.getTable().setModel(model);
        page.setDescription("All Work Order Items by Part Number. Created " + new Date().toString());
        setupPageToolbar(page);
        
        
        page.getTable().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int row=page.getTable().getSelectedRow();
				int col=page.getTable().getSelectedColumn();
				int index=marketNumber.indexOf(model.getValueAt(row, 2));
                tab.add(model.getValueAt(row, 1).toString(), MarketPanel.createMarketPage(tab, model.getValueAt(row, 2).toString(),model.getValueAt(row, 1).toString(),MarketType.values()[index]));
                tab.setSelectedComponent(tab.getComponentAt(tab.getTabCount()-1)); 
			}
		});

        return page;
    }

  public static void setupPageToolbar(FreePagePane page) {
	  FreeToolbarButton seekReceipts;

      seekReceipts=createButton("/free/test/print.png", "��ѯ�տ", true);
 
      page.getRightToolBar().add(seekReceipts);
      

		

      
}
  public static FreeToolbarButton createButton(String icon, String tooltip, boolean rover) {
      FreeToolbarButton button = null;
      if (rover) {
          button = new FreeToolbarRoverButton();
      } else {
          button = new FreeToolbarButton();
      }
      button.setIcon(TWaverUtil.getIcon(icon));
      button.setToolTipText(tooltip);
      
      return button;
  }
  


}

