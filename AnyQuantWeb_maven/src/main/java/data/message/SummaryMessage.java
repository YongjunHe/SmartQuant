package data.message;
import java.util.List;



public class SummaryMessage {
	private String status;
	private SummaryDateMessage data;
	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public SummaryDateMessage getData() {
		return data;
	}


	public void setData(SummaryDateMessage data) {
		this.data = data;
	}
	
	public static class SummaryDateMessage{
		private List <SummaryDateNode>trading_info;
		private String name;
		public List <SummaryDateNode> getTrading_info() {
			return trading_info;
		}
		public void setTrading_info(List <SummaryDateNode> trading_info) {
			this.trading_info = trading_info;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		
	}
	

}
