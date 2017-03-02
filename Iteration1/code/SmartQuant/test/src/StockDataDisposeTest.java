package src;

/*
 * @author: xuan
 * @date: 2016/03/09
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 对StockDataDispose的单元测试
 */

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import impl.StockDataDispose;
import message.Stock;
import message.StockDateNode;

public class StockDataDisposeTest {
	public static StockDataDispose stockDataDispose = new StockDataDispose();
	

	@Test
	public void testGetStockList() {
		Map l2014 = stockDataDispose.getStockList("2014");
		Map l2015 = stockDataDispose.getStockList("2015");
	
		Stock stock1 = (Stock)l2014.get("sh600000");
		Stock stock2 = (Stock)l2015.get("sh600216");
		assertEquals("http://121.41.106.89:8010/api/stock/sh600000", stock1.getLink());
		assertEquals("http://121.41.106.89:8010/api/stock/sh600216", stock2.getLink());
	
	}

	@Test
	public void testGetStockMessage() {
		List stockMessage = stockDataDispose.getStockMessage("sh600216", "2016-02-24", "2016-02-29");
		StockDateNode node1 = (StockDateNode)stockMessage.get(0);
		StockDateNode node2 = (StockDateNode)stockMessage.get(1);
		assertEquals(13.16, node1.getHigh(), 0.01);
		assertEquals(12.96, node1.getOpen(), 0.01);
		assertEquals(13.35, node2.getHigh(), 0.01);
		assertEquals(13.0, node2.getOpen(), 0.01);
	}


}
