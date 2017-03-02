package impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import enums.StockType;

public class FinalSign {

	


	public final static double[] DAYS = {1, 5, 10, 20, 60};

	public final static double[] CANCEL = {0, 6, 13, 27, 83};
	public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM");
	public static final SimpleDateFormat SDF = new SimpleDateFormat("EEEE");
	public static final String SUNDAY = "星期日";
	public static final String SATURDAY = "星期六";
	public static final double DAYTIME = 24 * 60 * 60 * 1000;
	public static final String MONDAY = "星期一";
	private static final String[] STOCKCTPYE = {"互联网信息", "高速公路", "房地产", "软件及系统", "中药", "汽车制造", "钢铁"};
	private static final int[] CANCELMDAYS = {0, -1, 0, 1, 2, 3, 4, 5} ;
	private static final int[] CANCELFDAYS = {0, 5, 4, 3, 2, 1, 0, -1} ;

	
	
	public static String dateAddone(String source){
	
		Date date = null;
		String result = null;
		
	    try {
			date = DF.parse(source);
	
			if(!FinalSign.SDF.format(date).equals(FinalSign.SUNDAY))
				result = DF.format(date.getTime() + (long)FinalSign.DAYTIME);
			else {
				result = source;
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static String monthChange(String source, int number){
		
		Date date = null;
		String result = null;
		
	    try {
			date = DF.parse(source);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar rightNow = Calendar.getInstance();  
		rightNow.setTime(date);  
		rightNow.add(Calendar.MONTH, number);  
		result = DF.format(rightNow.getTime());
		
		return result;
	}
	
	
	public static String setMondayDate(String source){
		
		Date date = null;
		String result = null;
		
	    try {
			date = DF.parse(source);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar rightNow = Calendar.getInstance();  
		rightNow.setTime(date);  
		rightNow.add(Calendar.DATE, - CANCELMDAYS[rightNow.get(Calendar.DAY_OF_WEEK)]);  
		result = DF.format(rightNow.getTime());
		
		return result;
		
	}
public static String setFirdayDate(String source){
		
		Date date = null;
		String result = null;
		
	    try {
			date = DF.parse(source);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar rightNow = Calendar.getInstance();  
		rightNow.setTime(date);  
		rightNow.add(Calendar.DATE, CANCELFDAYS[rightNow.get(Calendar.DAY_OF_WEEK)]);  
		result = DF.format(rightNow.getTime());
		
		return result;
		
	}




public static String weekChange(String source, int number){
	
	Date date = null;
	String result = null;
	
    try {
		date = DF.parse(source);			
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	Calendar rightNow = Calendar.getInstance();  
	rightNow.setTime(date);  
	rightNow.add(Calendar.DATE, 7* number);  
	result = DF.format(rightNow.getTime());
	
	return result;
	
}
	
	
	public static String getStockCType(StockType stockType){
		
		
		return STOCKCTPYE[stockType.ordinal()];
		
	}
	
	
	public static void main(String[]args){
		String id = "2015-12-11";
		String id2 = FinalSign.setFirdayDate(id);
		System.out.println(id2);
		
		
		
	}
}
