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

	
	ATRService atrService = new ATRImpl();
	Map<String, Double> atrMap = atrService.ATRData(name, beginDate, endDate);
	Map<String, Double> matrMap = atrService.MATRData(name, beginDate, endDate);
	ArrayList<UniversalDateNode> list = new ArrayList<UniversalDateNode>();
	Iterator<String> keys = atrMap.keySet().iterator();
	
	while(keys.hasNext()){
		String key = keys.next();
		double value = atrMap.get(key);
		double value2 = matrMap.get(key);
		UniversalDateNode node = new UniversalDateNode();
		node.setDate(key);
		node.setData(value);
		node.setData2(value2);
		list.add(node);
	}
	Collections.sort(list);
	result = gson.toJson(list);
	
	

	out.print(result);

%>