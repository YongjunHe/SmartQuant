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
StockCheckService scs = StockCheck.create();
Iterator it=scs.getSelectedStock();
Gson gson = new Gson();
ArrayList<StockDateNode> list = new ArrayList<StockDateNode>();
while(it.hasNext()){
	Stock stock=(Stock) it.next();
	StockDateNode node=stock.getTodayData();
	node.setcName(stock.getCName());
	node.setName(stock.getName());
	
		DecimalFormat dcmFmt = new DecimalFormat("0.000");
  		
		node.setChg(Double.parseDouble(dcmFmt.format(node.getChg())));
	list.add(node);

}

String result = gson.toJson(list);


out.print(result);
%>