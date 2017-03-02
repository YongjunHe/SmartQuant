package service;

import java.util.Iterator;

public interface KDJService {
	

	
	/*
	 * @author: xuan
	 * @date: 2016/06/01
	 * @description: 计算日KDJ，周期为5日
	 * 
	 */
	public Iterator getDayKDJ(String idNumber, String beginDate,
			String endDate) throws Exception;
	
	
	
	/*
	 * @author: xuan
	 * @date: 2016/06/01
	 * @description: 计算周KDJ，周期为4周
	 * 
	 */
	public Iterator getWeekKDJ(String idNumebr, String beginDate,
			String endDate) throws Exception;

}

