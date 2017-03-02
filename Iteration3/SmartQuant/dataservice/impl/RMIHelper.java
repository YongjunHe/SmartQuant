package impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

import service.StockDataService_RMI;
import service.SummaryDataService_RMI;


public class RMIHelper implements Runnable{
	
	private String URL;
	private static StockDataService_RMI stockService_RMI;
	private static SummaryDataService_RMI summaryService_RMI;
	private JDBCHelper helper;
	
	
	 
	public RMIHelper(JDBCHelper helper){
		this.helper = helper;
	}
	
	public void setService(){
		
		try {
			stockService_RMI = new StockDataImpl_RMI(helper);

			summaryService_RMI = new SummaryDataImpl_RMI(helper);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	




	public void setRMI(){
//		String URL = "localhost";
		try {
			URL = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(URL);

		try{
			LocateRegistry.createRegistry(1099);
			RMISocketFactory.setSocketFactory(new SMRMISocket());

	
			Naming.bind("//"+URL+":1099/getStockDataService", stockService_RMI);
			
			System.out.println("StockService succeed!!!");
			Naming.bind("//"+URL+":1099/getSummaryDataService",summaryService_RMI);
			System.out.println("SummaryService succeed!!!");

			System.out.println("OK to bound the RMI Service");

		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setService();
		this.setRMI();
		
	}


}

