package logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import data.impl.StockDataDispose;
import data.message.StockDateNode;
import data.message.UniversalDateNode;
import data.service.StockDisposeService;
import logic.service.XMAService;

public class XMAImpl implements XMAService{


	private static StockDisposeService service;
	private List<UniversalDateNode> nodesChange;
	private List<UniversalDateNode> XMA;

	public XMAImpl() {
		// TODO Auto-generated constructor stub
		service = new StockDataDispose();

	}



	private List<UniversalDateNode> nodeChange(String idNumber, String beginDate, String endDate){
		List<StockDateNode> list = null;
		List<UniversalDateNode> list2 = new ArrayList<>();
		try {
			list = service.getStockMessage(idNumber, beginDate, endDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<StockDateNode>iterator = list.iterator();
		while(iterator.hasNext()){
			StockDateNode node = iterator.next();
			UniversalDateNode node2 = new UniversalDateNode();
			node2.setData((node.getClose()+node.getHigh()+node.getOpen()+node.getLow())/4);
			node2.setDate(node.getDate());
			list2.add(node2);
		}

		nodesChange = list2;
		

		return list2;
	}
//
//	卖出:XMA(VAR1,N)*(1+P1/1000),COLORGREEN,LINETHICK2;
//	买入:XMA(VAR1,M)*(1-P2/1000),COLORMAGENTA,LINETHICK2;

	@Override
	public Iterator<UniversalDateNode> inXMA(String idNumber, String beginDate, String endDate, int vary , int p2) {
		// TODO Auto-generated method stub
		List<UniversalDateNode>list = nodeChange(idNumber, beginDate, endDate);
		

		 Iterator<UniversalDateNode> iterator = XMA(list.iterator(), vary);
		 
		 List<UniversalDateNode> temp = new ArrayList<>();
		 
		 while(iterator.hasNext()){
			 UniversalDateNode node = iterator.next();
			 UniversalDateNode n = new UniversalDateNode();
			 n.setDate(node.getDate());
			 n.setData(node.getData()*(1.0-(double)p2/1000));
			 temp.add(n);
		 }
		 
		 
		 
		 return temp.iterator();
	}

	@Override
	public Iterator<UniversalDateNode> outXMA(String idNumber, String beginDate, String endDate, int vary , int p1) {
		// TODO Auto-generated method stub
		Iterator<UniversalDateNode> iterator = XMA.iterator();
       List<UniversalDateNode> temp = new ArrayList<>();
		 
		 while(iterator.hasNext()){
			 UniversalDateNode node = iterator.next();
			 UniversalDateNode n = new UniversalDateNode();
			 n.setDate(node.getDate());
			 n.setData(node.getData()*(1.0+(double)p1/1000));
			 temp.add(n);
		 }
		 
		 XMA = null;
		 
		 return temp.iterator();
	}



	private Iterator<UniversalDateNode> XMA(Iterator<UniversalDateNode> iterator, int vary){
		double [] temp = new double[nodesChange.size()];
		int index = (vary+1)/2;
		for(int i=0;iterator.hasNext();i++){
			temp[i] = iterator.next().getData();   
		}
		Iterator<UniversalDateNode>i = nodesChange.iterator();
		List<UniversalDateNode>list = new ArrayList<>();
		int m = 0;
		for(int j=0;j<index;j++,m++){
			double before = 0;
			UniversalDateNode node = new UniversalDateNode();
			node.setDate(i.next().getDate());

			double data = 0;
			int k = 0;	
			while(k<(index+m)){
				if(k>temp.length-1){
					data = data + before;
				}
				else{
					data = data + temp[k];	
					before = (before*k + temp[k]) / (k+1);
				}
				k++;

			}
			data = data / (double)(index+m);
			node.setData(data);
			list.add(node);				
		}
		System.out.println(list.size());
		System.out.println(nodesChange.size());

		//从2位开始计算
		int i2 = 1;
		for(int i1=index;i1<nodesChange.size();i1++){
			double before = 0;;
			int i3 = 0;
			int i4 = i2;
			double data = 0;
			while(i3 < vary){
				if(i4>temp.length-1){
					data = data + before;
				}else{
					data = temp[i4] + data;
					before = (before*i3 + temp[i4]) / (i3+1);
				}
				i3++;
				i4++;
			}
			UniversalDateNode node = new UniversalDateNode();
			data = data/(double)vary;
			node.setData(data);
			node.setDate(i.next().getDate());
			list.add(node);
			i2++;

		}


		//计算结束
		nodesChange = null;	
		XMA = list;
		return list.iterator();
	}

	
	public static void main(String[]args){
		XMAService service = new XMAImpl();
		Iterator<UniversalDateNode> iterator1 = service.inXMA("600066", "2016-01-03", "2016-04-03", 32, 100);
		Iterator<UniversalDateNode> iterator2 = service.outXMA("600066", "2016-01-03", "2016-04-03", 20, 80);
		while (iterator1.hasNext()) {
			
       System.out.println(iterator1.next() + " " + iterator2.next());
       
			
		}

		
		
		
	}
}
