package data.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.enums.Cyc;
import data.enums.DataType;
import data.helper.FinalSign;



public class MeanDateNode implements Comparable<MeanDateNode>{
		
	private String date;
    private double data;
    private Cyc cyc;
    private DataType dataType;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getData() {
		return data;
	}
	public void setData(double data) {
		this.data = data;
	}
	public Cyc getVary() {
		return cyc;
	}
	public void setVary(Cyc vary) {
		this.cyc = vary;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public int compareTo(MeanDateNode o) {
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
	

}
