package financeui;

import impl.SmartSort;
import impl.StockCheck;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.NumericShaper.Range;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.security.auth.Refreshable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;




import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.MaskFormatter;

import jxl.biff.drawing.CheckBox;
import message.StockDateNode;
import dateChoose.DateChooserJButton;
import enums.SortType;
import free.BaseUI;
import free.FreePagePane;
import free.FreeReportPage;
import free.FreeSearchTextField;
import free.FreeStatusLabel;
import free.FreeTextField;
import free.FreeToolbarButton;
import free.FreeToolbarRoverButton;
import free.FreeUtil;
import service.SmartSortService;
import service.StockCheckService;
import smartUI.SmartPanel;
import twaver.TWaverUtil;
import twaver.base.A.E.f;
import message.*;

public class StockPanel{
	public static JTabbedPane tab;
	private static String stockNumber;
	private static  DefaultTableModel model;
	private static StockCheckService scs;
    private static SmartSortService sss;
	private static JCheckBox openBox,highBox,closeBox,lowBox,volumeBox,pbBox,adjPriceBox,turnoverBox;
	private static ArrayList<JCheckBox> boxlist;
    private static ArrayList<String> attrList,attrlistEnglish;
    private static Iterator stockIterator;
    private static ArrayList<StockDateNode> stockDateNodes;
	  
	
	
	

	
	public static FreeReportPage  createStockPage(JTabbedPane tab,String stockNumber){

	    StockPanel.tab=tab;
	    StockPanel.stockNumber=stockNumber;
	    
	    scs=StockCheck.create();


	    

        
		
		return createReportPage();
	}
	

	

	
    private static FreeReportPage createReportPage() {
    	attrList = new ArrayList<String>();
    	attrlistEnglish = new ArrayList<String>();
    	stockDateNodes = new ArrayList<StockDateNode>();
    	attrList.add("日期");
    	attrList.add("开盘价");
    	attrList.add("最高价");
    	attrList.add("收盘价");
    	attrList.add("最低价");
    	attrList.add("交易量（股）");
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
            row.add(node.getVolume());
            row.add(node.getPb());
            row.add(node.getAdj_price());
            row.add(node.getTurnover());
            model.addRow(row);
            stockDateNodes.add(node);
        }
        


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
		    	Iterator iterator = stockDateNodes.iterator();
//		    	while(iterator.hasNext()){
//		    		StockDateNode node = (StockDateNode) iterator.next();
//		    		System.out.println(node.getDate());
//		    	}
			    refreshByFilter(filterBox.getSelectedItem().toString(), lowerLimitField.getText(), higherlimitField.getText(), stockDateNodes.iterator());
		    }
        });
      
        page.getLeftToolBar().add(filterLabel);
        page.getLeftToolBar().add(filterBox);
        page.getLeftToolBar().add(lowerLimitLabel);
        page.getLeftToolBar().add(lowerLimitField);
        page.getLeftToolBar().add(higherLimitLabel);
        page.getLeftToolBar().add(higherlimitField);
        page.getLeftToolBar().add(filterStock);
      
      
		
	  	
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
      System.out.println(stockIterator.hasNext());
      try {
    	  stockIterator=scs.getStockMessage(stockNumber, beginDate, endDate);
      } catch (Exception e) {
    	// TODO: handle exception
        	JOptionPane.showMessageDialog(null, "对应股票在对应日期内数据有误", "错误",JOptionPane.WARNING_MESSAGE);
      }
     

     stockDateNodes.clear();
    	
     while(stockIterator.hasNext()){
     	 StockDateNode node=(StockDateNode) stockIterator.next();
         Vector row = new Vector();
         row.add(node.getDate());
         row.add(node.getOpen());
         row.add(node.getHigh());
         row.add(node.getClose());
         row.add(node.getLow());
         row.add(node.getVolume());
         row.add(node.getPb());
         row.add(node.getAdj_price());
         row.add(node.getTurnover());
         model.addRow(row);
         stockDateNodes.add(node);
     }
	  
	  	  
  }
  
    private static void refreshByFilter(String filterAttr,String lower,String higher,Iterator stockiterator){
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
    	  System.out.println(type.toString());
    	  System.out.println(range.getLower());
    	  

    	  
    	  stockIterator = sss.sort(type, range, stockiterator);
 
    	  
    	  while(model.getRowCount()>0){
    		     model.removeRow(0);
    	  }

    	  
          while(stockIterator.hasNext()){
        	  System.out.println("hh");
     			StockDateNode node=(StockDateNode) stockIterator.next();
      		    Vector row = new Vector();
      		    row.add(node.getDate());
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
