package logic.service;

import java.util.Iterator;

import data.message.UniversalDateNode;

public interface XMAService {
	
	
	
	
	/*
	 * @author: xuan
	 * @date: 2016/06/17
	 * 
	 * @mender: none
	 * @date: none
	 * 
	 * @type: interface
	 * @description: XMA买入 p2=100 M=32 inXMA必须在outXMA之前调用，并且前四个参数要一致
	 */
	public Iterator<UniversalDateNode> inXMA(String idNumber, String beginDate, String endDate, int vary ,int p2);
	/*
	 * @author: xuan
	 * @date: 2016/06/17
	 * 
	 * @mender: none
	 * @date: none
	 * 
	 * @type: interface
	 * @description: XMA卖出 p1=80 N=20
	 */
	public Iterator<UniversalDateNode> outXMA(String idNumber, String beginDate, String endDate, int vary, int p1);

}
