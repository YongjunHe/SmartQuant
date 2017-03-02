package data.impl;

import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

/*
 * @author: xuan
 * @date: 2016/03/04
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 对大盘点数据进行处理
 */

import java.util.Map;
import java.util.stream.Collectors;
import com.google.gson.Gson;

import data.enums.MarketType;
import data.message.Summary;
import data.message.SummaryDateNode;
import data.message.SummaryMessage;
import data.service.SummaryDisposeService;


public class SummaryDataDispose implements SummaryDisposeService{
	

	private static final String CHECKTYPE = "&fields=open+high+close+low+volume+adj_price+turnover+pb"; 
	private static final String[] MARKETTYPE = {"hs300"};
	private static JDBCHelper helper;
	
	
	
	
	public SummaryDataDispose(){
//		
//		try {
//			service_RMI = DataFactory.create().getSummaryService_RMI();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	helper = JDBCHelper.create();
	}
	
	/*
	 * @author: xuan
	 * @date: 2016/03/04
	 * @description: 获得制定交易所的指定时间内的大盘数据
	 */
	
	public List getSummaryMessage(MarketType marketType, String beginDate, String endDate) throws Exception{
//		String url = "api/benchmark/"+ MARKETTYPE[marketType.ordinal()] +"?start=" + beginDate + "&end=" + endDate
//				+ CHECKTYPE;
//		String response = HttpHelper.getHttp(url);
//		Gson gson = new Gson();		
//		SummaryMessage summaryMessage = gson.fromJson(response, SummaryMessage.class);
//		List<SummaryDateNode>dateMessage = summaryMessage.getData().getTrading_info();
//		Collections.sort(dateMessage);
//		return dateMessage;
		
		//RMI连接数据库
		Summary summary = helper.selectSummary(marketType.toString(), beginDate, endDate);
		
		List<SummaryDateNode>lists = summary.getDateMessage();
		Collections.sort(lists);
		return lists;
		
		
		
	}
	
	
}
