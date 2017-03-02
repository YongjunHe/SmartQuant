package message;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import enums.DataType;
import impl.FinalSign;

/*
 * @author: xuan
 * @date: 2016/03/05
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 封装每天的大盘点数据
 */



public  class SummaryDateNode implements Comparable<SummaryDateNode>, NodeService, Serializable{
	private double[]typeNumber;

	private double volume;
	private double high;
	private double adj_price;
	private double low;
	private String date;
	private double close;
	private double open;
	private double chg;

	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getAdj_price() {
		return adj_price;
	}
	public void setAdj_price(double adj_price) {
		this.adj_price = adj_price;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}


	@Override
	public int compareTo(SummaryDateNode o) {
		// TODO Auto-generated method stub
		Date thisDate = null;
		Date otherDate = null;
		try {
			thisDate = FinalSign.DF.parse(this.date);
			otherDate = FinalSign.DF.parse(o.date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(thisDate.before(otherDate))
			return -1;
		else if(thisDate.after(otherDate))
			return 1;
		else return 0;
	}
	@Override
	public double getType(DataType sortType) {
		// TODO Auto-generated method stub
		if(typeNumber == null){
			String result = date.replace("-", "");
			typeNumber = new double[] {volume,0,high,adj_price,low,Double.valueOf(result),close,open,0};
		}


		return typeNumber[sortType.ordinal()];
	}
	
	
	public int compareTo(String date) {
		// TODO Auto-generated method stub
		Date thisDate = null;
		Date otherDate = null;
		try {
			thisDate = FinalSign.DF.parse(this.date);
			otherDate = FinalSign.DF.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(thisDate.before(otherDate))
			return -1;
		else if(thisDate.after(otherDate))
			return 1;
		else return 0;
	}
	public double getChg() {
		return chg;
	}
	public void setChg(double chg) {
		this.chg = chg;
	}


}
