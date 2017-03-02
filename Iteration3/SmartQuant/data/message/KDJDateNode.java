package message;

import java.text.ParseException;
import java.util.Date;

import enums.Cyc;
import impl.FinalSign;

public class KDJDateNode implements Comparable<KDJDateNode>{
	
	
	private double K;
	private double J;
	private double D;
	private String date;
	private Cyc cyc;
	
	
	
	public double getK() {
		return K;
	}
	public void setK(double k) {
		K = k;
	}
	public double getJ() {
		return J;
	}
	public void setJ(double j) {
		J = j;
	}
	public double getD() {
		return D;
	}
	public void setD(double d) {
		D = d;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Cyc getCyc() {
		return cyc;
	}
	public void setCyc(Cyc cyc) {
		this.cyc = cyc;
	}
	
	@Override
	public int compareTo(KDJDateNode o) {
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
	
	public String toString(){
		return date + ":" + K + " "+ D +" " + J;  
		
		
	}
		

}
