<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家购买视频</title>
<link rel="stylesheet" href="${ctx}/source/css/partner/buy_package.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>

<script type="text/javascript">
var ctx = "${ctx}";

	function  confirm(){
		$("#confirmPackageDiv").css("display","none");
		$("#confirmPackageDivButton").css("display","none");
		
		$("#choosePayTypeDiv").css("display","");
	}
	
	function  pay(){
		
		var duration=$("#duration").val();
		var checkedPayType=$("input[type='radio']:checked").val();
		$("#waitPayNotifyDiv").css("display","");
		var packageId=$("#packageId").val();
		var userId=$("#userId").val();
		window.open("${ctx}/package/pay?payType="+checkedPayType+"&id="+packageId+"&duration="+duration);
		
	}
	
	function cancelPay(){
		$("#waitPayNotifyDiv").css("display","none");
		
	}
	
	function finishPay(){
		
		var userId=$("#userId").val();
		var packageId=$("#packageId").val();

		var url = ctx+"/package/ispay";
		//console.log(url);
		$.post(url,{userId:userId,packageId:packageId},function(data){
			var datas = data.substring(data.indexOf('{')+1,data.lastIndexOf('}'));
	  		  datas = "{" + datas + "}";
	  		  var attr = eval('('+datas+')');
	  		  console.log(attr);
	  		  if(attr.success){
	  			window.location='${ctx}/package/finishpay?packageId=${packageInfo.id }';
	  		  }else{
	  			  $("#payInfo").addClass("zf-fonts");
	  			  $("#payInfo").text("未完成支付，请支付");
	  		  }
		});
		
	}
	
	
	
</script>
</head>

<body>
<div class="container-fluid">
<div class="sj_buy-video">
   <div class="sj-buy-videoCons">
     <img src="${ctx}/source/images/partner/package/jdt_2.jpg" width="885" height="62" />
     <ul class="tjt-font">
     	<li style="margin-left:65px;">套餐选择</li>
        <li style="margin-left:295px;"><strong>套餐支付</strong></li>
        <li style="margin-left:280px;">开启视频电商</li>
        <div class="clear"/>
     </ul>
     <!--套餐选择-->
    <div>
     <!--支付平台-->
      <div class="video-kuangs"  style="z-index:1000;display: none" id="waitPayNotifyDiv">
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
      
     <div class="package-Choice"  id="confirmPackageDiv"><!--package-Choice Begin-->
       <div class="pageckge-choice-infors ">
         <ul>
         	<li style="color:#ffffff; font-size:18px;">项目内容</li>
            <li class="pageckge-Cons">可分享播放器</li>
            <li>每播放器搭载视频</li>
            <li class="pageckge-Cons">每播放器搭载商品</li>
            <li>免费赠送流量</li>
            <li class="pageckge-Cons">购买期限</li>
            <li style="height:100px; line-height:105px;color:#cc0000;">套餐总价格</li>
         </ul>
       </div>
       <div class="pageckge-choice-infors2" style="margin-left:222px; width:666px;">
         <ul>
         	<li style="color:#ffffff; font-size:18px;">${packageInfo.name }</li>
            <li class="pageckge-Cons">${packageInfo.playerNum }个</li>
            <li>${packageInfo.videoNum }个</li>
            <li class="pageckge-Cons">${packageInfo.productNum }个</li>
            
            <li >${packageInfo.flowNum }T</li>
            <li class="pageckge-Cons">${packageInfo.duration }个月</li>
            <li  style="height:100px; line-height:105px;color:#cc0000;">${packageInfo.totalPrice } 元</li>
            <input  type="hidden" id="packageId" value="${packageInfo.id}"/>
            <input type="hidden" id="userId" value="${userId}">
            <input type="hidden" id="duration" value="${packageInfo.duration}"/>
         </ul>
       </div>
     </div><!--package-Choice End-->
      <div class="next-btns" style="width:350px; " id="confirmPackageDivButton">
      <a href="${ctx }/package/buypackagelist?currentPackageId=${packageInfo.id}" class="black-btns-cons">返回上一层</a>
      <a href="javascript:confirm()" class="naxt-btns-cons">确认套餐并支付</a>
      <div class="clear"></div>  
     </div>
    </div> 
     </div>
     
     <!--请选择支付方式-->
     <div id="choosePayTypeDiv" class="choice-type" style="display: none">
    
        <div style="margin:30px 0 0 70px;color:#333333; font-size:16px;">请选择支付方式</div>
        <ul>
           <li><input type="radio" name="payType"  value="1" checked="checked" style=" vertical-align:top;margin-top:20px;margin-right:10px;"/><img src="${ctx}/source/images/partner/package/zfb.jpg" width="126" height="36" /></li>
           <li><input type="radio" name="payType" value="3"   style=" vertical-align:top;margin-top:20px;margin-right:10px;"/><img src="${ctx}/source/images/partner/package/weibo.jpg" width="126" height="36" /></li>
        </ul>
        <div class="next-btns-zf"><a href="javascript:pay()" class="naxt-btns-cons" style="width:100px;">支付</a></div>    
     </div>
         <div class="clear" style="padding-bottom:30px;">&nbsp;</div>  
   </div> 
</div>
</div>
</body>
</html>
