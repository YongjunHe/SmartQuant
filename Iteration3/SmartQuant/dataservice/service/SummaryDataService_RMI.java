package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import message.Stock;
import message.Summary;

public interface SummaryDataService_RMI  extends Remote{
	public Summary getSummary(String name, String beginDate, String endDate)throws RemoteException;

}
