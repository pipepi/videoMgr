<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<div class="sort-title">
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
<div class="comm-cons">
	<!--comm-cons Begin-->
	<ul style="overflow: hidden; height: 473px; overflow: auto;">
		<c:forEach var="p" items="${list }" varStatus="status" >
			<li ${(status.index+1)%3==0?'style="margin-right:0;"':'' } >
				<img src="${p.imagePath }" width="210" height="118" title="发布时间：${p.addedDate }  价格：${p.marketPrice }"/> 
				<span style="height:22px;width: 215px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;" title="${p.productDesc }">${p.productName}</span>
				<div>
					<input type="checkbox" name = "input-product" class="checkbox" value = "${p.id }" onchange= "checkProduct(this)"/>添加商品到此播放器
				</div>
			</li>
		</c:forEach>
	</ul>
	<div style="text-align: center; margin-top: 20px;">
		已选择添加<em id="checkednum2">0</em>个商品
	</div>
	<div style="width: 216px; margin: 0 auto; margin-top: 20px;">
		<a href="javascript:saveProductLinks()" class="video-btnsInfor">保存</a>
		<a href="javascript:recheckProduct()" class="video-btnsInfors">重选</a>
		<div class="clear"></div>
	</div>
	<div class="list-page" style="margin-top:20px;">
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
