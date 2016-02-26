<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<c:set var="rootpath" value="${CONTEXT_PATH}/package"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商家购买视频套餐</title>
<link rel="stylesheet" href="${ctx}/source/css/partner/buy_package.css" />
<script type = "text/javascript" charset="utf-8" src="${ctx}/source/js/libs/jquery/jquery/jquery.js"></script>

<script type="text/javascript">

function choosePackage(id){
	
	var beforeDuration=$("#choosedId").attr("value");
	$("#choosedId").attr("value",id);
	$("#packageDiv"+beforeDuration).removeClass("pageckge-choice-current");
	$("#packageDiv"+id).addClass("pageckge-choice-current");	

}


function nextStep(){
	var choosedId=$("#choosedId").attr("value");
	var duration=$("#duration"+choosedId).val();
	window.location='${ctx}/package/buypackageconfirm?id='+choosedId+"&duration="+duration;
}


function chooseDuration(id){
	
	var selectDuartion=$("#select"+id).val();
	var packagePrice=$("#packagePrice"+id).val();
	var monthFlowPrice=$("#monthFlowPrice"+id).val();
	
	
	var totalPrice=(parseFloat(packagePrice)+parseFloat(monthFlowPrice))*selectDuartion;
	
	//alert(selectDuartion+","+ packagePrice+","+monthFlowPrice+","+totalPrice);
	
	  
	totalPrice=Math.round(totalPrice*100)/100;
	$("#duration"+id).attr("value",selectDuartion);
	$("#totalPrice"+id).text(totalPrice+" 元");
	
	
}

</script>
</head>

<body>

<div class="container-fluid">
<div class="sj_buy-video">
   <div class="sj-buy-videoCons">
     <img src="${ctx}/source/images/partner/package/jdt_1.jpg" width="885" height="62" />
     <ul class="tjt-font">
     	<li style="margin-left:65px;"><strong>套餐选择</strong></li>
        <li style="margin-left:295px;">套餐支付</li>
        <li style="margin-left:280px;">开启视频电商</li>
        <div class="clear"></div>
     </ul>
     
     <!--套餐选择-->
     <div class="package-Choice"><!--package-Choice Begin-->
       <div class="pageckge-choice-infors ">
         <ul>
         	<li style="color:#ffffff; font-size:18px;">项目内容</li>
            <li class="pageckge-Cons">可分享播放器</li>
            <li>每播放器搭载视频</li>
            <li class="pageckge-Cons">每播放器搭载商品</li>
            <li>免费赠送流量</li>
            <li class="pageckge-Cons">购买期限</li>
            <li  style="height:100px; line-height:105px;color:#cc0000;">套餐总价格</li>
         </ul>
       </div>
       
        
       <c:forEach items="${packageList}" varStatus="status" var="packageinfo">
       	<input type="hidden" id="packagePrice${packageinfo.id}" value="${packageinfo.price}">
       	<input type="hidden" id="monthFlowPrice${packageinfo.id}" value="${packageinfo.monthFlowPrice}">
       	<input type="hidden" id="duration${packageinfo.id}" value="${packageinfo.duration}">
            <c:choose>
            	<c:when test="${(currentPackageId==null)&&(status.index ==0)}">
            	<div class="pageckge-choice-infors pageckge-choice-current" id="packageDiv${packageinfo.id}" style="margin-left:${(status.index+1)*222}px;" onclick="choosePackage('${packageinfo.id}')">
           		 <input type="hidden" id="choosedId" value="${packageinfo.id}">
            	</c:when>
            	<c:otherwise>
            		<div class="pageckge-choice-infors ${currentPackageId==packageinfo.id?'pageckge-choice-current':''}" id="packageDiv${packageinfo.id}" style="margin-left:${(status.index+1)*222}px;" onclick="choosePackage('${packageinfo.id}')">
            		<c:if test="${ currentPackageId==packageinfo.id}">
            			 <input type="hidden" id="choosedId" value="${packageinfo.id}">
            		</c:if>
            	</c:otherwise>
            </c:choose>
	         <ul onclick="">
	         	<li style="color:#ffffff; font-size:18px;">${packageinfo.name }</li>
	            <li class="pageckge-Cons">${packageinfo.playerNum }个</li>
	            <li>${packageinfo.videoNum }个</li>
	            <li class="pageckge-Cons">${packageinfo.productNum }个</li>
	           
	            <li >${packageinfo.flowNum }T</li>
	            
	             <li  class="pageckge-Cons">
	             <select style="height: 30px;width: 100px;font-size:15px;" id="select${packageinfo.id}" onchange="chooseDuration('${packageinfo.id}')" >
				    <!--option value="1" ${packageinfo.duration==1?'selected':''}>1个月</option-->
				    <option value="6" ${packageinfo.duration==6?'selected':''}>6个月</option>
				    <option value="12" ${packageinfo.duration==12?'selected':''}>12个月</option>
				    <option value="24" ${packageinfo.duration==24?'selected':''}>24个月</option>
				    </select>
				    </li>
	            
	            <li id="totalPrice${packageinfo.id}"  style="height:100px; line-height:105px;color:#cc0000;">${packageinfo.totalPrice } 元</li>
	         </ul>
	       </div>
							    
	  </c:forEach>
       
     </div><!--package-Choice End-->       
     <div class="next-btns"><a href="javascript:nextStep();" class="naxt-btns-cons">下一步</a> 
      <div class="clear"></div> 
     </div>
     
   </div>
    
</div>
</div>
</body>
</html>
