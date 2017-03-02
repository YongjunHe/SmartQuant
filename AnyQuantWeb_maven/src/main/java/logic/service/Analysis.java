package logic.service;

import java.util.Iterator;

import data.message.UniversalDateNode;

public interface Analysis {
	
	/*
	 * @author: xuan
	 * @date: 2016/06/09
	 * @description: 分析标准
	 * 
	 */
	
	public String getStandard();
	
	/*
	 * @author: xuan
	 * @date: 2016/06/09
	 * @description: 分析结论
	 * 
	 */
	String getAnalysis(Iterator iterator);
	
	

}
