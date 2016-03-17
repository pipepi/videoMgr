<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>套餐记录</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/buyers/buyers.css"/>
<link rel="stylesheet" href="${ctx}/source/css/partner/buy_package.css" />

<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/source/js/libs/divselect.js"></script>
<script type="text/javascript">
$(function(){
	$.divselect("#divselect","#inputselect");
});
</script>
<script type="text/javascript" src="${ctx}/source/js/libs/jquery.md5.js"></script>
</head>
<body>
<div class="container-fluid">
      <div class="video-add">
          <div class="video-add-title">
         	<ul>
            	<li><a href="javascript:buyFlow()">购买流量</a></li>
                <li class="current"><a href="#">购买历史查询</a></li>
            </ul>
         </div>
      </div>
      <div class="video-tabs" id="dataTable"><!--video-tabs Begin-->
         <div class="video-tabs-cons">
               <div class="video-add-cons">
                
	            <div class="voideo-add2"><!--voideo-add2 Begin-->
	            <!--同意退换货-->
					<table border="0" class="table-tc-list" cellpadding="0" cellspacing="0" width="100%">
						 <tr height="40" width="100%">
						 
                     	<th width="15%">类型</th>
                        <th width="15%">数量</th>
                        <th width="20%">购买时间</th>
                        <th width="20%">价格（元）</th>
                        <th width="15%">状态</th>
                        <th width="15%">操作</th>
                    	 </tr>
                    	 
                    	 
					</table>
	             
	             <!--comm-cons End-->
	              <div id="page-content" class="video-shenghe-infomation">  
	                
	              </div>
	    			<div class="clear"></div>
	               <!--list-page End-->
	               
	              </div>
	             
            </div><!--voideo-add2 End-->
            </div>
           </div>
           
           
            <div class="video-kuangs"  style="z-index:2000;left:30%;display: none" id="waitPayNotifyDiv">
           <div class="video-titles">
                   <span>支付平台</span>
                  <a href="javascript:cancelPay()" class="close-icon"></a>
                 </div>
                 <div id="payInfo" class="zf-fonts"><!--comm-cons Begin-->
                	请您在新打开的支付平台页面进行支付，支付完成前请不要关闭窗口
                </div>  
                   <div style="width:216px;margin-top:50px;margin-left:135px;">
                     <a href="javascript:cancelPay()" class="video-btnsInfors">取消</a>
                     <a href="javascript:finishPay()" class="video-btnsInfors-s">已支付完成</a>
                    </div>
          </div>
           
           
            <div class="choice-type2" style="display:none" id ="payDiv">
		        <div style="margin:30px 0 0 70px;color:#333333; font-size:16px;">请选择支付方式</div>
		        <ul>
		           <li><input type="radio" name="payType"  value="1" checked="checked" style=" vertical-align:top;margin-top:20px;margin-right:10px;"/><img src="${ctx}/source/images/partner/package/zfb.jpg" width="126" height="36" /></li>
		           <li><input type="radio"  type="radio" name="payType" value="3"   style=" vertical-align:top;margin-top:20px;margin-right:10px;"/><img src="${ctx}/source/images/partner/package/weibo.jpg" width="126" height="36" /></li>
		        </ul>
		        <div class="next-btns-zf" style="width:240px;"><a href="javascript:pay()" class="naxt-btns-cons" style="width:100px;">支付</a><a href="javascript:backChoose()" class="black-btns-cons" style="width:100px; margin-left:10px;">返回</a></div>    
		     </div>
   
        </div>
<script type="text/javascript">
var ctx = "${ctx}";
var _pn = 1;
var _ps = 8;
var _id = "0";


function doSearch(){
	 var url = ctx+"/package/subrecordpage";
	    $.post(url,
	    		{
			     pn:_pn,
			     ps:_ps},function(data){
	  	  $('#page-content').html(data);
	    });
}
function go(pn){
	_pn = pn;
	doSearch();
}
go(1);

function buyFlow(){
	window.location='${ctx}/package/buyflow';
}

var orderId='';
function cashPayment(id){
	orderId=id;
	$('#dataTable').hide();
	$('#payDiv').show();
}

function backChoose(){
	$("#dataTable").show();
	$("#payDiv").hide();
}


function  pay(){
	var checkedPayType=$("input[type='radio']:checked").val();
	$("#waitPayNotifyDiv").show();
	window.open("${ctx}/pay/platformPay?payType="+checkedPayType+"&orderId="+orderId);
	
}


function cancelPay(){
	$("#waitPayNotifyDiv").css("display","none");
	
}

function finishPay(){
	
	var url = "${ctx}/package/ispay";
	//console.log(url);
	$.post(url,{orderId:orderId},function(data){
		var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
  		  datas = "{" + datas + "}";
  		  var attr = eval('('+datas+')');
  		  console.log(attr);
  		  if(attr.success){
  			 go(_pn);
  			backChoose();
  			cancelPay();
  		  }else{
  			  $("#payInfo").addClass("zf-fonts");
  			  $("#payInfo").text("未完成支付，请支付");
  		  }
	});
	
}

</script>
</body>
</html>