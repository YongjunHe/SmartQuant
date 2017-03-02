package logic.service;

import java.util.Iterator;

public interface RSIService extends Analysis{
	
	
	/*
	 * @author: xuan
	 * @date: 2016/05/31
	 * @description: 计算日RSI
	 * 
	 */
	public Iterator getDayRSI(String idNumber, String beginDate,
			String endDate) throws Exception;
	
	
	
	/*
	 * @author: xuan
	 * @date: 2016/05/31
	 * @description: 计算月RSI
	 * 
	 */
	public Iterator getWeekRSI(String idNumebr, String beginDate,
			String endDate) throws Exception;

}

