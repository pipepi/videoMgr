<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>

   <!-- 关联视频 -->
   <div class="comm-cons-title">
     <div class="comm-cons-fonts">关联视频</div>
     <div class="sort-title">
	<a href="javascript:void(0)" onclick="$('#video-list').toggle();" class="close-icon"></a>
</div>
   </div>
   

<div class="comm-cons">

   <div class="comm-cons-video"  style="min-height:570px;">
  
   <div class="comm-cons-video-xi">
    <ul class="comm-cons-video-uls comm-cons-videos-titles">
    <li class="comm-cons-video-tabs1">关联</li>
    <li class="comm-cons-video-tabs2">视频图片</li>
    <li class="comm-cons-video-tabs3">视频名称</li>
    <li class="comm-cons-video-tabs4 comm-cons-video-time" onclick = "sort('updatetime','${sortTimeType}')" >发布时间 
    <div class="jiantou-paixu"><img src="../source/images/aztimg/jiantou_tan<c:if test='${sortTimeType=="desc" }'>2</c:if>.png" /></div>
    </li>
    <li class="comm-cons-video-tabs5 comm-cons-video-time" onclick = "sort('cnum','${sortCnumType}')" >播放次数
    <div class="jiantou-paixus"><img src="../source/images/aztimg/jiantou_tan<c:if test='${sortCnumType=="desc" }'>2</c:if>.png" /></div>
    </li>
   </ul>
  
   <c:forEach var="v" items="${list }" varStatus="status" >
	   <ul class="comm-cons-video-uls comm-cons-video-uls2">
	     <li class="comm-cons-video-tabs1 liHeis" ><input type="checkbox"  value = "${v.id }" onchange= "checkVideo(this)" /></li>
	     <li class="comm-cons-video-tabs2"><img src="${v.imgMin }"/></li>
	     <li class="comm-cons-video-tabs3 liHeis">${v.name}</li>
	     <li class="comm-cons-video-tabs4 liHeis"><fmt:formatDate value="${v.createTime }" pattern="yyyy-MM-dd mm:ss"/></li>
	     <li class="comm-cons-video-tabs5 liHeis">${v.videoCnum+v.h5VideoCnum }次</li>
	     <c:if test="${v.checkState!=2 }">
		     <div class="current1"><img src="../source/images/aztimg/wgxx_font.png" style="margin-top:60px;"  width="109" height="22"/></div>  <!-- 违规下架 -->
	     </c:if>
	   </ul>
   </c:forEach>
   </div>
     <div class="clear"></div>
   </div>
   <div class="tankuang-page" >
	  <em class="hoColor">${turn.page }</em>/<em>${turn.pageCount }</em> 
	  <a href="javascript:goVideo(${turn.prevPage })">上一页</a> 
	  <c:choose>
 		<c:when test="${turn.page==turn.pageCount }">
			<a href="javascript:void(0)" class="current">下一页</a>
 		</c:when>
 		<c:otherwise>
			 <a href="javascript:goVideo(${turn.nextPage })">下一页</a>
 		</c:otherwise>
 	</c:choose>
	</div>
	<!--comm-cons Begin
	<ul class="comm-consInfors" style="display:none">
		<c:forEach var="v" items="${list }" varStatus="status" >
			<li ${(status.index+1)%3==0?'style="margin-right:0;"':'' } ><img src="${v.imgMin }" width="210" height="118" /> 
				<span class="product-nameInfor">${v.name}</span>
				<div>
					<input type="checkbox" class="checkbox" value = "${v.id }" onchange= "checkVideo(this)"/>添加视频到此播放器
				</div>
			</li>
		</c:forEach>
	</ul>-->
	<div style="text-align: center; display:none; ">
		已选择添加<em id="checkednum">0</em>个视频
	</div>
	
	<div style="width: 216px; margin: 0 auto; margin-top: 20px;">
		<a href="javascript:saveVideoLinks()" class="video-btnsInfor">保存</a>
		<a href="javascript:void(0)" onclick="$('#video-list').toggle();" class="video-btnsInfors">取消</a>
		<a style="display:none;" href="javascript:recheckVideo()" class="video-btnsInfors">重选</a>
		<input type="hidden" name="videoNum" id="videoNum" value="${videoNum}" />
	</div>
</div>
<!--comm-cons End-->
