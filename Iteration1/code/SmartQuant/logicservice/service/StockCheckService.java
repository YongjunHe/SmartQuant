package service;

import java.util.Iterator;

import enums.MessageType;

public interface StockCheckService {
	
	  public Iterator getStockMessage(String idNumber, String beginDate, String endDate);
	  public Iterator getStockList(MessageType type, String Message);
	  public Iterator getSelectedStock();
	  public Iterator getAllStock();

}
