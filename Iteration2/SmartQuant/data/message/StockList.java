package message;

import java.util.List;


public class StockList {
	private String status;
	private List<Stock>data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Stock> getData() {
		return data;
	}
	public void setData(List<Stock> data) {
		this.data = data;
	}

}
