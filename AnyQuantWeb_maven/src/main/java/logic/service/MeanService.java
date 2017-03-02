package logic.service;


import java.net.SocketException;

/*
 * @author: xuan
 * @date: 2016/03/30
 * 
 * @mender: none
 * @date: none
 * 
 * @type: interface
 * @description: 提供收盘价以及成交量的平均数据处理
 */

import java.util.Iterator;
import java.util.List;

import data.enums.Cyc;



public interface MeanService {
	/*
	 * @author: xuan
	 * @date: 2016/03/30
	 * @description: 获得收盘价的平均值，Cyc参数是求平均的天数
	 * 
	 */

	public Iterator dateMeanClose(String name, Cyc cyc, String beginDate, String endDate) throws Exception;
	
	/*
	 * @author: xuan
	 * @date: 2016/03/30
	 * @description: 获得成交量的平均值，Cyc参数是求平均的天数
	 * 
	 */
	
	public Iterator dateMeanVolume(String name, Cyc cyc, String beginDate, String endDate) throws Exception;
	
	/*
	 * @author: xuan
	 * @date: 2016/04/13
	 * @description: 获得周收盘价的平均值，Cyc参数是求平均的天数
	 * 
	 */
	
	public Iterator weekMeanClose(String name, Cyc cyc, String beginDate, String endDate) throws Exception;
	/*
	 * @author: xuan
	 * @date: 2016/04/13
	 * @description: 获得周成交量的平均值，Cyc参数是求平均的天数
	 * 
	 */
	
	public Iterator weekMeanVolume(String name, Cyc cyc, String beginDate, String endDate) throws Exception;
	/*
	 * @author: xuan
	 * @date: 2016/04/13
	 * @description: 获得月成交量的平均值，Cyc参数是求平均的天数
	 * 
	 */
	
	public Iterator monthMeanVolume(String name, Cyc cyc, String beginDate, String endDate);
	/*
	 * @author: xuan
	 * @date: 2016/04/13
	 * @description: 获得月收盘价的平均值，Cyc参数是求平均的天数
	 * 
	 */
	
	public Iterator monthMeanClose(String name, Cyc cyc, String beginDate, String endDate);
}
