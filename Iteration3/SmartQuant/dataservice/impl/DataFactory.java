package impl;



import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import service.StockDataService_RMI;
import service.SummaryDataService_RMI;



public class DataFactory{
	String URL;
	private static DataFactory dataFactory;
	private static StockDataService_RMI stockService_RMI;
	private static SummaryDataService_RMI summaryService_RMI;



	private DataFactory(){
		//System.setSecurityManager(new RMISecurityManager());
		URL = "10.0.2.10";
		try {
			summaryService_RMI = (SummaryDataService_RMI)Naming.lookup("//"+URL+":1099/getSummaryDataService");
			stockService_RMI = (StockDataService_RMI)Naming.lookup("//"+URL+":1099/getStockDataService");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public SummaryDataService_RMI getSummaryService_RMI(){

		return summaryService_RMI;
	}


	public StockDataService_RMI getStockService_RMI(){

		return stockService_RMI;
	}





	public static DataFactory create() throws RemoteException{
		if(dataFactory == null){
			synchronized(DataFactory.class){

				if(dataFactory == null)
					dataFactory = new DataFactory();
			}
		}

		return dataFactory;
	}


}
