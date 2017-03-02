package service;


import java.util.Iterator;
import enums.SortType;
import message.Range;

public interface SmartSortService {
	public Iterator sort(SortType sortType, Range range, Iterator iterator);

}
