package data.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.enums.Cyc;



public class ARBRStock {
	
	private String name;
	private Map<Cyc, List<ARBRDateNode>> ARBRMap;
	
	public ARBRStock(){
		ARBRMap = new HashMap<>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Iterator getARBRMap(Cyc cyc) {
		return ARBRMap.get(cyc).iterator();
	}
	public void setARBR(Cyc cyc, List<ARBRDateNode>list) {
		ARBRMap.put(cyc, list);
	}
	
	
	
	
	
	

}
