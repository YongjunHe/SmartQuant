package impl;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import enums.Cyc;
import enums.DataType;
import enums.MarketType;
import message.NodeService;
import message.SimpleStock;
import message.SimpleStockNode;
import message.StockDateNode;
import service.SimpleStockService;
import service.SmartSortService;
import service.StockDisposeService;
import service.SummaryDisposeService;
//import src.StockDataDisposeTest;
import twaver.base.A.E.f;
import twaver.base.A.E.l;
import twaver.base.A.E.n;

public class SimpleStockImpl implements SimpleStockService{
	//	private Map<String , SimpleStock> mapList;
	private SmartSortService sortService;
	private StockDisposeService stockService;
	private SummaryDisposeService summaryService;
	private volatile static SimpleStockImpl impl;
	private static String beginDay = "01";
	private static String endDay = "31";


	private SimpleStockImpl() {
		// TODO Auto-generated constructor stub
		//		mapList = new HashMap<String, SimpleStock>();
		stockService = new StockDataDispose();
		sortService = new SmartSort();
		summaryService = new SummaryDataDispose();
	}


	@Override
	public Iterator getSimpleWeekNodes(String idNumber, String beginDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		//解析日期
		String beginDay = FinalSign.setMondayDate(beginDate);
		String endDay = FinalSign.setFirdayDate(endDate);	
		//解析结束

		List<NodeService> list = null;
		if(idNumber.equals("hs300"))
			list = summaryService.getSummaryMessage(MarketType.hs300, beginDay, endDay); 	
		else
			list = stockService.getStockMessage(idNumber, beginDay, endDay);


		List<SimpleStockNode>nodeList = new ArrayList<>();	

		Iterator iterator1 = list.iterator();
		while(Integer.valueOf(beginDay.replace("-", "")) < Integer.valueOf(endDay.replace("-", ""))){

			List<NodeService> temp = new ArrayList<>();
			double volumeSum = 0;
			while(iterator1.hasNext()){
				NodeService n = (NodeService) iterator1.next();
				if(n.compareTo(beginDay) >= 0&&n.compareTo(FinalSign.setFirdayDate(beginDay))<=0){
					volumeSum = volumeSum + n.getType(DataType.volume);
					temp.add(n);
				}else
					break;

			}
			if(temp.isEmpty()){
				beginDay =  FinalSign.weekChange(beginDay,1);
				continue;
			}


			SimpleStockNode node = new SimpleStockNode();
			node.setVolume(volumeSum);
			node.setDate(beginDay);
			node.setCyc(Cyc.week);
			node.setClose(temp.get(temp.size()-1).getType(DataType.close));
			node.setOpen(temp.get(0).getType(DataType.open));
			node.setHigh(((NodeService)sortService.downSort(DataType.high, temp.iterator()).next()).getType(DataType.high));
			node.setLow(((NodeService)sortService.upSort(DataType.low, temp.iterator()).next()).getType(DataType.low));
			nodeList.add(node);		
			beginDay =  FinalSign.weekChange(beginDay,1);
		}
		return nodeList.iterator();




		//		List<SimpleStockNode>nodeList = new ArrayList<>();	
		//		while(Integer.valueOf(beginDay.replace("-", "")) < Integer.valueOf(endDay.replace("-", ""))){
		//
		//			List<StockDateNode> temp = service.getStockMessage(idNumber, beginDay, FinalSign.setFirdayDate(beginDay));
		//			double volumeSum = 0;
		//			Iterator iterator = temp.iterator();
		//			while(iterator.hasNext()){
		//				StockDateNode n = (StockDateNode) iterator.next();
		//				volumeSum = volumeSum + n.getVolume();
		//
		//			}
		//
		//			SimpleStockNode node = new SimpleStockNode();
		//			node.setVolume(volumeSum);
		//			node.setDate(beginDay);
		//			node.setCyc(Cyc.week);
		//			node.setClose(temp.get(temp.size()-1).getClose());
		//			node.setOpen(temp.get(0).getOpen());
		//			node.setHigh(((StockDateNode)sortService.downSort(DataType.high, temp.iterator()).next()).getHigh());
		//			node.setLow(((StockDateNode)sortService.upSort(DataType.low, temp.iterator()).next()).getLow());
		//			nodeList.add(node);		
		//			beginDay =  FinalSign.weekChange(beginDay,1);
		//		}


		//		stock.setSimpleStockMessage(Cyc.week, nodeList);;
		//		return stock.getSimpleStockMessage(Cyc.week);
		//		mapList.put(idNumber, stock);
		//		return mapList.get(idNumber).getSimpleStockMessage(Cyc.week);
		//		SimpleStock stock = mapList.get(idNumber);
		//		if(stock == null){
		//			stock = new SimpleStock();
		//			stock.setName(idNumber);
		//		}
		//     
	}

