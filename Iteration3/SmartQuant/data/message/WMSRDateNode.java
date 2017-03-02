package message;

import enums.Cyc;

public class WMSRDateNode {
	private String name;
	private Cyc cyc;
	private double data;
	private String beginDate;
	private String endDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Cyc getCyc() {
		return cyc;
	}
	public void setCyc(Cyc cyc) {
		this.cyc = cyc;
	}
	public double getData() {
		return data;
	}
	public void setData(double data) {
		this.data = data;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	

}
