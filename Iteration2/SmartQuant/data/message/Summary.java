package message;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * @author: xuan
 * @date: 2016/03/05
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 封装大盘点数据
 */

public class Summary {
	private String name;
	private  List<SummaryDateNode> summaryDataNode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SummaryDateNode> getSummaryDataNode() {
		return summaryDataNode;
	}
	public void setSummaryDataNode(List<SummaryDateNode> summaryDataNode) {
		this.summaryDataNode = summaryDataNode;
	}
	public Iterator getAllDateMessage(){
		if(!summaryDataNode.isEmpty()){
//			Collections.sort(summaryDataNode);
			return summaryDataNode.iterator();
		}
		return null;
	}
	
	public List getDateMessage(){
		return summaryDataNode;
	}

}
