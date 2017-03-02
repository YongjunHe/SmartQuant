package logic.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import data.enums.*;
import data.helper.*;
import data.impl.*;
import data.message.*;
import data.service.*;

import logic.service.*;


public class KDJImpl implements KDJService{


	private SummaryDisposeService summaryService;
	private StockDisposeService stockService;
	private volatile static KDJImpl impl;
	private SmartSortService sortService;
	private SimpleStockService simpleStockService;


	private KDJImpl() {
		// TODO Auto-generated constructor stub
		summaryService = new SummaryDataDispose();
		stockService = new StockDataDispose();
		simpleStockService = SimpleStockImpl.create();
		sortService = new SmartSort();

	}

	/*
	 * @author: xuan
	 * @date: 2016/06/01
	 * @description: 单体模式的运用
	 */
	public static KDJImpl create(){
		if(impl == null){
			synchronized(KDJImpl.class){

				if(impl == null)
					impl = new KDJImpl();
			}
		}

		return impl;
	}

	@Override
	public Iterator getDayKDJ(String idNumber, String beginDate, String endDate)
			throws Exception {
		// TODO Auto-generated method stub
		//日期处理
		Date bDate = null;
		beginDate = MeanCaculation.dateDispose(beginDate);
		try {
			bDate = FinalSign.DF.parse(beginDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beginDate = FinalSign.DF.format(bDate.getTime() - (long)FinalSign.CANCEL[Cyc.week.ordinal()]*FinalSign.DAYTIME);

		List<NodeService>dataNodeList = null;

		if(idNumber.equals("sh000300"))
			dataNodeList = summaryService.getSummaryMessage(MarketType.hs300, beginDate, endDate); 	
		else
			dataNodeList = stockService.getStockMessage(idNumber, beginDate, endDate);

		if(dataNodeList.isEmpty())
			return null;

		//数据获取完成
		int time = (int) FinalSign.DAYS[Cyc.week.ordinal()];
		return KDJ(time, dataNodeList.iterator(), Cyc.day);

	}


	public Iterator KDJ(int time, Iterator<NodeService> iterator, Cyc cyc){
		ArrayList<KDJDateNode>nodes = new ArrayList<>();

		double K = 50;
		double D = 50;
		double J = 0;
		double RSV = 0;

		ArrayList<NodeService>temp = new ArrayList<>();



		for(int i = 0; i<time;i++){
			temp.add(iterator.next());

		}

		while(true){
			NodeService t = temp.get(time-1);
			double C = t.getType(DataType.close);
			double H = 
					((NodeService)sortService.downSort(DataType.high, temp.iterator()).next()).getType(DataType.high);
			double L = 
					((NodeService)sortService.upSort(DataType.low,temp.iterator()).next()).getType(DataType.low);

			RSV = (C - L)/(H - L) * 100;
			K = (double)2/3 * K + (double)1/3 * RSV;
			D = (double)2/3 * D + (double)1/3 * K;
			J = 3 * K - 2 * D; 

			KDJDateNode node = new KDJDateNode();
			node.setCyc(cyc);
			node.setD(D);
			node.setJ(J);
			node.setK(K);
			node.setDate(t.getDate());
			nodes.add(node);
			temp.remove(0);
			if(iterator.hasNext())
				temp.add(iterator.next());
			else
				break;

		}

		return nodes.iterator();



	}



	@Override
	public Iterator getWeekKDJ(String idNumber, String beginDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		Iterator<NodeService>iterator = null;

		iterator = simpleStockService.getSimpleWeekNodes(idNumber, FinalSign.weekChange(beginDate, -3), endDate);

		if(!iterator.hasNext())
			return null;

		//数据获取完成
		int time = (int) FinalSign.DAYS[Cyc.week.ordinal()] - 1;
		return KDJ(time, iterator, Cyc.day);


	}




	public static void main(String[]args){
		KDJService kdjService  = KDJImpl.create();
		Iterator<KDJDateNode>iterator= null;
		try {
			iterator = kdjService.getWeekKDJ("sh601919", "2016-04-30", "2016-05-31");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}



	}

	@Override
	public String getStandard() {
		// TODO Auto-generated method stub
		return Standard.KDJStandard;
	}

	@Override
	public String getAnalysis(Iterator iterator) {
		// TODO Auto-generated method stub
		

		return null;
	}

}
