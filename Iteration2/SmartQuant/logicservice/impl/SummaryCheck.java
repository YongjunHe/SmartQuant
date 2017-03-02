package impl;

/*
 * @author: xuan
 * @date: 2016/03/05
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description:SummaryCheckService接口的实现类
 */

import java.awt.Window.Type;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import enums.CompareType;
import enums.MarketType;
import message.Stock;
import message.Summary;
import service.SummaryCheckService;
import service.SummaryDisposeService;

public class SummaryCheck implements SummaryCheckService{
	
	private Map<Type , Summary> summaryList;
	private volatile static SummaryCheck summaryCheck;
	private SummaryDisposeService summaryDataDispose;
	private static final String[] MARKETTYPE = {"hs300"};
	
	
	private SummaryCheck() {
		// TODO Auto-generated constructor stub
		summaryDataDispose = new SummaryDataDispose();
		summaryList = new HashMap<>();
		
	}
	
	  public Summary getStock(Type type){
	    	 return summaryList.get(type);
	     }
	
	
	/*
	 * @author: xuan
	 * @date: 2016/03/05
	 * @description: 获得指定交易所的指定时间内的大盘点数据
	 */
	@Override
	public Iterator getSummaryMessage(MarketType marketType, String beginDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
		Summary summary = summaryList.get(marketType);
		if(summary==null){
			summary = new Summary();
		    summary.setName(MARKETTYPE[marketType.ordinal()]);
		}
		List summaryMessage = summaryDataDispose.getSummaryMessage(marketType, beginDate, endDate);
        summary.setSummaryDataNode(summaryMessage);	
		return summary.getAllDateMessage();
	}

	@Override
	public Iterator getSummaryMessageByDrop(MarketType marketType, CompareType CompareType, double dropRate,
			String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getSummaryMessageByRise(MarketType marketType, CompareType CompareType, double riseRate,
			String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*
	 * @author: xuan
	 * @date: 2016/03/10
	 * @description: 单体模式的运用
	 */
	public static SummaryCheck create(){
		if(summaryCheck == null){
			synchronized(SummaryCheck.class){

				if(summaryCheck == null)
					summaryCheck = new SummaryCheck();
			}
		}

		return summaryCheck;
	}


}
