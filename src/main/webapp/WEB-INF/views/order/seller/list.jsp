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
	$.divselect("#divselect","#inputselect");
});
</script>
<script type="text/javascript" src="${ctx}/source/js/libs/jquery.md5.js"></script>
</head>
<body>
<div class="container-fluid">
      <div class="video-add">
         <div class="houtai—title">
			<div style="float:left;" class="houtai-titles">商家订单管理系统</div>
         </div>
      </div>
      <div class="video-tabs"><!--video-tabs Begin-->
         <div class="video-tabs-cons">
               <div class="video-add-cons">
                <ul class="bfq-tab">
                    <li style="padding-top:10px;"><label>订单号</label></li>
                    <li><input type="text" id = "_id" class="inputs"></li>
                    <li style="padding-top:10px;margin-left:10px;"><label>买家名称</label></li>
                    <li><input type="text" id = "_buyers_name" class="inputs"></li>
                    <li style="padding-top:10px;margin-left:10px;"><label>订单状态</label></li>
                    <li><div id="divselect" style="z-index:100"> 
            <cite>请选择</cite> 
           	 <ul> 
            	<li><a href="javascript:;" selectid="1">未支付</a></li> 
           	    <li><a href="javascript:;" selectid="2">已支付</a></li> 
            </ul> 
           </div> 
			<input name="" type="hidden" value="" id="inputselect"> </li>
                    <li><a href="javascript:void(0)" class="zhineng-sreach" onclick="go(1)">搜索</a></li>
                </ul>
                <div class="clear"></div>
               </div>
   
            <div class="voideo-add2"><!--voideo-add2 Begin-->
            <!--同意退换货-->
			<div id="back-div" class="houtai-shBtn-lan-th" style="display:none">
				<div class="choice-zf-fs">同意退换货</div>
				<table cellpadding="7" cellspacing="0" border="0"
					class="wuliu-tabs">
					<tbody>
						<tr>
							<td align="right">用户</td>
							<td><div class="inputs-text-ps" id="back-name"></div></td>
						</tr>
						<tr>
							<td align="right">电话</td>
							<td><div class="inputs-text-ps" id="back-phone"></div></td>
						</tr>
						<tr>
							<td align="right">邮箱</td>
							<td><div class="inputs-text-ps" id="back-mail"></div></td>
						</tr>
						<tr>
							<td align="right">地址</td>
							<td><div class="inputs-text-psdz" id="back-addr"></div></td>
						</tr>

					</tbody>
				</table>

				<div class="next-btns-zf">
					<a href="javascript:void(0)" onclick="doAgreeBack()" class="naxt-btns-cons" style="width: 80px;">确定</a>
					<a href="javascript:void(0)" onclick="hideAgreeBack()" class="black-btns-cons" style="width: 80px;">取消</a>
				</div>
			</div>
			<!--是否完成退换货-->
            <div id="back-conf-div" class="houtai-kuangs video-kuangs-1" style="display:none">
                 
               <span>是否完成退换货</span>
                  <div style="width:216px;margin-top:20px;margin-left:75px;">
                      <a href="javascript:void(0)" onclick="doConfBack()" class="video-btnsInfor">确定</a>
                      <a href="javascript:void(0)" onclick="hideConfBack()" class="video-btnsInfors">取消</a>
                    </div>
             </div>
             
             
             <!--comm-cons End-->
              <div id="page-content" class="video-shenghe-infomation">  
                
              </div>
    			<div class="clear"></div>
               <!--list-page End-->
               
              </div>
             
            </div><!--voideo-add2 End-->
           </div>
   
        </div>
<script type="text/javascript">
var ctx = "${ctx}";
var _phone = "";
var _pn = 1;
var _ps = 8;
var _id = "0";
var _buyers_name = "";
var _order_state = "";

function doSearch(){
	 var url = ctx+"/seller/ordersearch";
	 _id = $('#_id').val();
	 _buyers_name = $('#_buyers_name').val();
	 _order_state = $('#inputselect').val();
	    $.post(url,
	    		{id:_id,
	    		 bn:_buyers_name,
	    		 os:_order_state,
			     pn:_pn,
			     ps:_ps},function(data){
	  	  $('#div-login').hide();
	  	  $('#page-content').html(data);
	    });
}
function go(pn){
	_pn = pn;
	doSearch();
}
go(1);
var _back_id;
function showAgreeBack(obj,id){
	_back_id = id;
	var _buyer = $(obj).attr('buyer');
	var _phone = $(obj).attr('phone');
	var _email = $(obj).attr('email');
	var _addr = $(obj).attr('addr');
	$('#back-name').html(_buyer);
	$('#back-phone').html(_phone);
	$('#back-mail').html(_email);
	$('#back-addr').html(_addr);
	$('#back-div').show();
}
function hideAgreeBack(){
	$('#back-div').hide();
}
function doAgreeBack(){
	var url = ctx+"/buyers/deliver";
	$.post(url,{id:_back_id,status:4},function(data){
		var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
		  datas = "{" + datas + "}";
		  var attr = eval('('+datas+')');
		if(attr.success){
			hideAgreeBack();
			doSearch();
		}else{
			alert("同意退换货失败");
		}
	});
}
var _conf_id=0;
function showConfBack(id){
	_conf_id = id;
	$('#back-conf-div').show();
}
function hideConfBack(){
	$('#back-conf-div').hide();
}
function doConfBack(){
	var url = ctx+"/buyers/deliver";
	$.post(url,{id:_conf_id,status:6},function(data){
		var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
		  datas = "{" + datas + "}";
		  var attr = eval('('+datas+')');
		if(attr.success){
			hideConfBack();
			doSearch();
		}else{
			alert("完成退换货失败");
		}
	});
}
</script>
</body>
</html>