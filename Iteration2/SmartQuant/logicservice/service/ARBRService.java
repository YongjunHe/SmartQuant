package service;

import java.net.SocketException;
import java.util.Iterator;

import enums.Cyc;
import sun.security.action.GetBooleanAction;

/*
 * @author: xuan
 * @date: 2016/04/01
 * 
 * @mender: none
 * @date: none
 * 
 * @type: interface
 * @description: 人气意愿分析接口
 */



public interface ARBRService {

	/*
	 * @author: xuan
	 * @date: 2016/04/01
	 * @description: 计算AR，Cyc参数是计算天数
	 * 
	 */
	public Iterator ARBRData(String name, Cyc cyc, String endDate) throws Exception;


	/*
	 * @author: xuan
	 * @date: 2016/04/01
	 * @description: 计算AR，day ARBR
	 * 
	 */
	public Iterator ARBRData(String name, String beginDate, String endDate) throws Exception;
	/*
	 * @author: xuan
	 * @date: 2016/04/01
	 * @description: 获得分析结论
	 * 
	 */
	public Iterator getSelectAnswer();
	public String getRecommend();

}	