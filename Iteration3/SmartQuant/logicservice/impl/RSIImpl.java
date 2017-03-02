package impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.methods.multipart.StringPart;

import enums.Cyc;
import enums.DataType;
import enums.MarketType;
import message.NodeService;
import message.UniversalDateNode;
import service.RSIService;
import service.StockDisposeService;
import service.SummaryDisposeService;

public class RSIImpl implements RSIService{
	private SummaryDisposeService summaryService;
	private StockDisposeService stockService;
	private volatile static RSIImpl impl;

	private RSIImpl() {
		// TODO Auto-generated constructor stub
		summaryService = new SummaryDataDispose();
		stockService = new StockDataDispose();

	}
	
	/*
	 * @author: xuan
	 * @date: 2016/05/31
	 * @description: 单体模式的运用
	 */
	public static RSIImpl create(){
		if(impl == null){
			synchronized(RSIImpl.class){

				if(impl == null)
					impl = new RSIImpl();
			}
		}

		return impl;
	}



	@Override
	public Iterator getDayRSI(String idNumber, String beginDate, String endDate){
		// TODO Auto-generated method stub
		List<NodeService> list = null;
		if(idNumber.equals("hs300"))
			try {
				list = summaryService.getSummaryMessage(MarketType.hs300, beginDate, endDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			try {
				list = stockService.getStockMessage(idNumber, beginDate, endDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		List<UniversalDateNode>nodeList = new ArrayList<>();	

		Iterator iterator1 = list.iterator();
		while(iterator1.hasNext()){

			double A = 0;
			double B = 0;

			NodeService n = (NodeService) iterator1.next();

			double open = n.getType(DataType.open);
			double close = n.getType(DataType.close);
			if(open < close)
				A = (close - open) / open;
			else
				B = (open - close) / open;

 

			UniversalDateNode node = new UniversalDateNode();
			double RS = A / (A + B);
			node.setData(100 - 100 / (1 + RS));
			node.setDate(n.getDate());
			node.setVary(Cyc.day);
			
			nodeList.add(node);
		}

		return nodeList.iterator();

	}

	@Override
	public Iterator getWeekRSI(String idNumber, String beginDate,
			String endDate)  {
		// TODO Auto-generated method stub

		//解析日期
		String beginDay = FinalSign.setMondayDate(beginDate);
		String endDay = FinalSign.setFirdayDate(endDate);	
		//解析结束

		return RSI(idNumber, beginDay, endDay);
	}


	public Iterator RSI(String idNumber, String beginDay, String endDay){
		List<NodeService> list = null;
		if(idNumber.equals("hs300"))
			try {
				list = summaryService.getSummaryMessage(MarketType.hs300, beginDay, endDay);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			try {
				list = stockService.getStockMessage(idNumber, beginDay, endDay);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		List<UniversalDateNode>nodeList = new ArrayList<>();	

		Iterator iterator1 = list.iterator();
		while(Integer.valueOf(beginDay.replace("-", "")) < Integer.valueOf(endDay.replace("-", ""))){

			double A = 0;
			double B = 0;

			while(iterator1.hasNext()){
				NodeService n = (NodeService) iterator1.next();
				if(n.compareTo(beginDay) >= 0&&n.compareTo(FinalSign.setFirdayDate(beginDay))<=0){
					double open = n.getType(DataType.open);
					double close = n.getType(DataType.close);
					if(open < close)
						A = (close - open) / open + A;
					else
						B = (open - close) / open + B;
				}else
					break;

			}


			UniversalDateNode node = new UniversalDateNode();
			double RS = A / (A + B);
			node.setData(100 - 100 / (1 + RS));
			node.setDate(beginDay);
			nodeList.add(node);


			beginDay =  FinalSign.weekChange(beginDay,1);
		}
		return nodeList.iterator();

	}
	
	
	public static void main(String[]args){
		StockDisposeService stockDisposeService = new StockDataDispose();
	
		RSIImpl rsiService = RSIImpl.create();
		Iterator iterator = null;
			iterator = rsiService.getDayRSI("sh600022", "2016-01-01", "2016-05-13");
		while(iterator.hasNext()){
			UniversalDateNode node = (UniversalDateNode) iterator.next();
			System.out.println(node.getData() + " " + node.getDate());
		}
		
		
		
		
		
		
		
		
	}

}
