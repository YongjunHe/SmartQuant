package financeui;

import impl.SmartSort;
import impl.SummaryCheck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import message.StockDateNode;
import message.SummaryDateNode;
import service.SmartSortService;
import service.StockCheckService;
import service.SummaryCheckService;
import smartUI.SmartComboBox;
import smartUI.SmartLabel;
import smartUI.SmartReportPane;
import smartUI.SmartTextField;
import smartUI.SmartToolBar;
import twaver.TWaverUtil;
import dateChoose.DateChooserJButton;
import enums.Cyc;
import enums.MarketType;
import enums.DataType;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeStatusLabel;
import free.FreeTextField;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;

public class MarketPanel {

	private static  DefaultTableModel model;
	private static SummaryCheckService summaryCheckService;
    private static SmartSortService sss;
	private static MarketType marketType;
	private static String markrtName;
	private static String marketNumber;
	private static JCheckBox openBox,closeBox,highBox,lowBox,adjPriceBox,volumeBox;
	private static ArrayList<JCheckBox> boxlist;
    private static ArrayList<String> attrList,attrlistEnglish;
    private static Iterator stockIterator;
    private static ArrayList<SummaryDateNode> summaryDateNodes;
    private static boolean isLowToUp;
    private static DataType nowType;
    private static Cyc nowKChartType = Cyc.day;
	

	
	public static SmartReportPane  createMarketPage(String marketNumber,String marketName,MarketType marketType) throws Exception{

	    MarketPanel.marketType=marketType;
	    MarketPanel.marketNumber=marketNumber;
	    MarketPanel.markrtName=marketName;
	    summaryCheckService = SummaryCheck.create();
	    

		
		return createReportPage();
	}
	

	

	
    private static SmartReportPane createReportPage() throws Exception {
    	attrList = new ArrayList<String>();
    	attrlistEnglish = new ArrayList<String>();
    	summaryDateNodes = new ArrayList<SummaryDateNode>();
        model = new DefaultTableModel();
        model.addColumn("日期");
        model.addColumn("指数名称");
        model.addColumn("指数代号");
        model.addColumn("开盘价");
        model.addColumn("收盘价");
        model.addColumn("最高价");
        model.addColumn("最低价");
        model.addColumn("后复权价");
        model.addColumn("成交量(千万股)");
        
    	attrList.add("日期");
    	attrList.add("开盘价");
    	attrList.add("最高价");
    	attrList.add("收盘价");
    	attrList.add("最低价");
    	attrList.add("成交量(千万股)");
    	attrList.add("后复权价");
    	attrlistEnglish.add("date");
    	attrlistEnglish.add("open");
    	attrlistEnglish.add("high");
    	attrlistEnglish.add("close");
    	attrlistEnglish.add("low");
    	attrlistEnglish.add("volume");
    	attrlistEnglish.add("adj_price");
    	
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
        
        
        stockIterator=summaryCheckService.getSummaryMessage(marketType, beginTime, nowTime);
        

        summaryDateNodes.clear();
        while(stockIterator.hasNext()){
        	SummaryDateNode node=(SummaryDateNode) stockIterator.next();
        	Vector row=new Vector();
        	row.add(node.getDate());
        	row.add(markrtName);
        	row.add(marketNumber);
        	row.add(node.getOpen());
        	row.add(node.getClose());
        	row.add(node.getHigh());
        	row.add(node.getLow());
        	row.add(node.getAdj_price());
        	row.add(node.getVolume()/10000000);
        	model.addRow(row);
        	summaryDateNodes.add(node);
        }
//
//        int test=0;
//        for (int i = 0; i < 100; i++) {
//            Vector row = new Vector();
//            row.add(nowTime);
//            row.add(test+"");
//            row.add(test+"");
//            row.add(test+"");
//            row.add(test+"");
//            row.add(test+"");
//            row.add(test+"");
//            row.add(test+"");
//            row.add(test+"");
//            test++;
//
//            model.addRow(row);
//        }

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
					refreshbyorder(model.getColumnName(col), summaryDateNodes.iterator());
				}
			}
		});

        return page;
    }

    public static void setupPageToolbar(SmartReportPane page) {
   	    FreeToolbarButton seekStock,filterStock;
   	    SmartLabel filterLabel,lowerLimitLabel,higherLimitLabel,beginDateLabel,endDateLabel,graphTypeLabel;
   	    SmartComboBox filterBox,graphTypeBox;
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
        
        page.getLeftToolBar().add(graphTypeLabel);
        page.getLeftToolBar().add(graphTypeBox);
        
      
        seekStock.addMouseListener(new MouseAdapter(){
   		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
   		    	String graphTypeStr = graphTypeBox.getSelectedItem().toString();
   		    	if(graphTypeStr.equals("表格")){
  			      refreshByDate(beginDateButton.getText(), endDateButton.getText());
 
   		    	}else if(graphTypeStr.equals("K线图")){
   		    		try {
   	   		    		page.showKchartOfSummary(MarketType.hs300, beginDateButton.getText(), endDateButton.getText(),nowKChartType);
					} catch (Exception e) {
						// TODO: handle exception
			        	JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
					}


   		    	}

		    }
        });
		
		
		
		
		
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
			    refreshByFilter(filterBox.getSelectedItem().toString(), lowerLimitField.getText(), higherlimitField.getText(), summaryDateNodes.iterator());
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
							page.showKchartOfSummary(MarketType.hs300, beginTime, endTime, Cyc.day);
							page.getLeftToolBar().remove(graphTools);
	
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
						}

	   		    		

	   		    		


					}else if(seleteGraphStr.equals("表格")){
						refreshByDate(beginTime, endTime);
						page.showGraph();

						page.getLeftToolBar().add(graphTools);
						
					}
				}
			}
		});
		
		
		
