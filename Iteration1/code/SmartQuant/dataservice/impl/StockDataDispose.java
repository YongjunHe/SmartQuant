package impl;

/*
 * @author: xuan
 * @date: 2016/03/04
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 对来自Http的股票数据进行处理
 */


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import message.Stock;
import message.StockDateNode;
import message.StockList;
import message.StockMessage;
import service.StockCheckService;
import service.StockDisposeService;


public class StockDataDispose implements StockDisposeService{
	private static final String URL = "http://121.41.106.89:8010/api/stock/";
	private static final String API = "api/stocks/?year=";
	private static final String EXCHANGE = "&exchange=sh";
	private static final String CHECKTYPE = "&fields=open+high+close+low+volume+adj_price+turnover+pb"; 
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SDF = new SimpleDateFormat("EEEE");
	private static final String SUNDAY = "星期日";
	private static final String MONDAY = "星期一";
	private static final double DAYTIME = 24 * 60 * 60 * 1000;

	/*
	 * @author: xuan
	 * @date: 2016/03/04
	 * @description: 获得股票列表
	 */
	public Map getStockList(String year){
		String url = API + year + EXCHANGE;
		String response = HttpHelper.getHttp(url);
		Gson gson = new Gson();		
		StockList stockList = gson.fromJson(response, StockList.class);
		Map<String, Stock>stocks = stockList.getData().stream().collect(Collectors.toMap(Stock::getName,(p) -> p));//list translates to map, cost a lot need to fix 
		return stocks;
	}

	/*
	 * @author: xuan
	 * @date: 2016/03/04
	 * @description: 获得指定股票在指定时间内的具体信息
	 */
	public List getStockMessage(String idNumber, String beginDate, String endDate){
		String url = "api/stock/"+ idNumber +"/?start=" + beginDate + "&end=" + endDate
				+ CHECKTYPE;
		String response = HttpHelper.getHttp(url);
		Gson gson = new Gson();
		StockMessage stockMessage = null;
		try {
			stockMessage = gson.fromJson(response, StockMessage.class);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		List dateMessage = stockMessage.getData().getTrading_info();
		Collections.sort(dateMessage);
		return dateMessage;
	}

	/*
	 * @author: xuan
	 * @date: 2016/03/08
	 * @description: 获得最新一天数据
	 */

	public StockDateNode  getStockDayMessage(String idNumber){
		Date date = new Date();
		String endDate = DF.format(date);
		String beginDate = null;
		if(SDF.format(date).equals(MONDAY))
			beginDate = DF.format(date.getTime() - (long)3 * DAYTIME);
		else if(SDF.format(date).equals(SUNDAY)){
			beginDate = DF.format(date.getTime() - (long)2 * DAYTIME);
		}else {
			beginDate = DF.format(date.getTime() - (long)DAYTIME);
		}
		List<StockDateNode>dateMessage = getStockMessage(idNumber, beginDate, endDate);
		
		return dateMessage.get(0);
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/04
	 * @description: 测试代码
	 */

}
