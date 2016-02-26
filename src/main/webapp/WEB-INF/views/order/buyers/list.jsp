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
<title>用户订单查询系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/source/css/buyers/buyers.css"/>
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/source/js/libs/divselect.js"></script>
<script type="text/javascript" src="${ctx}/source/js/checkcode.js"></script>
<script type="text/javascript" src="${ctx}/source/js/util.js"></script>
<script type="text/javascript">
$(function(){
	$.divselect("#divselect","#inputselect");
});
</script>
<script type="text/javascript" src="${ctx}/source/js/libs/jquery.md5.js"></script>
</head>
<body>
<div class="container-fluid">
 
 <!--登录弹出框-->
  <div id="div-login" class="commodity-kuang video-kuang" style="z-index: 10000;height: 220px;">
           <table cellpadding="3" cellspacing="0" border="0" class="houtai-loginKuang">
           	<tbody><tr>
                <td align="right"><span>*</span>联系电话：</td>
                <td><input type="text"  maxlength="11" id = "input-phone"></td>
                <td>&nbsp;</td>
              </tr>
              <tr>
	              <td align="right"> <span>*</span>安全认证：</td>
	              <td>
	              <input id="checkCode" name="checkCode" type="text"  maxlength="4"  value="请输入右图数字" onfocus="cc.clearCode()"/>
	              </td>
	              <td><img id="codeImage" class="" src="${ctx }/checkcode/check_code" alt="验证码图片" title="验证码图片" width="95" height="32" onclick="cc.changeCode()"/></td>
              </tr>
              <tr>
               <td align="right"><span>*</span>手机验证码：</td>
               <td><input type="text" id = "input-checkcode" value="请输入6位数验证码"  maxlength="11" onfocus="if(this.value =='请输入6位数验证码')this.value='';"></td>
               <td><a href="javascript:void(0)" id = "a-sms" class="hq-yzm" onclick="sendSms()">获取验证码</a></td>
              </tr>
              <tr>
               <td colspan="3"><a href="javascript:void(0)" onclick="querybyphone()" class="houtai-chaxun">查询</a></td>
              </tr>
           </tbody></table>   
  </div>
 
      <div class="video-add">
         <div class="houtai—title">
			<div style="float:left;" class="houtai-titles">用户订单查询系统</div>
         </div>
      </div>
      <div class="video-tabs"><!--video-tabs Begin-->
         <div class="video-tabs-cons">
               <div class="video-add-cons">
                <ul class="bfq-tab">
                    <li style="padding-top:10px;"><label>订单号</label></li>
                    <li><input type="text" id = "_id" class="inputs"></li>
                    <li style="padding-top:10px;margin-left:10px;"><label>卖家名称</label></li>
                    <li><input type="text" id = "_seller_name" class="inputs"></li>
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
            <!--是否确认收货-->
            <div id="check-div" class="houtai-kuangs video-kuangs-1" style="display:none">
               <span>是否确认收货</span>
                  <div style="width:216px;margin-top:20px;margin-left:75px;">
                      <a href="javascript:void(0)" onclick="checkReceive()" class="video-btnsInfor">确定</a>
                      <a href="javascript:void(0)" onclick = "hideCheckConfirm()" class="video-btnsInfors">取消</a>
                  </div>
            </div>
            <!--是否完成退换货-->
            <div id="check-back-div" class="houtai-kuangs video-kuangs-1" style="display:none">
               <span>是否完成退换货</span>
                  <div style="width:216px;margin-top:20px;margin-left:75px;">
                      <a href="javascript:void(0)" onclick="checkBack()" class="video-btnsInfor">确定</a>
                      <a href="javascript:void(0)" onclick = "hideCheckBack()" class="video-btnsInfors">取消</a>
                  </div>
            </div>
            
            <!--请选择支付方式-->
            <div class="choice-type" style="display:none">
               <div class="choice-zf-fs">请选择支付方式</div>
               <ul>
                  <li><input type="radio"  name="payType" value="1"  checked="checked" class="zhifu-images"/><img src="${ctx }/source/images/partner/zfb.jpg" width="126" height="36" /></li>
                  <li><input type="radio"  name="payType" value="4" class="zhifu-images"/><img src="${ctx }/source/images/partner/weibo.jpg" width="126" height="36" /></li>
               </ul>
               <div class="next-btns-zf"><a href="javascript:void(0)" onclick="pay()" class="naxt-btns-cons houtai-zf-btns" style="width:80px;">支付</a><a href="javacript:void(0)" onclick="hidePay()" class="black-btns-cons" style="width:80px;">取消</a></div>    
            </div>
       
       
             <!--支付平台-->
             <div id="paying-div" class="video-kuangs video-kuangs-pt" style="z-index:2000;display:none;"><!--comm-cons Begin-->
                  <div class="video-titles">
                          <span>支付平台</span>
                         <a href="javascript:void(0)" class="close-icon"></a>
                  </div>
                        <div class="zf-fonts">
                       请您在新打开的支付平台页面进行支付，支付完成前请不要关闭窗口
                       </div>  
                       <div class="next-btns-zf">
                         <a href="javascript:void(0)" onclick = "$('#paying-div').hide()" class="black-btns-cons" style="width:80px;margin-right:10px;">取消</a>
                         <a href="javascript:void(0)"  onclick = "finishPay()" class="naxt-btns-cons houtai-zf-btns-wc" style="width:100px;">已完成支付</a>
                       </div>    
             </div><!--comm-cons End-->
             
             
             <!--支付成功-->
             <div class="video-kuangs video-kuangs-cg" style="display:none"><!--comm-cons Begin-->
                <div class="video-titles"><span>支付平台</span><a href="javascript:void(0)" class="close-icon"></a></div>
                <div class="zf-cg-fonts">
                       支付成功
                  </div>  
                   <div class="next-btns-zf" style="width:80px;">
                         <a href="" class="black-btns-cons" style="width:80px;">完成</a>
                  </div>    
             </div>
            <!--comm-cons End-->
			<div id = "back-div" class="houtai-shBtn-lan-th" style="display: none;">
				<div class="choice-zf-fs">请填写退换货地址</div>
				<table cellpadding="7" cellspacing="0" border="0"
					class="wuliu-tabs">
					<tbody>
						<tr>
							<td align="right">联系电话</td>
							<td>
								<input id ="back-phone" type="text" maxlength="11" class="inputs-text-p">
								<span id = "phone-tip" class="gsbz" style = "display:none;"></span>
							</td>
						</tr>
						<tr>
							<td align="right">邮箱</td>
							<td>
								<input id = "back-mail" type="text" class="inputs-text-p" maxlength="45">
								<span id = "email-tip" class="gsbz" style = "display:none;"></span>
							</td>
						</tr>
						<tr>
							<td align="right">退货地址</td>
							<td>
								<textarea id = "back-addr" class="inputs-text-dz" style="padding-left: 10px;" maxlength="90"></textarea>
								<span id = "addr-tip" class="gsbz" style = "display:none;"></span>
							</td>
						</tr>
					</tbody>
				</table>

				<div class="next-btns-zf">
					<a href="javascript:void(0)" onclick="orderBack()" class="naxt-btns-cons" style="width: 80px;">确定</a>
					<a href="javascript:void(0)" onclick="hideBack()" class="black-btns-cons" style="width: 80px;">取消</a>
				</div>
			</div>
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
var _send_sms_state = false;//发送验证码状态
var _phone = "";
var _countdown = 60;
var _pn = 1;
var _ps = 8;
var _id = "0";
var _seller_name = "";
var _order_state = "";

