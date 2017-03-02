package service;

import java.net.SocketException;
import java.util.Iterator;

import enums.Location;
import enums.MessageType;
import message.Stock;

public interface StockCheckService {
	
	  public Iterator getStockMessage(String idNumber, String beginDate, String endDate) throws Exception;
	  public Iterator getStockList(MessageType type, String Message) throws Exception;
	  public Iterator getSelectedStock() throws Exception;
//	  public Iterator getAllStock(Location location);
	  public String getCName(String idNumber);
	  public Iterator<Stock> getStockMessageByCName(String CName);

}
