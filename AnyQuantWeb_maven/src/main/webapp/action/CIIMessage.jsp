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
	String type = request.getParameter("type");
	String result = "";
	
	Gson gson = new Gson();
	
	CCIService cciService = CCIImpl.create();
	ArrayList<UniversalDateNode> list = new ArrayList<UniversalDateNode>();
	Iterator iterator = null;
	
	if(type.equals("day")){
		iterator = cciService.getDayCCI(name, beginDate, endDate);
	}else{
		iterator = cciService.getWeekCCI(name, beginDate, endDate);
	}

	while(iterator.hasNext()){
		UniversalDateNode node = (UniversalDateNode) iterator.next();
		list.add(node);
	}
	
	
	
	result = gson.toJson(list);
	
	

	out.print(result);

%>