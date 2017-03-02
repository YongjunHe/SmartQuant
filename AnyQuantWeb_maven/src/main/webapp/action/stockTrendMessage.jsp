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
	
	XMAService xmaService = new XMAImpl();

	Iterator low = xmaService.inXMA(name, beginDate, endDate, 32, 100);
	
	Iterator high = xmaService.outXMA(name, beginDate, endDate, 20, 80);
	
	StockCheckService stockCheckService = StockCheck.create();
	Iterator stockIterator = stockCheckService.getStockMessage(name, beginDate, endDate);
	
	ArrayList<UniversalDateNode> list = new ArrayList<UniversalDateNode>();
	while(low.hasNext()&&high.hasNext()){
		UniversalDateNode node = new UniversalDateNode();
		UniversalDateNode lownode = (UniversalDateNode) low.next();
		UniversalDateNode highnode = (UniversalDateNode) high.next(); 
		StockDateNode truenode = (StockDateNode) stockIterator.next();
		node.setDate(lownode.getDate());
		node.setData(truenode.getClose());
		node.setData2(lownode.getData());
		node.setData3(highnode.getData());
		list.add(node);
	}
	Gson gson = new Gson();
	String result = gson.toJson(list);
	
	out.write(result);
%>