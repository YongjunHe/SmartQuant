package src;

import static org.junit.Assert.*;

import java.net.SocketException;
import java.util.Iterator;

import org.junit.Test;

import enums.Cyc;
import impl.MeanCaculation;
import message.MeanDateNode;

public class MeanCaculationTest {
	

	private static MeanCaculation mean = MeanCaculation.create();

	@Test
	public void testDateMeanClose() throws Exception {
        Iterator  iterator = mean.dateMeanClose("sh600000", Cyc.week, "2016-02-21", "2016-03-21");
        	MeanDateNode meanDateNode = (MeanDateNode) iterator.next();
        	assertEquals(18.45, meanDateNode.getData(), 0.01);
        	while(iterator.hasNext()){
        	meanDateNode = 	(MeanDateNode) iterator.next();

        	}
//        	assertEquals(17.49, meanDateNode.getData(), 0.01);
	}
        

	@Test
	public void testDateMeanVolume() throws Exception {
        Iterator  iterator = mean.dateMeanVolume("sh600000", Cyc.month, "2016-02-21", "2016-03-21");
    	MeanDateNode meanDateNode = (MeanDateNode) iterator.next();
//    	assertEquals(418070.55, meanDateNode.getData(), 0.01);
    	while(iterator.hasNext()){
    		meanDateNode = (MeanDateNode) iterator.next();
    		
    	}
//    	assertEquals(212865.7, meanDateNode.getData(), 0.01);

	}
}
