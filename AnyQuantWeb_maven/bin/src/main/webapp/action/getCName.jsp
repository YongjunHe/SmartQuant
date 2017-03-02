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
	String result = "";

	if(name.equals("sh000300")){
		
		result = "沪深300";
		
	}else{
		StockCheckService stockCheckService = StockCheck.create();

		result = stockCheckService.getCName(name);
	}


	out.print(result);

%>