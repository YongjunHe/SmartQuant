package message;

import java.text.ParseException;
import java.util.Date;

import enums.Cyc;
import enums.DataType;
import impl.FinalSign;

public class SimpleStockNode implements NodeService {
	
	
	private double[]typeNumber;
	private Cyc cyc;
	private String date;
	private double open;
	private double close;
	private double high;
	private double low;
	private double volume;
	
	
	
	public Cyc getCyc() {
		return cyc;
	}
	public void setCyc(Cyc cyc) {
		this.cyc = cyc;
	}
	@Override
	public double getType(DataType sortType) {
		// TODO Auto-generated method stub
			
		if(typeNumber == null){
			String result = date.replace("-", "");
			typeNumber = new double[] {volume,0,high,0,low,Double.valueOf(result),close,open,0};
		}
			
		return typeNumber[sortType.ordinal()];
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
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
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
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
	

}
