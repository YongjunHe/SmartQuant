package data.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.enums.Cyc;



public class SimpleStock {
	private String name;
	private Map<Cyc, List<SimpleStockNode>> maplist;
	
	public SimpleStock(){
		maplist = new HashMap<>();
		
		
	}
	
	

	public void setSimpleStockMessage(Cyc cyc,
			List<SimpleStockNode> nodeList) {
		// TODO Auto-generated method stub
		maplist.put(cyc, nodeList);
		
	}

	public Iterator getSimpleStockMessage(Cyc cyc) {
		// TODO Auto-generated method stub
		return maplist.get(cyc).iterator();
	}

	public void setName(String idNumebr) {
		// TODO Auto-generated method stub
		this.name = idNumebr;
	}
	
	public String getName(){
		return this.name;
	}



	public List getMessage(Cyc cyc) {
		// TODO Auto-generated method stub
		return maplist.get(cyc);
	}

}
