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
/* var remainMonths = "${remainMonths}";
var remainFlows = "${remainflows}"; */
	function  pay(){
		hideConfirmPay();
		var duration=$("#duration").val();
		var checkedPayType=$("input[type='radio']:checked").val();
		$("#waitPayNotifyDiv").css("display","");
		var packageId=$("#packageId").val();
		var userId=$("#userId").val();
		window.open("${ctx}/package/pay?payType="+checkedPayType+"&id="+packageId+"&duration="+duration);
		
	}
	/* function confirmPay(){
		var confirmStr = "尊敬的用户，您好：您的当前套餐有效期还剩"+remainMonths+"个月，流量还剩"+remainFlows+"GB,是否确认放弃当前套餐内所有项目（包括额外流量包，额外商品、视频位）并购买新套餐？";
		if(confirm(confirmStr)){
			pay();
		}else{
			
		}
	} */
	function showConfirmPay(){
		$(".video-kuangs_new").show();
	}
	function hideConfirmPay(){
		$(".video-kuangs_new").hide();
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
<div class="container-fluid container-fluid2">
<div class="sj_buy-video">
   <div class="sj-buy-videoCons" >
     <img src="${ctx}/source/images/partner/package/jdt_2.png" width="100%" />

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
      
     <div class="package-Choice"   id="confirmPackageDiv"><!--package-Choice Begin-->
       <div class="pageckge-choice-infors " >
         <ul>
         	<li style="color:#ffffff; font-size:18px;">项目内容</li>
            <li class="pageckge-Cons">智能播放器数量（个）</li>
            <li>关联视频数量（个）</li>
            <li class="pageckge-Cons">关联商品数量（个）</li>
            <li>商家视频存储位（个）</li>
            <li class="pageckge-Cons">可提交审核视频数量（个）</li>
            <li>免费域名</li>
            <li class="pageckge-Cons">免费邮箱</li>
            <li>买家合作账号登录</li>
            <li class="pageckge-Cons">智能跟踪分析系统</li>
            <li>移动端店铺优化</li>
            <li class="pageckge-Cons">SEO优化服务</li>
            <li>播放流量（GB）</li>
            <li class="pageckge-Cons">套餐期限（月）</li>
            <li style="height:100px; line-height:105px;color:#cc0000;">价格（元）</li>
         </ul>
       </div>
       <div class="pageckge-choice-infors2" style="margin-left:20%;width:80%;">
         <ul>
         	<li style="color:#ffffff; font-size:18px;">${packageInfo.name }</li>
            <li class="pageckge-Cons">${packageInfo.playerNum }</li>
            <li>${packageInfo.videoNum }</li>
            <li class="pageckge-Cons">${packageInfo.productNum }</li>
            <!-- just show begin -->
				<c:choose>
					<c:when test="${sort==1 }">
						<li>1</li>
						<li class="pageckge-Cons">2</li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>×</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
					</c:when>
					<c:when test="${sort==2 }">
						<li>3</li>
						<li class="pageckge-Cons">8</li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>×</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
					</c:when>
					<c:when test="${sort==3 }">
						<li>1</li>
						<li class="pageckge-Cons">20</li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
					</c:when>
					<c:when test="${sort==4 }">
						<li>5</li>
						<li class="pageckge-Cons">40</li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li></c:when>
					<c:otherwise>
						<li>0</li>
						<li class="pageckge-Cons">0</li>
						<li><span>×</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
						<li><span>×</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
						<li><span>×</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
					</c:otherwise>
				</c:choose>
	           	<!-- just show end -->
            <li >${packageInfo.flowNum }</li>
            <li class="pageckge-Cons">${packageInfo.duration }</li>
            <li  style="height:100px; line-height:105px;color:#cc0000;">${packageInfo.totalPrice } 元</li>
            <input  type="hidden" id="packageId" value="${packageInfo.id}"/>
            <input type="hidden" id="userId" value="${userId}">
            <input type="hidden" id="duration" value="${packageInfo.duration}"/>
         </ul>
       </div>
     </div><!--package-Choice End-->
	<div class="video-kuangs_new" style="z-index: 2000;display:none;">
		<div class="video-titles-new">
			<span>套餐续约</span> <a href="javascript:hideConfirmPay()" class="close-icon"><img
				src="${ctx}/source/images/partner/package/close.jpg" width="33" height="33"></a>
		</div>
		<span style="margin-top: 20px; display: block; margin-left: 20px;">尊敬的用户，您好：</span>
		<div class="zf-fonts-new">
			
			<!--comm-cons Begin-->
			您的当前套餐有效期还剩<c:choose><c:when test="${remainMonths<=0}">不足 <em>1</em>个月</c:when><c:otherwise><em>${remainMonths}</em> 个月</c:otherwise></c:choose> ，流量还剩 <em>${remainflows}</em> GB，
			是否确认放弃当前套餐内所有项目（包括额外流量包，额外 商品，视频位）并购买新套餐？
		</div>
		<div style="width: 150px; margin: 20px auto;">
			<a href="javascript:pay();" class="video-btnsInfors-new">是</a><a href="javascript:hideConfirmPay();"
				class="video-btnsInfors-new">否</a>
		</div>
	</div>
	<div  class="choice-type-s">
      	<span>请选择支付方式</span>
      	<ul>
        	<li><input type="radio" name="payType"  value="1" checked="checked" style=" vertical-align:top;margin-top:20px;margin-right:10px;"><img src="${ctx}/source/images/partner/package/zfb.jpg" width="126" height="36"></li>
        	<li><input type="radio" name="payType" value="3" style=" vertical-align:top;margin-top:20px;margin-right:10px;"><img src="${ctx}/source/images/partner/package/weibo.jpg" width="126" height="36"></li>
     	</ul>
     </div>
	      <div class="next-btns" style="width:350px;margin:15px auto; " id="confirmPackageDivButton">
	      <a href="${ctx }/package/buypackagelist?currentPackageId=${packageInfo.id}" class="black-btns-cons">返回上一层</a>
	      <a href="javascript:<c:choose><c:when test='${showCheckWin}'>showConfirmPay()</c:when><c:otherwise>pay()</c:otherwise></c:choose>" class="naxt-btns-cons">确认套餐并支付</a>
	      <div class="clear"></div>  
     	  </div>
    	</div> 
     </div>
    <div class="clear" style="padding-bottom:30px;">&nbsp;</div>  
   </div> 
</div>
</div>
</body>
</html>
