package service;

import java.net.SocketException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

/*
 * @author: hyj14
 * @date: 2016/04/13
 * 
 * @mender: none
 * @date: none
 * 
 * @type: interface
 * @description: 均幅指标接口
 */

public interface ATRService {
	/*
	 * @author: hyj14
	 * 
	 * @date: 2016/04/13
	 * 
	 * @description: 计算ATR
	 */
	public Map ATRData(String name, String beginDate, String endDate)
			throws SocketException, Exception;

	/*
	 * @author: hyj14
	 * 
	 * @date: 2016/04/13
	 * 
	 * @description: 计算MATR
	 */
	public Map MATRData(String name, String beginDate, String endDate)
			throws SocketException, Exception;

}
