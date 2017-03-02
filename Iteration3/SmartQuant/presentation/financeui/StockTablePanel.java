package financeui;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import impl.SmartSort;
import impl.StockCheck;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

import dateChoose.DateChooserJButton;
import message.StockDateNode;
import service.SmartSortService;
import service.StockCheckService;
import smartUI.SmartComboBox;
import smartUI.SmartLabel;
import smartUI.SmartReportPane;
import smartUI.SmartTextField;
import smartUI.SmartToolBar;
import twaver.TWaverUtil;
import enums.Cyc;
import enums.DataType;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeStatusLabel;
import free.FreeTextField;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;

public class StockTablePanel {
	private static String stockNumber;
	private static  DefaultTableModel model;
	private static StockCheckService scs;
    private static SmartSortService sss;
	private static JCheckBox openBox,highBox,closeBox,lowBox,volumeBox,pbBox,adjPriceBox,turnoverBox;
	private static ArrayList<JCheckBox> boxlist;
    private static ArrayList<String> attrList,attrlistEnglish;
    private static Iterator stockIterator;
    private static ArrayList<StockDateNode> stockDateNodes;
    private static boolean isLowToUp;
    private static DataType nowType;
    private static Cyc nowKChartType = Cyc.day;

    
	
    
    
	public static SmartReportPane  createStockPage(String stockNumber) throws Exception{

        
	    StockTablePanel.stockNumber=stockNumber;
	    
	    scs=StockCheck.create();

	    nowKChartType = Cyc.day;
	    

        
		
		return createReportPage();
	}
	
