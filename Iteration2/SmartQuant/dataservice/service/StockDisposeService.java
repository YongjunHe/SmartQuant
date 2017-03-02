package service;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

import enums.Location;
import message.StockDateNode;
import twaver.LayerModelListener;

public interface StockDisposeService {
	public Map getStockList(Location location);
	public Map getStockList(String year, Location location) throws Exception;
	public List getStockMessage(String idNumber, String beginDate, String endDate) throws Exception;
	public StockDateNode getStockDayMessage(String idNumber)throws Exception;
    public Map getSelectStockList()throws Exception;

}
