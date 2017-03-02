package impl;

import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import enums.Cyc;
import enums.DataType;
import message.StockDateNode;
import message.WMSRDateNode;
import message.WMSRStock;
import service.SmartSortService;
import service.StockDisposeService;
import service.WMSRService;
import smartUI.newSmartToolBar;

public class WMSRImpl implements WMSRService{
	private SmartSortService sortService;
	private StockDisposeService dataService;
	private volatile static WMSRImpl impl;
	private Map<String, WMSRStock> WMSRList;
//	private Map<String, WMSRStock> 
	
	
	
	private WMSRImpl() {
		// TODO Auto-generated constructor stub
		dataService = new StockDataDispose();
		WMSRList = new HashMap<String, WMSRStock>();
		
	}
	
	
	@Override
	public Iterator WMSRData(String name, Cyc cyc, String endDate) throws Exception {
		// TODO Auto-generated method stub
		//计算开始的日期 
		String beginDate = null;
		try {
			beginDate = FinalSign.DF.format(FinalSign.DF.parse(endDate).getTime() - (long)FinalSign.CANCEL[cyc.ordinal()]*FinalSign.DAYTIME);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		List<StockDateNode>dateMessage = dataService.getStockMessage(name, beginDate, endDate);
		double nowClose = dateMessage.get(dateMessage.size()-1).getClose();
		double cycHigh = ((StockDateNode)sortService.downSort(DataType.high, dateMessage.iterator()).next()).getHigh();
		double cycLow = ((StockDateNode)sortService.upSort(DataType.low, dateMessage.iterator()).next()).getLow();

//		%R=100－（C－Ln）/（Hn-Ln）×100
//				其中：C为当日收市价，Ln为N日内最低价，Hn为N日内最高价，公式中N日为选设参数，一般设为14日或20日。
		
		double result = 100 - (nowClose - cycLow)/(cycHigh - cycLow) * 100;
		
		WMSRStock stock = null;
		
		try {
			stock = WMSRList.get(name);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(stock == null){
			stock = new WMSRStock();
			stock.setName(name);
		}
		WMSRDateNode node = new WMSRDateNode();
		node.setBeginDate(beginDate);
		node.setEndDate(endDate);
		node.setCyc(cyc);
		node.setName(name);
		node.setData(result);
		List nodeList = new ArrayList<WMSRDateNode>();
		nodeList.add(node);
		stock.setWNSRM(cyc, nodeList);
		
		WMSRList.put(name, stock);
		
	
		return WMSRList.get(name).getWNSRMap(cyc);
	}

	@Override
	public Iterator WMSRData(String name, String beginDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
		List dateMessage = dataService.getStockMessage(name, beginDate, endDate);
		
		Iterator<StockDateNode>iterator = dateMessage.iterator();
		
		WMSRStock stock = null;
		try {
			stock = WMSRList.get(name);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(stock == null){
			stock = new WMSRStock();
			stock.setName(name);
		}
		
		List<WMSRDateNode> list = new ArrayList<>();
		while(iterator.hasNext()){
			WMSRDateNode node = new WMSRDateNode();
			StockDateNode dataNode = iterator.next();
			double result = 100 - (dataNode.getClose() - dataNode.getLow())/(dataNode.getHigh() - dataNode.getLow()) * 100;
			node.setBeginDate(beginDate);
			node.setEndDate(endDate);
			node.setCyc(Cyc.day);
			node.setName(name);
			node.setData(result);
			list.add(node);
		}
		
		stock.setWNSRM(Cyc.day, list);
	
		WMSRList.put(name, stock);
		
		return WMSRList.get(name).getWNSRMap(Cyc.day);
	}

	@Override
	public String getAnswer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	/*
	 * @author: xuan
	 * @date: 2016/04/01
	 * @description: 单体模式的运用
	 */
	public static WMSRImpl create(){
		if(impl == null){
			synchronized(WMSRImpl.class){

				if(impl == null)
					impl = new WMSRImpl();
			}
		}

		return impl;
	}
}
