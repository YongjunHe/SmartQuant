package impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import message.StockDateNode;
import service.StockDisposeService;




public class DataBaseSet implements Runnable{
	private JDBCHelper helper;
	private StockDisposeService stockDisposeService;




	public DataBaseSet(){
		helper = JDBCHelper.create();
		//		helper.creatDataBase();
		stockDisposeService = new StockDataDispose_Http();

	}


	public static void main(String[]args){
		Thread thread = new  Thread(new DataBaseSet());
		thread.start();
		
	}





	public boolean update(){



		ResultSet rs = helper.getAllStockList();
		try {
			while(rs.next()){
				String maxdate = helper.getMaxDate(rs.getString(1));
				Date current = new Date();
				String currentDate = FinalSign.DF.format(current);
				if(maxdate.equals(currentDate)){
					System.out.println(rs.getString(1) + "已更新");
					continue;
				}


				List<StockDateNode> lists  = stockDisposeService.getStockMessage(rs.getString(1), FinalSign.dateAddone(maxdate), currentDate);
				if(lists.isEmpty()){
					System.out.println(rs.getString(1) + "无更新数据！！！");
					continue;
				}

				for(StockDateNode node: lists){
					helper.insertStockDateNode(rs.getString(1), node);

				}

				System.out.println(rs.getString(1) + " 更新成功！！！");



			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}


	public void close(){

		helper.close();
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.update();
		
	}

}