	@Override
	public Iterator getSimpleMonthNodes(String idNumber, String beginDate,
			String endDate) {
		// TODO Auto-generated method stub
		//解析日期
		String beginMonth = beginDate.substring(0, 8) + beginDay;
		String endMonth = endDate.substring(0, 7);
		String nextMonth;
		//解析结束

		List<SimpleStockNode>nodeList = new ArrayList<>();		

		while(Integer.valueOf(beginMonth.substring(0,7).replace("-", "")) <= Integer.valueOf(endMonth.replace("-", ""))){
			double volumeSum = 0;
			List<NodeService> nodes = null;
			try{

				if(idNumber.equals("hs300"))
					nodes = summaryService.getSummaryMessage(MarketType.hs300, beginDay, endDay); 	
				else
					nodes = stockService.getStockMessage(idNumber, beginMonth, beginMonth.substring(0, 8) + endDay );
			}catch(Exception e){
				//				System.out.println(e + beginMonth);
				beginMonth = FinalSign.monthChange(beginMonth,1);
				continue;
			}
			Iterator iterator = nodes.iterator();
			while(iterator.hasNext()){
				NodeService node = (StockDateNode)iterator.next();
				volumeSum = volumeSum + node.getType(DataType.volume);
			}

			if(nodes.isEmpty()){
				beginMonth = FinalSign.monthChange(beginMonth,1);
				continue;
			}
			
			SimpleStockNode node = new SimpleStockNode();
			node.setVolume(volumeSum);
			node.setDate(beginMonth);
			node.setCyc(Cyc.month);
			node.setClose(nodes.get(nodes.size()-1).getType(DataType.close));
			node.setOpen(nodes.get(0).getType(DataType.open));
			node.setHigh(((NodeService)sortService.downSort(DataType.high, nodes.iterator()).next()).getType(DataType.high));
			node.setLow(((NodeService)sortService.upSort(DataType.low, nodes.iterator()).next()).getType(DataType.low));
			nodeList.add(node);		
			beginMonth = FinalSign.monthChange(beginMonth,1);
		}

		//		stock.setSimpleStockMessage(Cyc.month, nodeList);
		//		mapList.put(idNumebr, stock);
		//		return stock.getSimpleStockMessage(Cyc.month);
		//		SimpleStock stock = mapList.get(idNumebr);
		//		if(stock == null){
		//			stock = new SimpleStock();
		//			stock.setName(idNumebr);
		//		}
		return nodeList.iterator();
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/30
	 * @description: 单体模式的运用
	 */
	public static SimpleStockImpl create(){
		if(impl == null){
			synchronized(SimpleStockImpl.class){

				if(impl == null)
					impl = new SimpleStockImpl();
			}
		}

		return impl;
	}





	@Override
	public SimpleStock getStock(String idNumber) {
		// TODO Auto-generated method stub

		//		return mapList.get(idNumber);
		return null;
	}

//	public static void main(String[]args){
//		SimpleStockImpl impl = SimpleStockImpl.create();
//		MeanCaculation meanCaculation = MeanCaculation.create();
//		Iterator iterator = null;
//		try {
//			iterator = impl.getSimpleWeekNodes("sh600022", "2014-09-10", "2016-04-16");
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		while(iterator.hasNext()){
//			SimpleStockNode node = (SimpleStockNode) iterator.next();
//			System.out.println(node.getDate());
//			System.out.println(node.getOpen());
//		}
//
//	}

}
