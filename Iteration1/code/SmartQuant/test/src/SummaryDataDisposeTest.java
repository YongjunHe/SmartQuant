package src;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import enums.MarketType;
import impl.SummaryDataDispose;
import message.Summary;
import message.SummaryDateNode;

public class SummaryDataDisposeTest {
	public static SummaryDataDispose summary = new SummaryDataDispose();

	@Test
	public void testGetSummaryMessage() {
		List summaryMessage = summary.getSummaryMessage(MarketType.hs300, "2016-01-01","2016-02-02");
	    SummaryDateNode node1 = (SummaryDateNode)summaryMessage.get(0);
	    SummaryDateNode node2 = (SummaryDateNode)summaryMessage.get(1);
	    assertEquals(3731.0, node1.getClose(), 0.01);
		assertEquals(3760.9, node1.getOpen(), 0.01);
		assertEquals(3469.07, node2.getClose(), 0.01);
		assertEquals(3725.86, node2.getOpen(), 0.01);
	}

}