	private static SmartReportPane createReportPage() throws Exception{
		
    	attrList = new ArrayList<String>();
    	attrlistEnglish = new ArrayList<String>();
    	stockDateNodes = new ArrayList<StockDateNode>();
    	attrList.add("日期");
    	attrList.add("开盘价");
    	attrList.add("最高价");
    	attrList.add("收盘价");
    	attrList.add("最低价");
    	attrList.add("交易量（千万股）");
    	attrList.add("市净率");
    	attrList.add("后复权价");
    	attrList.add("换手率");
    	attrlistEnglish.add("date");
    	attrlistEnglish.add("open");
    	attrlistEnglish.add("high");
    	attrlistEnglish.add("close");
    	attrlistEnglish.add("low");
    	attrlistEnglish.add("volume");
    	attrlistEnglish.add("pb");
    	attrlistEnglish.add("adj_price");
    	attrlistEnglish.add("turnover");
        model = new DefaultTableModel();
        
        for(String attr:attrList){
        	model.addColumn(attr);
        }
        
        Vector sortrow = new Vector();
        sortrow.add("↓");
        for(int i=0;i<8;i++){
        	sortrow.add("-");
        }
        model.addRow(sortrow);
        isLowToUp = true;
        nowType = DataType.date;
        
        
        
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	String nowTime=format.format(date);
    	date.setMonth(date.getMonth()-1);
    	String beginTime=format.format(date);
    	
        stockIterator=scs.getStockMessage(stockNumber, beginTime, nowTime);
        
        stockDateNodes.clear();
        while(stockIterator.hasNext()){
        	StockDateNode node=(StockDateNode) stockIterator.next();
            Vector row = new Vector();
            row.add(node.getDate());
            row.add(node.getOpen());
            row.add(node.getHigh());
            row.add(node.getClose());
            row.add(node.getLow());
            row.add(node.getVolume()/10000000);
            row.add(node.getPb());
            row.add(node.getAdj_price());
            row.add(node.getTurnover());
            model.addRow(row);
            stockDateNodes.add(node);
        }
        

       
        SmartReportPane page = new SmartReportPane();
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
				
				if(row == 0){
					refreshbyorder(model.getColumnName(col), stockDateNodes.iterator());
				}
			}
		});

        return page;
	}
	
    public static void setupPageToolbar(SmartReportPane page) {
   	    FreeToolbarButton seekStock,filterStock;
   	    SmartLabel filterLabel,lowerLimitLabel,higherLimitLabel,beginDateLabel,endDateLabel,graphTypeLabel,kChartTypeLabel;
   	    SmartComboBox filterBox,graphTypeBox,kChartTypeBox;
   	    SmartTextField lowerLimitField,higherlimitField;
   	    SmartToolBar graphTools,kchartTools;
   	    DateChooserJButton beginDateButton,endDateButton;
   	    
    	Date date=new Date();
    	date.setMonth(date.getMonth()-1);
   	    
   	    beginDateLabel=new SmartLabel("开始日期：");
   	    beginDateButton=new DateChooserJButton(date);
   	    endDateLabel=new SmartLabel("结束日期：");
   	    endDateButton=new DateChooserJButton();
   	  
   	    page.getRightToolBar().add(beginDateLabel);
   	    page.getRightToolBar().add(beginDateButton);
   	    page.getRightToolBar().add(endDateLabel);
   	    page.getRightToolBar().add(endDateButton);
   	  
   	    seekStock=createButton("/free/test/print.png", "确认日期", true);
        page.getRightToolBar().add(seekStock);
        

        
        
        
        graphTypeLabel = new SmartLabel("图表类型：");
        graphTypeBox = new SmartComboBox();
        graphTypeBox.addItem("表格");
        graphTypeBox.addItem("K线图");
        graphTypeBox.addItem("均幅指数");
        

        
        page.getLeftToolBar().add(graphTypeLabel);
        page.getLeftToolBar().add(graphTypeBox);
      
        filterLabel = new SmartLabel("筛选条件：");
        filterBox = new SmartComboBox();
        for(String attr:attrList){
       	     if(!attr.equals("日期"))
    	     filterBox.addItem(attr);
        }
  
        lowerLimitLabel = new SmartLabel("下限:");
        higherLimitLabel = new SmartLabel("上限:");
        lowerLimitField = new SmartTextField();
        lowerLimitField.setColumns(4);
      
        higherlimitField = new SmartTextField();
        higherlimitField.setColumns(4);
      
        filterStock = createButton("/free/test/print.png", "筛选股票", true); 
        filterStock.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
		    	Iterator iterator = stockDateNodes.iterator();
//		    	while(iterator.hasNext()){
//		    		StockDateNode node = (StockDateNode) iterator.next();
//		    		System.out.println(node.getDate());
//		    	}
			    refreshByFilter(filterBox.getSelectedItem().toString(), lowerLimitField.getText(), higherlimitField.getText(), stockDateNodes.iterator());
		    }
        });
        
        
        graphTools = new SmartToolBar();
       
        graphTools.add(filterLabel);
        graphTools.add(filterBox);
        graphTools.add(lowerLimitLabel);
        graphTools.add(lowerLimitField);
        graphTools.add(higherLimitLabel);
        graphTools.add(higherlimitField);
        graphTools.add(filterStock);        

        page.getLeftToolBar().add(graphTools);
        
        
        kChartTypeLabel = new SmartLabel("K线图类型:");
        kChartTypeBox = new SmartComboBox();
        kChartTypeBox.addItem("日K线图");
        kChartTypeBox.addItem("周K线图");
        kChartTypeBox.addItem("月K线图");
        
        kchartTools = new SmartToolBar();
        kchartTools.add(kChartTypeLabel);
        kchartTools.add(kChartTypeBox);
        
        

        seekStock.addMouseListener(new MouseAdapter(){
   		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
   		    	String graphTypeStr = graphTypeBox.getSelectedItem().toString();
   		    	if(graphTypeStr.equals("表格")){
  			      refreshByDate(beginDateButton.getText(), endDateButton.getText());
 
   		    	}else if(graphTypeStr.equals("K线图")){
   		    		try {
   	   		    		page.showKchart(stockNumber, beginDateButton.getText(), endDateButton.getText(),nowKChartType);
					} catch (Exception e) {
						// TODO: handle exception
			        	JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
					}


   		    	}else if(graphTypeStr.equals("均幅指数")){
   		    		try {
						page.showATR(stockNumber, beginDateButton.getText(), endDateButton.getText());
					} catch (Exception e) {
						// TODO: handle exception
			        	JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
					}
   		    	}

		    }
        });
      
		
