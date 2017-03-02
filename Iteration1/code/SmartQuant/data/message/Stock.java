package message;


import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sun.print.resources.serviceui;


/*
 * @author: xuan
 * @date: 2016/03/02
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 封装股票具体信息
 */

public class Stock {
	private String link;//访问地址
	private String name;
	private String IDNumbe;
	private List<StockDateNode>dateMessage;
	private StockDateNode todayData;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIDNumbe() {
		return IDNumbe;
	}
	public void setIDNumbe(String iDNumbe) {
		IDNumbe = iDNumbe;
	}

	public Iterator getAllDateMessage(){
		if(!dateMessage.isEmpty()){
//			Collections.sort(dateMessage);
			return dateMessage.iterator();//返回value的迭代器
		}
		return null;
	}
	public void setDateMessage(List<StockDateNode>dateMessage) {
		this.dateMessage = dateMessage;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
	
	public String toString(){  
        return "name: " + name + " link: " + link; 
    }
	public StockDateNode getTodayData() {
		return todayData;
	}
	public void setTodayData(StockDateNode todayData) {
		this.todayData = todayData;
	}  
	
	

}
