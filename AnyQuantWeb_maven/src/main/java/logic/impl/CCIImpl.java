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


public class CCIImpl implements CCIService{



	private StockDisposeService stockService;
	private SummaryDisposeService summaryService;
	private SimpleStockService simpleStockService;
	private volatile static CCIImpl impl;
	private static final String[] strings = {"股价逢低可以及时买进", "还是观望一段期间吧", "股价还高就趁早脱手"};
	private static int[]Index = {-100,+100}; 



	@Override
	public Iterator getWeekCCI(String idNumber, String beginDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Iterator<NodeService>iterator = null;

		iterator = simpleStockService.getSimpleWeekNodes(idNumber, FinalSign.weekChange(beginDate, -3), endDate);

		if(!iterator.hasNext())
			return null;

		//数据获取完成
		int time = (int) FinalSign.DAYS[Cyc.week.ordinal()] - 1;
		return CCI(time, iterator, Cyc.day);
	}

	@Override
	public Iterator getDayCCI(String idNumber, String beginDate,
			String endDate) throws Exception {
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
		return CCI(time, dataNodeList.iterator(), Cyc.day);

	}



	private Iterator CCI(int time, Iterator<NodeService> iterator, Cyc cyc){

		ArrayList<UniversalDateNode>nodes = new ArrayList<>();


		ArrayList<Double>temp = new ArrayList<>();

		String currentDate = null;



		for(int i = 0; i<time;i++){
			NodeService node = iterator.next();
			double tp = ((node.getType(DataType.high) + node.getType(DataType.close) + node.getType(DataType.low)))/3;
			temp.add(tp);
			currentDate = node.getDate();
		}

		while(true){
			double TPSMA = 0;
			double SD = 0;
			for(double tp : temp){
				TPSMA = tp + TPSMA;	
			}
			TPSMA = (double)TPSMA / time;
			for(double tp : temp){
				SD = SD + Math.abs(tp - TPSMA);	
			}

			SD = (double) SD / time;
			double CCI = ((temp.get(time - 1)) - TPSMA) / (SD * 0.015);
			UniversalDateNode  n = new UniversalDateNode();
			n.setDate(currentDate);
			n.setData(CCI);
			n.setVary(Cyc.day);
			nodes.add(n);

			if(iterator.hasNext()){
				NodeService node = iterator.next();
				double tp = ((node.getType(DataType.high) + node.getType(DataType.close) + node.getType(DataType.low))) /3;
				temp.add(tp);
				currentDate = node.getDate();
			}
			else
				break;

		}

		return nodes.iterator();
	}


	private CCIImpl(){
		summaryService = new SummaryDataDispose();
		stockService = new StockDataDispose();
		simpleStockService = SimpleStockImpl.create();

	}

	/*
	 * @author: xuan
	 * @date: 2016/05/31
	 * @description: 单体模式的运用
	 */
	public static CCIImpl create(){
		if(impl == null){
			synchronized(CCIImpl.class){

				if(impl == null)
					impl = new CCIImpl();
			}
		}

		return impl;
	}



	public static void main(String[]args){
		CCIService kdjService  = CCIImpl.create();
		Iterator<UniversalDateNode>iterator= null;
		Iterator<UniversalDateNode>iterator2 = null;
		try {
			iterator = kdjService.getDayCCI("sh600066", "2016-05-12", "2016-06-12");
			iterator2 =  kdjService.getDayCCI("sh600066", "2016-05-12", "2016-06-12");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(iterator2.hasNext()){
			System.out.println(iterator2.next());
		}
		System.out.println(kdjService.getAnalysis(iterator));



	}

	@Override
	public String getStandard() {
		// TODO Auto-generated method stub
		return Standard.CCIStandard;
	}

	@Override
	public String getAnalysis(Iterator iterator) {
		// TODO Auto-generated method stub
		int[]temp = new int[3];


		while(iterator.hasNext()){
			double CCI = ((UniversalDateNode)iterator.next()).getData();
			int level = 0;


			while(CCI > Index[level]){
				level++;
				if(level >= Index.length)
					break;
			}

			temp[level]++;
		}


		String result = "在分析期间内，CCI超过+100的节点有" + temp[2] +"个;<br>" + 
				"CCI低于-100的节点有" + temp[0] +"个;<br>" + 
				"CCI介于-100～+100的节点有" + temp[1] +"个;<br>" + strings[FinalSign.getLocation(temp)] + "."; 	      

		return result;
	}

}