;


    	

        graphTypeBox.addItemListener(new ItemListener() {
        	


			
			@Override
			public void itemStateChanged(ItemEvent e) {
	        	String beginTime = beginDateButton.getText();
	        	String endTime = endDateButton.getText();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					String seleteGraphStr = e.getItem().toString();
					if(seleteGraphStr.equals("K线图")){
						try {
							page.showKchart(stockNumber, beginTime, endTime,nowKChartType);
							page.getLeftToolBar().remove(graphTools);
		   		    		page.getLeftToolBar().add(kchartTools);
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
						}

	   		    		

	   		    		


					}else if(seleteGraphStr.equals("表格")){
						refreshByDate(beginTime, endTime);
						page.showGraph();
						page.getLeftToolBar().remove(kchartTools);
						page.getLeftToolBar().add(graphTools);
						
					}else if(seleteGraphStr.equals("均幅指数")){
						try {
							page.showATR(stockNumber, beginTime,endTime);
							page.getLeftToolBar().remove(graphTools);
							page.getLeftToolBar().remove(kchartTools);
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
						}

						
					}
				}
			}
		});
        
        
        kChartTypeBox.addItemListener(new ItemListener() {

			
			@Override
			public void itemStateChanged(ItemEvent e) {
	        	String beginTime = beginDateButton.getText();
	        	String endTime = endDateButton.getText();
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					String seleteGraphStr = e.getItem().toString();
					if(seleteGraphStr.equals("日K线图")){
						try {
							nowKChartType=Cyc.day;
							page.showKchart(stockNumber, beginTime, endTime,Cyc.day);

							
						} catch (SocketException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
							e1.printStackTrace();
						}catch(Exception e2){
							
						}

					}else if(seleteGraphStr.equals("周K线图")){
						try {
							nowKChartType=Cyc.week;
							page.showKchart(stockNumber, beginTime, endTime,Cyc.week);
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
						}


						
					}else if(seleteGraphStr.equals("月K线图")){
						try {
							nowKChartType=Cyc.month;
							page.showKchart(stockNumber, beginTime, endTime,Cyc.month);

						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});
//	  openBox = new JCheckBox("开盘价",true);
//	  highBox = new JCheckBox("最高价",true);
//	  closeBox = new JCheckBox("收盘价",true);
//	  lowBox = new JCheckBox("最低价",true);
//	  volumeBox = new JCheckBox("交易量",true);
//	  pbBox = new JCheckBox("市净率",true);
//	  adjPriceBox = new JCheckBox("后复权价",true);
//	  turnoverBox = new JCheckBox("换手率",true);
//    boxlist = new ArrayList<JCheckBox>();
//	  boxlist.add(openBox);
//	  boxlist.add(highBox);
//	  boxlist.add(closeBox);
//	  boxlist.add(lowBox);
//	  boxlist.add(volumeBox);
//	  boxlist.add(pbBox);
//	  boxlist.add(adjPriceBox);
//	  boxlist.add(turnoverBox);	
//		
//	  	
//		
//	  ActionListener listener=new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// TODO Auto-generated method stub
//			JCheckBox box=(JCheckBox) e.getSource();
//			changeColumn(page,box.getText());
//			
//		}
//	};	
//		
//	
//	  for(JCheckBox box:boxlist){
//		  box.addActionListener(listener);
//		  page.getLeftToolBar().add(box);	
//	  }
	  	


      
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
  
  public  static void refreshByDate(String beginDate , String endDate){
     
   	  while(model.getRowCount()>0){
   		  model.removeRow(0);
   	  }

      try {
    	  stockIterator=scs.getStockMessage(stockNumber, beginDate, endDate);
      } catch (Exception e) {
    	// TODO: handle exception
        	JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
      }
     
     
     stockDateNodes.clear();
     
     Vector sortrow = new Vector();
     sortrow.add("↓");
     for(int i=0;i<8;i++){
     	sortrow.add("-");
     }
     model.addRow(sortrow);
     isLowToUp = true;
     nowType = DataType.date;
     
     try {
         while(stockIterator.hasNext()){
         	 StockDateNode node=(StockDateNode) stockIterator.next();
             Vector row = new Vector();
             row.add(node.getDate());
             row.add(node.getOpen());
             row.add(node.getHigh());
             row.add(node.getClose());
             row.add(node.getLow());
             row.add(node.getVolume()/10000000);
             row.add(node.getPb());
             row.add(node.getAdj_price());
             row.add(node.getTurnover());
             model.addRow(row);
             stockDateNodes.add(node);
         }
	} catch (Exception e) {
		// TODO: handle exception
    	JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
	}

	  
	  	  
  }
  
    private static void refreshByFilter(String filterAttr,String lower,String higher,Iterator stockiterator){
    	  sss=new SmartSort();
    	  //System.out.println(filterAttr+lower+higher);
    	  int index = attrList.indexOf(filterAttr);
    	  DataType type = DataType.valueOf(attrlistEnglish.get(index));
    	  double low = 0, high = Double.MAX_VALUE;
    	  if(!lower.equals("")){
    		     low = Double.parseDouble(lower);
    	  }
    	  if(!higher.equals("")){
    		     high = Double.parseDouble(higher);
    	  }
    	  
          
    	  message.Range range = new message.Range(low, high, !lower.equals(""), !higher.equals(""));
    	  System.out.println(type.toString());
    	  System.out.println(range.getLower());
    	  

    	  
    	  stockIterator = sss.sort(type, range, stockiterator);
 
    	  stockDateNodes.clear();
    	  while(model.getRowCount()>1){
    		     model.removeRow(1);
    	  }

    	  
          while(stockIterator.hasNext()){

     			StockDateNode node=(StockDateNode) stockIterator.next();
      		    Vector row = new Vector();
      		    row.add(node.getDate());
      		    row.add(node.getOpen());
      		    row.add(node.getHigh());
      		    row.add(node.getClose());
      		    row.add(node.getLow());
      		    row.add(node.getVolume()/10000000);
      		    row.add(node.getPb());
      		    row.add(node.getAdj_price());
      		    row.add(node.getTurnover());
      		    model.addRow(row);	
      		    stockDateNodes.add(node);
		 }
	  
    }
    
    private static void refreshbyorder(String order, Iterator stockiterator){
    	sss = new SmartSort();    	
    	int index = attrList.indexOf(order);
    	DataType type = DataType.valueOf(attrlistEnglish.get(index));
    	
    	if(type==nowType){
    		if(isLowToUp){
    			stockIterator = sss.downSort(type, stockDateNodes.iterator());
    			isLowToUp = false;
    		}else{
    			stockIterator = sss.upSort(type, stockDateNodes.iterator());
    			isLowToUp = true;
    		}
    	}else{
    		stockIterator = sss.upSort(type, stockDateNodes.iterator());
    		isLowToUp = true;
    	}
    	nowType=type;
  	    while(model.getRowCount()>0){
	     	     model.removeRow(0);
	    }
  	    Vector sortrow = new Vector();
  	    System.out.println(order);
  	    for(int i=0;i<9;i++){
  	    	
  	    	if(order.equals(model.getColumnName(i))){
  	    		if(isLowToUp){
  	    			sortrow.add("↓");
  	    		}else{
  	    			sortrow.add("↑");
  	    		}
  	    	}else{
  	    		sortrow.add("-");
  	    	}
  	    }
  	    model.addRow(sortrow);
  	    
  	     stockDateNodes.clear();
     	
  	     while(stockIterator.hasNext()){
  	     	 StockDateNode node=(StockDateNode) stockIterator.next();
  	         Vector row = new Vector();
  	         row.add(node.getDate());
  	         row.add(node.getOpen());
  	         row.add(node.getHigh());
  	         row.add(node.getClose());
  	         row.add(node.getLow());
  	         row.add(node.getVolume()/10000000);
  	         row.add(node.getPb());
  	         row.add(node.getAdj_price());
  	         row.add(node.getTurnover());
  	         model.addRow(row);
  	         stockDateNodes.add(node);
  	     }
    	
    	
    	
    }
  
//  public static void changeColumn(FreePagePane freepage,String checkBoxName){
//	  FreeReportPage page=(FreeReportPage) freepage;
//	  TableColumnModel   columnModel=page.getTable().getColumnModel();   
//	  TableColumn   column,dateColumn;
//	  int index=-1;
//	  JCheckBox box;
//	  
//	  ArrayList<String> boxnamelist = new ArrayList<String>();
//	  
//	  for(JCheckBox box2:boxlist){
//		  boxnamelist.add(box2.getText());
//	  }
//	  index = boxnamelist.indexOf(checkBoxName);
//	  column=columnModel.getColumn(index+1);
//	  dateColumn=columnModel.getColumn(0);
//	  box = boxlist.get(index);
//	  
//	  
//	  if(box.isSelected()){
//
//		    column.setMaxWidth(300);
//		    column.setPreferredWidth(80);
//		    column.sizeWidthToFit();
//
//		    dateColumn.setMaxWidth(1500);
//		    dateColumn.setPreferredWidth(80);
//	  }else{
//		    column.setMinWidth(0);   
//		    column.setMaxWidth(0);
//		    column.setWidth(0);
//		    column.setPreferredWidth(0);
//	  }
//  }
    


}
