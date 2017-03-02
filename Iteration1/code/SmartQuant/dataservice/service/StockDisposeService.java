package service;

import java.util.List;
import java.util.Map;
import message.StockDateNode;

public interface StockDisposeService {
	public Map getStockList(String year);
	public List getStockMessage(String idNumber, String beginDate, String endDate);
	public StockDateNode getStockDayMessage(String idNumber);

}
