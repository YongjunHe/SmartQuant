package src;

import static org.junit.Assert.*;

import java.net.SocketException;
import java.util.Iterator;

import org.junit.Test;

import impl.FinalSign;
import impl.SimpleStockImpl;
import message.SimpleStock;
import message.SimpleStockNode;

public class SimpleStockImplTest {
	
	private static SimpleStockImpl impl = SimpleStockImpl.create();
	
	

	@Test
	public void testGetSimpleWeekNodes() throws Exception {
		 Iterator iterator = impl.getSimpleWeekNodes("sh600011", "2016-02-21", "2016-03-21");
		 String current = "2016-02-22";
		 SimpleStockNode node = null;
		 while(iterator.hasNext()){
			node = (SimpleStockNode) iterator.next();
			assertEquals(current, node.getDate());
			current = FinalSign.weekChange(current, 1);
		}
		 assertEquals(7.85, node.getOpen(),0.01);
		 assertEquals(7.78, node.getClose(),0.01);
		
	}

	@Test
	public void testGetSimpleMonthNodes() {
		 Iterator iterator = impl.getSimpleMonthNodes("sh600011", "2015-01-21", "2015-03-21");
		 String current = "2015-01-01";
		 SimpleStockNode node = null;
		 while(iterator.hasNext()){
			 System.out.println(current);
			node = (SimpleStockNode) iterator.next();
			assertEquals(current, node.getDate());
			current = FinalSign.monthChange(current, 1);
		}
		 assertEquals(7.80, node.getOpen(),0.01);
		 assertEquals(8.26, node.getClose(),0.01);
	}

}
