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
<title>商家订单管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/buyers/buyers.css"/>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/source/js/libs/divselect.js"></script>
<script type="text/javascript">
$(function(){
	$.divselect("#divselect1","#inputselect");
});
</script>
</head>
<div class="container-fluid">
 <!--是否确认收货-->
<div id="check-div" class="houtai-kuangs video-kuangs-1" style=" z-index:1000; display:none">
     
   <span>是否确认发货</span>
      <div style="width:216px;margin-top:20px;margin-left:75px;">
          <a href="javascript:void(0)" onclick= "doCheck()" class="video-btnsInfor">确定</a><a href="javascript:hideCheck()" class="video-btnsInfors">取消</a>
        </div>
</div><!--comm-cons End-->               
      <div class="video-add">
         <div class="houtai—title">
			<div style="float:left;" class="houtai-titles">商家订单管理系统</div>
         </div>
      </div>
           <div class="video-add" style="margin-top:10px;">
         <div class="video-add-title">
         	编辑物流
         </div>
      </div>
      <div class="voideo-add2" style="margin-top:0"><!--voideo-add2 Begin-->
        <table cellpadding="7" cellspacing="0" border="0" class="wuliu-tabs">
        	<tbody><tr>
            	<td align="right">渠道ID</td>
                <td><div class="inputs-text-ps">${productOrder.channelId }</div></td>
            </tr>
            <tr>
            	<td align="right">订单号</td>
                <td><div class="inputs-text-ps">${productOrder.orderId }</div></td>
            </tr>
            <tr>
            	<td align="right">买家名称</td>
                <td><div class="inputs-text-ps">${productOrder.buyersName }</div></td>
            </tr>
            <tr>
            	<td align="right">卖家名称</td>
                <td><div class="inputs-text-ps">${productOrder.sellerName }</div></td>
            </tr>
            <tr>
            	<td align="right">价格（单位：元）</td>
                <td><div class="inputs-text-ps">${productOrder.price }</div></td>
            </tr>
            <tr>
            	<td align="right">收货地址</td>
                <td><div class="inputs-text-ps">${productOrder.toAddress }</div></td>
            </tr>
            <tr>
            	<td align="right">收货状态</td>
                <td><div class="inputs-text-ps"><span class="zhifu-kuangs-sj">${deliverStatus[productOrder.deliverStatus] }</span></div></td>
            </tr>
            <tr>
            	<td align="right">订单状态</td>
                <td>
                <input type="radio"  <c:if test="${productOrder.orderStatus == 1 }">checked="checked"</c:if> readonly="readonly">
                <span class="zhifu-yzf">
                <c:if test="${productOrder.orderStatus == 0 }">未支付</c:if>
                <c:if test="${productOrder.orderStatus == 1 }">已支付</c:if>
                </span></td>
            </tr>
            <tr>
            	<td align="right">物流公司</td>
                <td>
               
             <div id="divselect1" style="z-index:100;"> 
           	  <cite>
           	  <c:choose>
           	  	<c:when test='${productOrder.logisticsCompany=="ems"}'>EMS</c:when>
           	  	<c:when test='${productOrder.logisticsCompany=="shentong"}'>申通快递</c:when>
           	  	<c:when test='${productOrder.logisticsCompany=="tiantian"}'>天天快递</c:when>
           	  	<c:when test='${productOrder.logisticsCompany=="shunfeng"}'>顺丰快递</c:when>
           	  	<c:when test='${productOrder.logisticsCompany=="yuantong"}'>圆通快递</c:when>
           	  	<c:when test='${productOrder.logisticsCompany=="yunda"}'>韵达快递</c:when>
           	  	<c:when test='${productOrder.logisticsCompany=="zhaijisong"}'>宅急送</c:when>
           	  	<c:when test='${productOrder.logisticsCompany=="zhongtong"}'>中通快递</c:when>
           	  	<c:when test='${productOrder.logisticsCompany=="zhongyouwuliu"}'>中友物流</c:when>
           	  	<c:otherwise>请选择</c:otherwise>
           	  </c:choose>
           	  </cite> 
           	   <ul style="display: none;"> 
            	 <li><a href="javascript:;" selectid="ems">EMS</a></li> 
           	     <li><a href="javascript:;" selectid="shentong">申通快递</a></li> 
           	     <li><a href="javascript:;" selectid="tiantian">天天快递</a></li> 
           	     <li><a href="javascript:;" selectid="shunfeng">顺丰快递</a></li> 
           	     <li><a href="javascript:;" selectid="yuantong">圆通快递</a></li> 
           	     <li><a href="javascript:;" selectid="yunda">韵达快递</a></li> 
           	     <li><a href="javascript:;" selectid="zhaijisong">宅急送</a></li> 
           	     <li><a href="javascript:;" selectid="zhongtong">中通快递</a></li> 
           	     <li><a href="javascript:;" selectid="zhongyouwuliu">中友物流</a></li> 
           	   </ul> 
             </div> 
			<input name="logistics-type" type="hidden" value="" id="inputselect"> </td>
            </tr>
            <tr>
            	<td align="right">
            	<input type="hidden" id = "_id" value="${productOrder.id}"/>
            	物流单号</td>
                <td><input id="logistics-num" type="text" class="inputs-text-p" value="${productOrder.logisticsNum}"  <c:if test="${productOrder.deliverStatus > 0}">readonly=\"true\"</c:if> ></td>
            </tr>
        </tbody></table>
        <div class="wuliu-btns-cons">
        	<a id = "sub-a" href="javascript:void(0)" onclick = "showCheck()" class="wl-xz-shre">确定</a><a href="javascript:void(0)" onclick="history.go(-1);" class="wl-back">返回</a>
        </div>
        <div class="clear"></div>
      </div>
</div>
<script type="text/javascript">
init();
function init(){
	var _edit="${productOrder.deliverStatus > 0}";
	if(_edit=="true"){
		$('#sub-a').hide();
	}
}

var _type="";
var _num = "";
function showCheck(){
	_type = $('#inputselect').val();
	_num = $('#logistics-num').val();
	if(!_type||$.trim(_type).length==0||!_num||$.trim(_num).length==0){
		alert("请选择物流方式和物流单号");
		return;
	}
	$('#check-div').show();
}
function hideCheck(){
	$('#check-div').hide();
}
function doCheck(){
	var url = "${ctx}/admin/logistics";
	 _id = $('#_id').val();
	    $.post(url,
	    		{id:_id,
	    		logisticsNum:_num,
	    		logisticsCompany:_type},
	    		function(data){
	    			var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
		    		  datas = "{" + datas + "}";
		    		  var attr = eval('('+datas+')');
		    		 if(attr.code==0){
		    			 history.go(-1);
		    		 }else{
		    			 alert("物流添加失败");
		    		 }
	    });
}
</script>
</body>
</html>