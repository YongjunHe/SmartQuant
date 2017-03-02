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

	NewService newService = new NewImpl();
	
	Iterator it=newService.getNews("宇通客车");
	
	Gson gson = new Gson();
	
	String result = gson.toJson(it);
	
	out.write(result);
%>