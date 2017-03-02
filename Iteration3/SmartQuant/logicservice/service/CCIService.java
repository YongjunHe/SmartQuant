package service;

import java.util.Iterator;

public interface CCIService {
	
	/*
	 * @author: xuan
	 * @date: 2016/05/31
	 * @description: 计算日CCI
	 * 
	 */
	public Iterator getDayCCI(String idNumber, String beginDate,
			String endDate) throws Exception;
	
	
	
	/*
	 * @author: xuan
	 * @date: 2016/05/31
	 * @description: 计算月CCI
	 * 
	 */
	public Iterator getWeekCCI(String idNumebr, String beginDate,
			String endDate) throws Exception;

}
