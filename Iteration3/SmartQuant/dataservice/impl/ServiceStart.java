package impl;

public class ServiceStart {
	
	
	
	public static void main(String[]args){
		//Thread thread1 = new Thread(new DataBaseSet());
		//thread1.start();
		Thread thread2 = new Thread(new RMIHelper(JDBCHelper.create()));
		thread2.start();
		
	}

}
