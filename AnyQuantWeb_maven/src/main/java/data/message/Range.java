package data.message;

public class Range {
	
	private double lower;
	private double upper;
	private boolean hasLower;
	private boolean hasUpper;
	public double getLower() {
		return lower;
	}
	public void setLower(double lower) {
		this.lower = lower;
	}
	public double getUpper() {
		return upper;
	}
	public void setUpper(double upper) {
		this.upper = upper;
	}
	public boolean isHasLower() {
		return hasLower;
	}
	public void setHasLower(boolean hasLower) {
		this.hasLower = hasLower;
	}
	public boolean isHasUpper() {
		return hasUpper;
	}
	public void setHasUpper(boolean hasUpper) {
		this.hasUpper = hasUpper;
	}
	
	public Range(double lower, double upper, boolean hasLower, boolean hasUpper){
		this.lower = lower;
		this.upper = upper;
		this.hasLower = hasLower;
		this.hasUpper = hasUpper;
	}
	
	
}
