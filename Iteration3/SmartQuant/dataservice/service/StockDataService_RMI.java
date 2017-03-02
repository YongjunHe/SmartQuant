package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import message.Stock;


public interface StockDataService_RMI  extends Remote{
	
	public Stock getStock(String name, String beginDate, String endDate) throws RemoteException;

	public ArrayList<Stock> getStockWithCNmae(String name, String beginDate, String endDate)
			throws RemoteException;

}