//	    ActionListener listener=new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				JCheckBox box=(JCheckBox) e.getSource();
//				changeColumn(page,box.getText());
//				
//			}
//		};	
//				
//	    openBox = new JCheckBox("开盘价",true);
//	    closeBox = new JCheckBox("收盘价",true);
//	    highBox = new JCheckBox("最高价",true);
//	    lowBox = new JCheckBox("最低价",true);
//	    adjPriceBox = new JCheckBox("后复权价",true);	    
//	    volumeBox = new JCheckBox("交易量",true);
//	
//	
//	    boxlist=new ArrayList<JCheckBox>();
//	    boxlist.add(openBox);
//	    boxlist.add(closeBox);	    
//	    boxlist.add(highBox);
//	    boxlist.add(lowBox);
//	    boxlist.add(adjPriceBox);
//	    boxlist.add(volumeBox);
//	    
//	    for(JCheckBox box:boxlist){
//		   box.addActionListener(listener);
//		   page.getLeftToolBar().add(box);	
//		}
	   
      
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
	    
        Vector sortrow = new Vector();
        sortrow.add("↓");
        for(int i=0;i<8;i++){
        	sortrow.add("-");
        }
        model.addRow(sortrow);
        isLowToUp = true;
        nowType = DataType.date;
	  
        
        try {
			stockIterator=summaryCheckService.getSummaryMessage(marketType, beginDate, endDate);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "大盘信息在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
		}

    
      summaryDateNodes.clear();
      while(stockIterator.hasNext()){
      	  SummaryDateNode node=(SummaryDateNode) stockIterator.next();
    	
    	  Vector row=new Vector();
    	  row.add(node.getDate());
    	  row.add(markrtName);
    	  row.add(marketNumber);
    	  row.add(node.getOpen());
    	  row.add(node.getClose());
    	  row.add(node.getHigh());
    	  row.add(node.getLow());
    	  row.add(node.getAdj_price());
    	  row.add(node.getVolume()/10000000);
    	  model.addRow(row);
    	  summaryDateNodes.add(node);
      }
	  
	  	  
  }
