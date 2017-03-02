package data.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.rmi.RemoteException;

/*
 * @author: xuan
 * @date: 2016/03/04
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: 对来自Http的股票数据进行处理
 */


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.gson.Gson;

import data.enums.*;
import data.helper.FinalSign;
import data.message.Stock;
import data.message.StockDateNode;
import data.message.StockList;
import data.message.StockMessage;
import data.service.StockDisposeService;
import twaver.base.A.E.i;


public class StockDataDispose_Http implements StockDisposeService {
	private static final String URL = "http://121.41.106.89:8010/api/stock/";
	private static final String API = "api/stocks/?year=";
	private static final String EXCHANGE[] = {"&exchange=sh","&exchange=sz"};
	private static final String FILENAME = "data.xls"; 
	private static final String CHECKTYPE = "&fields=open+high+close+low+volume+adj_price+turnover+pb"; 

	

	/*
	 * @author: xuan
	 * @date: 2016/03/04
	 * @description: 获得股票列表
	 */
	public Map getStockList(String year, Location location)throws Exception{
		String url = API + year + EXCHANGE[location.ordinal()];

		String response = HttpHelper.getHttp(url);
		Gson gson = new Gson();		
		StockList stockList = gson.fromJson(response, StockList.class);
		Map<String, Stock>stocks = stockList.getData().stream().collect(Collectors.toMap(Stock::getName,(p) -> p));//list translates to map, cost a lot need to fix 
		return stocks;
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/04
	 * @description: 获得当年股票列表
	 */
	public Map getStockList(Location location){
		List<Stock>stockList = new ArrayList<>();
		HSSFWorkbook workbook = ExcelHelper.getExcel(FILENAME);
		HSSFSheet sheet = workbook.getSheetAt(location.ordinal());
		Iterator iterator = sheet.rowIterator();
		iterator.next();//跳过表头
		while(iterator.hasNext()){
			HSSFRow row = (HSSFRow) iterator.next();
			Stock stock = new Stock();
			stock.setName(row.getCell(0).getStringCellValue());		
			stock.setCName(row.getCell(1).getStringCellValue());
			stock.setLink(row.getCell(2).getStringCellValue());

			stockList.add(stock);	
		}
		Map<String, Stock>stocks = stockList.stream().collect(Collectors.toMap(Stock::getName,(p) -> p));//list translates to map, cost a lot need to fix 

		return stocks;
	}


	/*
	 * @author: xuan
	 * @date: 2016/04/06
	 * @description: 获得推荐股票列表
	 */

	public Map getSelectStockList() throws Exception{
		List<Stock>stockList = new ArrayList<>();
		HSSFWorkbook workbook = ExcelHelper.getExcel(FILENAME);
		HSSFSheet sheet = workbook.getSheetAt(2);
		Iterator iterator = sheet.rowIterator();
		iterator.next();//跳过表头
		while(iterator.hasNext()){
			HSSFRow row = (HSSFRow) iterator.next();
			Stock stock = new Stock();
			stock.setName(row.getCell(0).getStringCellValue());		
			stock.setCName(row.getCell(1).getStringCellValue());
			stock.setLink(row.getCell(2).getStringCellValue());
			stock.setStockType(StockType.valueOf(row.getCell(3).getStringCellValue()));
			stock.setTodayData(getStockDayMessage(stock.getName()));


			stockList.add(stock);	
		}
		Map<String, Stock>stocks = stockList.stream().collect(Collectors.toMap(Stock::getName,(p) -> p));//list translates to map, cost a lot need to fix 


		return stocks;
	}





	/*
	 * @author: xuan
	 * @date: 2016/03/04
	 * @description: 获得指定股票在指定时间内的具体信息
	 */
	public List getStockMessage(String idNumber, String beginDate, String endDate)throws Exception{
		String url = "api/stock/"+ idNumber +"/?start=" + beginDate + "&end=" + FinalSign.dateAddone(endDate)
				+ CHECKTYPE;

		String response = HttpHelper.getHttp(url);
	
		
		Gson gson = new Gson();
		StockMessage stockMessage = null;
		try {
			stockMessage = gson.fromJson(response, StockMessage.class);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		List dateMessage = stockMessage.getData().getTrading_info();
		Collections.sort(dateMessage);
		return dateMessage;
				
	}

	/*
	 * @author: xuan
	 * @date: 2016/03/08
	 * @description: 获得最新一天数据
	 */

	public StockDateNode  getStockDayMessage(String idNumber) throws Exception{
		Date date = new Date();
		String endDate = FinalSign.DF.format(date);
		String beginDate = null;
		if(FinalSign.SDF.format(date).equals(FinalSign.MONDAY))
			beginDate = FinalSign.DF.format(date.getTime() - (long)3 * FinalSign.DAYTIME);
		else if(FinalSign.SDF.format(date).equals(FinalSign.SUNDAY)){
			beginDate = FinalSign.DF.format(date.getTime() - (long)2 * FinalSign.DAYTIME);
		}else {
			beginDate = FinalSign.DF.format(date.getTime() - (long) FinalSign.DAYTIME);
		}
		List<StockDateNode>dateMessage = getStockMessage(idNumber, beginDate, endDate);

		return dateMessage.get(0);
	}


	/*
	 * @author: xuan
	 * @date: 2016/03/04
	 * @description: 测试代码
	 */

	/*
	 * @author: xuan
	 * @date: 2016/03/18
	 * @description: 保存股票列表
	 */
	private void saveStockList() throws Exception{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet0 = workbook.createSheet("上交所");
		HSSFSheet sheet1 = workbook.createSheet("深交所");
		for(int i=0;i<2;i++){
			sheet0.setColumnWidth((short)i, 25* 256); 
			sheet1.setColumnWidth((short)i, 25* 256);//设置单元格宽度
		}

		sheet0.setColumnWidth((short)2, 35* 256); 
		sheet1.setColumnWidth((short)2, 35* 256);

		HSSFRow row0 = sheet0.createRow(0);
		HSSFCell cell01 = row0.createCell(0);
		HSSFCell cell02 = row0.createCell(1);
		HSSFCell cell03 = row0.createCell(2);
		HSSFCell cell04 = row0.createCell(3);
		cell01.setCellValue("股票代号");
		cell02.setCellValue("股票名");
		cell03.setCellValue("股票链接");
		cell04.setCellValue("股票类型");

		HSSFRow row1 = sheet1.createRow(0);
		HSSFCell cell11 = row1.createCell(0);
		HSSFCell cell12 = row1.createCell(1);
		HSSFCell cell13 = row1.createCell(2);
		HSSFCell cell14 = row0.createCell(3);
		cell11.setCellValue("股票代号");
		cell12.setCellValue("股票名");
		cell13.setCellValue("股票链接");
		cell14.setCellValue("股票类型");
		Map map0 = this.getStockList("2016", Location.sh);
		Iterator iterator0 = map0.values().iterator();
		int i = 1;
		while(iterator0.hasNext()){

			Stock stock = (Stock) iterator0.next();
			HSSFRow r = sheet0.createRow(i);
			HSSFCell c1 = r.createCell(0);
			HSSFCell c2 = r.createCell(2);
			HSSFCell c3 = r.createCell(1);
			c1.setCellValue(stock.getName());
			c2.setCellValue(stock.getLink());
			c3.setCellValue(HttpConnectUtil.getName(stock.getName()));
			i++;
		}

		Map map1 = this.getStockList("2016", Location.sz);
		Iterator iterator1 = map1.values().iterator();
		//		List ss = stock.getData();
		//		Iterator iterator = ss.iterator();
		int n = 1;
		while(iterator1.hasNext()){

			Stock stock = (Stock) iterator1.next();
			HSSFRow r = sheet1.createRow(n);
			HSSFCell c1 = r.createCell(0);
			HSSFCell c2 = r.createCell(2);
			HSSFCell c3 = r.createCell(1);
			c1.setCellValue(stock.getName());
			c2.setCellValue(stock.getLink());
			c3.setCellValue(HttpConnectUtil.getName(stock.getName()));
			n++;
		}
		ExcelHelper.saveExcel(workbook, FILENAME);

	}


	@Override
	public String getCName(String idNumber) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterator<Stock> getStockMessageWithCName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
