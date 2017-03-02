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
	String name = request.getParameter("name");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	String para1 = request.getParameter("para1");
	String para2 = request.getParameter("para2");
	
	


	
	Gson gson = new Gson();
	StockCheckService stockCheckService = StockCheck.create();
	Iterator stockIterator = stockCheckService.getStockMessage(name, beginDate, endDate);
	
	SummaryCheckService summaryCheckService = SummaryCheck.create();
	Iterator marketIterator = summaryCheckService.getSummaryMessage(MarketType.hs300, beginDate, endDate);


	
	ArrayList<CorrelationDateNode> list = new ArrayList<CorrelationDateNode>();
	while(stockIterator.hasNext()){
		CorrelationDateNode node = new CorrelationDateNode();
		StockDateNode node2 =(StockDateNode) stockIterator.next();
		SummaryDateNode node3 = (SummaryDateNode) marketIterator.next();
		node.setDate(node2.getDate());
		if(para1.equals("open")){
			node.setData1(node2.getOpen());
		}else if(para1.equals("close")){
			node.setData1(node2.getClose());
		}else if(para1.equals("high")){
			node.setData1(node2.getHigh());
		}else if(para1.equals("low")){
			node.setData1(node2.getLow());
		}else if(para1.equals("volume")){
			node.setData1(node2.getVolume());
		}else if(para1.equals("adj_price")){
			node.setData1(node2.getAdj_price());
		}else if(para1.equals("pb")){
			node.setData1(node2.getPb());
		}else if(para1.equals("turnover")){
			node.setData1(node2.getTurnover());
		}else if(para1.equals("market")){
			node.setData1(node3.getClose());
		}
		
		if(para2.equals("open")){
			node.setData2(node2.getOpen());
		}else if(para2.equals("close")){
			node.setData2(node2.getClose());
		}else if(para2.equals("high")){
			node.setData2(node2.getHigh());
		}else if(para2.equals("low")){
			node.setData2(node2.getLow());
		}else if(para2.equals("volume")){
			node.setData2(node2.getVolume());
		}else if(para2.equals("adj_price")){
			node.setData2(node2.getAdj_price());
		}else if(para2.equals("pb")){
			node.setData2(node2.getPb());
		}else if(para2.equals("turnover")){
			node.setData2(node2.getTurnover());
		}else if(para2.equals("market")){
			node.setData2(node3.getClose());
		}
		list.add(node);
	}

	String result = gson.toJson(list);
	

	out.print(result);

%>