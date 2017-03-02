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
	String typeStr = request.getParameter("fliterType");
	String lowlimiteStr = request.getParameter("lowLimite");
	String highlimiteStr = request.getParameter("highLimite");
	
	Gson gson = new Gson();
	SummaryCheckService summaryCheckService = SummaryCheck.create();
	Iterator stockIterator = summaryCheckService.getSummaryMessage(MarketType.hs300, beginDate, endDate);

	
	
	DataType type = DataType.valueOf(typeStr);
	double low=0;
	double high=Double.MAX_VALUE;
	if(!lowlimiteStr.equals("")){
		low = Double.parseDouble(lowlimiteStr);
	}
	if(!highlimiteStr.equals("")){
		high = Double.parseDouble(highlimiteStr);
	}
	if(type == DataType.volume){
		low = low*10000000;
		high = high*10000000;
	}
	
	Range range = new Range(low, high, !lowlimiteStr.equals(""), !highlimiteStr.equals(""));
	SmartSortService sss = new SmartSort();
	stockIterator = sss.sort(type, range, stockIterator);
	
	ArrayList<SummaryDateNode> list = new ArrayList<SummaryDateNode>();
	while(stockIterator.hasNext()){
  		SummaryDateNode node=(SummaryDateNode) stockIterator.next();
  		node.setVolume(node.getVolume()/10000000);
  		node.setChg(node.getChg()*100);
  		DecimalFormat dcmFmt = new DecimalFormat("0.000");
  		
  		node.setChg(Double.parseDouble(dcmFmt.format(node.getChg())));
  		
  		list.add(node);
	}
	
	String result = gson.toJson(list);
	

	out.print(result);

%>