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
	
	ArrayList<UniversalDateNode> list = new ArrayList<UniversalDateNode>();
	Iterator iterator = null;
	
	if(name.equals("sh000300")){
		SummaryCheckService summaryCheckService = SummaryCheck.create();
		iterator = summaryCheckService.getSummaryMessage(MarketType.hs300, beginDate, endDate);

		while(iterator.hasNext()){
	  		SummaryDateNode node=(SummaryDateNode) iterator.next();
	  		UniversalDateNode node2 = new UniversalDateNode();
	  		node2.setData(node.getVolume());
	  		node2.setDate(node.getDate());
	  		list.add(node2);
		}
		
	}else{
		StockCheckService stockCheckService = StockCheck.create();
		iterator = stockCheckService.getStockMessage(name, beginDate, endDate);
		
		while(iterator.hasNext()){
	  		StockDateNode node=(StockDateNode) iterator.next();
	  		UniversalDateNode node2 = new UniversalDateNode();
	  		node2.setData(node.getVolume());
	  		node2.setDate(node.getDate());
	  		list.add(node2);
		}

	}
	
	


	
	result = gson.toJson(list);

	out.print(result);

%>