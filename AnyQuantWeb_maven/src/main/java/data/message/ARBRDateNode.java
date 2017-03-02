package data.message;

import data.enums.Cyc;

public class ARBRDateNode {
	
	private String name;
	private Cyc cyc;
	private double AR;
	private double BR;
	private String beginDate;
	private String endDate;
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
	public double getBR() {
		return BR;
	}
	public void setBR(double bR) {
		BR = bR;
	}
	public double getAR() {
		return AR;
	}
	public void setAR(double aR) {
		AR = aR;
	}
	public Cyc getCyc() {
		return cyc;
	}
	public void setCyc(Cyc cyc) {
		this.cyc = cyc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
