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
var _selected_sort_id = 1;//选择套餐id
var _selected_store_num = 0;//选择套餐播放器数量
var _selected_pNum_perStore = 0;//选择套餐每个播放器可关联产品数量

var _had_store_num = parseInt("${storeNum}");//拥有播放器数量
var _had_linkproduct_count = parseInt("${linkPNumCount}");//已关联产品总数量
var _had_linkproduct_str = "${linkPNumListStr}";//已关联产品数量详情字符串

function checkAnyGt(){
	var _had_linkproduct_arr = _had_linkproduct_str.split('\\,');
	if(_had_linkproduct_arr.length>0){
		for(var i=0;i<_had_linkproduct_arr.length;i++){
			if(_selected_pNum_perStore>_had_linkproduct_arr[i]) return false;
		}
	}
	return true;
}
$(function(){
	var $this = $('.pageckge-choice-current');
	_selected_store_num = parseInt($this.attr('attrStoreNum'));
	_selected_pNum_perStore = parseInt($this.attr('attrProductNum'));
	log();
});
function log(){
	console.log(_selected_store_num);
	console.log(_selected_pNum_perStore);
	
}
function choosePackage(id,selectedSortId,thisvar){
	_selected_sort_id = selectedSortId;
	var $this = $(thisvar);
	_selected_store_num = parseInt($this.attr('attrStoreNum'));
	_selected_pNum_perStore = parseInt($this.attr('attrProductNum'));
	log();
	var beforeDuration=$("#choosedId").attr("value");
	$("#choosedId").attr("value",id);
	$("#packageDiv"+beforeDuration).removeClass("pageckge-choice-current");
	$("#packageDiv"+id).addClass("pageckge-choice-current");	

}

function check(){
	if(_had_store_num>_selected_store_num||_had_linkproduct_count>_selected_pNum_perStore*_selected_store_num||!checkAnyGt()){
		//tip
		var tip= "尊敬的用户，您好：您当前已建立的智能播放器数量为"+_had_store_num+"个，每播放器关联的商品数量分别为"+_had_linkproduct_str+"个;\n"
		+"您选择的新套餐的智能播放器数量为"+_selected_store_num+"个，每个智能播放器可关联的商品数量为"+_selected_pNum_perStore+"个;\n"
		+"请先在您的视频电商控制台删除多余的播放器或取消关联多余的商品再进行套餐续约，谢谢！";
		if(comfirm(tip)){
			window.location = '${ctx}/store/videolist'	
		}else{
		}
	}else{
		nextStep();
	}
}

function nextStep(){
	var choosedId=$("#choosedId").attr("value");
	var duration=$("#duration"+choosedId).val();
	//console.log("_selected_sort_id"+_selected_sort_id);
	window.location='${ctx}/package/buypackageconfirm?id='+choosedId+"&duration="+duration+"&sort="+_selected_sort_id;
}



/* function chooseDuration(id){
	
	var selectDuartion=$("#select"+id).val();
	var packagePrice=$("#packagePrice"+id).val();
	var monthFlowPrice=$("#monthFlowPrice"+id).val();
	
	
	var totalPrice=(parseFloat(packagePrice)+parseFloat(monthFlowPrice))*selectDuartion;
	
	//alert(selectDuartion+","+ packagePrice+","+monthFlowPrice+","+totalPrice);
	
	  
	totalPrice=Math.round(totalPrice*100)/100;
	$("#duration"+id).attr("value",selectDuartion);
	$("#totalPrice"+id).text(totalPrice+" 元");
	
	
} */

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
            <li  style="height:100px; line-height:105px;color:#cc0000;">套餐价格（元）</li>
         </ul>
       </div>
       
        
       <c:forEach items="${packageList}" varStatus="status" var="packageinfo">
            <c:choose>
            	<c:when test="${(currentPackageId==null)&&(status.index ==0)}">
            	<div class="pageckge-choice-infors pageckge-choice-current" id="packageDiv${packageinfo.id}" style="margin-left:${(status.index+1)*178}px;" attrStoreNum = "${packageinfo.playerNum }" attrProductNum="${packageinfo.productNum }" onclick="choosePackage('${packageinfo.id}',${status.index+1 },this)">
           		 <input type="hidden" id="choosedId" value="${packageinfo.id}">
            	</c:when>
            	<c:otherwise>
            		<div class="pageckge-choice-infors ${currentPackageId==packageinfo.id?'pageckge-choice-current':''}" id="packageDiv${packageinfo.id}" style="margin-left:${(status.index+1)*178}px;" attrStoreNum = "${packageinfo.playerNum }" attrProductNum="${packageinfo.productNum }" onclick="choosePackage('${packageinfo.id}',${status.index+1 },this)">
            		<c:if test="${ currentPackageId==packageinfo.id}">
            			 <input type="hidden" id="choosedId" value="${packageinfo.id}">
            		</c:if>
            	</c:otherwise>
            </c:choose>
	         <ul onclick="">
	         	<li style="color:#ffffff; font-size:18px;">${packageinfo.name }</li>
	            <li class="pageckge-Cons">${packageinfo.playerNum }</li>
	            <li>${packageinfo.videoNum }</li>
	            <li class="pageckge-Cons">${packageinfo.productNum }</li>
	           	<!-- just show begin -->
				<c:choose>
					<c:when test="${status.index==0 }">
						<li>1</li>
						<li class="pageckge-Cons">2</li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>×</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
					</c:when>
					<c:when test="${status.index==1 }">
						<li>3</li>
						<li class="pageckge-Cons">8</li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>×</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>×</span></li>
					</c:when>
					<c:when test="${status.index==2 }">
						<li>1</li>
						<li class="pageckge-Cons">20</li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
						<li><span>√</span></li>
						<li class="pageckge-Cons"><span>√</span></li>
					</c:when>
					<c:when test="${status.index==3 }">
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
	            <li >${packageinfo.flowNum }</li>
	            
	             <li  class="pageckge-Cons"> ${packageinfo.duration }
	             <!-- 
	             	<select style="height: 30px;width: 100px;font-size:15px;" id="select${packageinfo.id}" onchange="chooseDuration('${packageinfo.id}')" >
					    <option value="1" ${packageinfo.duration==1?'selected':''}>1</option>
					    <option value="6" ${packageinfo.duration==6?'selected':''}>6</option>
					    <option value="12" ${packageinfo.duration==12?'selected':''}>12</option>
					    <option value="24" ${packageinfo.duration==24?'selected':''}>24</option>
				    </select>
				 -->
				    </li>
	            
	            <li id="totalPrice${packageinfo.id}"  style="height:100px; line-height:105px;color:#cc0000;">${packageinfo.totalPrice } </li>
	         </ul>
	       </div>
							    
	  </c:forEach>
       
     </div><!--package-Choice End-->       
     <div class="next-btns"><a href="javascript:check();" class="naxt-btns-cons">下一步</a> 
      <div class="clear"></div> 
     </div>
     
   </div>
    
</div>
</div>
</body>
</html>
