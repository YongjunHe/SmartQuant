<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.service.*"%>
<%@ page import="data.impl.*"%>
<%@ page import="data.enums.*"%>
<%@ page import="data.message.*"%>
<%@ page import="logic.impl.*"%>
<%@ page import="logic.service.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.google.gson.*"%>
<%
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	String name = request.getParameter("name");
	String result = "";
	
	Gson gson = new Gson();
	
	if(name.equals("sh000300")){
		SummaryCheckService summaryCheckService = SummaryCheck.create();
		Iterator stockIterator = summaryCheckService.getSummaryMessage(MarketType.hs300, beginDate, endDate);

		
		

		
		ArrayList<SummaryDateNode> list = new ArrayList<SummaryDateNode>();
		while(stockIterator.hasNext()){
	  		SummaryDateNode node=(SummaryDateNode) stockIterator.next();
	  		list.add(node);
		}
		result = gson.toJson(list);
	}else{
		StockCheckService stockCheckService = StockCheck.create();
		Iterator stockIterator = stockCheckService.getStockMessage(name, beginDate, endDate);
		
		ArrayList<StockDateNode> list = new ArrayList<StockDateNode>();
		while(stockIterator.hasNext()){
	    	StockDateNode node=(StockDateNode) stockIterator.next();
	  		list.add(node);
		}
		result = gson.toJson(list);
	}
	
	

	
	

	out.print(result);

%>