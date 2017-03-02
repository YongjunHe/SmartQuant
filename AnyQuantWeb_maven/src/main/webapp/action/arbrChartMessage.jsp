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
	ARBRService arbrService = ARBRImpl.create();
	Iterator iterator = arbrService.getSelectAnswer();
	ArrayList<ARBRReport> reportList = new ArrayList<ARBRReport>();
	String result = "";

	while(iterator.hasNext()){
		ARBRReport report = (ARBRReport) iterator.next();
		if(!report.getcType().equals("软件及系统"))
		reportList.add(report);
	}
	
	Gson gson = new Gson();
	result = gson.toJson(reportList);
	
	out.write(result);
%>