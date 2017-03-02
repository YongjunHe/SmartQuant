<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.service.*"%>
<%@ page import="data.impl.*"%>
<%@ page import="data.enums.*"%>
<%@ page import="data.message.*"%>
<%@ page import="logic.impl.*"%>
<%@ page import="logic.service.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.google.gson.*"%>
<%
	String name = request.getParameter("name");
	name = new String(name.getBytes("ISO-8859-1"),"UTF-8");
	StockCheckService scs = StockCheck.create();
	Iterator it = scs.getStockMessageByCName(name);
	ArrayList<Stock> list = new ArrayList<Stock>();
	while(it.hasNext()){
		Stock stock =(Stock) it.next();
		list.add(stock);
	}
	
	String result = null;
	Gson gson = new Gson();
	result = gson.toJson(list);
	
	out.write(result);
%>