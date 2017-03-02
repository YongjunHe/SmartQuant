package message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import enums.DataType;

public class MeanStock {
	private String name;
	private Map<String, List<MeanDateNode>> meanMap;
	
	public MeanStock(){
		meanMap = new HashMap<>();

	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public Iterator getMeanMessage(String type){
		List result = meanMap.get(type);
		return result.iterator();
	}
	
	public void setMap(String name, List<MeanDateNode>list){
		meanMap.put(name, list);
	}
 }
