package data.message;

import data.enums.DataType;

public interface NodeService {
	
	public double getType(DataType sortType);
	public String getDate();
	public int compareTo(String date);

}
