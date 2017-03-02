package impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import message.Summary;
import service.SummaryDataService_RMI;

public class SummaryDataImpl_RMI extends UnicastRemoteObject  implements SummaryDataService_RMI{
	
	private JDBCHelper helper;

	protected SummaryDataImpl_RMI(JDBCHelper helper) throws RemoteException {
		super();
		this.helper = helper;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Summary getSummary(String name, String beginDate, String endDate) throws RemoteException{
		// TODO Auto-generated method stub
		return helper.selectSummary(name, beginDate, endDate);
	}

}
