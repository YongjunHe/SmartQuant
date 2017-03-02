package data.message;

import java.text.ParseException;
import java.util.Date;

import data.enums.Cyc;
import data.enums.DataType;
import data.helper.FinalSign;

public class CorrelationDateNode implements Comparable<CorrelationDateNode>, NodeService{
	private String date;
    private double data1;
    private double data2;
    private Cyc cyc;
    private DataType dataType;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public int compareTo(CorrelationDateNode o) {
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
		return data1;
	}
	public double getData2() {
		return data2;
	}
	public void setData2(double data2) {
		this.data2 = data2;
	}
	public double getData1() {
		return data1;
	}
	public void setData1(double data1) {
		this.data1 = data1;
	}
	@Override
	public int compareTo(String date) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString(){
		return date + " " + data1+" "+data2;
	}
}
