package impl;

/*
 * @author: xuan
 * @date: 2016/03/20
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 实现数据的筛选和排序
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;

import enums.DataType;
import message.NodeService;
import message.Range;
import message.Stock;
import message.StockDateNode;
import service.SmartSortService;
//import src.StockCheckTest;
import twaver.base.A.E.i;

public class SmartSort implements SmartSortService{

	private ArrayList<NodeService>nodes ;
	private SummaryCheck summaryCheck ;
	private StockCheck stockCheck ;


	public SmartSort() {
		// TODO Auto-generated constructor stub
		summaryCheck = summaryCheck.create();
		stockCheck = stockCheck.create();	
	}

	@Override
	public Iterator sort(DataType sortType, Range range, Iterator iterator) {
		// TODO Auto-generated method stub
		if(range.isHasLower()&&range.isHasUpper())
			return sort(sortType, range.getLower(),range.getUpper(), iterator);
		if(!range.isHasLower())
			return sort(sortType, range.getUpper(), iterator);
		else return sort(range.getLower(), sortType, iterator);


	}


	public Iterator sort(DataType sortType, double  lower, double upper, Iterator iterator) {
		// TODO Auto-generated method stub
		nodes = new ArrayList<>();
		while(iterator.hasNext()){
			NodeService node = (NodeService) iterator.next();
			if(lower<=node.getType(sortType)&&node.getType(sortType)<=upper)
				nodes.add(node);
		}

		return nodes.iterator();

	}

	public Iterator sort(DataType sortType, double upper, Iterator iterator) {
		// TODO Auto-generated method stub
		nodes = new ArrayList<>();
		while(iterator.hasNext()){
			NodeService node = (NodeService) iterator.next();
			if(node.getType(sortType)<=upper)
				nodes.add(node);
		}

		return nodes.iterator();
	}

	public Iterator sort(double lower, DataType sortType, Iterator iterator) {
		// TODO Auto-generated method stub
		nodes = new ArrayList<>();
		while(iterator.hasNext()){
			NodeService node = (NodeService) iterator.next();
			if(lower<=node.getType(sortType))
				nodes.add(node);
		}

		return nodes.iterator();
	}



	@Override
	public Iterator upSort(DataType sortType, Iterator iterator) {
		// TODO Auto-generated method stub
		nodes  = new ArrayList<>();
		while(iterator.hasNext())
			nodes.add((NodeService)iterator.next());
		
		Collections.sort(nodes, new MyUpComparator(sortType));
		return nodes.iterator();
	
	}

	@Override
	public Iterator downSort(DataType sortType, Iterator iterator) {
		// TODO Auto-generated method stub
		nodes = new ArrayList<>();
		while(iterator.hasNext())
			nodes.add((NodeService)iterator.next());
		
		Collections.sort(nodes, new MyDownComparator(sortType));
		return nodes.iterator();
	}


	public class MyUpComparator implements Comparator<NodeService>{
		private DataType sortType;
		
		public MyUpComparator(DataType sortType){
			this.sortType = sortType;
		}
		
		
		public int compare(NodeService arg0, NodeService arg1) {
			double a0 = arg0.getType(sortType);
			double a1 = arg1.getType(sortType);
			if(a0 > a1)
				return 1;
			else if(a0 < a1)
				return -1;
			else 
				return 0;
		}
	}
	
	
	public class MyDownComparator implements Comparator<NodeService>{
		private DataType sortType;
		
		public MyDownComparator(DataType sortType){
			this.sortType = sortType;
		}
		public int compare(NodeService arg0, NodeService arg1) {
			double a0 = arg0.getType(sortType);
			double a1 = arg1.getType(sortType);
			if(a0 > a1)
				return -1;
			else if(a0 < a1)
				return 1;
			else 
				return 0;
		}
	}


//	@Override
//	public Iterator upSort(DataType sortType, String name) {
//		// TODO Auto-generated method stub
//		Stock stock = stockCheck.getStock(name);
//		List dateMesage = stock.getDateMessage();
//		Collections.sort(dateMesage, new MyUpComparator(sortType));
//		return dateMesage.iterator();
//	}
//
//	@Override
//	public Iterator downSort(DataType sortType, String name) {
//		// TODO Auto-generated method stub
//		Stock stock = stockCheck.getStock(name);
//		List dateMesage = stock.getDateMessage();
//		Collections.sort(dateMesage, new MyDownComparator(sortType));
//		return dateMesage.iterator();
//	}
	
	
}





