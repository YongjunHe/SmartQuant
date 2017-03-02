package service;

import java.net.SocketException;
import java.util.Date;
import java.util.Iterator;

import enums.CompareType;
import enums.MarketType;

public interface SummaryCheckService {
	public Iterator getSummaryMessage(MarketType type, String beginDate, String endDate) throws Exception;
	public Iterator getSummaryMessageByDrop(MarketType marketType, CompareType CompareType, double dropRate, String beginDate, String endDate);
	public Iterator getSummaryMessageByRise(MarketType marketType, CompareType CompareType, double riseRate, String beginDate, String endDate);


}
