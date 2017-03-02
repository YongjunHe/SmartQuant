package logic.service;

/*
 * @author: xuan
 * @date: 2016/03/20
 * 
 * @mender: none
 * @date: none
 * 
 * @type: interface
 * @description: 对数据股票进行筛选排序
 */
import java.util.Iterator;

import data.enums.DataType;
import data.message.Range;


public interface SmartSortService {
	
	
	/*
	 * @author: xuan
	 * @date: 2016/03/20
	 * @description: 根据上下限对数据进行筛选
	 * 
	 */
	
	public Iterator sort(DataType sortType, Range range, Iterator iterator);
	/*
	 * @author: xuan
	 * @date: 2016/03/20
	 * @description: 根据数据类型进行升序排序
	 * 
	 */
	
	public Iterator upSort(DataType sortType, Iterator iterator);
	/*
	 * @author: xuan
	 * @date: 2016/03/20
	 * @description: 根据数据类型进行降序排列
	 * 
	 */
	
	public Iterator downSort(DataType sortType, Iterator iterator);

	
	
	

}
