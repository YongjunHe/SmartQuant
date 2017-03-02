package financeui;

import impl.StockCheck;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import message.Stock;
import message.StockDateNode;
import free.FreePagePane;
import free.FreeSearchTextField;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;
import service.StockCheckService;
import smartUI.*;
import twaver.TWaverUtil;

public class MarketTablePanel {
	private static  DefaultTableModel model;
    private static StockCheckService scs = null;
    private static JPanel containerPanel;
    private static SmartOutlookPane outlookPane;
	
	public static SmartReportPane createMarketTablePanel(JPanel containerPanel, SmartOutlookPane outlookPane) throws Exception{
		
		MarketTablePanel.containerPanel =  containerPanel;
		MarketTablePanel.outlookPane = outlookPane;
	    scs=StockCheck.create();
		
		return createReportPane();
	}
	
	private static SmartReportPane createReportPane() throws Exception{
        model = new DefaultTableModel();
        model.addColumn("日期");
        model.addColumn("股票代号");
        model.addColumn("股票名称");
        model.addColumn("开盘价");
        model.addColumn("最高价");
        model.addColumn("收盘价");
        model.addColumn("最低价");
        model.addColumn("交易量（千万股）");
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
            row.add(stock.getCName());
            row.add(node.getOpen());
            row.add(node.getHigh());
            row.add(node.getClose());
            row.add(node.getLow());
            row.add(node.getVolume()/10000000);
            row.add(node.getPb());
            row.add(node.getAdj_price());
            row.add(node.getTurnover());
        	model.addRow(row);
        }
        
        SmartReportPane page =new SmartReportPane();
        page.getTable().setModel(model);

        
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
				
				MainFrame.titlelabel.setText(model.getValueAt(row, 2).toString());   //改变标题文字
            	outlookPane.changeIcon(1);  //更改图标至个股
	    		containerPanel.removeAll();
	    		try {
					containerPanel.add(StockTablePanel.createStockPage(model.getValueAt(row, 1).toString()),BorderLayout.CENTER);
				} catch(Exception e1){
					JOptionPane.showMessageDialog(null, "网络连接错误", "错误",JOptionPane.WARNING_MESSAGE);
					System.out.println("MarketTable140");
					e1.printStackTrace();
				}
	    		
	    		containerPanel.repaint();
	    		containerPanel.updateUI();
				
				
			}
		});

        

        return page;
	}
	
	
	 public static void setupPageToolbar(SmartReportPane page) {
		  FreeToolbarButton addAccount,deleteAccount,fixAccount,seekAccount;

	      
	      
		  SmartSearchField seekField=new SmartSearchField("请输入股票代号");
		  seekField.setColumns(20);
	      page.getLeftToolBar().add(seekField);
	      
	      
	      seekField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {

		    		containerPanel.add(StockTablePanel.createStockPage(seekField.getText()),BorderLayout.CENTER);
	            	outlookPane.changeIcon(1);  //更改图标至个股
		    		containerPanel.removeAll();
		    		containerPanel.add(StockTablePanel.createStockPage(seekField.getText()),BorderLayout.CENTER);

		    		String stockname = scs.getCName(seekField.getText());
		    		MainFrame.titlelabel.setText(stockname);
		    		seekField.setText("");
		    		containerPanel.repaint();
		    		containerPanel.updateUI();
				} catch (Exception e2) {
					// TODO: handle exception
		        	JOptionPane.showMessageDialog(null, "您输入的股票代码错误或本系统无此股票信息", "错误",JOptionPane.WARNING_MESSAGE);
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
