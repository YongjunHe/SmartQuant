package data.impl;



/*
 * @author: xuan
 * @date: 2016/05/07
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: JDBC 连接数据的helper
 */

import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.enums.StockType;
import data.helper.FinalSign;
import data.message.Stock;
import data.message.StockDateNode;
import data.message.Summary;
import data.message.SummaryDateNode;
import twaver.Rack;
import twaver.base.A.E.h;








public class JDBCHelper {  
	public static String url = null;  
	public static final String driver = "com.mysql.jdbc.Driver";  
	public static final String user = "root";  
	public static final String password = "xuan";  
	public static final String characterEncoding = "utf-8";
	private static JDBCHelper helper;

	private Connection conn = null;  
	private PreparedStatement pStatement = null;

	private JDBCHelper() {  
		try {  
			//动态设置url 只能本地操作 需修改

			url = "jdbc:mysql://localhost:3306/sys?characterEncoding=UTF-8";
			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, password);

			System.out.println("数据库连接成功！！！");

		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	}  

	public static JDBCHelper create(){
		if(helper == null){
			synchronized(JDBCHelper.class){

				if(helper == null)
					helper = new JDBCHelper();
			}
		}

		return helper;
	}







	public Summary selectSummary(String name, String beginDate, String endDate){
		Summary summary = new Summary();
		ArrayList<SummaryDateNode> nodes = new ArrayList<SummaryDateNode>();
		ResultSet rs = null;
		beginDate = FinalSign.dateMsOne(beginDate);
		String sql = "select * from sh000300 where date >= ? and date <= ?";//获得查询时间内的datanode

		try {

			pStatement = conn.prepareStatement(sql);
			//			pStatement.setString(1, name);
			pStatement.setDate(1, new java.sql.Date(FinalSign.DF.parse(beginDate).getTime())); 
			pStatement.setDate(2, new java.sql.Date(FinalSign.DF.parse(endDate).getTime()));  
			rs = pStatement.executeQuery();
			//存在重复代码，需要重构
			double bfChg = 0;
			if(rs.next())
				bfChg = rs.getDouble("close");
			
			
			while(rs.next()){
				SummaryDateNode node = new SummaryDateNode();
				node.setVolume(rs.getDouble("volume"));
				node.setHigh(rs.getDouble("high"));
				node.setAdj_price(rs.getDouble("adj_price"));
				node.setLow(rs.getDouble("low"));
				node.setDate(rs.getDate("date").toString());
				node.setClose(rs.getDouble("close"));
				node.setOpen(rs.getDouble("open"));
				node.setChg((rs.getDouble("close")- bfChg )/bfChg);
				bfChg = rs.getDouble("close");
				nodes.add(node);
			}
			Collections.sort(nodes);
			summary.setSummaryDataNode(nodes);
			summary.setName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return summary;
	}


	public ArrayList<Stock> selectStockWithCname(String CName){
		ArrayList<Stock>stocks = new ArrayList<Stock>();
		ArrayList<StockDateNode> nodes = new ArrayList<StockDateNode>();
		ResultSet rs = null;
		String sql1_0 = "select name from stock where cname = ?";
		String sql1_1 = "select name from stock where cname like '%%"+ CName +"%%'";

		try {
			pStatement = conn.prepareStatement(sql1_0);
			pStatement.setString(1, CName);
			rs = pStatement.executeQuery();
			if(rs.next()){
				stocks.add(selectStock(rs.getString("name")));
			}else{
				pStatement = conn.prepareStatement(sql1_1);
				//				pStatement.setString(1, name);
				rs = pStatement.executeQuery();
				while(rs.next()){
					stocks.add(selectStock(rs.getString("name")));

				}

			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stocks;
	}


	public Map getSelectStockList(){
		HashMap<String, Stock> result = new HashMap<String, Stock>();
		ResultSet rs = null;
		String sql = "select * from selectstock";

		try {
			pStatement = conn.prepareStatement(sql);
			rs = pStatement.executeQuery();
			while(rs.next()){

				Stock stock = new Stock();
				stock.setName(rs.getString("name").substring(2));
				stock.setCName(rs.getString("cname"));
				stock.setLink(rs.getString("url"));
				stock.setStockType(StockType.valueOf(rs.getString("stocktype")));
				stock.setTodayData(getNowDate(rs.getString("name")));
				result.put(rs.getString("name"), stock);
			}

		}catch(Exception e){

		}

		return  result;
	}



    public String  getMaxDate(String name){
    	String sql = "select max(date) from "+ name;//获得最近一天的datanode


		try {
			pStatement = conn.prepareStatement(sql);

			ResultSet rs = pStatement.executeQuery();
			if(rs.next())
			return rs.getString(1);
    	
    }catch(Exception e){
    	e.printStackTrace();
    }
		   return null;
	
    }
    
    
    
	private StockDateNode getNowDate(String name){
//		String sql = "select * from "+ name + " where date in (select max(date) from "+ name + ")";//获得最近一天的datanode
		String sql = "SELECT * FROM "+ name + " ORDER BY date DESC limit 0,2";
		StockDateNode node = new StockDateNode();
       


		try {
			pStatement = conn.prepareStatement(sql);

			ResultSet rs = pStatement.executeQuery();
			double nowClose = 0;
			double chg;


			if(rs.next()){
				node.setVolume(rs.getDouble("volume"));
				node.setPb(rs.getDouble("pb"));
				node.setHigh(rs.getDouble("high"));
				node.setAdj_price(rs.getDouble("adj_price"));
				node.setLow(rs.getDouble("low"));
				node.setDate(rs.getDate("date").toString());
				nowClose = rs.getDouble("close");
				node.setClose(nowClose);
				node.setOpen(rs.getDouble("open"));
				node.setTurnover(rs.getDouble("turnover"));
			} 
			
			if(rs.next()){
				chg = (nowClose - rs.getDouble("close")) / rs.getDouble("close");
				node.setChg(chg);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return node;
	}

	private Stock selectStock(String name){
		Stock stock = new Stock();
		ResultSet rs = null;
		String sql = "select * from stock where name = ?";
		try {
			pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, name);
			rs = pStatement.executeQuery();
			if(rs.next()){
				stock.setName(rs.getString("name").substring(2));
				stock.setCName(rs.getString("cname"));
				stock.setLink(rs.getString("url"));
				//				stock.setStockType(StockType.valueOf(rs.getString("stocktype")));
			}else{
				return null;
			}
		}catch(Exception e){

		}

		stock.setTodayData(getNowDate(name));


		return stock;
	}


	public Stock selectStock(String name, String beginDate, String endDate){
		Stock stock = selectStock(name);
		ArrayList<StockDateNode> nodes = new ArrayList<StockDateNode>();
		ResultSet rs = null;
        beginDate = FinalSign.dateMsOne(beginDate);
		String sql = "select * from "+ name +" where date >= ? and date <= ?";//获得查询时间内的datanode

		if(stock==null)
			return null;
		try{

			pStatement = conn.prepareStatement(sql);
			pStatement.setDate(1, new java.sql.Date(FinalSign.DF.parse(beginDate).getTime()));  
			pStatement.setDate(2, new java.sql.Date(FinalSign.DF.parse(endDate).getTime()));  
			rs = pStatement.executeQuery();
			//存在重复代码，需要重构
			
			double bfChg = 0;
			if(rs.next())
				bfChg = rs.getDouble("close");
			while(rs.next()){
				StockDateNode node = new StockDateNode();
				node.setVolume(rs.getDouble("volume"));
				node.setPb(rs.getDouble("pb"));
				node.setHigh(rs.getDouble("high"));
				node.setAdj_price(rs.getDouble("adj_price"));
				node.setLow(rs.getDouble("low"));
				node.setDate(rs.getDate("date").toString());
				node.setClose(rs.getDouble("close"));
				node.setOpen(rs.getDouble("open"));
				node.setTurnover(rs.getDouble("turnover"));
				node.setChg((rs.getDouble("close")- bfChg )/bfChg);
				bfChg = rs.getDouble("close");
				nodes.add(node);
			}
			Collections.sort(nodes);
			stock.setDateMessage(nodes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return stock;
	}




	public boolean insertStockMessage(Stock stockpo) {
		//		缺少股票类
		String sql = "insert into stock(name, cname, url) values(?, ?, ?)";
		boolean flag = true;
		// TODO Auto-generated method stub
		try {
			pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, stockpo.getName());
			pStatement.setString(2, stockpo.getCName());
			pStatement.setString(3, stockpo.getLink());
			//						pStatement.setString(4, stockpo.getStockType().toString());
			int i = pStatement.executeUpdate();
			if(i == 0){
				flag=false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator<StockDateNode>iterator = stockpo.getAllDateMessage();
		if(iterator == null)
			return false;
		while(iterator.hasNext()){
			flag = insertStockDateNode(stockpo.getName(), iterator.next());
			if(!flag)
				return flag;
		}
		if(flag){
			System.out.println(stockpo.getName() + " insert succeed!!!");


		}
		return flag;

	}  


	public boolean insertStockDateNode(String name, StockDateNode dateNode) {
		String sql = "insert into " + name +"(volume, pb, high, adj_price, low, date, close, open, turnover) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		//		String sql1 = "insert into stock(name, pb, date) values(?, ?, ?)";
		boolean flag = true;
		// TODO Auto-generated method stub
		try {
			pStatement = conn.prepareStatement(sql);
			pStatement.setDouble(1, dateNode.getVolume());
			pStatement.setDouble(2, dateNode.getPb());
			pStatement.setDouble(3, dateNode.getHigh());
			pStatement.setDouble(4, dateNode.getAdj_price());
			pStatement.setDouble(5, dateNode.getLow());
			pStatement.setDate(6, new java.sql.Date(FinalSign.DF.parse(dateNode.getDate()).getTime()));  
			pStatement.setDouble(7, dateNode.getClose());
			pStatement.setDouble(8, dateNode.getOpen());
			pStatement.setDouble(9, dateNode.getTurnover());
			int i = pStatement.executeUpdate();
			if(i==0){
				flag=false;
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;

	}  

	public boolean deleteStock(){
		boolean flag=true;

		String sql = "delete from stock";

		try {
			pStatement = conn.prepareStatement(sql);
			int i = pStatement.executeUpdate();
			if(i==0){
				flag = false;
				return flag;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return flag;
	}


	public boolean deleteStockNode(){
		boolean flag=true;

		String sql = "delete from stockdatenode";

		try {
			pStatement = conn.prepareStatement(sql);
			int i = pStatement.executeUpdate();
			if(i==0){
				flag = false;
				return flag;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return flag;
	}


	public boolean deleteSummaryNode(){
		boolean flag=true;

		String sql = "delete from summarydatenode";

		try {
			pStatement = conn.prepareStatement(sql);
			int i = pStatement.executeUpdate();
			if(i==0){
				flag = false;
				return flag;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return flag;
	}


	public boolean deleteAll(){
		boolean flag=true;

		String sql1 = "delete from stock";
		String sql2 = "delete from stockdatenode";
		String sql3 = "delete from summarydatenode";

		try {
			pStatement = conn.prepareStatement(sql1);
			int i = pStatement.executeUpdate();
			if(i==0){
				flag = false;
				return flag;
			}
			pStatement = conn.prepareStatement(sql2);
			i = pStatement.executeUpdate();
			if(i == 0){
				flag = false;
				return flag;
			}


			pStatement = conn.prepareStatement(sql3);
			i = pStatement.executeUpdate();
			if(i == 0)
				flag = false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return flag;


	}


	public String getCName(String idNumber){

		String sql = "select cname from stock where name = ?";
		ResultSet rs = null;

		try {
			pStatement = conn.prepareStatement(sql);

			pStatement.setString(1, idNumber);

			rs = pStatement.executeQuery();

			if(rs.next())
				return rs.getString(1);



		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}



	public boolean delete(String name){

		boolean flag=true;

		String sql1 = "delete from stock where name = ?";
		String sql2 = "delete from stockdatenode where name = ?";

		try {
			pStatement = conn.prepareStatement(sql1);
			pStatement.setString(1, name);
			int i = pStatement.executeUpdate();
			if(i==0){
				flag = false;
				return flag;
			}
			pStatement = conn.prepareStatement(sql2);
			pStatement.setString(1, name);
			i = pStatement.executeUpdate();
			if(i == 0)
				flag = false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return flag;
	}

	public void close() {  
		try {  
			conn.close();  
			pStatement.close();  
			System.out.println("数据库连接关闭！！！");
		} catch (SQLException e) {  
			e.printStackTrace();  
		}  
	}


	public boolean insertSummaryMessage(Summary summary) {
		String sql = "insert into summarydatenode(name, volume, high, adj_price, low, date, close, open) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";		
		boolean flag = true;
		// TODO Auto-generated method stub
		Iterator<SummaryDateNode> iterator = summary.getAllDateMessage();
		if(iterator == null)
			return true;
		while(iterator.hasNext()){
			try {
				SummaryDateNode dateNode = iterator.next();
				pStatement = conn.prepareStatement(sql);
				pStatement.setString(1, summary.getName());
				pStatement.setDouble(2, dateNode.getVolume());
				pStatement.setDouble(3, dateNode.getHigh());
				pStatement.setDouble(4, dateNode.getAdj_price());
				pStatement.setDouble(5, dateNode.getLow());
				pStatement.setDate(6, new java.sql.Date(FinalSign.DF.parse(dateNode.getDate()).getTime()));  
				pStatement.setDouble(7, dateNode.getClose());
				pStatement.setDouble(8, dateNode.getOpen());

				int i = pStatement.executeUpdate();
				if(i==0){
					flag = false;
					return flag;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return flag;

	}  




	public void creatDataBase(){
		int flag;
		String sql1 = "create table stock(name varchar(20), cname varchar(20), url varchar(50), "
				+ "stocktype enum('Internet', 'Motorway', 'Estate', 'Software', 'Traditional_Chinese_Medicine', 'Car_Machine, Steel'), primary key(name))";
		String sql2 = "create table stockdatenode(name varchar(20), volume double, pb double, high double, adj_price double, low double, date date, close double, open double, turnover double, primary key(date, name))";
		String sql3 = "create table summarydatenode(name varchar(20), volume double, high double, adj_price double, low double, date date, close double, open double, primary key(name, date))";
		try {
			pStatement = conn.prepareStatement(sql1);
			flag = pStatement.executeUpdate();
			if(flag == -1)
				System.out.println("stock creates not succeed");
			else
				System.out.println("stock creates succeed");

			pStatement = conn.prepareStatement(sql2);
			flag = pStatement.executeUpdate();
			if(flag == -1)
				System.out.println("stockdatenode creates not succeed");
			else
				System.out.println("stockdatenode creates succeed");

			pStatement = conn.prepareStatement(sql3);
			flag = pStatement.executeUpdate();
			if(flag == -1)
				System.out.println("summarydatenode creates not succeed");
			else
				System.out.println("summarydatenode creates succeed");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void createAnotherWay(){
		ResultSet rs = this.getAllStockList();
		StockDataDispose_Http stockDisposeService = new StockDataDispose_Http();
		try {
			
			for(int i=0;i<2223;i++)
				rs.next();
			while(rs.next()){

				String sql = "create table " + rs.getString(1) + "(volume double, pb double, high double, adj_price double, low double, date date, close double, open double, turnover double, primary key(date))";

				pStatement = conn.prepareStatement(sql);
				pStatement.executeUpdate();
				System.gc();
				List<StockDateNode>list = stockDisposeService.getStockMessage(rs.getString(1), "2014-01-01", "2016-06-10");
				for(StockDateNode node : list){
					this.insertStockDateNode(rs.getString(1), node);
				}
				System.out.println(rs.getString(1) + "set!!!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ResultSet getAllStockList(){
		HashMap<String, Stock> result = new HashMap<String, Stock>();
		ResultSet rs = null;
		String sql = "select name from stock";

		try {
			pStatement = conn.prepareStatement(sql);
			rs = pStatement.executeQuery();
		}catch(Exception e){

		}

		return  rs;
	}
	
	
	public void test(){
		String sql = "show tables";
		ResultSet rs = null;

		try {
			pStatement = conn.prepareStatement(sql);

			rs = pStatement.executeQuery();

			while(rs.next())
              System.out.println(rs.getString(1));


		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[]args){
		JDBCHelper helper = JDBCHelper.create();
		helper.test();		
	}
	
	
  




}  