//    public static void changeColumn(FreePagePane freepage,String checkBoxName){
//    	
//	    FreeReportPage page=(FreeReportPage) freepage;
//	    TableColumnModel   columnModel=page.getTable().getColumnModel();   
//	    TableColumn   column,dateColumn;
//	    int index=-1;
//	    int before=3;
//	    JCheckBox box;
//	  
//	    ArrayList<String> boxnamelist = new ArrayList<String>();
//	    ArrayList<TableColumn> beforeColumnlist=new ArrayList<TableColumn>();
//	  
//	  
//	    for(JCheckBox box2:boxlist){
//		    boxnamelist.add(box2.getText());
//	    }
//
//
//	    index = boxnamelist.indexOf(checkBoxName);
//	    column=columnModel.getColumn(index+before);
//        for(int i=0;i<before;i++){
//    	    beforeColumnlist.add(columnModel.getColumn(i));
//        }
//	  
//	  
//	    box = boxlist.get(index);
//	  
//	  
//	    if(box.isSelected()){
//
//		    column.setMaxWidth(300);
//		    column.setPreferredWidth(80);
//		    column.sizeWidthToFit();
//
//		    for(TableColumn beforeColumn:beforeColumnlist){
//		    	beforeColumn.setMaxWidth(1500);
//		    	beforeColumn.setPreferredWidth(80);
//		    }
//	    }else{
//		    column.setMinWidth(0);   
//		    column.setMaxWidth(0);
//		    column.setWidth(0);
//		    column.setPreferredWidth(0);
//	    }
//    }
    
    
    private static void refreshByFilter(String filterAttr,String lower,String higher,Iterator iterator){
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
  	  stockIterator = sss.sort(type, range, iterator);
  	  
  	  summaryDateNodes.clear();
  	  while(model.getRowCount()>1){
  		     model.removeRow(1);
  	  }
  	  
      while(stockIterator.hasNext()){
      	  SummaryDateNode node=(SummaryDateNode) stockIterator.next();
    	
    	  Vector row=new Vector();
    	  row.add(node.getDate());
    	  row.add(markrtName);
    	  row.add(marketNumber);
    	  row.add(node.getOpen());
    	  row.add(node.getClose());
    	  row.add(node.getHigh());
    	  row.add(node.getLow());
    	  row.add(node.getAdj_price());
    	  row.add(node.getVolume()/10000000);
    	  model.addRow(row);
		  summaryDateNodes.add(node);
      }
	  
  }
    
    private static void refreshbyorder(String order, Iterator stockiterator){
    	sss = new SmartSort();    	
    	int index = attrList.indexOf(order);
    	DataType type = DataType.valueOf(attrlistEnglish.get(index));
    	
    	if(type==nowType){
    		if(isLowToUp){
    			stockIterator = sss.downSort(type, summaryDateNodes.iterator());
    			isLowToUp = false;
    		}else{
    			stockIterator = sss.upSort(type, summaryDateNodes.iterator());
    			isLowToUp = true;
    		}
    	}else{
    		stockIterator = sss.upSort(type, summaryDateNodes.iterator());
    		isLowToUp = true;
    	}
    	nowType=type;
  	    while(model.getRowCount()>0){
	     	     model.removeRow(0);
	    }
  	    Vector sortrow = new Vector();
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
  	    
  	     summaryDateNodes.clear();
     	
  	     while(stockIterator.hasNext()){
  	     	 SummaryDateNode node=(SummaryDateNode) stockIterator.next();
  	    	  Vector row=new Vector();
  	    	  row.add(node.getDate());
  	    	  row.add(markrtName);
  	    	  row.add(marketNumber);
  	    	  row.add(node.getOpen());
  	    	  row.add(node.getClose());
  	    	  row.add(node.getHigh());
  	    	  row.add(node.getLow());
  	    	  row.add(node.getAdj_price());
  	    	  row.add(node.getVolume()/10000000);
  	         model.addRow(row);
  	         summaryDateNodes.add(node);
  	     }
    	
    	
    	
    }
    

  
}
