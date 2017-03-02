package message;

import java.util.List;

public class StockMessage {
	private String status;
	private StockDateMessage data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public StockDateMessage getData() {
		return data;
	}
	public void setData(StockDateMessage data) {
		this.data = data;
	}


	public static class StockDateMessage {
		private List <StockDateNode>trading_info;
		private String name;
		

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List <StockDateNode> getTrading_info() {
			return trading_info;
		}
		public void setTrading_info(List <StockDateNode> trading_info) {
			this.trading_info = trading_info;
		}

	}


}
