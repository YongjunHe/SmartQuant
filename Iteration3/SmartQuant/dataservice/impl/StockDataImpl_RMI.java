package impl;

import java.awt.Stroke;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

import message.Stock;
import message.StockDateNode;
import service.StockDataService_RMI;

public class StockDataImpl_RMI extends UnicastRemoteObject implements StockDataService_RMI{
	
	
	private JDBCHelper helper;

	public StockDataImpl_RMI(JDBCHelper helper) throws RemoteException {
		super();
		this.helper = helper;
		// TODO Auto-generated constructor stub
	}


	@Override
	public Stock getStock(String name, String beginDate, String endDate) throws RemoteException{
		// TODO Auto-generated method stub
		return helper.selectStock(name, beginDate, endDate);
	}
	
	
	@Override
	public ArrayList<Stock> getStockWithCNmae(String name, String beginDate, String endDate) throws RemoteException{
		// TODO Auto-generated method stub
		return helper.selectStockWithCname(name);
	}
	
	
		
	}