function sendSms(){
		if(_send_sms_state) return;//如果在发送中，不能发送
		_send_sms_state = true;//发送中
		_phone = $('#input-phone').val();
		//校验手机号码格式
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
		   if(!myreg.test(_phone)) 
		   { 
		    alert('请输入有效的手机号码！'); 
		    return false; 
		   }
		   checkPhoneCode();
}
function checkPhoneCode(){
		   if(!cc.flag){
			   alert('请输入安全认证');
			   _send_sms_state = false;
		   	   return;
		   }
		   setTimeout(countdown,1000);
		   var t = new Date().getTime();
	    $.post(ctx+"/sms/sendcode", { phone : _phone, time : t, 
	         key: $.md5(_phone + t + 'azt123qet')},
	     function(data){
       	 var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
   		  datas = "{" + datas + "}";
   		  var attr = eval('('+datas+')');
	        if(attr.code == 0){
	          //alert("短信已发送成功！");
	        }
	   }); 
}
function countdown(){
	if(_countdown==0){
		$('#a-sms').html("获取验证码");
		_countdown = 60;
		_send_sms_state = false;
		return;
	}else{
		$('#a-sms').html("重新发送("+_countdown+")");
		_countdown--;
		_send_sms_state = true;
	}
	setTimeout(countdown,1000);
}

