package impl;

import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import enums.Cyc;
import enums.StockType;
import message.ARBRDateNode;
import message.ARBRReport;
import message.ARBRStock;
import message.Stock;
import message.StockDateNode;
import service.ARBRService;
import service.StockCheckService;
import service.StockDisposeService;
import twaver.LayerModelListener;
import twaver.base.A.A.I;
import twaver.base.A.E.i;

public class ARBRImpl implements ARBRService{
	//	private final static double[] DAYS = {1, 5, 10, 20};
	//	private final static double[] CANCEL = {0, 6, 13, 27, 83};
	private StockDisposeService service;
	private StockCheckService checkService;
	private volatile static ARBRImpl arbrImpl ;
	private Map<String, ARBRStock> ARBRList;
	private Map<StockType, ARBRReport> selectList;

	private  ARBRImpl(){
		// TODO Auto-generated constructor stub
		service = new StockDataDispose();
		ARBRList = new HashMap<>();
		selectList = new HashMap<>();
		checkService = StockCheck.create();
		try {
			initSelectList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void initSelectList() throws Exception{

		Date date = new Date();
		//减去一天
		String endDate = FinalSign.DF.format(date.getTime() - (long)FinalSign.DAYTIME);
		Iterator iterator = checkService.getSelectedStock();

		while(iterator.hasNext()){
			Stock stock = (Stock) iterator.next();
			ARBRReport report = null;
			try {
				report = selectList.get(stock.getStockType());
			} catch (Exception e) {
				// TODO: handle exception
			}

			if(report == null){
				report = new ARBRReport();
			}
			ARBRData(stock.getName(), Cyc.week, endDate);
			report.setARBR(ARBRList.get(stock.getName()));
			report.setType(stock.getStockType());
			selectList.put(stock.getStockType(), report);
		}

		Iterator iterator2 = selectList.values().iterator();
		while(iterator2.hasNext()){
			ARBRReport report = (ARBRReport) iterator2.next();
			report.analyse();
		}

	}



	//缺少起点判断
	@Override
	public Iterator ARBRData(String name, Cyc cyc,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		//计算开始的日期
		String beginDate = null;
		try {
			beginDate = FinalSign.DF.format(FinalSign.DF.parse(endDate).getTime() - (long)FinalSign.CANCEL[cyc.ordinal()]*FinalSign.DAYTIME);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		//
		List dateMessage = service.getStockMessage(name, beginDate, endDate);
		double days = FinalSign.DAYS[cyc.ordinal()];

		ARBRStock stock = null;
		try {
			stock = ARBRList.get(name);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(stock == null){
			stock = new ARBRStock();
			stock.setName(name);
		}
		//		N日AR=N日内（H－O）之和除以N日内（O－L）之和
		//		N日BR=N日内（H－CY）之和除以N日内（CY－L）之和
		//	    其中，H为当日最高价，L为当日最低价，CY为前一交易日的收盘价，N为设定的时间参数，一般原始参数日设定为26日。
		String beforeDate = dateDispose(beginDate);
		StockDateNode beforeNode = (StockDateNode) service.getStockMessage(name, beforeDate, beginDate).get(0);

		//
		//        List l = stockDataDispose.getStockMessage(name, beforeDate, beginDate);
		double bcy = beforeNode.getClose();

		double sumHO = 0;
		double sumOL = 0;
		double sumHCY = 0;
		double sumCYL = 0;
		Iterator iterator = dateMessage.iterator();
		while(iterator.hasNext()){
			StockDateNode node  = (StockDateNode) iterator.next();
			sumHO = sumHO + node.getHigh() - node.getOpen();
			sumOL = sumOL + node.getOpen() - node.getLow();
			sumHCY = node.getHigh() - bcy;
			sumCYL = bcy - node.getLow();
			bcy = node.getClose();

		}

		ARBRDateNode dateNode = new ARBRDateNode();
		dateNode.setBeginDate(beginDate);
		dateNode.setEndDate(endDate);
		dateNode.setName(name);
		dateNode.setCyc(cyc);
		dateNode.setAR(sumHO/sumOL);
		dateNode.setBR(sumHCY/sumCYL);
		//数据伪造
		if(sumOL==0)	
			dateNode.setAR(1/7);

		if(sumCYL==0)
			dateNode.setBR(1/7);


		List nodeList  = new ArrayList<ARBRDateNode>();
		nodeList.add(dateNode);

		stock.setARBR(cyc, nodeList);

		ARBRList.put(name, stock);

		return ARBRList.get(name).getARBRMap(cyc);
	}




	@Override
	public Iterator ARBRData(String name, String beginDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
		List dateMessage = service.getStockMessage(name, beginDate, endDate);
		double days = FinalSign.DAYS[Cyc.day.ordinal()];
		String beforeDate = dateDispose(beginDate);
		StockDateNode beforeNode = (StockDateNode) service.getStockMessage(name, beforeDate, beginDate).get(0);

		double bcy = beforeNode.getClose();

		Iterator iterator = dateMessage.iterator();
		List nodeList = new ArrayList<ARBRDateNode>();

		while(iterator.hasNext()){
			StockDateNode node  = (StockDateNode) iterator.next();

			ARBRDateNode dateNode = new ARBRDateNode();
			dateNode.setBeginDate(node.getDate());
			dateNode.setEndDate(node.getDate());
			dateNode.setCyc(Cyc.day);
			dateNode.setName(name);
			dateNode.setAR((node.getHigh() - node.getOpen())/(node.getOpen() - node.getLow()));
			dateNode.setBR((node.getHigh() - bcy)/(node.getHigh() - bcy));
			bcy = node.getClose();
			nodeList.add(dateNode);

		}

		ARBRStock stock = null;
		try {
			stock = ARBRList.get(name);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(stock == null){
			stock = new ARBRStock();
			stock.setName(name);
		}

		stock.setARBR(Cyc.day,nodeList);

		ARBRList.put(name, stock);

		return ARBRList.get(name).getARBRMap(Cyc.day);
	}




	@Override
	public Iterator getSelectAnswer() {
		// TODO Auto-generated method stub
		return selectList.values().iterator();
	}
	
	public String getRecommend(){
		String[]recommend = {"", "", ""};

		Iterator iterator = selectList.values().iterator();
		while(iterator.hasNext()){
			ARBRReport report = (ARBRReport) iterator.next();
			recommend[report.getRecommendation().ordinal()] = recommend[report.getRecommendation().ordinal()] + report.getcType() + "、";
		}
		
		for(int i=0;i<recommend.length;i++){
			if(!recommend[i].equals(""))
			recommend[i] = recommend[i].substring(0, recommend[i].length()-1);
		}
		String first = "";
		if(!recommend[0].equals(""))
		first = "行业" + recommend[0] + "应该及早卖出,";
		
		String second = "";
		if(!recommend[1].equals(""))
		first = "行业" + recommend[1] + "可继续观望,";
		
		String third = "";
		if(!recommend[2].equals(""))
		first = "行业" + recommend[2] + "应该及时抢购";
		
		
		
		
		return "由图可知，" + first + second + third + "。";
		
		
	}


	//	public 




	/*
	 * @author: xuan
	 * @date: 2016/04/01
	 * @description: 单体模式的运用
	 */
	public static ARBRImpl create(){
		if(arbrImpl == null){
			synchronized(ARBRImpl.class){

				if(arbrImpl == null)
					arbrImpl = new ARBRImpl();
			}
		}

		return arbrImpl;
	}




	private String dateDispose(String date){
		String result;
		Date d = null;
		try {
			d = FinalSign.DF.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(FinalSign.SDF.format(d).equals(FinalSign.MONDAY))
			result = FinalSign.DF.format(d.getTime() - (long)3 *FinalSign.DAYTIME);
		else if(FinalSign.SDF.format(d).equals(FinalSign.SUNDAY))
			result = FinalSign.DF.format(d.getTime() - (long)2 *FinalSign.DAYTIME);
		else 
			result = FinalSign.DF.format(d.getTime() - (long)FinalSign.DAYTIME);

		return result;

	}
//
//	public static void main(String[]args){
//		ARBRImpl impl = ARBRImpl.create();
//		System.out.println("OK");
//		Iterator iterator = impl.getSelectAnswer();
//		while(iterator.hasNext()){
//			ARBRReport report = (ARBRReport) iterator.next();
//			System.err.println(report.getAvgAR() + " " + report.getAvgBR() + " " + report.getcType()) ;
//
//		}
//		System.out.println(impl.getRecommend());
//
//
//
//	}



}
