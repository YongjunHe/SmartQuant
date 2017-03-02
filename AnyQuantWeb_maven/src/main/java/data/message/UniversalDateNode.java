package data.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.enums.Cyc;
import data.enums.DataType;
import data.helper.FinalSign;

;

public class UniversalDateNode implements Comparable<UniversalDateNode>, NodeService{
		
	private String date;
    private double data;
    private double data2;
    private double data3;
    public double getData2() {
		return data2;
	}
	public void setData2(double data2) {
		this.data2 = data2;
	}
	public double getData3() {
		return data3;
	}
	public void setData3(double data3) {
		this.data3 = data3;
	}

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
	public int compareTo(UniversalDateNode o) {
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
		return data;
	}
	@Override
	public int compareTo(String date) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString(){
		return date + " " + data;
	}
	

}
