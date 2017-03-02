package data.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.enums.Location;
import data.message.Stock;
import data.message.StockDateNode;



public interface StockDisposeService {
	public Map getStockList(Location location);
	public Map getStockList(String year, Location location) throws Exception;
	public List getStockMessage(String idNumber, String beginDate, String endDate) throws Exception;
	public StockDateNode getStockDayMessage(String idNumber)throws Exception;
    public Map getSelectStockList()throws Exception;
    public String getCName(String idNumber);
    public Iterator<Stock> getStockMessageWithCName(String name);

}
