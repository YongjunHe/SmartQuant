package impl;

import java.util.ArrayList;
import java.util.Iterator;

import enums.SortType;
import message.NodeService;
import message.Range;
import service.SmartSortService;
import twaver.base.A.E.i;

public class SmartSort implements SmartSortService{

	private ArrayList<NodeService>nodes ;

	@Override
	public Iterator sort(SortType sortType, Range range, Iterator iterator) {
		// TODO Auto-generated method stub
		if(range.isHasLower()&&range.isHasUpper())
			return sort(sortType, range.getLower(),range.getUpper(), iterator);
		if(!range.isHasLower())
			return sort(sortType, range.getUpper(), iterator);
		else return sort(range.getLower(), sortType, iterator);


	}


	public Iterator sort(SortType sortType, double  lower, double upper, Iterator iterator) {
		// TODO Auto-generated method stub
		nodes = new ArrayList<>();
		while(iterator.hasNext()){
			NodeService node = (NodeService) iterator.next();
			if(lower<=node.getType(sortType)&&node.getType(sortType)<=upper)
				nodes.add(node);
		}

		return nodes.iterator();

	}

	public Iterator sort(SortType sortType, double upper, Iterator iterator) {
		// TODO Auto-generated method stub
		nodes = new ArrayList<>();
		while(iterator.hasNext()){
			NodeService node = (NodeService) iterator.next();
			if(node.getType(sortType)<=upper)
				nodes.add(node);
		}

		return nodes.iterator();
	}

	public Iterator sort(double lower, SortType sortType, Iterator iterator) {
		// TODO Auto-generated method stub
		nodes = new ArrayList<>();
		while(iterator.hasNext()){
			NodeService node = (NodeService) iterator.next();
			if(lower<=node.getType(sortType))
				nodes.add(node);
		}

		return nodes.iterator();
	}


}
