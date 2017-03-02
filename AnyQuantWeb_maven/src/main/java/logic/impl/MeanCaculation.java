package logic.impl;

import java.net.SocketException;

/*
 * @author: xuan
 * @date: 2016/03/30
 * 
 * @mender: none
 * @date: none
 * 
 * @type: interface
 * @description: 实现MeanService接口，包含数据
 */

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import data.enums.*;
import data.helper.*;
import data.impl.*;
import data.message.*;
import data.service.*;
import logic.service.*;

import twaver.base.A.E.i;

public class MeanCaculation implements MeanService{

	private volatile static MeanCaculation meanCaculation;
//	private Map<String, MeanStock> meanStockList;
	private StockDisposeService stockService;
	private SummaryDisposeService summaryService;
	private SimpleStockService simpleService;



	private MeanCaculation() {
		// TODO Auto-generated constructor stub
//		meanStockList = new HashMap<>();
		stockService = new StockDataDispose();
		summaryService = new SummaryDataDispose();
		simpleService = SimpleStockImpl.create();
	}

	@Override
	public Iterator dateMeanClose(String name, Cyc cyc, String beginDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		return dateMean(name, cyc, DataType.close, beginDate, endDate);
	}

	@Override
	public Iterator dateMeanVolume(String name, Cyc cyc, String beginDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		return dateMean(name, cyc, DataType.volume, beginDate, endDate);
	}
//
	
	
	public Iterator weekMeanClose(String name, Cyc cyc, String beginDate, String endDate) throws Exception{ 

		// List<NodeService>dataNodeList = simpleService.getStock(name).getMessage(cyc);
		return weekMean(name, cyc, DataType.close, beginDate, endDate);
	}
	public Iterator weekMeanVolume(String name, Cyc cyc, String beginDate, String endDate) throws Exception{

		// List<NodeService>dataNodeList = simpleService.getStock(name).getMessage(cyc);
		return weekMean(name, cyc, DataType.volume, beginDate, endDate);
	}

	public Iterator monthMeanVolume(String name, Cyc cyc, String beginDate, String endDate){

		// List<NodeService>dataNodeList = simpleService.getStock(name).getMessage(cyc);
		return monthMean(name, cyc, DataType.volume, beginDate, endDate);
	}
	
	public Iterator monthMeanClose(String name, Cyc cyc, String beginDate, String endDate){

		// List<NodeService>dataNodeList = simpleService.getStock(name).getMessage(cyc);
		return monthMean(name, cyc, DataType.close, beginDate, endDate);
	}
	//未决解



	//缺少判断起点的不存在
	private Iterator dateMean(String name, Cyc cyc, DataType type, String beginDate,
			String endDate) throws Exception {

		Date bDate = null;
		beginDate = dateDispose(beginDate);
		try {
			bDate = FinalSign.DF.parse(beginDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		beginDate = FinalSign.DF.format(bDate.getTime() - (long)FinalSign.CANCEL[cyc.ordinal()]*FinalSign.DAYTIME);
		
		List<NodeService>dataNodeList = null;

		if(name.equals("hs300"))
			dataNodeList = summaryService.getSummaryMessage(MarketType.hs300, beginDate, endDate); 	
		else
			dataNodeList = stockService.getStockMessage(name, beginDate, endDate);

		
		return calculation(name, dataNodeList, cyc, type);
	}
	
	
	
	private Iterator weekMean(String name, Cyc cyc, DataType type, String beginDate, String endDate) throws Exception {

		String beginDay = FinalSign.weekChange(beginDate, - ((int) (FinalSign.DAYS[cyc.ordinal()]-1)));
        List<NodeService>list = new ArrayList<>();
        Iterator iterator = simpleService.getSimpleWeekNodes(name, beginDay, endDate);
        while(iterator.hasNext()){
        	list.add((NodeService) iterator.next());   	
        }
		return calculation(name, list, cyc, type);
	}
	
	
	
	private Iterator monthMean(String name, Cyc cyc, DataType type, String beginDate,
			String endDate) {

		String beginDay = FinalSign.monthChange(beginDate, - ((int) (FinalSign.DAYS[cyc.ordinal()]-1)));
        List<NodeService>list = new ArrayList<>();
        Iterator iterator = simpleService.getSimpleMonthNodes(name, beginDay, endDate);
        while(iterator.hasNext()){
        	list.add((NodeService) iterator.next());   	
        }
		return calculation(name, list, cyc, type);
	}

	public Iterator calculation(String name, List<NodeService> dataNodeList, Cyc cyc, DataType type){

		double days = FinalSign.DAYS[cyc.ordinal()];
		double[] datas;
		String[] dates;


		datas = new double[dataNodeList.size()];
		dates = new String[(int) (dataNodeList.size()-days+1)];
		for(int i=0;i<dataNodeList.size();i++){
			datas[i] = dataNodeList.get(i).getType(type);
			if(i>=days-1){
				dates[(int) (i-days+1)] = dataNodeList.get(i).getDate();
			}
		}
		//计算开始	

		List<MeanDateNode> list = new ArrayList<>();
		double sum = 0;
		for(int n=0;n<days-1;n++){
			try {
				sum = sum + datas[n];
			} catch (Exception e) {
				// TODO: handle exception
			}

		}   

		for(int n=0; n<dates.length;n++){
			MeanDateNode mNode = new MeanDateNode();
			mNode.setDate(dates[n]);
			sum = sum + datas[(int) (n+days)-1];
			mNode.setData(sum/days);
			list.add(mNode);
			sum = sum - datas[n];
		}

		return list.iterator();
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/30
	 * @description: 单体模式的运用
	 */
	public static MeanCaculation create(){
		if(meanCaculation == null){
			synchronized(MeanCaculation.class){

				if(meanCaculation == null)
					meanCaculation = new MeanCaculation();
			}
		}

		return meanCaculation;
	}



	public static String dateDispose(String date){
		String result;
		Date d = null;
		try {
			d = FinalSign.DF.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(FinalSign.SDF.format(d).equals(FinalSign.SATURDAY))
			result = FinalSign.DF.format(d.getTime() + (long)2 * FinalSign.DAYTIME);
		else if(FinalSign.SDF.format(d).equals(FinalSign.SUNDAY))
			result = FinalSign.DF.format(d.getTime() + (long) FinalSign.DAYTIME);
		else 
			result = date;

		return result;

	}


	private boolean isWeekends(String date){
		Date d = null;
		try {
			d = FinalSign.DF.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(FinalSign.SDF.format(d).equals(FinalSign.SATURDAY)||FinalSign.SDF.format(d).equals(FinalSign.SUNDAY))
			return true;

		else 
			return false;

	}




	

}



