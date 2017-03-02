package message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import enums.Cyc;

public class WMSRStock {
	
	
	private String name;
	private Map<Cyc, List<WMSRDateNode>> WNSRMap;
	
	
	public WMSRStock(){
		WNSRMap = new HashMap<Cyc, List<WMSRDateNode>>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Iterator getWNSRMap(Cyc cyc) {
		return WNSRMap.get(cyc).iterator();
		
	}
	public void setWNSRM(Cyc cyc, List<WMSRDateNode> list) {
		WNSRMap.put(cyc, list);
	}
	
	
	
	

}
