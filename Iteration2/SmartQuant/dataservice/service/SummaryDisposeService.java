package service;

import java.net.SocketException;
import java.util.List;
import enums.MarketType;

public interface SummaryDisposeService {
	
	public List getSummaryMessage(MarketType marketType, String beginDate, String endDate) throws Exception;

}
