package data.service;


import java.util.List;

import data.enums.MarketType;


public interface SummaryDisposeService {
	
	public List getSummaryMessage(MarketType marketType, String beginDate, String endDate) throws Exception;

}
