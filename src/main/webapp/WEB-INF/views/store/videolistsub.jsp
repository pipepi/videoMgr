<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<div class="sort-title">
	<a href="javascript:void(0)" onclick="$('#video-list').toggle();" class="close-icon"></a>
</div>
<div class="comm-cons">
	<!--comm-cons Begin-->
	<ul class="comm-consInfors">
		<c:forEach var="v" items="${list }" varStatus="status" >
			<li ${(status.index+1)%3==0?'style="margin-right:0;"':'' } ><img src="${v.imgMin }" width="210" height="118" /> 
				<span class="product-nameInfor">${v.name}</span>
				<div>
					<input type="checkbox" class="checkbox" value = "${v.id }" onchange= "checkVideo(this)"/>添加视频到此播放器
				</div>
			</li>
		</c:forEach>
	</ul>
	<div style="text-align: center; ">
		已选择添加<em id="checkednum">0</em>个视频
	</div>
	<div style="width: 216px; margin: 0 auto; margin-top: 20px;">
		<a href="javascript:saveVideoLinks()" class="video-btnsInfor">保存</a>
		<a href="javascript:recheckVideo()" class="video-btnsInfors">重选</a>
	</div>
</div>
<!--comm-cons End-->
