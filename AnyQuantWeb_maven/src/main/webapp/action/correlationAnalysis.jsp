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
	
	


	

	StockCheckService stockCheckService = StockCheck.create();
	Iterator stockIterator = stockCheckService.getStockMessage(name, beginDate, endDate);

	SummaryCheckService summaryCheckService = SummaryCheck.create();
	Iterator marketIterator = summaryCheckService.getSummaryMessage(MarketType.hs300, beginDate, endDate);
	
	ArrayList<Double> list1 = new ArrayList<Double>();
	ArrayList<Double> list2 = new ArrayList<Double>();
	while(stockIterator.hasNext()){
		StockDateNode node =(StockDateNode) stockIterator.next();
		SummaryDateNode node3 = (SummaryDateNode) marketIterator.next();

		if(para1.equals("open")){
			list1.add(node.getOpen());
		}else if(para1.equals("close")){
			list1.add(node.getClose());
		}else if(para1.equals("high")){
			list1.add(node.getHigh());
		}else if(para1.equals("low")){
			list1.add(node.getLow());
		}else if(para1.equals("volume")){
			list1.add(node.getVolume());
		}else if(para1.equals("adj_price")){
			list1.add(node.getAdj_price());
		}else if(para1.equals("pb")){
			list1.add(node.getPb());
		}else if(para1.equals("turnover")){
			list1.add(node.getTurnover());
		}else if(para1.equals("market")){
			list1.add(node3.getClose());
		}
		
		if(para2.equals("open")){
			list2.add(node.getOpen());
		}else if(para2.equals("close")){
			list2.add(node.getClose());
		}else if(para2.equals("high")){
			list2.add(node.getHigh());
		}else if(para2.equals("low")){
			list2.add(node.getLow());
		}else if(para2.equals("volume")){
			list2.add(node.getVolume());
		}else if(para2.equals("adj_price")){
			list2.add(node.getAdj_price());
		}else if(para2.equals("pb")){
			list2.add(node.getPb());
		}else if(para2.equals("turnover")){
			list2.add(node.getTurnover());
		}else if(para2.equals("market")){
			list2.add(node3.getClose());
		}

	}
	
	CLCoefficientService scClCoefficientService = new CLCoefficientImpl();

	String result = scClCoefficientService.calculation(list1.iterator(), list2.iterator(), list1.size());
	

	out.print(result);

%>