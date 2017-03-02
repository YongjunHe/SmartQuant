package financeui;

import impl.SmartSort;
import impl.SummaryCheck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import message.StockDateNode;
import message.SummaryDateNode;
import service.SmartSortService;
import service.StockCheckService;
import service.SummaryCheckService;
import twaver.TWaverUtil;
import dateChoose.DateChooserJButton;
import enums.MarketType;
import enums.SortType;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeStatusLabel;
import free.FreeTextField;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;

public class MarketPanel {
	public static JTabbedPane tab;
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
	

	
	public static FreeReportPage  createMarketPage(JTabbedPane tab,String marketNumber,String marketName,MarketType marketType){

	    MarketPanel.tab=tab;
	    MarketPanel.marketType=marketType;
	    MarketPanel.marketNumber=marketNumber;
	    MarketPanel.markrtName=marketName;
	    summaryCheckService = SummaryCheck.create();
	    

		
		return createReportPage();
	}
	

	

	
    private static FreeReportPage createReportPage() {
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
        model.addColumn("成交量");
        
    	attrList.add("日期");
    	attrList.add("开盘价");
    	attrList.add("最高价");
    	attrList.add("收盘价");
    	attrList.add("最低价");
    	attrList.add("成交量");
    	attrList.add("后复权价");
    	attrlistEnglish.add("date");
    	attrlistEnglish.add("open");
    	attrlistEnglish.add("high");
    	attrlistEnglish.add("close");
    	attrlistEnglish.add("low");
    	attrlistEnglish.add("volume");
    	attrlistEnglish.add("adj_price");
   
        
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	String nowTime=format.format(date);
    	date.setMonth(date.getMonth()-1);
    	String beginTime=format.format(date);
        
        
        stockIterator=summaryCheckService.getSummaryMessage(marketType, beginTime, nowTime);
        
        Vector row1=new Vector();
        row1.add(markrtName);
        model.addRow(row1);
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
        	row.add(node.getVolume());
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

        FreeReportPage page = new FreeReportPage();
        page.getTable().setModel(model);
        page.setDescription("All Work Order Items by Part Number. Created " + new Date().toString());
        setupPageToolbar(page);

        return page;
    }

    public static void setupPageToolbar(FreePagePane page) {
   	    FreeToolbarButton seekStock,filterStock;
   	    FreeStatusLabel filterLabel,lowerLimitLabel,higherLimitLabel,beginDateLabel,endDateLabel;
   	    JComboBox filterBox;
   	    FreeTextField lowerLimitField,higherlimitField;
   	    DateChooserJButton beginDateButton,endDateButton;
   	  
   	    beginDateLabel=new FreeStatusLabel("开始日期：");
   	    beginDateButton=new DateChooserJButton();
   	    endDateLabel=new FreeStatusLabel("结束日期：");
   	    endDateButton=new DateChooserJButton();
  	  
  	    page.getRightToolBar().add(beginDateLabel);
  	    page.getRightToolBar().add(beginDateButton);
  	    page.getRightToolBar().add(endDateLabel);
  	    page.getRightToolBar().add(endDateButton);
  	  
  	    seekStock=createButton("/free/test/print.png", "确认日期", true);
        page.getRightToolBar().add(seekStock);
      
		seekStock.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
                  refreshByDate(beginDateButton.getText(), endDateButton.getText());
		    }
		});
		
		
		
		
		
        filterLabel = new FreeStatusLabel("筛选条件：");
        filterBox = new JComboBox();
        for(String attr:attrList){
       	     if(!attr.equals("日期"))
    	     filterBox.addItem(attr);
        }
  
        lowerLimitLabel = new FreeStatusLabel("下限:");
        higherLimitLabel = new FreeStatusLabel("上限:");
        lowerLimitField = new FreeTextField();
        lowerLimitField.setColumns(4);
      
        higherlimitField = new FreeTextField();
        higherlimitField.setColumns(4);
      
        filterStock = createButton("/free/test/print.png", "筛选股票", true); 
        filterStock.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent arg0) 
		    {   
			    refreshByFilter(filterBox.getSelectedItem().toString(), lowerLimitField.getText(), higherlimitField.getText(), summaryDateNodes.iterator());
		    }
        });
      
        page.getLeftToolBar().add(filterLabel);
        page.getLeftToolBar().add(filterBox);
        page.getLeftToolBar().add(lowerLimitLabel);
        page.getLeftToolBar().add(lowerLimitField);
        page.getLeftToolBar().add(higherLimitLabel);
        page.getLeftToolBar().add(higherlimitField);
        page.getLeftToolBar().add(filterStock);
		
		
		
		
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
	  
      stockIterator=summaryCheckService.getSummaryMessage(marketType, beginDate, endDate);
    
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
    	  row.add(node.getVolume());
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
  	  SortType type = SortType.valueOf(attrlistEnglish.get(index));
  	  double low = 0, high = Double.MAX_VALUE;
  	  if(!lower.equals("")){
  		     low = Double.parseDouble(lower);
  	  }
  	  if(!higher.equals("")){
  		     high = Double.parseDouble(higher);
  	  }
  	  

  	  message.Range range = new message.Range(low, high, !lower.equals(""), !higher.equals(""));
  	  stockIterator = sss.sort(type, range, iterator);
  	  
  	  
  	  while(model.getRowCount()>0){
  		     model.removeRow(0);
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
    	  row.add(node.getVolume());
    	  model.addRow(row);
      }
	  
  }
  
}
