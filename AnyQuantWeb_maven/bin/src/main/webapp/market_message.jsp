<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import="data.service.*"%>
<%@ page import="data.impl.*"%>
<%@ page import="data.enums.*"%>
<%@ page import="data.message.*"%>
<%@ page import="logic.impl.*"%>
<%@ page import="logic.service.*"%>
<%@ page import="java.text.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
    <link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts/authority/commonAll.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
    <script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
    <title>股票信息系统</title>
    <script type="text/javascript">

        function searchByDate(){
        	
        	var beginDate = $("#startDate").val();
        	var endDate = $("#endDate").val();
        	var fliterType = $("#fliterType").val();
        	var lowLimite = $("#lowlimite").val();
        	var highLimite = $("#highlimite").val();
        	
            $.ajax({  
                type : "GET",  //提交方式  
                url : "action/marketDateMessage.jsp",//路径  
                data : "beginDate="+beginDate+"&endDate="+endDate+"&fliterType="+fliterType+"&lowLimite="+lowLimite+"&highLimite="+highLimite, 
                dataType: "json",
                success : function(result) {//返回数据根据结果进行相应的处理  
                		rebuTable(result);
                } 
            	
            });
        	
 

        return false;
        	
        	
        }
        
        function searchByFilter(){
        	
        }
        
        function rebuTable(list){
            var table = document.getElementById("marketTable"),
            trs = table.getElementsByTagName("tr");
               
        	for(var i = trs.length - 1; i > 0; i--) {
        	    table.deleteRow(i);
        	}

        	
        	for(var i = 0; i < list.length; i++){
        		var row = table.insertRow(table.rows.length);
        		row.insertCell(0).innerHTML = list[i].date;
        		row.insertCell(1).innerHTML = "沪深300";
        		row.insertCell(2).innerHTML = "sh000300";
        		row.insertCell(3).innerHTML = list[i].open;
        		row.insertCell(4).innerHTML = list[i].close;
        		row.insertCell(5).innerHTML = list[i].high;
        		row.insertCell(6).innerHTML = list[i].low;
        		row.insertCell(7).innerHTML = list[i].adj_price;
        		row.insertCell(8).innerHTML = list[i].volume;
        	}
        	
        	
        }





        /** 普通跳转 **/
        function jumpNormalPage(page){
            $("#submitForm").attr("action", "house_list.html?page=" + page).submit();
        }

        /** 输入页跳转 **/
        function jumpInputPage(totalPage){
            // 如果“跳转页数”不为空
            if($("#jumpNumTxt").val() != ''){
                var pageNum = parseInt($("#jumpNumTxt").val());
                // 如果跳转页数在不合理范围内，则置为1
                if(pageNum<1 | pageNum>totalPage){
                    art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
                    pageNum = 1;
                }
                $("#submitForm").attr("action", "house_list.html?page=" + pageNum).submit();
            }else{
                // “跳转页数”为空
                art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'请输入合适的页数，\n自动为您跳到首页', ok:true,});
                $("#submitForm").attr("action", "house_list.html?page=" + 1).submit();
            }
        }

        /**设置日期选择框的默认日期**/
        function showdate(){

            var date = new Date();
            document.getElementById('endDate').valueAsDate = date;
            date.setMonth(date.getMonth()-1);
            document.getElementById('startDate').valueAsDate = date;
        }

    </script>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body onload="showdate();searchByDate()">
<form id="submitForm" name="submitForm" action="" method="post">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div class="box_center" id="box_center1">
                  	              筛选条件
                        <select name="fliterType" id="fliterType" class="ui_select01" >
                            <option value="open">开盘价</option>
                            <option value="high">最高价</option>
                            <option value="close">收盘价</option>
                            <option value="low">最低价</option>
                            <option value="volume">成交量（千万股）</option>
                            <option value="adj_price">后复权价</option>
                        </select>
							
                                                                                    下限&nbsp;&nbsp;<input type="number" id="lowlimite" name="marketMessage.lowlimite" class="ui_input_number02" />

                       	上限&nbsp;&nbsp;<input type="number" id="highlimite" name="marketMessage.highlimite" class="ui_input_number02" />

                        	  开始日期<input type="date" name="start_date" id="startDate"/>

                                                                                                    结束日期<input type="date" name="end_date" id="endDate"/>
                        </div>


                    <div  id="box_bottom">

                        <input type="button" value="查询" class="ui_input_btn01" onclick="searchByDate();" />
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" id="marketTable" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th>日期</th>
                        <th>指数名称</th>
                        <th>指数代号</th>
                        <th>开盘价</th>
                        <th>收盘价</th>
                        <th>最高价</th>
                        <th>最低价</th>
                        <th>后复权价</th>
                        <th>交易量（千万股）</th>
                    </tr>
                    
                    

                </table>
            </div>
            <div class="ui_tb_h30">
                <div class="ui_flt" style="height: 30px; line-height: 30px;">
                    共有
                    <span class="ui_txt_bold04">90</span>
                    条记录，当前第
						<span class="ui_txt_bold04">1
						/
						9</span>
                    页
                </div>
                <div class="ui_frt">
                    <!--    如果是第一页，则只显示下一页、尾页 -->

                    <input type="button" value="首页" class="ui_input_btn01" />
                    <input type="button" value="上一页" class="ui_input_btn01" />
                    <input type="button" value="下一页" class="ui_input_btn01"
                           onclick="jumpNormalPage(2);" />
                    <input type="button" value="尾页" class="ui_input_btn01"
                           onclick="jumpNormalPage(9);" />



                    <!--     如果是最后一页，则只显示首页、上一页 -->

                    转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01" />页
                    <input type="button" class="ui_input_btn01" value="跳转" onclick="jumpInputPage(9);" />
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>