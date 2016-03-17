<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家购买流量</title>
<link rel="stylesheet" href="${ctx}/source/css/partner/buy_package.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>

<script type="text/javascript">

function chooseFlow(id,totalPrice){
	
	var beforeDuration=$("#choosedId").attr("value");
	$("#choosedId").attr("value",id);
	$("#flowli"+beforeDuration).removeClass("current");
	$("#flowli"+id).addClass("current");	
	$("#currentPrice").text(totalPrice);

}



function  pay(){
	
	var checkedPayType=$("input[type='radio']:checked").val();
	$("#waitPayNotifyDiv").css("display","");
	var packageId=$("#choosedId").val();
	window.open("${ctx}/package/pay?payType="+checkedPayType+"&id="+packageId+"&duration="+1);
	
}

function confirmFlow(){
	
	$("#chooseFlowDiv").css("display","none");
	
	$("#payDiv").css("display","");
}




function backChoose(){
	$("#chooseFlowDiv").css("display","");
	
	$("#payDiv").css("display","none");
}



function cancelPay(){
	$("#waitPayNotifyDiv").css("display","none");
	
}

function finishPay(){
	
	
	var packageId=$("#packageId").val();

	var url = "${ctx}/package/ispay";
	//console.log(url);
	$.post(url,{packageId:packageId},function(data){
		var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
  		  datas = "{" + datas + "}";
  		  var attr = eval('('+datas+')');
  		  //console.log(attr);
  		  if(attr.success){
  			  
  			$("#chooseFlowDiv").css("display","none");
  			$("#payDiv").css("display","none");
  			$("#waitPayNotifyDiv").css("display","none");
  			$("#paySuccessDiv").css("display","");
  			
  		  }else{
  			  $("#payInfo").addClass("zf-fonts");
  			  $("#payInfo").text("未完成支付，请支付");
  		  }
	});
	
}

function packageRecord(){
	window.location='${ctx}/package/recordpage';
}

</script>

</head>

<body>
<div class="container-fluid">
      <div class="video-add">
         <div class="video-add-title">
         	<ul>
              <li class="current"><a href="javascript:void(0)">购买流量</a></li>
              <li><a href="javascript:packageRecord();">购买历史查询</a></li>
              
            </ul>
         </div>
      </div>
        
 <div class="sj_buy-video" style="padding-top:20px;">
   
   <!--数据流量选择配置-->
      <div class="data-container"  id="chooseFlowDiv">
        <div class="data-sjll-font"><span>数据流量</span>选择配置:</div>
        <div class="data-peizhi">
        	<ul>
        	 <c:forEach items="${flowList}" varStatus="status" var="flowInfo">
            	<li id="flowli${flowInfo.id}" class="${currentFlow.id==flowInfo.id?'current':''}" onclick="chooseFlow('${flowInfo.id}','${flowInfo.totalPrice}')">${flowInfo.flowNum}GB ¥${flowInfo.totalPrice}</li>
            		<c:if test="${ currentFlow.id==flowInfo.id}">
            			 <input type="hidden" id="choosedId" value="${flowInfo.id}">
            		</c:if>
        	 </c:forEach>
           </ul>
        </div>
        <div class="data-money">您需要结算金额为 ￥<span id="currentPrice">${currentFlow.totalPrice}</span></div>
        <div class="next-btns-zf" style="width:120px; margin-top:20px;"><a href="javascript:confirmFlow()" class="naxt-btns-cons" style="width:100px;">结算</a></div>
      </div>
      
            <!--支付平台-->
      <div class="video-kuangs"  style="z-index:2000;left:30%;display: none" id="waitPayNotifyDiv">
           <div class="video-titles">
                   <span>支付平台</span>
                  <a href="javascript:void(0)" class="close-icon"></a>
                 </div>
                 <div id="payInfo" class="zf-fonts"><!--comm-cons Begin-->
                	请您在新打开的支付平台页面进行支付，支付完成前请不要关闭窗口
                </div>  
                   <div style="width:216px;margin-top:50px;margin-left:135px;">
                     <a href="javascript:cancelPay()" class="video-btnsInfors">取消</a>
                     <a href="javascript:finishPay()" class="video-btnsInfors-s">已支付完成</a>
                    </div>
          </div><!--comm-cons End-->
      </div>
      
      
       <!--支付成功-->
       <div  id="paySuccessDiv" class="sj_buy-video" style="padding-top:20px; padding-bottom:50px;display: none">
		 <div class="lc-zfgg"><span class="sj-pay-font">支付成功</span></div>
		 <div class="next-btns-zf" style="width:100px;margin-top:50px;"><a href="${ctx}/store/videolist" class="black-btns-cons" style="width:100px; ">返回</a>
		      <div class="clear"></div>  
		     </div>
		     
		     <div style="text-align:center;"><a href="" class="col999">点击返回视频电商控制台</a></div>
		 </div>
    

     <!--请选择支付方式-->
     <div class="choice-type2" style="display:none" id ="payDiv">
        <div style="margin:30px 0 0 70px;color:#333333; font-size:16px;">请选择支付方式</div>
        <ul>
           <li><input type="radio" name="payType"  value="1" checked="checked" style=" vertical-align:top;margin-top:20px;margin-right:10px;"/><img src="${ctx}/source/images/partner/package/zfb.jpg" width="126" height="36" /></li>
           <li><input type="radio"  type="radio" name="payType" value="3"   style=" vertical-align:top;margin-top:20px;margin-right:10px;"/><img src="${ctx}/source/images/partner/package/weibo.jpg" width="126" height="36" /></li>
        </ul>
        <div class="next-btns-zf" style="width:240px;"><a href="javascript:pay()" class="naxt-btns-cons" style="width:100px;">支付</a><a href="javascript:backChoose()" class="black-btns-cons" style="width:100px; margin-left:10px;">返回</a></div>    
     </div>
         <div class="clear" style="padding-bottom:30px;">&nbsp;</div>  
   </div> 
</div>


</body>
</html>
