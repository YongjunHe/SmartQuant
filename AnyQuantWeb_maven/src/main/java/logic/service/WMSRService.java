package logic.service;

import java.util.Iterator;



/*
 * @author: xuan
 * @date: 2016/04/01
 * 
 * @mender: none
 * @date: none
 * 
 * @type: interface
 * @description: 威廉指数分析接口
 */

public interface WMSRService extends Analysis {



	/*
	 * @author: xuan
	 * @date: 2016/04/01
	 * @description: 计算威廉指数，Cyc参数是计算天数
	 * 
	 */
	public Iterator WMSRWeek(String name, String beginDate, String endDate) throws Exception;


	/*
	 * @author: xuan
	 * @date: 2016/04/01
	 * @description: 计算day 
	 * 
	 */
	public Iterator WMSRDay(String name, String beginDate, String endDate) throws Exception;
	/*
	 * @author: xuan
	 * @date: 2016/04/01
	 * @description: 先调用WMSRData，再获得分析结论
	 * 
	 */
	public String getAnswer(String name);








}