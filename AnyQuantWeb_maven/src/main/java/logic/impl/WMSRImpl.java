package logic.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import data.enums.Cyc;
import data.enums.DataType;
import data.helper.FinalSign;
import data.helper.Standard;
import data.impl.StockDataDispose;
import data.message.StockDateNode;
import data.message.UniversalDateNode;
import data.service.StockDisposeService;
import logic.service.SmartSortService;
import logic.service.WMSRService;



public class WMSRImpl implements WMSRService{
	private SmartSortService sortService;
	private StockDisposeService dataService;
	private volatile static WMSRImpl impl;
	private static int[]index = {15, 85};
	private static String[]strings1 = {"超买状态", "平稳状态", "超卖状态"};
	private static String[]strings2 = {"及时卖出", "可观望可出手", "伺机买进"};



	private WMSRImpl() {
		// TODO Auto-generated constructor stub
		dataService = new StockDataDispose();
		sortService = new SmartSort();

	}


	@Override
	public Iterator WMSRWeek(String name, String beginDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
		//计算开始的日期 

		try {
			beginDate = FinalSign.DF.format(FinalSign.DF.parse(beginDate).getTime() - (long)FinalSign.CANCEL[Cyc.week.ordinal()]*FinalSign.DAYTIME);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		List<StockDateNode>dateMessage = dataService.getStockMessage(name, beginDate, endDate);
		Iterator<StockDateNode>iterator = dateMessage.iterator();
		if(iterator == null)
			return null;
		List<StockDateNode> temp = new ArrayList<>();
		List<UniversalDateNode> nodeList = new ArrayList<UniversalDateNode>();
		for(int i = 0 ; i <  5 ; i++){
			temp.add(iterator.next());
		}

		while(true){

			StockDateNode n = temp.get(4);
			double nowClose = n.getClose();
			double cycHigh = ((StockDateNode)sortService.downSort(DataType.high, temp.iterator()).next()).getHigh();
			double cycLow = ((StockDateNode)sortService.upSort(DataType.low, temp.iterator()).next()).getLow();

			double result = 100 - (nowClose - cycLow)/(cycHigh - cycLow) * 100;

			UniversalDateNode node = new UniversalDateNode();
			node.setDate(n.getDate());
			node.setVary(Cyc.week);

			node.setData(result);

			nodeList.add(node);
			temp.remove(0);

			if(iterator.hasNext())
				temp.add(iterator.next());
			else
				break;

		}
		return nodeList.iterator();
	}

	@Override
	public Iterator WMSRDay(String name, String beginDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
		List dateMessage = dataService.getStockMessage(name, beginDate, endDate);

		Iterator<StockDateNode>iterator = dateMessage.iterator();


		List<UniversalDateNode> list = new ArrayList<>();
		while(iterator.hasNext()){
			UniversalDateNode node = new UniversalDateNode();
			StockDateNode dataNode = iterator.next();
			double result = 100 - (dataNode.getClose() - dataNode.getLow())/(dataNode.getHigh() - dataNode.getLow()) * 100;
			node.setDate(dataNode.getDate());
			node.setVary(Cyc.day);
			node.setData(result);
			list.add(node);
		}



		return list.iterator();
	}

	@Override
	public String getAnswer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[]args){
		WMSRImpl Service  = WMSRImpl.create();
		Iterator<UniversalDateNode>iterator= null;
		try {
			iterator = Service.WMSRWeek("sh601919", "2016-02-30", "2016-05-31");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}


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


	@Override
	public String getStandard() {
		// TODO Auto-generated method stub
		return Standard.WMSRStandard;
	}


	@Override
	public String getAnalysis(Iterator iterator) {
		// TODO Auto-generated method stub
		int temp[] = new int [3];


		while(iterator.hasNext()){
			int level = 0;
			double WMSR = ((UniversalDateNode)iterator.next()).getData();
			while(WMSR >= index[level]){
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
