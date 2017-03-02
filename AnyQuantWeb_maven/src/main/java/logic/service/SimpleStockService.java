package logic.service;


import java.net.SocketException;

/*
 * @author: xuan
 * @date: 2016/04/13
 * 
 * @mender: none
 * @date: none
 * 
 * @type: interface
 * @description: 周月股票数据处理接口
 */

import java.util.Iterator;

import data.message.SimpleStock;



public interface SimpleStockService {
	
	
	
	/*
	 * @author: xuan
	 * @date: 2016/04/13
	 * @description: 计算周股票节点
	 * 
	 */
	public Iterator getSimpleWeekNodes(String idNumber, String beginDate,
			String endDate) throws Exception;
	
	
	
	/*
	 * @author: xuan
	 * @date: 2016/04/13
	 * @description: 计算月股票节点
	 * 
	 */
	public Iterator getSimpleMonthNodes(String idNumebr, String beginDate,
			String endDate);
	
	
	public SimpleStock getStock(String idNumber);

}
