package financeui;

import impl.StockCheck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import message.Stock;
import message.StockDateNode;
import service.SmartSortService;
import service.StockCheckService;
import twaver.TWaverUtil;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeSearchTextField;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;

public class StockListPanel {
	private static JTabbedPane tab;
	private static String userId;
	private static  DefaultTableModel model;
    private static FreePagePane stockPage = null;
    private static StockCheckService scs = null;

	
	
	
	
	public static FreeReportPage  createAccountManagementPage(JTabbedPane tab,String userId){

	    StockListPanel.tab=tab;
	    StockListPanel.userId=userId;
        
	    try {
		    scs=StockCheck.create();
			return createReportPage();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}


	}
	
    private static FreeReportPage createReportPage() {
        model = new DefaultTableModel();
        model.addColumn("日期");
        model.addColumn("股票代号");
        model.addColumn("股票名称");
        model.addColumn("开盘价");
        model.addColumn("最高价");
        model.addColumn("收盘价");
        model.addColumn("最低价");
        model.addColumn("交易量（股	）");
        model.addColumn("市净率");
        model.addColumn("后复权价");
        model.addColumn("换手率");
        
        
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	String nowTime=format.format(date);
        
   
        Iterator it=scs.getSelectedStock();
        
        while(it.hasNext()){

        	Stock stock=(Stock) it.next();
        	StockDateNode node=stock.getTodayData();
            Vector row = new Vector();
            row.add(node.getDate());
            row.add(stock.getName());//此处应该用股票代号
            row.add(stock.getName());
            row.add(node.getOpen());
            row.add(node.getHigh());
            row.add(node.getClose());
            row.add(node.getLow());
            row.add(node.getVolume());
            row.add(node.getPb());
            row.add(node.getAdj_price());
            row.add(node.getTurnover());
        	model.addRow(row);
        }
        
        
//        for (int i = 0; i < 100; i++) {
//            Vector row = new Vector();
//            row.add("2016-03-05");
//            row.add("sz000850");
//            row.add("--");
//            row.add("5.43");
//            row.add("5.49");
//            row.add("5.59");
//            row.add("5.41");
//            row.add("5.59");
//            row.add("5.41");
//            row.add("5.49");
//            row.add("109583");
//
//            model.addRow(row);
//        }

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
                tab.add(model.getValueAt(row, 1).toString(), StockPanel.createStockPage(tab, model.getValueAt(row, 1).toString()));
                tab.setSelectedComponent(tab.getComponentAt(tab.getTabCount()-1)); 
			}
		});


		System.out.println(model.getColumnCount());

        return page;
    }

  public static void setupPageToolbar(FreePagePane page) {
	  FreeToolbarButton addAccount,deleteAccount,fixAccount,seekAccount;
//	  addAccount=createButton("/free/test/add.png", "�����˻�", true);
//      page.getRightToolBar().add(addAccount);

      
      
	  FreeSearchTextField seekField=new FreeSearchTextField();
	  seekField.setColumns(20);
      seekField.setText("请输入股票代码");
      page.getRightToolBar().add(seekField);
      
      
      
      seekField.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
	            tab.add(seekField.getText(), StockPanel.createStockPage(tab, seekField.getText()));
	            tab.setSelectedComponent(tab.getComponentAt(tab.getTabCount()-1)); 				
			} catch (Exception e2) {
				// TODO: handle exception
		   		JOptionPane.showMessageDialog(null, "您输入的股票代码有误", "提示",JOptionPane.WARNING_MESSAGE); 
			}

		}
	});
      

      
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