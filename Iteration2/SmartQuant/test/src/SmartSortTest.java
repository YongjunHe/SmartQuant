package src;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import enums.DataType;
import impl.SmartSort;
import impl.StockCheck;
import message.StockDateNode;
import smartUI.newSmartToolBar;

public class SmartSortTest {
	
	
	private static SmartSort smartSort = new SmartSort();
	private static StockCheck stockCheck =  StockCheck.create();

	@Test
	public void testUpSortSortTypeIterator() {

		
	
		
	}

	@Test
	public void testDownSortSortTypeIterator() {
	
	}

//	@Test
//	public void testUpSortSortTypeString() {
//		stockCheck.getStockMessage("sh600000", "2016-02-21", "2016-03-21");
//		Iterator iterator = smartSort.upSort(DataType.close, "sh600000");
//		double before = ((StockDateNode) iterator.next()).getClose();
//		double now;
//		while(iterator.hasNext()){
//	    now = ((StockDateNode) iterator.next()).getClose();
//		if(before>now){
//			fail();
//			return;
//		}
//		before = now;	
//		}
//	}
//
//	@Test
//	public void testDownSortSortTypeString() {
//		
//		stockCheck.getStockMessage("sh600000", "2016-02-21", "2016-03-21");
//		Iterator iterator = smartSort.downSort(DataType.close, "sh600000");
//		double before = ((StockDateNode) iterator.next()).getClose();
//		double now;
//		while(iterator.hasNext()){
//	    now = ((StockDateNode) iterator.next()).getClose();
//		if(before<now){
//			fail();
//			return;
//		}
//		before = now;	
//		}
//	}

}
