<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<div class="sort-title" style="display:none;">
	<ul class="sort-cons">
   	 <li>排序：</li>
   	 <li class="curpo ${orderby==0?'curcolh':'' }" style="margin-right:20px;margin-left:0px;"
   	 		onclick="loadProductListOrdered('time','${ordertype==0?'asc':'desc' }')">时间 
   	 		<div class="${(orderby==0&&ordertype==0)?'jt-img':'jt-img-move' }"></div></li>
   	 <li class="curpo ${orderby==0?'':'curcolh' }" 
   	 		onclick="loadProductListOrdered('price','${ordertype==0?'asc':'desc' }')">价格 
   	 		<div class="${(orderby>0&&ordertype==0)?'jt-img2':'jt-img2-move' }"></div></li>
     <!-- <li>库存</li> -->
   </ul>
	<a href="javascript:void(0)" onclick="$('#product-list').toggle();" class="close-icon"></a>
</div>

   <div class="comm-cons-title">
     <div class="comm-cons-fonts">关联商品</div>
     <div class="sort-title">
	<a href="javascript:void(0)" onclick="$('#product-list').toggle();" class="close-icon"></a>
</div>
     <div class="comm-product-name">
                   商品名称
     <input type="text" class="comm-pro-nameBtns" id="productKey"/>      
     <img src="../source/images/aztimg/sreach.png" width="23" height="23" onclick="searchByKey()"/> 
     </div>
     
   </div>
   
<div class="comm-cons">

<div class="comm-cons-video" style="min-height:570px;">
  
   <div class="comm-cons-video-xi">
    <ul class="comm-cons-video-uls3 comm-cons-video-titles">
    <li class="comm-cons-video-tabs1">关联</li>
    <li class="comm-cons-video-tabs6">商品图片</li>
    <li class="comm-cons-video-tabs7">商品名称</li>
    <li class="comm-cons-video-tabs4 comm-cons-video-time" onclick="loadProductListOrdered('time','${ordertype==0?'asc':'desc' }')">发布时间 
    <div class="jiantou-paixu"><img src="../source/images/aztimg/jiantou_tan${(orderby==0&&ordertype==0)?'':'2' }.png"/></div>
    </li>
    <li class="comm-cons-video-tabs5 comm-cons-video-time" onclick="loadProductListOrdered('price','${ordertype==0?'asc':'desc' }')">商品价格
    <div class="jiantou-paixus"><img src="../source/images/aztimg/jiantou_tan${(orderby>0&&ordertype==0)?'':'2' }.png"/></div>
    </li>
   </ul>
   <c:forEach var="p" items="${list }" varStatus="status" >
	    <ul class="comm-cons-video-uls3 comm-cons-video-uls2">
	     <li class="comm-cons-video-tabs1 liHeis" ><input type="checkbox"  name = "input-product" attrAuditState="${p.auditState }"  value = "${p.id }" onchange= "checkProduct(this)"/></li>
	     <li class="comm-cons-video-tabs6"><img width="42" height="42" src="${p.imagePath }"/></li>
	     <li class="comm-cons-video-tabs7 col6s liHeis">${p.productName}</li>
	     <li class="comm-cons-video-tabs4 col6s liHeis">${p.addedDate }</li>
	     <li class="comm-cons-video-tabs5 col6s liHeis">${p.marketPrice }</li>
	     <c:if test="${p.auditState!=2 }">
	     <div class="current1"><img src="../source/images/aztimg/wgxx_font.png" width="109" height="22" class="margintops"/></div> <!-- 违规下架 -->
	     </c:if>
	   </ul>
   </c:forEach>
   </div>
</div>
	<div style="text-align: center; margin-top: 20px;">
		单个播放器可关联 ${canLinkNum} 个商品， 已选择添加<em id="checkednum2" class="checkNumbers">0</em>商品
	</div>
	<div class="tankuang-page" >
	  <em class="hoColor">${turn.page }</em>/<em>${turn.pageCount }</em> 
	  <a href="javascript:go(${turn.prevPage })">上一页</a> 
	  <c:choose>
 		<c:when test="${turn.page==turn.pageCount }">
			<a href="javascript:void(0)" class="current">下一页</a>
 		</c:when>
 		<c:otherwise>
			 <a href="javascript:go(${turn.nextPage })">下一页</a>
 		</c:otherwise>
 	</c:choose>
	</div>
	<div style="width: 216px; margin: 0 auto; margin-top: 20px;">
		<a href="javascript:saveProductLinks()" class="video-btnsInfor">保存</a>
		<a href="javascript:void(0)" onclick="$('#product-list').toggle();" class="video-btnsInfors">取消</a>
		<a href="javascript:recheckProduct()" class="video-btnsInfors" style="display:none;">重选</a>
		<div class="clear"></div>
	</div>
	<div class="list-page" style="margin-top:20px; display: none;">
	<i>${turn.page }/${turn.pageCount }页 </i>
	<a href="javascript:go(${turn.prevPage })" class="pre-page">上一页</a>
	<c:if test="${turn.page-2>0}">
		<a href="javascript:go(${turn.page-2 })">${turn.page-2 }</a>
	</c:if>
	<c:if test="${turn.page-1>0}">
		<a href="javascript:go(${turn.page-1 })">${turn.page-1 }</a>
	</c:if>
	<a href="javascript:void(0)" class="current">${turn.page}</a>
	<c:if test="${turn.page+1<=turn.pageCount}">
		<a href="javascript:go(${turn.page+1 })">${turn.page+1 }</a>
	</c:if>
	<c:if test="${turn.page+2<=turn.pageCount}">
		<a href="javascript:go(${turn.page+2 })">${turn.page+2 }</a>
	</c:if>
	<c:choose>
 		<c:when test="${turn.page==turn.pageCount }">
			<a href="javascript:void(0)" class="current">下一页</a>
 		</c:when>
 		<c:otherwise>
			 <a href="javascript:go(${turn.nextPage })" class="next-page">下一页</a>
 		</c:otherwise>
 	</c:choose>
	</div>
</div>
<!--comm-cons End-->
<script type="text/javascript">
$('#productKey').keypress(function(e){
	var key = e.which;
	if(key==13){
		searchByKey();
	}
});
</script>