function querybyphone(){
	var checkCode = $('#input-checkcode').val();
	var t = new Date().getTime();
 	$.post(ctx+"/sms/checkcode", { phone : _phone, content : checkCode, 
        time : t, key: $.md5(_phone + t + 'azt123qet')},
       function(data){
        	var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
    		  datas = "{" + datas + "}";
    		  var attr = eval('('+datas+')');
	        if(attr.code == 0){
	          //加载相关订单信息
	        	doSearch();
	        }else{
			   alert("手机验证码有误！");
			} 
   });
}
function doSearch(){
	 var url = ctx+"/buyers/ordersearch";
	 _id = $('#_id').val();
	 _seller_name = $('#_seller_name').val();
	 _order_state = $('#inputselect').val();
	    $.post(url,
	    		{id:_id,
	    		 sn:_seller_name,
	    		 os:_order_state,
			     pn:_pn,
			     ps:_ps},function(data){
	  	  $('#div-login').hide();
	  	  $('#page-content').html(data);
	    });
}
init();
function init(){
	var login = "${login}"
	if(login=="0"){
		doSearch();
	}
}
function go(pn){
	_pn = pn;
	doSearch();
}
function checkAll(obj){
	var _all_state = $(obj).attr("checked");
	var _checks = $('input[name="checkboxname"]');
	if(_all_state){
		if(_checks){
			if(_checks.length>0){
				for(var i = 0;i<_checks.length;i++){
					$(_checks[i]).attr("checked",true);
				}
			}
		}
	}else{
		if(_checks){
			if(_checks.length>0){
				for(var i = 0;i<_checks.length;i++){
					$(_checks[i]).attr("checked",false);
				}
			}
		}
		
	}
}
function allDelete(){
	var _checks = $('input[ostate="0"][name="checkboxname"]:checked');
	if(_checks.length==0){
		alert("没有选中未支付订单");
		return;
	}
	var _ids = "";
	for(var i=0;i<_checks.length;i++){
		_ids += $(_checks[i]).attr("oid")+",";
	}
	_ids = _ids.substring(0,_ids.length-1);
	var url = ctx+"/buyers/multidelete";
	$.post(url,{ids:_ids},function(data){
		  var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
  		  datas = "{" + datas + "}";
  		  var attr = eval('('+datas+')');
  		  if(attr.success){
  			doSearch();
  		  }
	});
}
var _check_id = "0";
function checkReceive(){
	var url = ctx+"/buyers/deliver";
	$.post(url,{id:_check_id,status:2},function(data){
		 var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
 		  datas = "{" + datas + "}";
 		  var attr = eval('('+datas+')');
		if(attr.success){
			hideCheckConfirm();
			doSearch();
		}else{
			alert("确认收货失败");
		}
	});
}
//完成退换货
var _check_back_id=0;
function checkBack(){
	var url = ctx+"/buyers/deliver";
	$.post(url,{id:_check_back_id,status:5},function(data){
		 var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
 		  datas = "{" + datas + "}";
 		  var attr = eval('('+datas+')');
		if(attr.success){
			hideCheckBack();
			doSearch();
		}else{
			alert("确认完成退换货失败");
		}
	});
}
//申请退换货
var _back_id = 0;
function showBack(obj,id){
	_back_id = id;
	var _phone = $(obj).attr('phone');
	var _email = $(obj).attr('email');
	var _addr = $(obj).attr('addr');
	$('#back-phone').val(_phone);
	$('#back-mail').val(_email);
	$('#back-addr').val(_addr);
	$('#back-div').show();
}
function orderBack(){
	if(_back_id==0)return;
	var _phone = $('#back-phone').val();
	var _email = $('#back-mail').val();
	var _addr = $('#back-addr').val();
	//校验输入
	var vali = true;
	if(!isMobil(_phone)){
		tip('phone-tip','格式不正确');
		vali = false;
	}
	if(!isEmail(_email)){
		tip('email-tip','格式不正确');
		vali = false;
	}
	if($.trim(_addr).length<1){
		tip('addr-tip','不能为空');
		vali = false;
	}
	if(!vali) return;
	var url = ctx+"/buyers/back";
	$.post(url,{id:_back_id,
				phone:_phone,
				mail:_email,
				address:_addr
	},function(data){
		 var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
		  datas = "{" + datas + "}";
		  var attr = eval('('+datas+')');
		if(attr.success){
			hideBack();
			doSearch();
		}else{
			alert("确认退换货失败");
		}
	});
}
function hideBack(){
	$('#back-div').hide();
	$('#phone-tip').hide();
	$('#email-tip').hide();
	$('#addr-tip').hide();
}
function showCheckConfirm(id){
		_check_id = id;
	$('#check-div').show();
}
function showCheckBack(id){
		_check_back_id = id;
	$('#check-back-div').show();
}
function hideCheckBack(id){
	$('#check-back-div').hide();
}
function hideCheckConfirm(){
	$('#check-div').hide();
}
var _pay_id = 0;
function showPay(id){
	_pay_id = id;
	$('.choice-type').show();	
}
function hidePay(){
	$('.choice-type').hide();
}
function  pay(){
	hidePay();
	$("#paying-div").show();
	var checkedPayType=$("input[type='radio']:checked").val();
	window.open(ctx+"/pay/platformPay?payType="+checkedPayType+"&orderId="+_pay_id);
}
function finishPay(){
	$("#paying-div").hide();
	doSearch();
}
cc.init(ctx,'codeImage','checkCode','请输入右图数字');
</script>
</body>
</html>