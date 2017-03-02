package impl;


/*
 * @author: xuan
 * @date: 2016/03/04
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 对股票数据进行加工查询
 */

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import enums.Location;
import enums.MessageType;
import message.Stock;
import message.StockDateNode;
import service.StockCheckService;
import service.StockDisposeService;
import sun.reflect.generics.tree.VoidDescriptor;

import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;


public class StockCheck implements StockCheckService{
	private static final int NUMBER = 20;
	private volatile static StockCheck stockCheck;
	private Map<Location, Map<String, Stock>> marketStock; //内存股票的中文名列表
	private Map<String, Stock> selectStockList; //内存推荐股票列表
	private StockDisposeService stockDataDispose; //数据层的服务

	public Stock getStock(String name){
		return marketStock.get(Location.valueOf(name.substring(0,2))).get(name);
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 对象初始化
	 */

	private StockCheck(){
		stockDataDispose = new StockDataDispose();
		Map<String, Stock> stockList1 = stockDataDispose.getStockList(Location.sh);//获得2016年股票列表
		Map<String, Stock> stockList2 = stockDataDispose.getStockList(Location.sz);
		marketStock = new HashMap();
		marketStock.put(Location.sh, stockList1);
		marketStock.put(Location.sz, stockList2);
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 获得制定特定股票在特定时间的具体信息
	 * 
	 * 
	 * @change: 改变了数据结构
	 * @changeDate: 2016/03/18
	 */

	@Override
	public Iterator<StockDateNode> getStockMessage(String idNumber, String beginDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
//		返回临时变量，由上层直接保存持有数据
		List<StockDateNode> dateMessage = stockDataDispose.getStockMessage(idNumber, beginDate, endDate);	
        return dateMessage.iterator();
        
        
//		Stock stock = null;
//		Map<String, Stock> stockList = marketStock.get(Location.valueOf(idNumber.substring(0, 2)));
//
//		try {
//			stock = stockList.get(idNumber);
//		} catch (NullPointerException e) {
//			// TODO: handle exception
//			System.err.println(e);
//		}
//
//		if(stock==null){
//			stock = new Stock();
//			stock.setTodayData(stockDataDispose.getStockDayMessage(stock.getName()));
//			stock.setName(idNumber);
//		}
//		stock.setDateMessage(dateMessage);
//		stockList.put(idNumber, stock);
//		return stock.getAllDateMessage();
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 返回特定股票默认一个月的信息
	 * @other: MessageType只有name
	 */

	@Override
	public Iterator<StockDateNode> getStockList(MessageType type, String Message) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String endDate = FinalSign.DF.format(date); 
		Calendar rightNow = Calendar.getInstance();  
		rightNow.setTime(date);  
		rightNow.add(Calendar.MONTH, -1);  
		String beginDate = FinalSign.DF.format(rightNow.getTime());

		//对于MessageTpye暂无
		return this.getStockMessage(Message, beginDate, endDate);
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 返回随机股票，只包括sh
	 * 
	 * @mender: xuan
	 * @date: 2016/04/06
	 * @description: 实现推荐股票
	 */
	@Override
	public Iterator<Stock> getSelectedStock() throws Exception {
		// TODO Auto-generated method stub

		if(selectStockList == null)
			selectStockList = stockDataDispose.getSelectStockList();
			
		return selectStockList.values().iterator();
	}

	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 获得股票的列表
	 * 
	 * @change: 增加市场的参数
	 * @changeDate: 2016/03/18
	 */
	@Override
	public Iterator<Stock> getAllStock(Location location) {
		// TODO Auto-generated method stub
		return marketStock.get(location).values().iterator();
	}

	/*
	 * @author: xuan
	 * @date: 2016/03/10
	 * @description: 单体模式的运用
	 */
	public static StockCheck create(){
		if(stockCheck == null){
			synchronized(StockCheck.class){

				if(stockCheck == null)
					stockCheck = new StockCheck();
			}
		}
		return stockCheck;
	}


	
	/*
	 * @author: xuan
	 * @date: 2016/04/13
	 * @description: 获得股票的中文名
	 */
	@Override
	public String getCName(String idNumber) {
		// TODO Auto-generated method stub
		return marketStock.get(Location.valueOf(idNumber.substring(0, 2))).get(idNumber).getCName();
	}



}
