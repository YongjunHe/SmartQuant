package logic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.methods.multipart.StringPart;

import com.sun.swing.internal.plaf.basic.resources.basic_pt_BR;

import data.enums.*;
import data.helper.*;
import data.impl.*;
import data.message.*;
import data.service.*;

import logic.service.*;
import twaver.base.A.E.b;

public class RSIImpl implements RSIService{
	private SummaryDisposeService summaryService;
	private StockDisposeService stockService;
	private volatile static RSIImpl impl;
	private static double[]index = {20,50,80};
	private static String[]strings1 = {"极弱买入", "弱观望", "强买入", "极强卖出"};
	private static String[]strings2 = {"较少买入", "可观望可出手", "尽量买入", "一定要卖出"};


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
		if(idNumber.equals("sh000300"))
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
				A = (double)(close - open) / open;
			else
				B = (double)(open - close) / open;



			if(new Double(B).equals(new Double(0.0))){
				B = A;
			}

			UniversalDateNode node = new UniversalDateNode();
			double RS = A / B;
			double RSI = 100 - (double)100 / (1 + RS);
			if(Double.isNaN(RSI))
				node.setData(100);
			else 
				node.setData(RSI);
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
		if(idNumber.equals("sh000300"))
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



			if(new Double(B).equals(new Double(0)))
				B = A;


			UniversalDateNode node = new UniversalDateNode();
			double RS = A / B;


			double RSI = 100 - (double)100 / (1 + RS);
			if(Double.isNaN(RSI))
				node.setData(100);
			else 
				node.setData(RSI);

			node.setDate(beginDay);
			nodeList.add(node);


			beginDay =  FinalSign.weekChange(beginDay,1);
		}
		return nodeList.iterator();

	}







	@Override
	public String getStandard() {
		// TODO Auto-generated method stub
		return Standard.RSIStandard;
	}

	@Override
	public String getAnalysis(Iterator iterator) {
		// TODO Auto-generated method stub
		int temp[] = new int [4];


		while(iterator.hasNext()){
			int level = 0;
			double RSI = ((UniversalDateNode)iterator.next()).getData();
			while(RSI >= index[level]){
				level++;
				if(level >= index.length)
					break;
			}

			temp[level]++;
		}
		int index = FinalSign.getLocation(temp);
		String result = "该股票在该段期间位于" + strings1[index] + "状态，您可以" + strings2[index] + ".";
		return result;
	}


}
