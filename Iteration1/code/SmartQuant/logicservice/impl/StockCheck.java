package impl;

/*
 * @author: xuan
 * @date: 2016/03/05
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description:StockCheckService接口的实现类
 */


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
import enums.MessageType;
import message.Stock;
import message.StockDateNode;
import service.StockCheckService;
import service.StockDisposeService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;


public class StockCheck implements StockCheckService{
	private static final int NUMBER = 20;
	private volatile static StockCheck stockCheck;
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private Map<String, Stock> stockList;
	private Map<String, Stock> selectStockList;
	private StockDisposeService stockDataDispose;



	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 对象初始化
	 */

	private StockCheck(){
		stockDataDispose = new StockDataDispose();
		stockList = stockDataDispose.getStockList("2015");//获得2015年股票列表
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 获得制定特定股票在特定时间的具体信息
	 */

	@Override
	public Iterator<StockDateNode> getStockMessage(String idNumber, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		List<StockDateNode> dateMessage = stockDataDispose.getStockMessage(idNumber, beginDate, endDate);	
		Stock stock = null;
		try {
			stock = stockList.get(idNumber);
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.err.println(e);
		}

		if(stock==null){
			stock = new Stock();
			stock.setName(idNumber);
		}
		stock.setDateMessage(dateMessage);
		stockList.put(idNumber, stock);


		return stock.getAllDateMessage();
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 返回特定股票默认一个月的信息
	 * @other: MessageType只有name
	 */

	@Override
	public Iterator<StockDateNode> getStockList(MessageType type, String Message) {
		// TODO Auto-generated method stub
		Date date = new Date();
		String endDate = DF.format(date); 
		Calendar rightNow = Calendar.getInstance();  
		rightNow.setTime(date);  
		rightNow.add(Calendar.MONTH, -1);  
		String beginDate = DF.format(rightNow.getTime());

		//对于MessageTpye暂无
		return this.getStockMessage(Message, beginDate, endDate);
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 返回随机股票
	 */
	@Override
	public Iterator<Stock> getSelectedStock() {
		// TODO Auto-generated method stub
		selectStockList = new HashMap<>();
		Iterator<Stock> iterator = stockList.values().iterator();
		for(int i = 0; i < NUMBER ; i++){
			if(!iterator.hasNext())
				break;
			Stock stock = iterator.next();
			stock.setTodayData(stockDataDispose.getStockDayMessage(stock.getName()));		 
			selectStockList.put(stock.getName(), stock);
		}
		return selectStockList.values().iterator();
	}

	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 获得股票的列表
	 */
	@Override
	public Iterator<Stock> getAllStock() {
		// TODO Auto-generated method stub
		return stockList.values().iterator();
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



}
