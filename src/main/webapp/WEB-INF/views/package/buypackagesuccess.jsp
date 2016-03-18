<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>购买视频</title>
<link rel="stylesheet" href="${ctx}/source/css/partner/buy_package.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>
</head>

<body>
<div class="container-fluid">
<div class="sj_buy-video">
   <div class="sj-buy-videoCons">
     <img src="${ctx}/source/images/partner/package/jdt_3.jpg" width="885" height="62" />
     <ul class="tjt-font">
     	<li style="margin-left:65px;"><strong>套餐选择</strong></li>
        <li style="margin-left:295px;"><strong>套餐支付</strong></li>
        <li style="margin-left:280px;"><strong>开启视频电商</strong></li>
        <div class="clear"></div>
     </ul>
     
     <!--支付成功-->
     <div class="sj-pay">
       <span class="sj-pay-font">支付成功</span>
       <span>请按照以下步骤，完成您的视频电商</span>
     </div>
     
     <!--步骤-->
     <div class="sj-buzou">
       <ul>
       	<li><div class="sj-buzou-cons"><img src="${ctx}/source/images/partner/package/bz_01.jpg" width="204"   height="271"/></div>
        <img src="${ctx}/source/images/partner/package/1_1.jpg" width="115" height="28" /></li>
        <li><div class="sj-buzou-cons"><img src="${ctx}/source/images/partner/package/bz_02.jpg" width="204"   height="271"/></div>
        <img src="${ctx}/source/images/partner/package/1_2.jpg" width="93" height="27"/></li>
        <li><div class="sj-buzou-cons"><img src="${ctx}/source/images/partner/package/bz_03.jpg" width="204"   height="271"/></div>
        <img src="${ctx}/source/images/partner/package/1_3.jpg" width="92" height="27" /></li>
        <li style="margin-right:0"><div class="sj-buzou-cons"><img src="${ctx}/source/images/partner/package/bz_04.jpg" width="204"   height="271"/></div>
        <img src="${ctx}/source/images/partner/package/1_4.jpg" width="58" height="22"/></li>
       </ul>
     </div> 
	<input type="hidden" id="IndexUrl" value="${partnerIndexUrl}">
     <div class="next-btns" style="width:350px; "><a href="javascript:toHelpPage()" class="ck-infors-btns">查看详情说明</a><a href="javascript:goVideoManage()" class="naxt-btns-cons">我去开启</a>
      <div class="clear"></div>  
     </div>
   
   </div>
</div>
</div>


<script type="text/javascript">
	function goVideoManage(){
		   //var IndexUrl=$("#IndexUrl").val();
		   //top.window.location.href=partner_ctx+"/SellerAdmin?url="+ctx+"/video/async/page&tar="+ctx+"/video/listhm";
			window.top.location="${ctx}/store/videolist";
		    
	}
	function toHelpPage(){
		
		top.window.location.href="${partnerIndexUrl}/Article/Category";
	}

</script>

</body>
</html>
