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
	String type = request.getParameter("chartType");
	String result = "";
	

	Gson gson = new Gson();
	Analysis analysis = null;
	Iterator iterator =null;
	

	
	if(type.equals("CCIDayChart.html")){
		analysis = CCIImpl.create();
		iterator = CCIImpl.create().getDayCCI(name, beginDate, endDate);
	
	}else if(type.equals("KDJDayChart.html")){
		analysis = KDJImpl.create();
		//iterator = KDJImpl.create().getDayKDJ(name, beginDate, endDate);
	}else if(type.equals("RSIDayChart.html")){
		analysis = RSIImpl.create();
		iterator = RSIImpl.create().getDayRSI(name, beginDate, endDate);
	}else if(type.equals("WMSDayChart.html")){
		analysis = WMSRImpl.create();
		iterator = WMSRImpl.create().WMSRDay(name, beginDate, endDate);
	}else if(type.equals("averageChart.html")){
		analysis = new ATRImpl();
	}
	
	if(analysis!=null){
		result = analysis.getStandard();
		if(iterator!=null)
		result = result+"<br>"+analysis.getAnalysis(iterator);	
	}


	
	

	out.print(result);

%>